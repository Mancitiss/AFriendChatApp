using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Threading;
using System.Runtime.InteropServices;

namespace A_Friend
{
    
    public partial class FormLogin : Form
    {
        public const int WM_NCLBUTTONDOWN = 0xA1;
        public const int HT_CAPTION = 0x2;
        [DllImportAttribute("user32.dll")]
        public static extern int SendMessage(IntPtr hWnd, int Msg, int wParam, int lParam);
        [DllImportAttribute("user32.dll")]
        public static extern bool ReleaseCapture();

        private void Form1_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                ReleaseCapture();
                SendMessage(Handle, WM_NCLBUTTONDOWN, HT_CAPTION, 0);
            }
        }

        protected override bool ShowWithoutActivation { get { return true; } }

        public bool lockLogin = false;

        public FormLogin()
        {
            InitializeComponent();
            textBoxUserName.Font = ApplicationFont.GetFont(textBoxUserName.Font.Size);
            textBoxPassword.Font = ApplicationFont.GetFont(textBoxPassword.Font.Size);
            labelWarning.Font = ApplicationFont.GetFont(labelWarning.Font.Size);
            buttonExit.Font = ApplicationFont.GetFont(buttonExit.Font.Size);
            buttonSignUp.Font = ApplicationFont.GetFont(buttonSignUp.Font.Size);
            buttonLogIn.Font = ApplicationFont.GetFont(buttonLogIn.Font.Size);
            labelTittle.Font = ApplicationFont.GetFont(labelTittle.Font.Size);

            labelWarning.Text = "";
            this.MouseDown += (sender, e) => Form1_MouseDown(sender, e);
            Console.WriteLine("test");
        }

        private void ResetTexts()
        {
            textBoxUserName.Texts = "";
            textBoxPassword.Texts = "";
            labelWarning.Text = "";
        }

        private void buttonSignUp_Click(object sender, EventArgs e)
        {
            var frm = new FormSignUp();
            frm.Location = this.Location;
            frm.StartPosition = FormStartPosition.Manual;
            frm.FormClosing += delegate { this.Show(); };
            this.ResetTexts();
            frm.Show();
            this.Hide();           
        }

        private void buttonExit_Click(object sender, EventArgs e)
        {
            Environment.Exit(0);
        }

        private void buttonLogIn_Click(object sender, EventArgs e)
        {
            Login();
        }

        private bool CheckInvalidUsernameCharacter()
        {
            foreach (char i in textBoxUserName.Texts)
            {
                if (!(i >= 48 && i <= 57 || i >= 65 && i <= 90 || i >= 97 && i <= 122 || i == 95))
                {
                    return true;
                }
            }
            return false;
        }

        private bool CheckInvalidPasswordCharacter()
        {
            foreach (char i in textBoxPassword.Texts)
            {
                if (!(i == 33 || i > 34 && i < 38 || i >= 42 && i <= 43 || i == 45 || i >= 48 && i <= 57 || i >= 64 && i <= 90 || i == 94 || i == 95 || i >= 97 && i <= 122))
                {
                    return true;
                }
            }
            return false;
        }


        private void Login()
        {
            timerDisconnect.Enabled = true;
            timerDisconnect.Start();
            if (this.EmptyTextBoxes())
            {
                if (EmptyTextBoxes())
                {
                    labelWarning.Text = "Something is missing!";
                    return;
                }
            }
            else
            {
                if (CheckInvalidUsernameCharacter())
                {
                    labelWarning.Text = "User name or Password is incorrect";
                    return;
                }
                else
                {
                    if (CheckInvalidPasswordCharacter())
                    {
                        labelWarning.Text = "User name or Password is incorrect";
                        return;
                    }
                    else
                    {
                        if (!CorrectPassword())
                        {
                            labelWarning.Text = "User name or Password is incorrect";
                            return;
                        }
                    }
                }
            }
            labelWarning.Text = "You have logged in successfully".ToUpper();
            labelWarning.ForeColor = Color.FromArgb(37, 75, 133);
            timerClosing.Start();
        }

        private bool CorrectPassword()
        {
            bool res = AFriendClient.Logged_in(textBoxUserName.Texts, textBoxPassword.Texts);
            AFriendClient.loginResult = true;
            return res;
        }

        private bool EmptyTextBoxes()
        {
            if(string.IsNullOrEmpty(textBoxUserName.Texts) || string.IsNullOrEmpty(textBoxPassword.Texts))
            {
                return true;
            }
            return false;
        }

        private void textBoxUserName_KeyDown(object sender, KeyEventArgs e)
        {
            if  (e.KeyCode == Keys.Enter) 
            {
                buttonLogIn.PerformClick();
            }
        }

        private void textBoxPassword_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                buttonLogIn.PerformClick();
            }
        }

        private void textBoxUserName_Enter(object sender, EventArgs e)
        {
            if (labelWarning.Text == "" || labelWarning.Text == "Something is missing!")
            {
                return;
            }
            this.ResetTexts();
        }

        private void textBoxPassword_Enter(object sender, EventArgs e)
        {
            if (labelWarning.Text == "" || labelWarning.Text == "Something is missing!")
            {
                return;
            }
            this.ResetTexts();
        }

        private void timerClosing_Tick(object sender, EventArgs e)
        {
            timerClosing.Stop();
            var frm = new FormApplication();
            frm.Location = new System.Drawing.Point(Screen.FromControl(this).WorkingArea.Width / 4, Screen.FromControl(this).WorkingArea.Height / 4);
            frm.StartPosition = FormStartPosition.CenterScreen;
            //frm.FormClosing += delegate { this.Show(); this.Opacity = 1; };
            this.ResetTexts();
            frm.Show();
            this.Hide();
            Program.mainform = frm;
            Thread thread = new Thread(AFriendClient.ExecuteClient);
            thread.IsBackground = true;
            thread.Start();
        }
        private void timerDisconnect_Tick(object sender, EventArgs e)
        {
            timerDisconnect.Stop();
            if (labelWarning.Text == "" || labelWarning.Text == "Something is missing!")
            {
                AFriendClient.stream.Close();
                AFriendClient.client.Close();
                labelWarning.Text = "Cannot connect to the server";
            }
        }

        private void FormLogin_Load(object sender, EventArgs e)
        {
            this.Text = "\t";
            this.ControlBox = false;
            //this.TopMost = true;
            //this.TopMost = false;
            this.Activate();
            this.TopMost = true;
            this.BringToFront();
            this.TopMost = false;
        }
    }
}
