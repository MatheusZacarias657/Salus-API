using Domain.DTO.Message;
using Domain.Entity.Redis;

namespace Domain.Interface.Application
{
    public interface ISendMessage
    {
        void SendMessageToUser(List<ConstructMessage> messages, string userNumber);
        Task SendSingleMessage(QueueMessage message);
    }
}