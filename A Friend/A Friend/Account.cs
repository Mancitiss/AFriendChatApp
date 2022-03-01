using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace A_Friend
{
    public class Account
    {
        public string username;
        public string name;
        public string id;
        public byte state;
        public bool priv = false;

        public Account() 
        {

        }
        public Account(string username, string name, string id, byte state) 
        { 
            this.username = username;
            this.name = name;
            this.id = id;
            this.state = state;
        }
    }
}
