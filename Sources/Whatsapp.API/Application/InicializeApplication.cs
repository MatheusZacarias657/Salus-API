using Application.ManageMessage;
using Application.RabbitMQ;
using Domain.Interface.Application;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using System;
using Whatsapp.VonageAPI.Services.Utils;

namespace Application
{
    public static class InicializeApplication
    {
        public static IServiceCollection AddAplication(this IServiceCollection services, IConfiguration configuration)
        {
            //Add Utils
            services.AddTransient<ISendMessage, SendMessage>();
            services.AddTransient<IBuildMessages, BuildMessages>();

            //Add Processing
            services.AddTransient<IProcessVonageMessage, ProcessVonageMessage>();

            //Add Rabbit
            services.AddTransient<IMessageReceiver, MessageReceiver>();

            return services;
        }

        public static void InicializeRabbit(IServiceProvider serviceProvider)
        {
            IMessageReceiver rabbitMessageReceiver = serviceProvider.GetRequiredService<IMessageReceiver>();
            rabbitMessageReceiver.ReceiveWhatsappNotification();
        }
    }
}
