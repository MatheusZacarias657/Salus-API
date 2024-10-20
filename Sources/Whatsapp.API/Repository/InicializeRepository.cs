using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Repository.Redis;

namespace Repository
{
    public static class InicializeRepository
    {
        public static IServiceCollection AddRepository(this IServiceCollection services, IConfiguration configuration)
        {
            services.AddRedis(config => config.ConnectionString = configuration["Settings:Connections:RedisUrl"]);

            return services;
        }
    }
}
