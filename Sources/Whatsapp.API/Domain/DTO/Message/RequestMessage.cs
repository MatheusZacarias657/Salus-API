using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.DTO.Message
{
    public class RequestMessage
    {
        public bool IsNotification { get; set; }
        public string UserNumber { get; set; }
        public string Message { get; set; }
    }
}
