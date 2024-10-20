using Application;
using Serilog;

namespace Whatsapp.VonageAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            WebApplicationBuilder builder = WebApplication.CreateBuilder(args);

            //Add Config
            ConfigurationBuilder configBuilder = new ConfigurationBuilder();
            configBuilder.SetBasePath(Directory.GetCurrentDirectory()).AddJsonFile("appsettings.json", optional: true, true).AddEnvironmentVariables();
            IConfigurationRoot configuration = configBuilder.Build();
            builder.Configuration.AddEnvironmentVariables();

            // Add services to the container.
            builder.Services.AddControllers();
            builder.Services.AddHttpClient();

            //Enable serilog
            builder.Host.UseSerilog(((ctx, config) =>
                config.ReadFrom.Configuration(ctx.Configuration)
                .Enrich.WithProperty("Host", Environment.MachineName)
            ));

            //Add Custom Services
            Inicialize.InicializeServices(builder.Services, configuration);

            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            WebApplication app = builder.Build();

            InicializeApplication.InicializeRabbit(app.Services);

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            //app.UseHttpsRedirection();
            app.UseAuthorization();
            app.MapControllers();
            app.Run();
        }
    }
}