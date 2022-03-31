using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net;
using System.IO;
using System.Diagnostics;
using System.Configuration;

namespace A_Friend
{
    class Program
    {
        public static FormApplication mainform;
        internal static int[] thisversion = {3, 2, 1, 2};

        static void Main(string[] args)
        {
            Console.WriteLine("Running");
            try
            {
                bool newv = false;
                try
                {
                    string data;
                    using (WebClient wc = new WebClient())
                    {
                        data = wc.DownloadString(ConfigurationManager.AppSettings.Get("check_new_version_address"));
                    }
                    string[] newestversion = data.Split('.');
                    int[] newestversionint = new int[newestversion.Count()];
                    for (int i = 0; i < newestversion.Count(); i++)
                    {
                        newestversionint[i] = int.Parse(newestversion[i]);
                    }
                    Console.WriteLine("This version: {0}.{1}.{2}.{3}", thisversion[0], thisversion[1], thisversion[2], thisversion[3]);
                    Console.WriteLine("Newest version found: {0}.{1}.{2}.{3}", newestversionint[0], newestversionint[1], newestversionint[2], newestversionint[3]);
                    for (int i = 0; i < newestversionint.Count(); i++)
                    {
                        if (i >= 4) break;
                        if (newestversionint[i] > thisversion[i])
                        {
                            newv = true;
                            break;
                        }
                        else if (newestversionint[i] == thisversion[i])
                        {
                            continue;
                        }
                        else break;
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
                if (newv)
                {
                    string url = ConfigurationManager.AppSettings.Get("download_new_version_address");
                    string path = Path.GetTempPath();
                    string fileName = "AFriend";
                    string tmpFile = Path.Combine(path, fileName);
                    string tempFile = Path.ChangeExtension(tmpFile, ".exe");
                    using (var client = new WebClient())
                    {
                        client.DownloadFile(url, tempFile);
                        Process.Start(tempFile, "/SP- /silent /noicons \" / dir = expand:{ autopf}\\A Friend\"");
                    }
                }
                else
                {
                    Console.WriteLine("A Friend, version {0}.{1}.{2}.{3}", thisversion[0], thisversion[1], thisversion[2], thisversion[3]);
                    Application.EnableVisualStyles();
                    Application.SetCompatibleTextRenderingDefault(false);
                    //Application.SetUnhandledExceptionMode(UnhandledExceptionMode.CatchException);
                    //Application.ThreadException += myHandler;
                    Application.Run(new FormLogin());

                }
            } catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        /*
        static void myHandler(object sender, System.Threading.ThreadExceptionEventArgs e)
        {
            //do something
        }
        */
    }
}
