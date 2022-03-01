using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace A_Friend
{
    public partial class FormApplication : Form
    {
        /*
        protected override CreateParams CreateParams
        {
            get
            {
                const int WS_EX_COMPOSITED = 0x02000000;
                var cp = base.CreateParams;
                cp.ExStyle |= WS_EX_COMPOSITED;
                return cp;
            }
        }*/

        public delegate void SortContactItemsdelegate();
        public SortContactItemsdelegate sort_contact_item_delegate;
        public delegate void SetAvatarDelegate(string id, Image img);
        public SetAvatarDelegate set_avatar_delegate;
        public delegate void ShowLoginDelegate();
        public ShowLoginDelegate show_login_delegate;

        public A_Friend.CustomControls.PanelChat currentpanelchat;

        public delegate void ShowPanelChatDelegate(string id, bool force);
        public ShowPanelChatDelegate showPanelChatDelegate;
        public delegate void AddContactItem(Account acc);
        public AddContactItem addContactItemDelegate;
        public delegate void AddMessageItem(string str, bool left);
        public AddMessageItem addMessageItemDelegate;
        public delegate void TurnContactActiveState(string id, byte state);
        public TurnContactActiveState turnContactActiveStateDelegate;
        public Dictionary<string, CustomControls.PanelChat> panelChats = new Dictionary<string, CustomControls.PanelChat>();
        public static string currentID;

        internal Dictionary<string, CustomControls.ContactItem> contactItems = new Dictionary<string, CustomControls.ContactItem>();
        SortedDictionary<int, string> orderOfContactItems = new SortedDictionary<int, string>();

        public string currentUsername;
        public CustomControls.ContactItem currentContactItem; 
        private Panel panelRight2 = new Panel();
        private Panel panelContact2 = new Panel();
        private Panel panelGetStarted = new Panel();
        internal Panel panelLoading = new Panel();
        private PictureBox pictureBoxNotFound = new PictureBox();
        private FormContactRemoved formContactRemoved = new FormContactRemoved();
        private FormGetStarted formGetStarted = new FormGetStarted();
        internal FormLoading formLoading = new FormLoading();    
        public FormSettings formSettings = new FormSettings();
        public FormAddContact formAddContact = new FormAddContact();
        private bool check = true;
        private string searchText = "";
        internal bool loaded = false;
        private bool priv = false;

        internal static ConcurrentDictionary<string, Form> subForms = new ConcurrentDictionary<string, Form>();

        public FormApplication()
        {
            this.SetStyle(ControlStyles.SupportsTransparentBackColor | 
                ControlStyles.UserPaint |
                ControlStyles.AllPaintingInWmPaint |
                ControlStyles.OptimizedDoubleBuffer | 
                ControlStyles.ResizeRedraw, true);
            InitializeComponent();
            panelRight.BackgroundImageLayout = ImageLayout.Stretch;
            panelRight2.BackgroundImageLayout = ImageLayout.Stretch;
            this.ResizeBegin += FormApplication_ResizeBegin;
            this.ResizeEnd += FormApplication_ResizeEnd;
            InitializeSubPanels();
            showPanelChatDelegate = new ShowPanelChatDelegate(ShowPanelChat);
            addContactItemDelegate = new AddContactItem(AddContact);
            turnContactActiveStateDelegate = new TurnContactActiveState(TurnActiveState);
            sort_contact_item_delegate = new SortContactItemsdelegate(SortContactItems);
            set_avatar_delegate = new SetAvatarDelegate(SetAvatar);
            show_login_delegate = new ShowLoginDelegate(ShowLogin);
            customTextBoxSearch.Font = ApplicationFont.GetFont(customTextBoxSearch.Font.Size);
            this.Text += $" {Program.thisversion[0]}.{Program.thisversion[1]}.{Program.thisversion[2]}.{Program.thisversion[3]}";
            this.Size = new System.Drawing.Size(Screen.FromControl(this).WorkingArea.Width / 2, Screen.FromControl(this).WorkingArea.Height / 2);
        }

        private void FormApplication_ResizeBegin(Object sender, EventArgs e)
        {
            this.SuspendLayout();
        }

        private void FormApplication_ResizeEnd(Object sender, EventArgs e)
        {
            this.ResumeLayout(true);
        }

        private void FormApplication_Load(object sender, EventArgs e)
        {
            this.SuspendLayout();

            notifyIconApp.BalloonTipTitle = "Notify";
            notifyIconApp.BalloonTipText = "Apps running in the background";
            notifyIconApp.Text = "AppChat";
            this.ResumeLayout();
        }

        private void InitializeSubPanels()
        {
            this.Controls.Add(panelRight2);
            this.panelRight2.Anchor = panelRight.Anchor;
            this.panelRight2.Location = panelRight.Location;
            this.panelRight2.Size = panelRight.Size;
            this.panelRight2.Margin = panelRight.Margin;

            this.panelLeft.Controls.Add(this.panelContact2);
            this.panelContact2.Padding = panelContact.Padding;
            this.panelContact2.Margin = panelContact.Margin;
            this.panelContact2.Anchor = panelContact.Anchor;
            this.panelContact2.AutoScroll = true;
            this.panelContact2.BackColor = panelContact.BackColor;
            this.panelContact2.Location = panelContact.Location;
            this.panelContact2.Size = panelContact.Size;

            this.Controls.Add(panelGetStarted);
            panelGetStarted.SendToBack();
            panelGetStarted.Anchor = panelRight.Anchor;
            panelGetStarted.Location = new Point(0, 0);
            panelGetStarted.Size = new Size(this.Width, panelBottomLeft.Top + 2);
            panelGetStarted.Padding = new Padding(1,0,0,0);
            panelGetStarted.Resize += (o, e) => {
                Console.WriteLine("PanelGetStarted Paint delegate");
                if (panelGetStarted.Width != this.Width) 
                {
                    var graphic = panelGetStarted.CreateGraphics();
                    using (Pen pen = new Pen(Color.Gray, 1))
                    {
                        graphic.DrawLine(pen, 0, 0, 0, panelGetStarted.Height - 1);
                    }
                }
            };    

            panelAdd.Hide();
            formAddContact.Dock = DockStyle.Fill;
            formAddContact.TopLevel = false;
            panelAdd.Controls.Add(formAddContact);
            panelAdd.BringToFront();
            formAddContact.Visible = true;

            this.Controls.Add(panelLoading);
            panelLoading.Location = new Point(0, 0);
            panelLoading.Size = this.Size;
            panelLoading.Anchor = AnchorStyles.Top | AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;
            formLoading.TopLevel = false;
            formLoading.Size = panelLoading.Size;
            formLoading.Anchor = panelLoading.Anchor;
            //formLoading.Dock = DockStyle.Fill;
            formLoading.Visible = true;
            panelLoading.Controls.Add(formLoading);
            panelLoading.BringToFront();

            pictureBoxNotFound.Dock = DockStyle.Fill;
            pictureBoxNotFound.Image = global::A_Friend.Properties.Resources.no_result_found;
            pictureBoxNotFound.SizeMode = PictureBoxSizeMode.Zoom;
        }

        private void buttonSend_Click(object sender, EventArgs e)
        {
            currentpanelchat.buttonSend_Click(sender, e);
        }
        private void textboxWriting_KeyDown(object sender, KeyEventArgs e)
        {
            currentpanelchat.textboxWriting_KeyDown(sender, e);
        }

        public void AddContact(Account account)
        {
            try
            {
                if (!panelChats.ContainsKey(account.id))
                {
                    panelContact.SuspendLayout();
                    var contactItem = new CustomControls.ContactItem(account);
                    contactItem.Dock = DockStyle.Top;
                    contactItem.BackColor = panelContact.BackColor;
                    contactItem.Unread = true;
                    //contactItem.BringToFront();
                    if (loaded)
                    {
                        panelContact.Controls.Add(contactItem);
                    }
                    panelContact.ResumeLayout();


                    if (orderOfContactItems.Count == 0)
                    {
                        orderOfContactItems.Add(0, account.id);
                    }
                    else
                    {
                        orderOfContactItems.Add(orderOfContactItems.Keys.Last() + 1, account.id);
                    }

                    if (loaded)
                    {
                        panelContact.ScrollControlIntoView(contactItem);
                    }
                    contactItems.Add(account.id, contactItem);
                    CustomControls.PanelChat panelChat = new CustomControls.PanelChat(account);
                    panelChats.Add(account.id, panelChat);

                    panelChat.LoadMessage();
                    panelChat.ScrollToBottom();
                    contactItem.LastMessage = panelChat.GetLastMessage();

                    panelChat.ControlAdded += delegate
                    {
                        contactItem.LastMessage = panelChat.GetLastMessage();
                        if (loaded && !panelChat.IsLastMessageFromYou())
                        {
                            contactItem.Unread = true;
                        }
                        if (contactItem.ID != orderOfContactItems.Values.Last())
                        {
                            if (!panelChat.isloadingoldmessages)
                            {
                                BringContactItemToTop(panelChat.ID);
                            }
                        }
                    };

                    panelChat.ControlRemoved += delegate
                    {
                        contactItem.LastMessage = panelChat.GetLastMessage();
                    };

                    contactItem.Click += delegate
                    {
                        if (!FormApplication.subForms.TryGetValue(account.id, out Form form1)) {
                            ShowPanelChat(account.id);
                            //contactItem.Unread = true;
                            panelChat.ScrollToBottom();

                            if (!string.IsNullOrEmpty(customTextBoxSearch.Texts))
                            {
                                check = false;
                                customTextBoxSearch.Texts = "";
                                this.ActiveControl = contactItem;
                                //customTextBoxSearch.SetPlaceHolder();
                                panelContact2.Controls.Clear();
                                panelContact.Controls.Clear();
                                foreach (KeyValuePair<int, string> i in orderOfContactItems)
                                {
                                    panelContact.Controls.Add(contactItems[i.Value]);
                                }
                                panelContact.BringToFront();
                                check = true;
                            }

                            if (!panelChat.IsLastMessageFromYou() && contactItem.Unread)
                            {
                                contactItem.Unread = false;
                                AFriendClient.Queue_command(Encoding.Unicode.GetBytes("1234" + account.id + "1"));
                            } 
                        }
                    };

                    panelChat.Click += delegate
                    {
                        if (!panelChat.IsLastMessageFromYou() && contactItem.Unread)
                        {
                            contactItem.Unread = false;
                            AFriendClient.Queue_command(Encoding.Unicode.GetBytes("1234" + account.id + "1"));
                        }
                    };
                }
                else
                {
                    formAddContact.ChangeWarning("This user existed in your contacting list!", Color.Red);
                }
            } catch(Exception e)
            {
                Console.WriteLine(e);
                throw e;
            }
        }

        public void SortContactItems()
        {
            try
            {
                int lenght = contactItems.Count;
                for (int i = 0; i < contactItems.Count; i++)
                {
                    string min = "";
                    int j = 0;
                    foreach (KeyValuePair<int, string> keyValuePair in orderOfContactItems)
                    {
                        if (j == lenght)
                            break;
                        if (min == "")
                        {
                            min = keyValuePair.Value;
                        }
                        else
                        {
                            if (panelChats[min].DateTimeOflastMessage > panelChats[keyValuePair.Value].DateTimeOflastMessage)
                            {
                                min = keyValuePair.Value;
                            }
                        }
                        j++;
                    }
                    lenght--;
                    BringContactItemToTop(min);
                }

                foreach (KeyValuePair<int, string> keyValuePair1 in orderOfContactItems)
                {
                    panelContact.Controls.Add(contactItems[keyValuePair1.Value]);
                }

                loaded = true;

                panelLoading.SendToBack();
                formLoading.StopSpinning();
                formLoading.Dispose();
                panelLoading.Dispose();

                if (panelChats.Count > 0)
                {
                    ShowPanelChat(orderOfContactItems.Values.Last());
                    panelChats[orderOfContactItems.Values.Last()].ScrollToBottom();
                    this.currentContactItem = contactItems[orderOfContactItems.Values.Last()];
                    this.currentContactItem.Clicked = true;
                }
                else
                {
                    panelRight.Controls.Clear();
                    customTextBoxSearch.Visible = false;
                    formGetStarted.Dock = DockStyle.Fill;
                    formGetStarted.TopLevel = false;
                    formGetStarted.FormBorderStyle = FormBorderStyle.None;
                    panelGetStarted.Controls.Add(formGetStarted);
                    panelGetStarted.BringToFront();
                    formGetStarted.Visible = true;
                }
            } catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        private string GetCurrentPanelChatId()
        {
            if (panelRight.Controls.Count > 0)
            {
                if (panelRight.Controls[0] is CustomControls.PanelChat)
                {
                    return (panelRight.Controls[0] as CustomControls.PanelChat).ID;
                }
            }

            if (panelRight2.Controls.Count > 0)
            {
                if (panelRight2.Controls[0] is CustomControls.PanelChat)
                {
                    return (panelRight2.Controls[0] as CustomControls.PanelChat).ID;
                }

            }

            return "";
        }

        private void BringContactItemToTop(string id)
        {
            if (orderOfContactItems.Count <= 1)
                return;

            int key = -1;
            foreach (KeyValuePair<int, string> i in orderOfContactItems)
            {
                if (i.Value == id)
                {
                    key = i.Key;
                    break;
                }
            }

            if (key != -1)
            {
                orderOfContactItems.Remove(key);
                orderOfContactItems.Add(orderOfContactItems.Keys.Last() + 1, id);
            }

            if (loaded)
            {
                CustomControls.ContactItem item = contactItems[id];
                if (searchText == "")
                {
                    panelContact.Controls.Remove(item);
                    panelContact.Controls.Add(item);
                }
            }
        }

        public void ShowPanelChat(string id, bool force = false)
        {
            CustomControls.PanelChat item = panelChats[id];

            if (panelRight.Controls.Count == 0)
            {
                if (force || (GetCurrentPanelChatId() == "") || !(panelRight2.Controls[0] is CustomControls.PanelChat) || (panelRight2.Controls[0] as CustomControls.PanelChat).ID != id)
                {
                    panelRight.BackgroundImage = Tools.SetImgOpacity(Tools.ResizeImage(item.Avatar, panelRight.Width, panelRight.Height), 0.19f);
                    panelRight.BackColor = Color.Transparent;
                    panelRight.Controls.Add(item);
                    panelRight.BringToFront();
                    panelRight2.SendToBack();
                    panelRight2.Controls.Clear();
                }
            }
            else
            {
                if (force || (GetCurrentPanelChatId() == "") || !(panelRight.Controls[0] is CustomControls.PanelChat) || (panelRight.Controls[0] as CustomControls.PanelChat).ID != id)
                {
                    panelRight2.BackgroundImage = Tools.SetImgOpacity(Tools.ResizeImage(item.Avatar, panelRight2.Width, panelRight2.Height), 0.19f);
                    panelRight2.BackColor = Color.Transparent;
                    panelRight2.Controls.Add(item);
                    panelRight2.BringToFront();
                    panelRight.SendToBack();
                    panelRight.Controls.Clear();
                }
            }
            item.Dock = DockStyle.Fill;
            currentID = item.ID;
            currentpanelchat = item;
        }

        // state (0,1,2) => (offline, online, away)
        public void TurnActiveState(string id, byte state)
        {
            if (panelChats.ContainsKey(id))
            {
                CustomControls.PanelChat item = panelChats[id];
                item.State = state;
            }

            if (contactItems.ContainsKey(id))
            {
                CustomControls.ContactItem item = contactItems[id];
                item.State = state;
            }
        }

        private bool logout = false;

        private void LogoutButton_Click_1(object sender, EventArgs e)
        {
            AFriendClient.Queue_command(Encoding.Unicode.GetBytes("2004"));
            foreach(Form f in subForms.Values)
            {
                for(int i = f.Controls.Count -1; i>=0; i--)
                {
                    f.Controls[i].Dispose();
                }
                f.Dispose();
            }
            this.logout = true;
            for (int i = this.Controls.Count - 1; i >= 0; i--)
            {
                this.Controls[i].Dispose();
            }
            foreach(CustomControls.PanelChat p in panelChats.Values)
            {
                foreach (Control c in p.Controls) c.Dispose();
                p.Dispose();
            }
            this.Dispose(true);
            FormLogin lg = new FormLogin();
            lg.Show();
            Program.mainform = null;
            try
            {
                if (AFriendClient.user != null)
                {
                    AFriendClient.user.state = 0;
                }
            } catch           
            {
                AFriendClient.user = null;
            }
            GC.Collect();
        }

        private void SettingButton_Click(object sender, EventArgs e)
        {
            formSettings.StartPosition = FormStartPosition.CenterScreen;
            this.Hide();
            formSettings.ShowDialog();
            this.Show();
        }

        public void ButtonAdd_Click_1(object sender, EventArgs e)
        {
            PanelGetStartedSlideToRight();

            if (panelContact.Height == panelBottomLeft.Top - panelTopLeft.Bottom)
            {
                panelContact.Height -= panelAdd.Height;
                panelContact2.Height -= panelAdd.Height;
                formAddContact.ResetTexts();
                formAddContact.ChangeWarning("Enter your friend's ID", Color.FromArgb(143, 228, 185));
                panelAdd.Show();
            }
            else
            {
                panelContact.Height += panelAdd.Height;
                panelContact2.Height += panelAdd.Height;
                panelAdd.Hide();
            }
        }

        private void FormApplication_FormClosed(object sender, FormClosedEventArgs e)
        {
            Program.mainform = null;
            Application.Exit();
        }

        private bool UsernameCheck()
        {
            return true;
        }

        private void customTextBoxSearch__TextChanged(object sender, EventArgs e)
        {
            if (check)
            {
                string text = customTextBoxSearch.Texts.Trim().ToLower();
                if (text == searchText)
                    return;
                searchText = text;
                if (!string.IsNullOrEmpty(searchText))
                {
                    if (panelContact.Controls.Count > 0)
                    {
                        foreach (KeyValuePair<string, CustomControls.ContactItem> i in contactItems)
                        {
                            if (i.Value.FriendName.ToLower().Contains(searchText))
                            {
                                panelContact2.Controls.Add(i.Value);
                            }
                        }
                        if (panelContact2.Controls.Count == 0)
                        {
                            if (panelContact.Controls.Contains(pictureBoxNotFound))
                            {
                                return;
                            }
                            panelContact2.Controls.Add(pictureBoxNotFound);
                            pictureBoxNotFound.BringToFront();
                        }
                        panelContact2.BringToFront();
                        panelContact.Controls.Clear();
                    }
                    else
                    {
                        foreach (KeyValuePair<string, CustomControls.ContactItem> i in contactItems)
                        {
                            if (i.Value.FriendName.ToLower().Contains(searchText))
                            {
                                panelContact.Controls.Add(i.Value);
                            }
                        }
                        if (panelContact.Controls.Count == 0)
                        {
                            if (panelContact2.Controls.Contains(pictureBoxNotFound))
                            {
                                return;
                            }
                            panelContact.Controls.Add(pictureBoxNotFound);
                            pictureBoxNotFound.BringToFront();
                        }
                        panelContact.BringToFront();
                        panelContact2.Controls.Clear();
                    }
                }
                else
                {
                    panelContact.Controls.Clear();
                    panelContact2.Controls.Clear();
                    foreach(KeyValuePair<int, string> i in orderOfContactItems)
                    {
                        panelContact.Controls.Add(contactItems[i.Value]);
                    }
                    panelContact.BringToFront();
                }
            }
        }

        internal bool Is_this_person_added(string id)
        {
            return contactItems.ContainsKey(id);
        }

        private void notifyIconApp_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.Show();
            notifyIconApp.Visible = false;
            WindowState = FormWindowState.Normal;
        }
        private void closeMessengerToolStripMenuItem_Click(object sender, EventArgs e)
        {
            try
            {
                AFriendClient.Queue_command(Encoding.Unicode.GetBytes("2004"));
            }
            catch (Exception done)
            {

            }
            System.Environment.Exit(0);
            notifyIconApp.Visible = false;
            notifyIconApp.Icon = null;
        }

        private void FormApplication_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (WindowState == FormWindowState.Normal && !logout)
            {
                e.Cancel = true;
                ((Control)sender).Hide();
                notifyIconApp.Visible = true;
                //notifyIconApp.ShowBalloonTip(1000);
            }
            if (!loaded && !logout)
            {
                try
                {
                    AFriendClient.Queue_command(Encoding.Unicode.GetBytes("2004"));
                } catch (Exception done)
                {

                }
                System.Environment.Exit(0);
                notifyIconApp.Visible = false;
                notifyIconApp.Icon = null;
            }
            if (logout)
            {
                try
                {
                    AFriendClient.Queue_command(Encoding.Unicode.GetBytes("2004"));
                }
                catch (Exception done)
                {

                }
                notifyIconApp.Visible = false;
                notifyIconApp.Icon = null;
            }
        }

        private void PanelGetStartedSlideToRight()
        {
            panelGetStarted.Location = panelRight.Location;
            panelGetStarted.Size = new Size(panelRight.Width, panelLeft.Height);
            formGetStarted.TopColor = panelTopLeft.BackColor;
            formGetStarted.BottomColor = panelBottomLeft.BackColor;
            var graphic = panelGetStarted.CreateGraphics();
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                Console.WriteLine("PanelGetStarted Paint");
                graphic.DrawLine(pen, 0, 0, 0, panelGetStarted.Height - 1);
            }
        }

        private void PanelGetStartedFill()
        {
            if (contactItems.Count == 0) {
                panelGetStarted.Location = new Point(0, 0);
                panelGetStarted.Size = new Size(this.Width, panelGetStarted.Height);
                formGetStarted.TopColor = Color.White;
            }
        }

        private void panelContact_ControlAdded(object sender, ControlEventArgs e)
        {
            if (panelGetStarted.Location.X != panelRight.Location.X)
            {
                PanelGetStartedSlideToRight();
            }
            customTextBoxSearch.Visible = true;
        }

        private void panelTopLeft_Paint(object sender, PaintEventArgs e)
        {
            Console.WriteLine("panelTopLeft_Paint");
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, panelTopLeft.Height - 1, panelTopLeft.Width, panelTopLeft.Height - 1);
            }
        }

        private void panelBottomLeft_Paint(object sender, PaintEventArgs e)
        {
            Console.WriteLine("panelBottomLeft_Paint");
            using (Pen pen = new Pen(Color.Gray, 1))
            {
                e.Graphics.DrawLine(pen, 0, 1, panelBottomLeft.Width - 0, 1);
            }
        }

        public void RemoveContact(string id)
        {
            if (!panelChats.ContainsKey(id) || !contactItems.ContainsKey(id))
                return;
            this.ActiveControl = null;
            if (panelContact.Controls.Contains(contactItems[id]))
            {
                panelContact.Controls.Remove(contactItems[id]);
            }
            if (panelContact2.Controls.Contains(contactItems[id]))
            {
                panelContact2.Controls.Remove(contactItems[id]);
            }

            if (panelRight.Controls.Contains(panelChats[id]))
            {
                if (id == GetCurrentPanelChatId())
                {
                    panelRight.Controls.Remove(panelChats[id]);
                    formContactRemoved.Dock = DockStyle.Fill;
                    formContactRemoved.TopLevel = false;
                    panelRight.Controls.Add(formContactRemoved);
                    panelRight.BringToFront();
                    formContactRemoved.Visible = true;
                }
                else
                {
                    panelRight.Controls.Remove(panelChats[id]);
                }
            }
            else if (panelRight2.Controls.Contains(panelChats[id]))
            {
                if (id == GetCurrentPanelChatId())
                {
                    panelRight2.Controls.Remove(panelChats[id]);
                    formContactRemoved.Dock = DockStyle.Fill;
                    formContactRemoved.TopLevel = false;
                    panelRight2.Controls.Add(formContactRemoved);
                    panelRight2.BringToFront();
                    formContactRemoved.Visible = true;
                }
                else
                {
                    panelRight2.Controls.Remove(panelChats[id]);
                }
            }

            panelChats.Remove(id);
            contactItems.Remove(id);
            foreach (KeyValuePair<int, string> pair in orderOfContactItems)
            {
                if (pair.Value == id)
                {
                    orderOfContactItems.Remove(pair.Key);
                    break;
                }
            }
        }

        public void SetAvatar(string id, Image img)
        {
            if (panelChats.ContainsKey(id))
            {
                panelChats[id].Avatar = img;
                contactItems[id].Avatar = img;
            }
        }

        public void ShowLogin()
        {
            foreach (Form f in subForms.Values)
            {
                f.Close();
            }
            this.Close();
            FormLogin lg = new FormLogin();
            lg.Show();
            Program.mainform = null;
            try
            {
                if (AFriendClient.user != null)
                {
                    AFriendClient.user.state = 0;
                }
            }
            catch
            {
                AFriendClient.user = null;
            }
            MessageBox.Show("You have been logged out due to a login from another device.");
        }

        public void ChangePrivateMode(bool priv)
        {
            this.priv = priv;
        }
    }
}
