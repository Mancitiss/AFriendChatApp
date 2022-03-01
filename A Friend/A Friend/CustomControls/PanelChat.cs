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
using System.IO;
using System.Threading;
using System.Media;
using System.Collections.Concurrent;
using System.Runtime.InteropServices;

namespace A_Friend.CustomControls
{
    public partial class PanelChat : UserControl
    {
        public Account account;
        string id;
        byte state;
        Int64 loadedmessagenumber = 0;

        internal bool is_showing;
        internal int is_form_showing;

        Color stateColor = Color.Gainsboro;
        bool locking = false;
        internal ConcurrentQueue<string> files_to_send = new ConcurrentQueue<string>();
        internal Dictionary<long, ChatItem> messages = new Dictionary<long, ChatItem>();
        internal Int64 currentmin = -1, currentmax = -1;
        ChatItem currentChatItem;
        bool currentChatItemShowing;
        public bool isloadingoldmessages = false;

        public delegate void AddMessageItem(MessageObject message);
        public AddMessageItem AddMessageDelegate;

        public delegate void LoadMessageItem(List<MessageObject> messageObjects);
        public LoadMessageItem LoadMessageDelegate;

        internal delegate void RemoveMessageInvoker(long messagenumber);
        internal RemoveMessageInvoker RemoveMessage_Invoke;

        private void Must_initialize()
        {
            this.SetStyle(ControlStyles.SupportsTransparentBackColor |
                ControlStyles.UserPaint |
                ControlStyles.AllPaintingInWmPaint |
                ControlStyles.OptimizedDoubleBuffer |
                ControlStyles.ResizeRedraw, true);
            InitializeComponent();
            panel_Chat.BackColor = Color.Transparent;
            panelBottomRight.BackColor = Color.Transparent;
            panelTopRight.BackColor = Color.Transparent;
            LoadMessageDelegate = new LoadMessageItem(LoadMessage);
            AddMessageDelegate = new AddMessageItem(AddMessage);
            RemoveMessage_Invoke = new RemoveMessageInvoker(RemoveMessage_Passively);
            panel_Chat.MouseWheel += new System.Windows.Forms.MouseEventHandler(panel_Chat_MouseWheel);
            textboxWriting.LinkClicked += (o, e) => { System.Diagnostics.Process.Start(e.LinkText); };
            this.DoubleBuffered = true;
            this.CreateControl();
            //textboxWriting.dynamicMode = true;
            //textboxWriting.SetMaximumTextLenght(2021);
        }

        public PanelChat()
        {
            Must_initialize();
        }

        public PanelChat(Account account)
        {
            Must_initialize();
            this.is_form_showing = 0;
            this.is_showing = false;

            labelFriendName.Font = ApplicationFont.GetFont(labelFriendName.Font.Size);
            labelState.Font = ApplicationFont.GetFont(labelState.Font.Size);
            textboxWriting.Font = ApplicationFont.GetFont(textboxWriting.Font.Size);

            this.account = account;
            this.DoubleBuffered = true;
            this.Name = "panelChat_" + account.id;
            labelFriendName.Text = account.name;
            //textboxWriting.PlaceholderText = "to " + account.name;
            this.id = account.id;
            State = account.state;
            Console.WriteLine("Handler created");
            Console.WriteLine(this.id);
            panel_Chat.Click += panelTopRight_Click;
        }

        public static string ImageToString(Image im)
        {
            MemoryStream ms = new MemoryStream();
            im.Save(ms, System.Drawing.Imaging.ImageFormat.Png);
            byte[] array = ms.ToArray();
            return Convert.ToBase64String(array);
        }

        public Image Avatar
        {
            set
            {
                friendPicture.Crop(value);
                if (FormApplication.currentID == ID)
                {
                    Program.mainform.Invoke(Program.mainform.showPanelChatDelegate, new object[] { ID, true });
                }
            }
            get
            {
                return friendPicture.Image;
            }
        }

        public Icon AvatarIcon
        {
            get
            {
                return Icon.FromHandle(new Bitmap(friendPicture.Image, new Size(256, 256)).GetHicon());
            }
        }

        private void panel_Chat_MouseWheel(object sender, MouseEventArgs e)
        {
            if (isloadingoldmessages) panel_Chat.VerticalScroll.Value = current_vertical_value;
            if (panel_Chat.VerticalScroll.Value == 0 && !locking)
            {
                Int64 num = this.loadedmessagenumber - 1;
                if (num > 1)
                {
                    string datasend = num.ToString();
                    string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                    Console.WriteLine(datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend);
                    AFriendClient.Queue_command(Encoding.Unicode.GetBytes("6475" + this.ID + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                    locking = true;
                    timerChat.Start();
                    //panel_Chat.VerticalScroll.Value = 5;
                }
            }
        }

        private void panel_Chat_Scroll(object sender, ScrollEventArgs e)
        {
            if (isloadingoldmessages) panel_Chat.VerticalScroll.Value = current_vertical_value;
            if (panel_Chat.VerticalScroll.Value == 0 && !locking)
            {
                Int64 num = this.loadedmessagenumber - 1;
                if (num > 1)
                {
                    string datasend = num.ToString();
                    string datasendbyte = Encoding.Unicode.GetByteCount(datasend).ToString();
                    Console.WriteLine(datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend);
                    AFriendClient.Queue_command(Encoding.Unicode.GetBytes("6475" + this.ID + datasendbyte.Length.ToString().PadLeft(2, '0') + datasendbyte + datasend));
                    locking = true;
                    timerChat.Start();
                    //panel_Chat.VerticalScroll.Value = 5;
                }
            }
        }

        public ChatItem CurrentChatItem
        {
            get
            {
                return currentChatItem;
            }
            set
            {
                if (value == currentChatItem) 
                    return;
                Console.WriteLine("set");
                if (currentChatItem != null)
                {
                    currentChatItem.ShowDetail = currentChatItemShowing;
                }
                currentChatItem = value;
                currentChatItemShowing = !value.ShowDetail;
            }
        }

        public string ID
        {
            get { return this.id; }
        }

        public byte State
        {
            get
            {
                return state;
            }
            set
            {
                if (state != value)
                {
                    state = value;

                    if (state == 0)
                    {
                        stateColor = Color.Gainsboro;
                        labelState.Text = "offline";
                        labelState.ForeColor = stateColor;
                        panelTopRight.Invalidate();
                    }
                    else if (state == 1)
                    {
                        stateColor = Color.SpringGreen;
                        labelState.Text = "online";
                        labelState.ForeColor = stateColor;
                        panelTopRight.Invalidate();
                    }
                    else
                    {
                        stateColor = Color.Red;
                        labelState.Text = "away";
                        labelState.ForeColor = stateColor;
                        panelTopRight.Invalidate();
                    }
                    this.Invalidate();
                }
            }
        }

        public DateTime DateTimeOflastMessage
        {
            get
            {
                if (messages.Count == 0)
                {
                    return DateTime.Now;
                }
                else
                {
                    return messages[currentmax].messageObject.timesent;
                }
            }
        }

        internal void panelTopRight_Click(object sender, EventArgs e)
        {
            //this.OnClick(e);
            textboxWriting.Focus();
        }

        internal void RemoveMessage_Passively(long messagenumber)
        {
            byte type = messages[messagenumber].messageObject.type;
            Console.WriteLine("Begin deleting");
            panel_Chat.Controls.Remove(messages[messagenumber]);
            Console.WriteLine("{0},{1}", /*chatItems.Remove(messages[messagenumber]),*/ messages.Remove(messagenumber));
            Console.WriteLine("deleted: {0}", messagenumber);
            string id1 = AFriendClient.user.id;
            string id2 = id;
            if (type == 3) AFriendClient.files_on_cancel[id1 + "_" + id2 + "_" + messagenumber] = true;
        }

        public void RemoveMessage(long messagenumber)
        {
            //chatItems.Remove(messages[messagenumber]);
            byte type = messages[messagenumber].messageObject.type;
            panel_Chat.Controls.Remove(messages[messagenumber]);
            messages.Remove(messagenumber); 
            Program.mainform.contactItems[id].LastMessage = GetLastMessage();
            ReEvaluateMaxmin();
            if (messages.Count > 0)
            {
                ScrollControlIntoView(messages[currentmax]);
            }
            // code to remove message
            string id1 = AFriendClient.user.id;
            string id2 = id;
            if (type == 3) AFriendClient.files_on_cancel[id1+"_"+id2+"_"+messagenumber] = true;
            AFriendClient.Queue_command(Encoding.Unicode.GetBytes("2002"+this.ID+AFriendClient.data_with_byte(messagenumber.ToString())));
        }

        protected int timi = 240; // this is the elapsed time (in second) between 2 message needed to show timer

        public void AddMessage(MessageObject message)
        {
            try
            {
                if (messages.ContainsKey(message.messagenumber))
                {
                    Console.WriteLine($"message number {message.messagenumber} existed in this conversation!");
                    return;
                }
                /*
                if (messages.ContainsKey(message.messagenumber - 1))
                {
                    Console.WriteLine("Ton tai tin nhan phia truoc");
                }*/
                if (currentmin == -1 || currentmin > message.messagenumber) currentmin = message.messagenumber;
                if (currentmax == -1 || currentmax < message.messagenumber) currentmax = message.messagenumber;
                panel_Chat.SuspendLayout();
                ChatItem chatItem = new ChatItem(message);
                chatItem.Dock = DockStyle.Top;
                chatItem.BackColor = panel_Chat.BackColor;
                //chatItems.Add(chatItem);
                if (message.type == 3 || !messages.ContainsKey(message.messagenumber - 1) || messages[message.messagenumber - 1].messageObject.type == 3 || (message.timesent - messages[message.messagenumber - 1].messageObject.timesent).TotalSeconds > timi)
                {
                    chatItem.ShowDetail = true;
                }
                messages.Add(message.messagenumber, chatItem);
                panel_Chat.Controls.Add(chatItem);
                chatItem.UpdateDateTime();
                chatItem.BringToFront();
                panel_Chat.ResumeLayout();
                if (is_form_showing == 1 && panel_Chat.VerticalScroll.Value > panel_Chat.VerticalScroll.Maximum-350-chatItem.Height)
                    panel_Chat.ScrollControlIntoView(chatItem);
                else if (is_form_showing == 0 && panel_Chat.VerticalScroll.Value > panel_Chat.VerticalScroll.Maximum - 2*panel_Chat.Height - chatItem.Height)
                {
                    panel_Chat.ScrollControlIntoView(chatItem);
                } else if (chatItem.IsMyMessage())
                {
                    panel_Chat.ScrollControlIntoView(chatItem);
                }
                if (!chatItem.IsMyMessage() && is_form_showing > 0)
                {
                    FlashWindow.Flash(FormApplication.subForms[id]);
                    SoundPlayer snd = new SoundPlayer(Properties.Resources.message);
                    snd.Play();
                } else if (!chatItem.IsMyMessage())
                {
                    SoundPlayer snd = new SoundPlayer(Properties.Resources.message);
                    snd.Play();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private void AddMessageToTop(MessageObject message)
        {
            if (messages.ContainsKey(message.messagenumber))
            {
                Console.WriteLine($"message number {message.messagenumber} existed in this conversation!");
                return;
            }
            if (currentmin == -1 || currentmin > message.messagenumber) currentmin = message.messagenumber;
            if (currentmax == -1 || currentmax < message.messagenumber) currentmax = message.messagenumber;
            //await Task.Delay(5);
            this.loadedmessagenumber = message.messagenumber;
            Console.WriteLine(this.loadedmessagenumber);
            try
            {
                panel_Chat.SuspendLayout();
                ChatItem chatItem = new ChatItem(message);
                chatItem.Dock = DockStyle.Top;
                chatItem.BackColor = panel_Chat.BackColor;
                //chatItems.Insert(0, chatItem);
                chatItem.ShowDetail = true;
                if (messages.ContainsKey(message.messagenumber + 1) && (messages[message.messagenumber + 1].messageObject.timesent - message.timesent).TotalSeconds < timi)
                {
                    if (messages[message.messagenumber + 1].messageObject.type != 3)
                    {
                        messages[message.messagenumber + 1].ShowDetail = false;
                    }
                }
                chatItem.UpdateDateTime();
                messages.Add(message.messagenumber, chatItem);
                panel_Chat.Controls.Add(chatItem);
                panel_Chat.ResumeLayout();
            }catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            Console.WriteLine("Finish successfully");
        }

        public void textboxWriting_KeyDown(object sender, KeyEventArgs e)
        {
            textboxWriting.Select();
            if (e.KeyCode == Keys.Enter /*&& !locking*/ && !(e.Modifiers == Keys.Shift && e.KeyCode == Keys.Enter))
            {
                e.Handled = true;
                e.SuppressKeyPress = true;
                if (!string.IsNullOrWhiteSpace(textboxWriting.Text.TrimEnd()))
                {
                    AFriendClient.Send_to_id(id, AFriendClient.user.id, textboxWriting.Text.TrimEnd());
                    textboxWriting.Clear();
                    //textboxWriting.RemovePlaceHolder();
                    Console.WriteLine("Wrote");
                    //textboxWriting.Multiline = false;
                } else
                {
                    textboxWriting.Clear();
                }
            }
            else if (e.KeyCode == Keys.V && e.Modifiers == Keys.Control)
            {
                Thread temp = new Thread(() => { do_shit(sender, e); });
                temp.IsBackground = true;
                temp.SetApartmentState(ApartmentState.STA);
                temp.Start();
                e.Handled = true;
                e.SuppressKeyPress = true;
                //this.textboxWriting.Paste();
            }
        }

        private void do_shit(object sender, KeyEventArgs e)
        {
            Console.WriteLine("Doing");
            if (Clipboard.ContainsText())
            {
                Console.WriteLine("Text detected");
                //this.textboxWriting.Text += Clipboard.GetText();
                this.textboxWriting.Paste(DataFormats.GetFormat("Text"));
                //textboxWriting.
            }
            else if (Clipboard.ContainsImage())
            {
                Console.WriteLine("Image detected");
                Image img = Clipboard.GetImage();
                if (img != null)
                {
                    string img_string = ImageToString(img);
                    Console.WriteLine("Finished img to string\n");
                    var b = AFriendClient.Combine(Encoding.Unicode.GetBytes("1902" + id), Encoding.ASCII.GetBytes(AFriendClient.data_with_ASCII_byte(img_string)));
                    //var b = new Byte[200000];
                    //for (int i = 0; i < 200000; i++) b[i] = 0;
                    Console.WriteLine("before sending nude: {0}", b.Length);
                    AFriendClient.Queue_command(b);
                    //AFriendClient.Ping();
                    Console.WriteLine("Nude sent");
                }
            }
            //else
            //{
            //    Console.WriteLine("IDK");
            //}
        }

        public void buttonSend_Click(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(textboxWriting.Text.TrimEnd()) /*&& !locking*/)
            {
                AFriendClient.Send_to_id(id, AFriendClient.user.id, textboxWriting.Text.TrimEnd());
                textboxWriting.Text = "";
                //textboxWriting.RemovePlaceHolder();
                //textboxWriting.Multiline = false;
            }
        }

        private void panelTopRight_Paint(object sender, PaintEventArgs e)
        {
            using (Pen pen = new Pen(stateColor, 2))
            {
                e.Graphics.SmoothingMode = SmoothingMode.AntiAlias;
                e.Graphics.DrawEllipse(pen, friendPicture.Left - 1, friendPicture.Top - 1, friendPicture.Width + 2, friendPicture.Width + 2);
            }

            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, panelTopRight.Height - 1, panelTopRight.Width, panelTopRight.Height - 1);
                e.Graphics.DrawLine(pen, 0, panelTopRight.Height, 0, 0);
            }
        }

        private void panel_Chat_Click(object sender, EventArgs e)
        {
            panel_Chat.Focus();
        }

        public void LoadMessage()
        {
            AFriendClient.Queue_command(Encoding.Unicode.GetBytes("6475"+this.ID+"0120"));
        }

        private int current_vertical_value = 0;

        public void LoadMessage(List<MessageObject> messageObjects)
        {
            isloadingoldmessages = true;
            panel_Chat.SuspendLayout();
            var current_min_chat = currentmin;
            foreach(MessageObject messageObject in messageObjects)
            {
                AddMessageToTop(messageObject);
            }
            panel_Chat.ResumeLayout();
            if (panel_Chat.Controls.Count > messageObjects.Count)
            {
                panel_Chat.ScrollControlIntoView(messages[current_min_chat]);
                panel_Chat.VerticalScroll.Value += textboxWriting.Height;
                current_vertical_value = panel_Chat.VerticalScroll.Value;
            }
            isloadingoldmessages = false;
        }

        private void PanelChat_Load(object sender, EventArgs e)
        {
            textboxWriting.Focus();
            this.ActiveControl = textboxWriting;
        }

        private void ReEvaluateMaxmin()
        {
            if (messages.Count == 0) return;
            while (!messages.ContainsKey(currentmax)) currentmax -= 1;
            while (!messages.ContainsKey(currentmin)) currentmin += 1;
        }

        public string GetLastMessage()
        {
            if (messages.Count == 0)
                return "New conversation!";
            ReEvaluateMaxmin();
            var messageObject = messages[currentmax].messageObject;
            if (messageObject.type == 0)
            {
                return messageObject.message;
            }
            else if (messageObject.type == 1)
            {
                return "<Photo>";
            }
            return "";
        }
        public string GetFirstMessage()
        {
            if (messages.Count == 0)
                return "";
            ReEvaluateMaxmin();
            return messages[currentmin].messageObject.message;
        }

        public bool IsLastMessageFromYou()
        {
            if (panel_Chat.Controls.Count == 0)
                return true;
            ReEvaluateMaxmin();
            ChatItem message = messages[currentmax];
            if (message.IsMyMessage())
                return true;
            return false;
        }

        private void panel_Chat_ControlAdded(object sender, ControlEventArgs e)
        {
            this.OnControlAdded(e);
        }

        private void panel_Chat_ControlRemoved(object sender, ControlEventArgs e)
        {
            this.OnControlRemoved(e);
        }
        private void timerChat_Tick(object sender, EventArgs e)
        {
            locking = false;
            timerChat.Stop();
        }

        private void textboxWriting_SizeChanged(object sender, EventArgs e)
        {
            panelBottomRight.Height = textboxWriting.Height + 6 + buttonSend.Height; // fix this line, 6 is the padding height between textboxwriting and buttonSend
            panelBottomRight.Location = new Point(0, this.Height - panelBottomRight.Height);
            panel_Chat.Height = this.Height - panelBottomRight.Height - panelTopRight.Height;
        }

        private void panelBottomRight_Paint(object sender, PaintEventArgs e)
        {
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, 1, panelBottomRight.Width, 1);
                e.Graphics.DrawLine(pen, 0, 0, 0, panelBottomRight.Height);
            }
        }

        private void panelTopRight_Resize(object sender, EventArgs e)
        {
            panelTopRight.Invalidate();
        }

        private void panelBottomRight_Resize(object sender, EventArgs e)
        {
            panelBottomRight.Invalidate();
        }

        private void panel_Chat_Paint(object sender, PaintEventArgs e)
        {
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, 0, 0, panel_Chat.Height);
            }
        }

        private void textboxWriting__TextChanged(object sender, EventArgs e)
        {
            //textboxWriting.Multiline = true;
            this.OnClick(e);
        }

        public void ScrollToBottom()
        {
            if (panel_Chat.Controls.Count > 0)
            {
                panel_Chat.ScrollControlIntoView(panel_Chat.Controls[0]);
            }
        }

        private void sendImageButton_Click(object sender, EventArgs e)
        {
            Thread thread = new Thread(() =>
            {
                using (OpenFileDialog ofd = new OpenFileDialog())
                {
                    ofd.Filter = "Images|*.pjp;*.jpg;*.pjpeg;*.jpeg;*.jfif;*.png";
                    ofd.Multiselect = true;
                    if (ofd.ShowDialog() == DialogResult.OK)
                    {
                        foreach (string file in ofd.FileNames)
                        {
                            try
                            {
                                using (Image img = Image.FromFile(file))
                                {
                                    if (img != null)
                                    {
                                        string img_string = ImageToString(img);
                                        Console.WriteLine("Finished img to string\n");
                                        var b = AFriendClient.Combine(Encoding.Unicode.GetBytes("1902" + id), Encoding.ASCII.GetBytes(AFriendClient.data_with_ASCII_byte(img_string)));
                                        //var b = new Byte[200000];
                                        //for (int i = 0; i < 200000; i++) b[i] = 0;
                                        Console.WriteLine("before sending nude: {0}", b.Length);
                                        AFriendClient.Queue_command(b);
                                        //AFriendClient.Ping();
                                        Console.WriteLine("Nude sent");
                                    }
                                }
                            }
                            catch (Exception ex)
                            {
                                Console.WriteLine(ex.ToString());
                                FormSettings.TopMostMessageBox.Show("Cannot use this file: " + file, file);
                            }
                        }
                    }
                }
            });
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
        }

        private void SendFileButton_Click(object sender, EventArgs e)
        {
            Thread thread = new Thread(() =>
            {
                using (OpenFileDialog ofd = new OpenFileDialog())
                {
                    ofd.Filter = "All files (*.*)|*.*";
                    ofd.Multiselect = true;
                    if (ofd.ShowDialog() == DialogResult.OK)
                    {
                        foreach (string file in ofd.FileNames)
                        {
                            AFriendClient.Queue_command(AFriendClient.Combine(Encoding.Unicode.GetBytes("1903" 
                                + id + AFriendClient.data_with_byte(Path.GetFileName(file))) 
                                ,Encoding.ASCII.GetBytes(AFriendClient.data_with_ASCII_byte((new FileInfo(file).Length).ToString()))));
                            files_to_send.Enqueue(file);
                        }
                    }
                }
            });
            thread.SetApartmentState(ApartmentState.STA);
            thread.Start();
        }

        private void buttonDelete_Click(object sender, EventArgs e)
        {
            DialogResult dialogResult = MessageBox.Show("You are about to delete your conversation with this person, this action cannot be undone, are you sure you want to DELETE ALL YOUR MESSAGES WITH THIS PERSON?", "Warning", MessageBoxButtons.YesNo, MessageBoxIcon.Warning, MessageBoxDefaultButton.Button2);
            if (dialogResult == DialogResult.Yes)
            {
                dialogResult = MessageBox.Show("This action will DELETE ALL YOUR MESSAGES with THIS PERSON! Think twice! Are you serious?", "Warning", MessageBoxButtons.YesNo, MessageBoxIcon.Warning, MessageBoxDefaultButton.Button2);
                if (dialogResult == DialogResult.Yes)
                {
                    if (this.Parent != null && this.Parent.Parent != null && this.Parent.Parent is FormApplication)
                    {
                        AFriendClient.Queue_command(Encoding.Unicode.GetBytes("5859" + this.ID));
                        (this.Parent.Parent as FormApplication).RemoveContact(this.ID);
                    }
                }
            }
        }
    }
}
