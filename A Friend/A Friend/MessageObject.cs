using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace A_Friend
{
    public class MessageObject
    {
        public string id1; 
        public string id2; 
        public long messagenumber; 
        public DateTime timesent; 
        public bool sender; 
        public string message;
        public byte type;

        public MessageObject(string id1, string id2, long messagenumber, DateTime timesent, bool sender, string message, byte type)
        {
            this.id1 = id1;
            this.id2 = id2;
            this.messagenumber = messagenumber;
            this.timesent = timesent;
            this.sender = sender;
            this.message = message;
            this.type = type;
        }

        public MessageObject() { } 
    }
}
