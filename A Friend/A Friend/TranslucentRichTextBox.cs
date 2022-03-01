using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace A_Friend
{
    public partial class TranslucentRichTextBox : RichTextBox
    {
        [DllImport("kernel32.dll", CharSet = CharSet.Auto)]
        static extern IntPtr LoadLibrary(string lpFileName);

        protected override CreateParams CreateParams
        {
            get
            {
                CreateParams prams = base.CreateParams;
                if (LoadLibrary("msftedit.dll") != IntPtr.Zero)
                {
                    prams.ExStyle |= 0x020; // transparent
                    prams.ClassName = "RICHEDIT50W";
                }
                return prams;
            }
        }
        public TranslucentRichTextBox()
        {
            InitializeComponent();
            
            SetStyle(/*ControlStyles.SupportsTransparentBackColor |*/
                ControlStyles.OptimizedDoubleBuffer |
                ControlStyles.AllPaintingInWmPaint |
                ControlStyles.ResizeRedraw /*|
                ControlStyles.UserPaint*/, true);
            //BackColor = Color.Transparent;
        }
    }
}
