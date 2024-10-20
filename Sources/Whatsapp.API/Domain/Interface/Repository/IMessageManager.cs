using Domain.Entity.Redis;

namespace Domain.Interface.Repository
{
    public interface IMessageManager
    {
        string GetQueueName(string sessionId);
        void Add(string queueName, List<QueueMessage> messages);
        QueueMessage GetNext(string queueName);
        QueueMessage GetOne(string queueName);
        bool QueueExist(string queueName);
        void RemoveOne(string queueName);
        QueueMessage RetrySend(string queueName);
    }
}