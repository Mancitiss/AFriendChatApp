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
using System.IO;
using System.Drawing.Imaging;
using System.Runtime.InteropServices;
using Microsoft.VisualBasic.FileIO;

namespace A_Friend.CustomControls
{
    public partial class ChatItem : UserControl
    {
        internal class ModifiedRichTextBox: RichTextBox
        {
            [DllImport("user32.dll", CharSet = CharSet.Auto)]
            private static extern IntPtr SendMessage(IntPtr hWnd, int msg, IntPtr wp, IntPtr lp);
            
            [DllImport("user32.dll", EntryPoint = "HideCaret")]
            public static extern long HideCaret(IntPtr hwnd);

            const int WM_MOUSEWHEEL = 0x020A;

            //thanks to a-clymer's solution
            protected override void WndProc(ref Message m)
            {
                HideCaret(this.Handle);
                if (m.Msg == WM_MOUSEWHEEL)
                {
                    //directly send the message to parent without processing it
                    //according to https://stackoverflow.com/a/19618100
                    SendMessage(this.Parent.Handle, m.Msg, m.WParam, m.LParam);
                    m.Result = IntPtr.Zero;
                }
                else base.WndProc(ref m);
            }
            internal ModifiedRichTextBox()
            {

            }
        }

        [DllImport("user32.dll", CharSet=CharSet.Auto)]
        private static extern IntPtr SendMessage(IntPtr hWnd, int msg, IntPtr wp, IntPtr lp);

        const int WM_MOUSEWHEEL = 0x020A;

        //thanks to a-clymer's solution
        protected override void WndProc(ref Message m)
        {
            if (m.Msg == WM_MOUSEWHEEL)
            {
                //directly send the message to parent without processing it
                //according to https://stackoverflow.com/a/19618100
                SendMessage(this.Parent.Handle, m.Msg, m.WParam, m.LParam);
                m.Result = IntPtr.Zero;
            }
            else base.WndProc(ref m);
        }

        private bool showDetail = false;
        public MessageObject messageObject;

        public string ImageToString(string path)
        {
            if (path == null)
                throw new ArgumentNullException("path");
            Image im = Image.FromFile(path);
            MemoryStream ms = new MemoryStream();
            im.Save(ms, im.RawFormat);
            byte[] array = ms.ToArray();
            return Convert.ToBase64String(array);
        }

        public Image StringToImage(string imageString)
        {

            if (imageString == null)
                throw new ArgumentNullException("imageString");
            byte[] array = Convert.FromBase64String(imageString);
            Image image = Image.FromStream(new MemoryStream(array));
            return image;
        }

        private static void open_image(Image image)
        {
            string tempFile = System.IO.Path.GetTempPath() + Guid.NewGuid().ToString() + ".png";
            (new Bitmap(image)).Save(tempFile, ImageFormat.Png);
            System.Diagnostics.Process.Start(tempFile);
        }

        internal Image image;

        public ChatItem(MessageObject messageObject)
        {
            InitializeComponent();

            this.messageObject = messageObject;
            labelAuthor.Font = ApplicationFont.GetFont(labelAuthor.Font.Size);
            DoubleBuffered = true;

            if (this.messageObject.type == 0 || this.messageObject.type == 3)
            {
                //this.MaximumSize = new Size(900, int.MaxValue); //this line causes problems!!
                labelBody.Text = messageObject.message;
                labelBody.Font = ApplicationFont.GetFont(labelBody.Font.Size);
                labelBody.BackColor = panelBody.BackColor;
                labelBody.BorderStyle = BorderStyle.None;
                labelBody.ScrollBars = RichTextBoxScrollBars.None;
                labelBody.ReadOnly = true;
                labelBody.HideSelection = false;
            }
            else if (this.messageObject.type == 1)
            {
                image = StringToImage(this.messageObject.message);
                panelBody.Controls.Remove(labelBody);
                labelBody.Dispose();
                panelBody.DoubleClick += delegate
                {
                    //code to open image in photo viewer 
                    ThreadPool.QueueUserWorkItem((state) => open_image(this.image));
                };
            }

            buttonCopy.Enabled = false;
            buttonRemove.Enabled = false;
            buttonCopy.Visible = false;
            buttonRemove.Visible = false;

            if (IsMyMessage())
            {
                panelBody.Dock = DockStyle.Right;
                panelButton.Dock = DockStyle.Right;
                labelAuthor.Dock = DockStyle.Right;
            }
            else
            {
                BackgroundColor = Color.FromArgb(215, 244, 241);
                if (this.messageObject.type == 0 || this.messageObject.type == 3)
                {
                    labelBody.ForeColor = SystemColors.ControlText;
                }
            }

            if (this.messageObject.type == 3)
            {
                labelBody.BackColor = Color.Yellow;
                labelBody.ForeColor = Color.Black;
                panelBody.BackColor = Color.Yellow;
                labelBody.DoubleClick += LabelBody_DoubleClick;
                Change_text_upload = new ChangeTextUploading(Upload);
                startTimerDelegate = new StartTimer(Start_Timer);
            }
        }

        private void Upload(byte percent)
        {
            labelAuthor.Text = percent + "%";
            labelAuthor.ForeColor = Color.Blue;
            if (percent == 100)
            {
                labelAuthor.Text = "DONE";
            }
        }

        internal delegate void ChangeTextUploading(byte percent);
        internal ChangeTextUploading Change_text_upload;

        internal delegate void StartTimer(string file, long size);
        internal StartTimer startTimerDelegate;
        private System.Windows.Forms.Timer timer;
        private long original_file_size;
        private string original_file_name;

        private void Start_Timer(string file, long size)
        {
            original_file_name = file;
            original_file_size = size/1048576;
            timer = new System.Windows.Forms.Timer();
            timer.Interval = 1000;
            timer.Tick += Timer_Tick;
            labelAuthor.ForeColor = Color.Green;
            timer.Start();
        }

        private void Timer_Tick(object sender, EventArgs e)
        {
            try
            {
                long left = AFriendClient.files[original_file_name].size;
                labelAuthor.Text = /*timetext /*+ (original_file_size - left / 1048576) + "/" + original_file_size + " MB" + " " +*/ 100*(original_file_size - left / 1048576) / original_file_size + "%";
                if (left == 0)
                {
                    //Downloading = false;
                    //Downloaded = true;
                    labelAuthor.Text = "DONE";
                    timer.Stop();
                    timer.Dispose();
                }
            } 
            catch (KeyNotFoundException knfex)
            {
                labelAuthor.Text = "DONE";
                timer.Stop();
                timer.Dispose();
            } 
            catch (Exception ex)
            {
                labelAuthor.Text = timetext;
                timer.Stop();
                timer.Dispose();
            }
        }

        private void LabelBody_DoubleClick(object sender, EventArgs e)
        {
            string partner_id = (messageObject.id1 == messageObject.id2) ? messageObject.id1 : (messageObject.id1 == AFriendClient.user.id) ? messageObject.id2 : messageObject.id1;
            Thread thread = new Thread(() =>
            {
                try
                {
                    using (SaveFileDialog saveFileDialog = new SaveFileDialog())
                    {
                        saveFileDialog.Filter = "All files (*.*)|*.*";
                        saveFileDialog.FileName = labelBody.Text;
                        if (saveFileDialog.ShowDialog() == DialogResult.OK)
                        {
                            labelAuthor.Text = "Try again later!";
                            labelAuthor.ForeColor = Color.Red;
                            if (File.Exists(saveFileDialog.FileName)) FileSystem.DeleteFile(saveFileDialog.FileName, UIOption.OnlyErrorDialogs, RecycleOption.SendToRecycleBin);
                            AFriendClient.Queue_command(AFriendClient.Combine(Encoding.Unicode.GetBytes("1905" + partner_id), Encoding.ASCII.GetBytes(AFriendClient.data_with_ASCII_byte(messageObject.messagenumber.ToString()))));
                            AFriendClient.files.Add(messageObject.id1 + "_" + messageObject.id2 + "_" + messageObject.messagenumber + ".", new AFriendClient.file(saveFileDialog.FileName, 0));
                        }
                    }
                } catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            });
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start(); 
        }

        public long ID
        {
            get
            {
                return this.messageObject.messagenumber;
            }
        }
        public bool IsMyMessage()
        {
            if (messageObject.sender == false)
            {
                if (messageObject.id1 == AFriendClient.user.id)
                {
                    return true;
                }
                return false;
            }
            if (messageObject.id2 == AFriendClient.user.id)
            {
                return true;
            }
            return false;
        }

        public string timetext;

        public void UpdateDateTime()
        {
            //if (IsMyMessage())
            //messageObject.timesent = DateTime.SpecifyKind(messageObject.timesent, DateTimeKind.Utc);
            if (true)
            {
                if (messageObject.timesent.ToLocalTime() < DateTime.Today)
                {
                    labelAuthor.Text = $"{messageObject.timesent.ToLocalTime().ToString("dd/MM/yyyy") + " - " + messageObject.timesent.ToLocalTime().ToShortTimeString()}";
                }
                else
                {
                    labelAuthor.Text = $"{messageObject.timesent.ToLocalTime().ToShortTimeString()}";
                }
                timetext = labelAuthor.Text;
            }
            else
            {
                if (this.Parent.Parent != null && this.Parent.Parent is PanelChat)
                {
                    string author = (this.Parent.Parent as PanelChat).account.name;
                    if (messageObject.timesent < DateTime.Today)
                    {
                        labelAuthor.Text = $"{author}, {messageObject.timesent.ToLocalTime().ToString("dd/MM/yyyy") + " - " + messageObject.timesent.ToLocalTime().ToShortTimeString()}";
                    }
                    else
                    {
                        labelAuthor.Text = $"{author}, {messageObject.timesent.ToLocalTime().ToShortTimeString()}";
                    }
                }
            }

        }

        public Color BackgroundColor
        {
            get
            {
                return panelBody.BackColor;
            }

            set
            {
                if (messageObject.type == 0 || messageObject.type == 3)
                {
                    labelBody.BackColor = value;
                }
                panelBody.BackColor = value;
            }
        }

        public bool ShowDetail
        {
            get
            {
                return showDetail;
            }
            set
            {
                showDetail = value;
                panelBottom.Visible = value;
                if (value)
                {
                    this.Height = 5 + panelTop.Height + panelBottom.Height;
                    this.Invalidate();
                }
                else
                {
                    this.Height = 5 + panelTop.Height;
                    this.Invalidate();
                }
            }
        }

        protected override void OnResize(EventArgs e)
        {
            base.OnResize(e);
            ResizeBubbles();
            buttonCopy.Location = new Point(buttonCopy.Left, (int)(buttonCopy.Parent.Height / 2 - buttonCopy.Height / 2));
            buttonRemove.Location = new Point(buttonRemove.Left, (int)(buttonRemove.Parent.Height / 2 - buttonRemove.Height / 2));
        }

        public void ResizeBubbles()
        {
            if (messageObject != null && (messageObject.type == 0|| messageObject.type == 3))
            {
                //int maxwidth = this.Width - 200;
                int maxwidth = this.Parent.Width-2*this.Parent.Width/5;
                labelAuthor.MaximumSize = new Size(maxwidth, int.MaxValue);
                labelBody.MaximumSize = new Size(maxwidth - 2 * labelBody.Left, int.MaxValue);
                SuspendLayout();
                Label temp = new Label();
                temp.Font = labelBody.Font;
                temp.AutoSize = true;
                temp.MaximumSize = new Size(labelBody.MaximumSize.Width, labelBody.MaximumSize.Height);
                temp.Text = labelBody.Text;
                this.Controls.Add(temp);
                var size = temp.Size;
                this.Controls.Remove(temp);
                /*
                var size = TextRenderer.MeasureText(labelBody.Text, labelBody.Font, new Size(labelBody.MaximumSize.Width, 0), TextFormatFlags.Default);
                if (size.Width > labelBody.MaximumSize.Width)
                {
                    size.Height = (size.Width / labelBody.MaximumSize.Width) * 19 + 19;
                    size.Width = labelBody.MaximumSize.Width;
                }
                */
                //the old size = measure("something", font) always return (299, 19)
                Console.WriteLine("{0}:{1}", size.Width, size.Height);

                panelBody.Width = size.Width + 2 * labelBody.Left;
                panelTop.Height = size.Height + 2 * labelBody.Top;
                labelBody.Size = new Size(size.Width, size.Height);

                /*
                if (labelBody.Width <= maxwidth - 2 * labelBody.Left && labelBody.Height <= 19)
                {
                    panelBody.Width = labelBody.Width + 2 * labelBody.Left;
                }
                panelBody.Width = labelBody.Width + 2 * labelBody.Left;
                panelTop.Height = labelBody.Height + 2 * labelBody.Top;
                */

                if (showDetail)
                {
                    panelBottom.Size = new Size(this.Parent.Width, labelAuthor.Size.Height + 5);
                    this.Height = 5 + panelTop.Height + panelBottom.Height;
                }
                else
                {
                    this.Height = 5 + panelTop.Height;
                }
                panelBottom.Location = new Point(0, 0);

                ResumeLayout();
            }
            else if (messageObject != null && messageObject.type == 1)
            {
                int maxwidth = this.Width*3/5;
                labelAuthor.MaximumSize = new Size(maxwidth, int.MaxValue);
                if (image.Width > maxwidth)
                { 
                var img = Tools.ResizeImage(image, maxwidth, maxwidth * image.Height / image.Width);
                panelBody.BackgroundImage = img;
                }
                else if (panelBody.BackgroundImage != image)
                {
                    panelBody.BackgroundImage = image;
                }
                panelTop.Height = panelBody.BackgroundImage.Height;
                panelBody.Width = panelBody.BackgroundImage.Width;
                if (ShowDetail)
                {
                    panelBottom.Size = new Size(this.Parent.Width, labelAuthor.Size.Height + 5);
                    this.Height = 5 + panelTop.Height + panelBottom.Height;
                } else
                {
                    this.Height = 5 + panelTop.Height;
                }
                panelBottom.Location = new Point(0, 0);
            }
        }

        private void buttonCopy_Click(object sender, EventArgs e)
        {
            Thread t = new Thread((ThreadStart)(() =>
            {
                if (messageObject.type == 0 || messageObject.type == 3)
                { 
                    Clipboard.SetText(labelBody.Text);
                }
                else if (messageObject.type == 1)
                {
                    Clipboard.SetImage(image);
                }
            }));
            t.SetApartmentState(ApartmentState.STA);
            t.Start();
        }

        private void buttonRemove_Click(object sender, EventArgs e)
        {
            if (this.Parent.Parent is PanelChat)
            {
                DialogResult dialogResult = MessageBox.Show("You wanna delete this message? (no one will see it after you delete it)", "Warning", MessageBoxButtons.YesNo);
                if (dialogResult == DialogResult.Yes)
                {
                    (this.Parent.Parent as PanelChat).RemoveMessage(this.ID);
                }
            }
        }

        public void HideButtons()
        {
            buttonCopy.Enabled = false;
            buttonRemove.Enabled = false;
            buttonCopy.Visible = false;
            buttonRemove.Visible = false;
        }

        public void ShowButtons()
        {
            buttonCopy.Enabled = true;
            buttonRemove.Enabled = true;
            buttonCopy.Visible = true;
            buttonRemove.Visible = true;
            buttonCopy.Location = new Point(buttonCopy.Left, (int)(panelButton.Height / 2 - buttonCopy.Height / 2));
            buttonRemove.Location = new Point(buttonRemove.Left, (int)(panelButton.Height / 2 - buttonRemove.Height / 2));

            foreach (var control in this.Parent.Controls)
            {
                if (control is ChatItem && control != this)
                {
                    (control as ChatItem).HideButtons(); ;
                }
            }
        }

        protected override void OnMouseLeave(EventArgs e)
        {
            if (!ClientRectangle.Contains(PointToClient(Control.MousePosition)))
            {
                base.OnMouseLeave(e);
            }
        }

        protected override void OnMouseEnter(EventArgs e)
        {
            if (ClientRectangle.Contains(PointToClient(Control.MousePosition)))
            {
                base.OnMouseEnter(e);
            }
        }
        
        private void ChatItem_Load(object sender, EventArgs e)
        {
            ResizeBubbles();
        }

        private void ChatItem_MouseEnter(object sender, EventArgs e)
        {
            if (ClientRectangle.Contains(PointToClient(Control.MousePosition)))
            {
                ShowButtons();
            }
        }

        private void ChatItem_MouseLeave(object sender, EventArgs e)
        {
            if (!ClientRectangle.Contains(PointToClient(Control.MousePosition)))
            {
                HideButtons();
            }
        }

        private void labelBody_Click(object sender, EventArgs e)
        {
            this.OnClick(e);
        }
    }
}
