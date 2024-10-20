using Domain.DTO.Message;

namespace Domain.Interface.Application
{
    public interface IBuildMessages
    {
        List<ConstructMessage> BuildWhatsMessages(string text);
    }
}