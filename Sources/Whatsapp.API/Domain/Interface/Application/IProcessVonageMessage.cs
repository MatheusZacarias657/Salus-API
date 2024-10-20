using Domain.DTO.Message;
using Domain.DTO.Vonage;

namespace Domain.Interface.Application
{
    public interface IProcessVonageMessage
    {
        void ProcessStatusMessage(VonageStatus status);
        void ProcessRequestMessage(RequestMessage message);
    }
}