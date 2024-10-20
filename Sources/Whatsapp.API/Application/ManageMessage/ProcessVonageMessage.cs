using Domain.DTO.Message;
using Domain.DTO.Vonage;
using Domain.Entity.Redis;
using Domain.Enum;
using Domain.Interface.Application;
using Domain.Interface.Repository;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
using Whatsapp.VonageAPI.Services.Utils;

namespace Application.ManageMessage
{
    public class ProcessVonageMessage : IProcessVonageMessage
    {
        private readonly ILogger<ProcessVonageMessage> _logger;
        private readonly IConfiguration _configuration;
        private readonly IMessageManager _messageManager;
        private readonly ISendMessage _sendMessage;
        private readonly IBuildMessages _buildMessages;

        public ProcessVonageMessage(ILogger<ProcessVonageMessage> logger, IConfiguration configuration, 
                                    IMessageManager messageManager, ISendMessage sendMessage, IBuildMessages buildMessages)
        {
            _logger = logger;
            _configuration = configuration;
            _messageManager = messageManager;
            _sendMessage = sendMessage;
            _buildMessages = buildMessages;
        }

        public void ProcessStatusMessage(VonageStatus status)
        {
            try
            {
                string userNumber = status.to;

                /* Adiciona um 9 caso o número não venha com ele */
                if (userNumber.Length == 12)
                    userNumber = userNumber.Insert(4, "9");

                string queueName = _messageManager.GetQueueName(userNumber);
                QueueMessage message = new QueueMessage();

                if (status.status.ToLower().Equals("submitted"))
                {
                    message = _messageManager.GetNext(queueName);
                }
                else if (status.status.ToLower().Equals("rejected"))
                {
                    message = _messageManager.RetrySend(queueName);
                }

                if (message != default(QueueMessage))
                    _sendMessage.SendSingleMessage(message);
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error on ProcessStatusMessage: {@status}", status);
            }

            return;
        }

        public void ProcessRequestMessage(RequestMessage message)
        {
            if (string.IsNullOrEmpty(message.Message))
                return;

            List<ConstructMessage> sendMessages = new List<ConstructMessage>();

            if (!message.IsNotification)
            {
                sendMessages = _buildMessages.BuildWhatsMessages(message.Message);
            }
            else
            {
                sendMessages.Add(new ConstructMessage
                {
                    messageType = RequestMessageType.Notification,
                    text = message.Message
                });
            }

            _sendMessage.SendMessageToUser(sendMessages, message.UserNumber);
        }
    }
}
