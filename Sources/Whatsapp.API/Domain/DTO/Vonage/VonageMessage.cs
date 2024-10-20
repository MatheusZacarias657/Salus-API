namespace Domain.DTO.Vonage
{
    public class VonageMessage
    {
        public string to { get; set; }
        public string from { get; set; }
        public string channel { get; set; }
        public string message_uuid { get; set; }
        public string timestamp { get; set; }
        public string message_type { get; set; }
        public string? text { get; set; }
        public Button? button { get; set; }
        public Attachment? image { get; set; }
        public Attachment? file { get; set; }
        public Attachment? audio { get; set; }
        public Attachment? video { get; set; }
        public Location? location { get; set; }
        public Profile profile { get; set; }
        public Context? context { get; set; }
    }

    public class Profile
    {
        public string name { get; set; }
    }

    public class Attachment
    {
        public string url { get; set; }
        public string? caption { get; set; }
    }

    public class Location
    {
        public string address { get; set; }
        public string name { get; set; }
        public double @lat { get; set; }
        public double @long { get; set; }
    }

    public class Button
    {
        public string text { get; set; }
        public string payload { get; set; }
    }

    public class Context
    {
        public string message_from { get; set; }
        public string message_uuid { get; set; }

    }
}
