using Domain.DTO.Message;
using Domain.DTO.Vonage;
using Domain.Entity.Redis;
using Domain.Enum;
using Domain.Interface.Application;
using Domain.Interface.Repository;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using System.Net;
using Vonage;
using Vonage.Messages;
using Vonage.Messages.WhatsApp;
using Vonage.Request;

namespace Whatsapp.VonageAPI.Services.Utils
{
    public class SendMessage : ISendMessage
    {
        private readonly IConfiguration _configuration;
        private readonly ILogger<SendMessage> _logger;
        private readonly IMessageManager _messageManager;
        private readonly IHttpFactoryRequests _httpRequests;

        public SendMessage(IHttpFactoryRequests httpRequests, IConfiguration configuration, ILogger<SendMessage> logger, IMessageManager messageManager)
        {
            _configuration = configuration;
            _logger = logger;
            _messageManager = messageManager;
            _httpRequests = httpRequests;
        }

        public void SendMessageToUser(List<ConstructMessage> messages, string userNumber)
        {
            try
            {
                string queueName = _messageManager.GetQueueName(userNumber);
                _logger.LogDebug("Registrando no redis para a fila {@queueName} os seguintes documentos {@messages}", queueName, messages);
                List<QueueMessage> currentMessages = new List<QueueMessage>();

                foreach (ConstructMessage message in messages)
                {
                    currentMessages.Add(new QueueMessage
                    {
                        UserNumber = userNumber,
                        Tries = 0,
                        Message = message
                    });
                }

                if (!_messageManager.QueueExist(queueName))
                    SendSingleMessage(currentMessages[0]).Wait();

                _messageManager.Add(queueName, currentMessages);
            }
            catch (Exception)
            {
                throw;
            }
        }
        
        private async Task SendProdMessage(QueueMessage message)
        {
            try
            {
                if (message == null)
                    return;

                string systemNumber = _configuration.GetValue<string>("Settings:Vonage:SystemNumber");
                string appId = _configuration.GetValue<string>("Settings:Vonage:AppId");
                string privateKey = _configuration.GetValue<string>("Settings:Vonage:PrivateKey");

                Credentials credentials = Credentials.FromAppIdAndPrivateKey(appId, privateKey);
                VonageClient vonageClient = new VonageClient(credentials);

                var request = GenerateRequest(message.Message, message.UserNumber, systemNumber);
                _logger.LogDebug("O payload da Vonage para {@current} é {@request}", message, request);
                MessagesResponse response = await vonageClient.MessagesClient.SendAsync(request);
                _logger.LogDebug("O response da Vonage para {@request} é {@response}", request, response);

                return;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error on send payload to vonage: {@current}", message);

                try
                {
                    string queueName = _messageManager.GetQueueName(message.UserNumber);
                    message = _messageManager.RetrySend(queueName);

                    if (message != null)
                    {
                        await Task.Delay(1000);
                        SendSingleMessage(message).Wait();
                    }
                }
                catch (Exception exe)
                {
                    _logger.LogError(exe, "Error on Retry Message: {@message}", message);
                }

                return;
            }
        }

        private async Task SendSandboxMessage(QueueMessage message)
        {
            string systemNumber = _configuration.GetValue<string>("Settings:Vonage:SystemNumber");

            VonageSandbox payload = new VonageSandbox()
            {
                from = systemNumber,
                to = message.UserNumber,
                message_type = "text",
                text = message.Message.text,
                channel = "whatsapp"
            };

            Uri url = _httpRequests.BuildUri(_configuration.GetValue<string>("Settings:Vonage:SandBox:Url"), "/v1/messages");

            string authentication = Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes($"{_configuration.GetValue<string>("Settings:Vonage:SandBox:User")}:{_configuration.GetValue<string>("Settings:Vonage:SandBox:Password")}"));
            Dictionary<string, string> header = new Dictionary<string, string>() { 
                { "Authorization", $"Basic {authentication}" },
                 { "Accept", "application/json" }
            };

            HttpResponseMessage response = _httpRequests.DefaultPostRequest(url, payload, header).Result;

            string a = _httpRequests.ProcessResponse<string>(response);
            if (response.StatusCode != HttpStatusCode.Accepted)
            {
                Console.WriteLine("Error");
            }
        }

        public async Task SendSingleMessage(QueueMessage message)
        {
            string isSandbox = _configuration.GetValue<string>("Settings:Vonage:IsSandBox");

            if (Convert.ToBoolean(isSandbox))
            {
                SendSandboxMessage(message);
            }
            else
            {
                SendProdMessage(message);
            }
        }

        private MessageRequestBase GenerateRequest(ConstructMessage message, string to, string brandName)
        {
            try
            {
                _logger.LogDebug("Construido texto para {@to} com a info {@message}", to, message);

                switch (message.messageType)
                {
                    case RequestMessageType.File:
                        return new WhatsAppFileRequest
                        {
                            To = to,
                            From = brandName,
                            File = new CaptionedAttachment
                            {
                                Caption = message.fileName,
                                Url = message.text
                            }
                        };

                    case RequestMessageType.Image:
                        return new WhatsAppImageRequest
                        {
                            To = to,
                            From = brandName,
                            Image = new CaptionedAttachment
                            {
                                Caption = "",
                                Url = message.text
                            }
                        };

                    case RequestMessageType.Notification:

                        return new WhatsAppTemplateRequest
                        {
                            To = to,
                            From = brandName,
                            WhatsApp = new MessageWhatsApp
                            {
                                Policy = "deterministic",
                                Locale = "pt-BR"
                            },
                            Template = new MessageTemplate
                            {
                                Name = message.text,
                                Parameters = message.Parameters
                            }
                        };

                    default:
                        return new WhatsAppTextRequest
                        {
                            To = to,
                            From = brandName,
                            Text = message.text
                        };
                }; 
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}
