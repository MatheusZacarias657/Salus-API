using Domain.DTO.Message;

namespace Domain.Entity.Redis
{
    public class QueueMessage
    {
        public string UserNumber { get; set; }
        public int Tries { get; set; }
        public ConstructMessage Message { get; set; }
    }
}
