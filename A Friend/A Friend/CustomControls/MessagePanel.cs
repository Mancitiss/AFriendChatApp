using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Drawing;


namespace A_Friend.CustomControls
{
    public class MessagePanel : Panel
    {
        public MessagePanel()
        {
            this.DoubleBuffered = true;
        }

        int borderRadius = 20;
        private GraphicsPath GetFigurePath(Rectangle rect, int radius)
        {
            GraphicsPath path = new GraphicsPath();

            path.StartFigure();
            path.AddArc(rect.X, rect.Y, radius, radius, 180, 90);
            path.AddArc(rect.Right - radius, rect.Y, radius, radius, 270, 90);
            path.AddArc(rect.Right - radius, rect.Bottom - radius, radius, radius, 0, 90);
            path.AddArc(rect.X, rect.Bottom - radius, radius, radius, 90, 90);
            path.CloseAllFigures();
            return path;
        }

        protected override void OnPaint(PaintEventArgs pevent)
        {
            base.OnPaint(pevent);
            pevent.Graphics.SmoothingMode = SmoothingMode.AntiAlias;

            var rectSurface = this.ClientRectangle;

            using (GraphicsPath pathSurface = GetFigurePath(rectSurface, borderRadius))
            using (Pen penSurface = new Pen(this.Parent.BackColor, 2))
            {
                this.Region = new Region(pathSurface);

                pevent.Graphics.DrawPath(penSurface, pathSurface);
            }
        }

        protected override void OnResize(EventArgs eventargs)
        {
            base.OnResize(eventargs);
            this.Invalidate();
        }
    }
}
