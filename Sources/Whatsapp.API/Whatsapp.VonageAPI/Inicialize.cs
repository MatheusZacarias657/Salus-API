using Application;
using Repository;

namespace Whatsapp.VonageAPI
{
    public class Inicialize
    {
        public static void InicializeServices(IServiceCollection services, IConfiguration configuration)
        {
            //Add Repository
            services.AddRepository(configuration);

            //Add Application
            services.AddAplication(configuration);

            return;
        }
    }
}
