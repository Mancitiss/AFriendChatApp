using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace AFriendServer
{
    internal class FileToWrite
    {
        internal long size;
        internal System.IO.FileStream fileStream;

        internal FileToWrite() { }
        internal FileToWrite(long size)
        {
            this.size = size;
        }
        internal FileToWrite(long size, System.IO.FileStream fileStream)
        {
            this.size = size;
            this.fileStream = fileStream;
        }
    }
}
