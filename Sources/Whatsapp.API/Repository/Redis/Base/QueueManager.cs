using Domain.Interface.Repository;
using Microsoft.Extensions.Options;
using System.Text.Json;

namespace Repository.Redis.Base
{
    public class QueueManager : IQueueManager
    {
        private readonly RedisOptions _options;
        private RedisConnection _connection;

        public QueueManager(IOptions<RedisOptions> options)
        {
            _options = options.Value;
        }

        public async Task<T> Peek<T>(string queueName)
        {
            if (!_options.IsEnabled)
            {
                return default;
            }

            if (_connection is null)
            {
                _connection = await RedisConnection.InitializeAsync(_options.ConnectionString);
            }

            string itemJson = await _connection.BasicRetryAsync(async (db) => await db.ListGetByIndexAsync(queueName, -1));
            if (string.IsNullOrWhiteSpace(itemJson))
            {
                return default;
            }

            return JsonSerializer.Deserialize<T>(itemJson);
        }

        public async Task Enqueue<T>(T item, string queueName)
        {
            if (!_options.IsEnabled || item == null)
            {
                return;
            }

            if (_connection is null)
            {
                _connection = await RedisConnection.InitializeAsync(_options.ConnectionString);
            }

            string itemJson = JsonSerializer.Serialize(item);
            await _connection.BasicRetryAsync(async (db) => await db.ListLeftPushAsync(queueName, itemJson));
        }

        public async Task Enqueue<T>(List<T> items, string queueName)
        {
            foreach (T item in items)
            {
                await Enqueue(item, queueName);
            }
        }

        public async Task<T> Dequeue<T>(string queueName)
        {
            if (!_options.IsEnabled)
            {
                return default;
            }

            if (_connection is null)
            {
                _connection = await RedisConnection.InitializeAsync(_options.ConnectionString);
            }

            string itemJson = await _connection.BasicRetryAsync(async (db) => await db.ListRightPopAsync(queueName));
            if (string.IsNullOrWhiteSpace(itemJson))
            {
                return default;
            }

            return JsonSerializer.Deserialize<T>(itemJson);
        }

        public async Task Requeue<T>(T item, string queueName)
        {
            if (!_options.IsEnabled)
            {
                return;
            }

            if (_connection is null)
            {
                _connection = await RedisConnection.InitializeAsync(_options.ConnectionString);
            }

            string itemJson = JsonSerializer.Serialize(item);
            await _connection.BasicRetryAsync(async (db) => await db.ListRightPushAsync(queueName, itemJson));
        }

        public async Task<bool> QueueExist(string queueName)
        {
            if (!_options.IsEnabled)
            {
                return false;
            }

            if (_connection is null)
            {
                _connection = await RedisConnection.InitializeAsync(_options.ConnectionString);
            }

            return await _connection.BasicRetryAsync(async (db) => await db.KeyExistsAsync(queueName));
        }

        public async Task Clear(string key)
        {
            if (!_options.IsEnabled)
            {
                return;
            }

            if (_connection is null)
            {
                _connection = await RedisConnection.InitializeAsync(_options.ConnectionString);
            }

            await _connection.BasicRetryAsync(async (db) => await db.KeyDeleteAsync(key));
        }
    }
}
