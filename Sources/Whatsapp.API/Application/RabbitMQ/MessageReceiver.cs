using Domain.DTO.Message;
using Domain.Interface.Application;
using Microsoft.Extensions.Configuration;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;

namespace Application.RabbitMQ
{
    public class MessageReceiver : IMessageReceiver
    {
        private readonly IConfiguration _configuration;
        private readonly IProcessVonageMessage _processVonageMessage;

        public MessageReceiver(IConfiguration configuration, IProcessVonageMessage processVonageMessage) { 
            _configuration = configuration;
            _processVonageMessage = processVonageMessage;
        }

        public void ReceiveWhatsappNotification()
        {
            var factory = new ConnectionFactory
            {
                HostName = _configuration.GetValue<string>("Settings:RabbitMQ:Host"),
                UserName = _configuration.GetValue<string>("Settings:RabbitMQ:User"),
                Password = _configuration.GetValue<string>("Settings:RabbitMQ:Password")
            };

            var connection = factory.CreateConnection();
            var channel = connection.CreateModel();
            channel.QueueDeclare(
                "whatsapp-notification-queue",
                durable: true,
                exclusive: false,
                autoDelete: false,
                arguments: null
            );

            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (model, eventArgs) =>
            {
                var body = eventArgs.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);
                RequestMessage request = JsonSerializer.Deserialize<RequestMessage>(message);
                _processVonageMessage.ProcessRequestMessage(request);
            };

            channel.BasicConsume(queue: "whatsapp-notification-queue", autoAck: true, consumer: consumer);
        }
    }
}
