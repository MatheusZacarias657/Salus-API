using Domain.Entity.Redis;
using Domain.Interface.Repository;
using Microsoft.Extensions.Configuration;

namespace Repository.Redis
{
    public class MessageManager : IMessageManager
    {
        private readonly IQueueManager _messagesQueue;
        private static int tries;

        public MessageManager(IQueueManager messagesQueue, IConfiguration configuration)
        {
            _messagesQueue = messagesQueue;
            tries = Convert.ToInt16(configuration["Settings:Vonage:TrySends"]);
        }

        public string GetQueueName(string sessionId)
        {
            try
            {
                return $"whatsapp:{sessionId}";
            }
            catch (Exception)
            {
                throw;
            }
        }

        public QueueMessage GetNext(string queueName)
        {
            try
            {
                RemoveOne(queueName);

                return GetOne(queueName);
            }
            catch (Exception)
            {
                throw;
            }
        }

        public QueueMessage GetOne(string queueName)
        {
            try
            {
                return _messagesQueue.Peek<QueueMessage>(queueName).Result;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public void RemoveOne(string queueName)
        {
            try
            {
                _messagesQueue.Dequeue<QueueMessage>(queueName).Wait();

                return;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public QueueMessage RetrySend(string queueName)
        {
            try
            {
                QueueMessage message = _messagesQueue.Dequeue<QueueMessage>(queueName).Result;

                if (message == null)
                    return default;

                message.Tries++;

                if (message.Tries <= tries)
                {
                    _messagesQueue.Requeue(message, queueName).Wait();
                }

                return _messagesQueue.Peek<QueueMessage>(queueName).Result;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public bool QueueExist(string queueName)
        {
            try
            {
                return _messagesQueue.QueueExist(queueName).Result;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public void Add(string queueName, List<QueueMessage> messages)
        {
            try
            {
                _messagesQueue.Enqueue(messages, queueName).Wait();

                return;
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
