namespace Domain.Interface.Repository
{
    public interface IQueueManager
    {
        Task Clear(string key);
        Task<T> Dequeue<T>(string queueName);
        Task Enqueue<T>(List<T> items, string queueName);
        Task Enqueue<T>(T item, string queueName);
        Task<T> Peek<T>(string queueName);
        Task<bool> QueueExist(string queueName);
        Task Requeue<T>(T item, string queueName);
    }
}