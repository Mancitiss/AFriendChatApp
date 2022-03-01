using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing.Drawing2D;
using System.Threading;

namespace A_Friend.CustomControls
{
    public partial class ContactItem : UserControl
    {
        Account account;
        byte state = 0;
        string id;
        Color mouseOnColor = Color.FromArgb(65, 165, 238);
        Color stateColor = Color.Gainsboro;
        int borderSize = 20;
        bool mouseOn = false;
        bool clicked = false;
        bool unread = false;

        public ContactItem()
        {
            InitializeComponent();
            this.DoubleBuffered = true;
        }

        public ContactItem(Account account)
        {
            InitializeComponent();
            labelName.Font = ApplicationFont.GetFont(labelName.Font.Size);
            labelLastMessage.Font = ApplicationFont.GetFont(labelLastMessage.Font.Size);

            this.account = account;
            this.DoubleBuffered = true;
            this.Name = "contacItem_" + account.id;
            this.FriendName = account.name;
            this.id = account.id;
            State = account.state;

            this.DoubleClick += new EventHandler(Open_chat);
            this.labelLastMessage.DoubleClick += new EventHandler(Open_chat);
            this.labelName.DoubleClick += new EventHandler(Open_chat);
            this.friendPicture.DoubleClick += new EventHandler(Open_chat);
        }

        private void Open_chat(object sender, EventArgs e)
        {
            Program.mainform.panelChats[id].is_form_showing++;
            if (1 == Program.mainform.panelChats[id].is_form_showing)
            {
                SnapForm form = new SnapForm();
                form.Icon = Program.mainform.panelChats[id].AvatarIcon;
                form.ClientSize = new Size(300, 450); 
                form.BackgroundImage = Tools.SetImgOpacity(Tools.ResizeImage(Program.mainform.panelChats[id].Avatar, form.ClientSize.Width, form.ClientSize.Height), 0.19f);
                form.FormBorderStyle = FormBorderStyle.FixedSingle;
                form.MaximizeBox = false;
                form.Controls.Add(Program.mainform.panelChats[id]);
                if (Program.mainform.panelChats[id].account.name.Length > 10)
                    (form.Controls[0] as PanelChat).labelFriendName.Text = Program.mainform.panelChats[id].account.name.Substring(0, 10) + "...";
                form.FormClosing += (fs, fe) =>
                {
                    Program.mainform.panelChats[id].labelFriendName.Text = Program.mainform.panelChats[id].account.name;
                    Program.mainform.panelChats[id].is_form_showing = 0;
                    form.Controls.Remove(Program.mainform.panelChats[id]);
                    FormApplication.subForms.TryRemove(ID, out Form form1);
                };
                form.Resize += (o, ev) =>
                {
                    if (form.WindowState == FormWindowState.Normal && Program.mainform.panelChats[id].messages.Count > 0)
                        (form.Controls[0] as PanelChat).panel_Chat.ScrollControlIntoView(
                            (form.Controls[0] as PanelChat).messages[(form.Controls[0] as PanelChat).currentmax]);
                };
                FormApplication.subForms.TryAdd(ID, form);
                form.Show(); 
                if (Program.mainform.panelChats[id].messages.Count > 0)
                    (form.Controls[0] as PanelChat).panel_Chat.ScrollControlIntoView(
                        (form.Controls[0] as PanelChat).messages[(form.Controls[0] as PanelChat).currentmax]);
                Rectangle rectangle = form.RectangleToScreen(form.ClientRectangle);
                form.DesktopLocation = new System.Drawing.Point(
                    Screen.FromControl(Program.mainform).WorkingArea.Width -7 - rectangle.Width * (FormApplication.subForms.Count) > 0 ?
                    Screen.FromControl(Program.mainform).WorkingArea.Width -7 - rectangle.Width * (FormApplication.subForms.Count) : 0,
                    Screen.FromControl(Program.mainform).WorkingArea.Height -3 - rectangle.Height - (rectangle.Top - form.Top) > 0 ?
                    Screen.FromControl(Program.mainform).WorkingArea.Height -3 - rectangle.Height - (rectangle.Top - form.Top) : 0);
            }
        }

        public Image Avatar
        {
            set
            {
                friendPicture.Crop(value);
            }
        }

        public bool Clicked
        {
            get
            {
                return clicked;
            }
            set
            {
                clicked = value;
                this.Invalidate();
            }
        }

        public Color MouseOnColor { get => mouseOnColor; set => mouseOnColor = value; }

        public ContactItem(string name, string lastmessage, bool unread)
        {
            InitializeComponent();
            FriendName = name;
            LastMessage = lastmessage;
            Unread = unread;
        }

        public string ID
        {
            get => id;
        }

        public bool Unread
        {
            get { return unread; }
            set
            {
                unread = value;
                if (unread)
                {
                    labelLastMessage.ForeColor = Color.FromArgb(65, 165, 238);
                }
                else
                {
                    labelLastMessage.ForeColor = Color.DimGray;
                }
            }
        }

        public string FriendName
        {
            get
            {
                return account.name;
            }
            set
            {
                labelName.Text = value;
            }
        }
        public string LastMessage
        {
            set
            {
                labelLastMessage.Text = value.Trim().Replace('\n', '-');
            }
        }

        public byte State
        {
            get
            {
                return state;
            }
            set
            {
                if (state != value) {
                    state = value;

                    if (state == 0)
                    {
                        stateColor = Color.Gainsboro;
                    }
                    else if (state == 1)
                    {
                        stateColor = Color.SpringGreen;
                    }
                    else
                    {
                        stateColor = Color.Red;
                    }
                    this.Invalidate();
                }
            }
        }

        public void TurnActive()
        {
            friendPicture.BorderColor = Color.FromArgb(58, 206, 58);
            friendPicture.BorderColor2 = Color.FromArgb(180, 236, 180);
        }

        public void TurnAway()
        {
            friendPicture.BorderColor = Color.FromArgb(255, 32, 21);
            friendPicture.BorderColor2 = Color.FromArgb(255, 178, 174);
        }

        public void TurnOffline()
        {
            friendPicture.BorderColor = Color.Gray;
            friendPicture.BorderColor2 = Color.Gray;
        }

        private GraphicsPath GetFigurePath(RectangleF rect, float radius)
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

        private void ContactItem_Paint(object sender, PaintEventArgs e)
        {
            if(clicked)
            {
                var rect = new Rectangle(10, 2, this.Width - 20, this.Height - 4);
                using (var path = GetFigurePath(rect, borderSize))
                //using (var brush = new SolidBrush(Color.FromArgb(30, Color.Gray)))
                using (var brush = new SolidBrush(Color.FromArgb(95, 255, 202, 152)))
                //using (var brush = new SolidBrush(Color.FromArgb(228, 128, 113)))
                {
                    e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;
                    e.Graphics.FillPath(brush, path);
                }
            }
            else if (mouseOn)
            {
                var rect = new Rectangle(10, 2, this.Width - 20, this.Height - 4);
                using (var path = GetFigurePath(rect, borderSize))
                //using (var brush = new SolidBrush(Color.FromArgb(20, Color.Gray)))
                using (var brush = new SolidBrush(Color.FromArgb(50, 255, 202, 152)))
                {
                    e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;
                    e.Graphics.FillPath(brush, path);
                }
            }

            using (Pen pen = new Pen(stateColor, 2))
            using (var path = GetFigurePath(new Rectangle(friendPicture.Left - 1, friendPicture.Top - 1, friendPicture.Width + 2, friendPicture.Width + 2), friendPicture.Width + 2))
            {
                e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;
                e.Graphics.DrawPath(pen, path);
            }
            //base.OnPaint(e);
        }

        private void labelName_Resize(object sender, EventArgs e)
        {
            this.Invalidate();
        }

        private void friendPicture_Click(object sender, EventArgs e)
        {
            this.OnClick(e);
        }

        private void labelName_Click(object sender, EventArgs e)
        {
            this.OnClick(e);
        }

        private void labelLastMessage_Click(object sender, EventArgs e)
        {
            this.OnClick(e);
        }

        private void ContactItem_Resize(object sender, EventArgs e)
        {
            this.Invalidate();
        }

        private void ContactItem_Click(object sender, EventArgs e)
        {
            if (this.Parent.Parent.Parent is FormApplication)
            {
                FormApplication parent = this.Parent.Parent.Parent as FormApplication;
                if (parent.currentContactItem != null && parent.currentContactItem != this)
                {
                    parent.currentContactItem.Clicked = false;
                }
                parent.currentContactItem = this;
                Clicked = true;
            }
        }
        protected override void OnMouseEnter(EventArgs e)
        {
            if (ClientRectangle.Contains(PointToClient(Control.MousePosition)))
            {
                base.OnMouseEnter(e);
            }
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            if (!ClientRectangle.Contains(PointToClient(Control.MousePosition)))
            {
                base.OnMouseLeave(e);
            }
        }

        private void ContactItem_MouseEnter(object sender, EventArgs e)
        {
            mouseOn = true;
            this.Invalidate();
        }

        private void ContactItem_Leave(object sender, EventArgs e)
        {
            mouseOn = false;
            this.Invalidate();
        }

        private void labelName_TextChanged(object sender, EventArgs e)
        {
            var size = TextRenderer.MeasureText(labelName.Text, labelName.Font);
            labelName.Height = size.Height;
        }

        private void labelLastMessage_TextChanged(object sender, EventArgs e)
        {
            var size = TextRenderer.MeasureText(labelLastMessage.Text, labelLastMessage.Font);
            labelLastMessage.Height = size.Height;
        }
    }
}