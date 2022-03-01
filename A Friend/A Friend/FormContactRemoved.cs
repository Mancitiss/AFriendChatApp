using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace A_Friend
{
    public partial class FormContactRemoved : Form
    {
        public FormContactRemoved()
        {
            InitializeComponent();
        }

        private void FormContactRemoved_Load(object sender, EventArgs e)
        {
            pictureBox1.Location = new Point((int)(this.Width / 2 - pictureBox1.Width / 2), (int)(this.Height / 2 - pictureBox1.Height / 2 - label1.Height / 2) - 10);
            label1.Location = new Point((int)(this.Width / 2 - label1.Width / 2), pictureBox1.Bottom + 5);
        }

        private void FormContactRemoved_Paint(object sender, PaintEventArgs e)
        {
            using(Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, 0, 0, this.Height);
            }
        }

        private void FormContactRemoved_Resize(object sender, EventArgs e)
        {
            this.Invalidate();
        }
    }
}
