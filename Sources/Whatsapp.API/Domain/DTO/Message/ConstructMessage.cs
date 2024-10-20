using Domain.Enum;

namespace Domain.DTO.Message
{
    public class ConstructMessage
    {
        public RequestMessageType messageType {  get; set; }
        public string text { get; set; }
        public string fileName { get; set; }

        public List<object> Parameters { get; set; }
    }
}
