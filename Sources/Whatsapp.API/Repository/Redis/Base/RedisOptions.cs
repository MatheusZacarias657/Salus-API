namespace Repository.Redis.Base
{
    public class RedisOptions
    {
        public string ConnectionString { get; set; }
        public bool IsEnabled => !string.IsNullOrWhiteSpace(ConnectionString);
    }
}