namespace Domain.DTO.Vonage
{
    public class VonageStatus
    {
        public string to { get; set; }
        public string from { get; set; }
        public string channel { get; set; }
        public string message_uuid { get; set; }
        public string timestamp { get; set; }
        public Usage? usage { get; set; }
        public string status { get; set; }
        public Whatsapp? whatsapp { get; set; }
    }

    public class Usage
    {
        public string price { get; set; }
        public string currency { get; set; }
    }

    public class Whatsapp
    {
        public Conversation conversation { get; set; }
    }

    public class Conversation
    {
        public string id { get; set; }
        public Origin origin { get; set; }
    }

    public class Origin
    {
        public string type { get; set; }
    }
}
