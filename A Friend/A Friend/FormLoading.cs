using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace A_Friend
{
    public partial class FormLoading : Form
    {
        private Pen curvePen;
        private Pen circlePen;
        private Rectangle rect;
        private int startAngle = 0;
        private int curveAngle = 120;
        private float percent = -1;

        internal delegate void show_progress_delegate(int percent);
        internal show_progress_delegate Show_progress_delegate;

        private void Show_progress(int percent)
        {
            if (this.percent == -1)
                this.percent = 0;
            this.labelTittle.Text = percent.ToString() + "%";
            labelTittle.Location = new Point((int)(this.Width / 2 - labelTittle.Width / 2), rect.Bottom + 10);
            if (labelTittle.Visible == false)
            {
                labelTittle.Visible = true;
            }
        }

        private Stopwatch sw = new Stopwatch();
        private bool stop = false;
        public FormLoading()
        {
            InitializeComponent();
            Show_progress_delegate = new show_progress_delegate(Show_progress);
            this.DoubleBuffered = true;
            curvePen = new Pen(Color.Black, 10);
            curvePen.StartCap = System.Drawing.Drawing2D.LineCap.Round;
            curvePen.EndCap = System.Drawing.Drawing2D.LineCap.Round;
            circlePen = new Pen(Color.LightGray, 10);
            rect = new Rectangle();
            rect.Width = 100;
            rect.Height = 100;
            rect.Location = new Point((int)(this.Width / 2- rect.Width / 2), (int)(this.Height / 2 - rect.Height / 2));
            labelTittle.Location = new Point((int)(this.Width / 2 - labelTittle.Width / 2), rect.Bottom + 10);
            labelTittle.Visible = false;
        }

        public int StartAngel
        {
            get
            {
                return startAngle;
            }
        }

        private /*async*/ void FormLoading_Load(object sender, EventArgs e)
        {
            timer.Start();
            sw.Start();
            /*
            Task t = Spin_Async(sender, e);
            await t;
            */
        }

        private void FormLoading_Resize(object sender, EventArgs e)
        {
            rect.Location = new Point((int)(this.Width / 2- rect.Width / 2), (int)(this.Height / 2 - rect.Height / 2));
            labelTittle.Location = new Point((int)(this.Width / 2 - labelTittle.Width / 2), rect.Bottom + 20);
            this.Invalidate();
        }

        private void FormLoading_Paint(object sender, PaintEventArgs e)
        {
            e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            e.Graphics.DrawArc(circlePen, rect, 0, 360);
            e.Graphics.DrawArc(curvePen, rect, startAngle, curveAngle);
        }


        private async Task Spin_Async(object sender, EventArgs e)
        {
            while (!this.stop) 
            {
                await Task.Delay(200);
                startAngle = (startAngle + 36) % 360;
                this.Invalidate(); 
            }
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            startAngle = (startAngle + 5) % 360;
            if (percent > 90F && percent < 99F)
                percent += 0.05F;
            else if (percent > 80F && percent <= 90F)
                percent += 0.1F;
            else if (percent <= 80F)
                percent += 0.5F;
            Show_progress((int)(percent));
            this.Invalidate();
        }

        public void StartSpinning()
        {
            timer.Start();
            timer1.Start();
        }

        public void StopSpinning()
        {
            this.stop = true;

            timer.Stop();

            sw.Stop();
            Console.WriteLine("Timer: " + sw.ElapsedMilliseconds);
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            if (labelTittle.Visible == false)
            {
                labelTittle.Visible = true;
            }
            timer1.Stop();
        }
    }
}
