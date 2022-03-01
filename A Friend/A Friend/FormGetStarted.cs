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
    public partial class FormGetStarted : Form
    {
        public FormGetStarted()
        {
            InitializeComponent();
            panel2.Visible = false;
            panel1.Visible = false;
        }

        public Color TopColor
        {
            get
            {
                return panel1.BackColor;
            }
            set
            {
                panel1.BackColor = value;

                if (value != this.BackColor)
                {
                    panel1.Visible = true;
                    panel1.Invalidate();
                }
                else
                {
                    panel1.Visible = false;
                }
            }
        }
        public Color BottomColor
        {
            get
            {
                return panel2.BackColor;
            }
            set
            {
                panel2.BackColor = value;

                if (value != this.BackColor)
                {
                    panel2.Visible = true;
                    panel2.Invalidate();
                }
                else
                {
                    panel2.Visible = false;
                }
            }
        }

        private void customButton1_Click(object sender, EventArgs e)
        {
            if (this.Parent.Parent != null && this.Parent.Parent is FormApplication)
            {
                (this.Parent.Parent as FormApplication).ButtonAdd_Click_1(sender, e);
            }
        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, panel1.Height - 1, panel1.Width, panel1.Height -  1);
            }
        }

        private void panel2_Paint(object sender, PaintEventArgs e)
        {
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, 1, panel2.Width - 0, 1);
            }
        }

        private void FormGetStarted_SizeChanged(object sender, EventArgs e)
        {
            panel1.Invalidate();
            panel2.Invalidate();
        }
    }
}
