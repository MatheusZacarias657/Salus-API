using Domain.Interface.Repository;
using Microsoft.Extensions.DependencyInjection;
using Repository.Redis.Base;

namespace Repository.Redis
{
    public static class RedisConfig
    {
        internal static IServiceCollection AddRedis(this IServiceCollection services, Action<RedisOptions> configure)
        {
            RedisOptions options = new RedisOptions();
            configure.Invoke(options);
            services.Configure(configure);

            services.AddSingleton<IQueueManager, QueueManager>();
            services.AddTransient<IMessageManager, MessageManager>();

            return services;
        }
    }
}
