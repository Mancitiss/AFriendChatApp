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
    public partial class FormSignUp : Form
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

        public Font ChangeFontSize(Font font, float fontSize)
        {
            if (font != null)
            {
                float currentSize = font.Size;
                if (currentSize != fontSize)
                {
                    font = new Font(font.Name, fontSize,
                        font.Style, font.Unit,
                        font.GdiCharSet, font.GdiVerticalFont);
                }
            }
            return font;
        }

        public FormSignUp()
        {
            InitializeComponent();
            textBoxUserName.Font = ApplicationFont.GetFont(textBoxUserName.Font.Size);
            textBoxPassword.Font = ApplicationFont.GetFont(textBoxPassword.Font.Size);
            textBoxConfirmPassword.Font = ApplicationFont.GetFont(textBoxConfirmPassword.Font.Size);
            labelWarning.Font = ApplicationFont.GetFont(labelWarning.Font.Size);
            buttonCancel.Font = ApplicationFont.GetFont(buttonCancel.Font.Size);
            buttonSignUp.Font = ApplicationFont.GetFont(buttonSignUp.Font.Size);
            labelTittle.Font = ApplicationFont.GetFont(labelTittle.Font.Size);
            labelWarning.Text = "";
            this.MouseDown += (sender, e) => Form1_MouseDown(sender, e);
        }

        private void ResetTexts()
        {
            textBoxUserName.Texts = "";
            textBoxPassword.Texts = "";
            textBoxConfirmPassword.Texts = "";
            labelWarning.Text = "";
        }

        private void buttonCancel_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void buttonSignUp_Click(object sender, EventArgs e)
        {
            SignUp();
        }

        private void SignUp()
        {
            timerDisconnect.Enabled = true;
            timerDisconnect.Start();
            if (EmptyTextBoxes())
            {
                labelWarning.Text = "Something is missing?";
                labelWarning.Font = ChangeFontSize(labelWarning.Font, 12);
                labelWarning.TextAlign = ContentAlignment.MiddleCenter;
                labelWarning.ForeColor = Color.Red;
                return;
            }
            else
            {
                if (CheckInvalidUsernameCharacter())
                {
                    labelWarning.Text = "User name can contain only the following characters:\n     - UK English uppercase(A-Z)\n     - UK English lowercase(a-z)\n     - Numbers(0 - 9)\n     - Underline character(_)";
                    labelWarning.Font = ChangeFontSize(labelWarning.Font, 9);
                    labelWarning.TextAlign = ContentAlignment.MiddleLeft;
                    labelWarning.ForeColor = Color.Red;
                    return;
                }
                else
                {
                    if (CheckInvalidPasswordCharacter())
                    {
                        labelWarning.Text = "Password can contain only the following characters:\n     - UK English uppercase(A-Z)\n     - UK English lowercase(a-z)\n     - Numbers(0 - 9)\n     -Non-alphabetic characters (!,@,#,$,%,^,*,+,-,_)";
                        labelWarning.Font = ChangeFontSize(labelWarning.Font, 9);
                        labelWarning.TextAlign = ContentAlignment.MiddleLeft;
                        labelWarning.ForeColor = Color.Red;
                        return;
                    }
                    else
                    {
                        if (!MatchPasswords())
                        {
                            labelWarning.Text = "Those passwords doesn't match";
                            labelWarning.Font = ChangeFontSize(labelWarning.Font, 12);
                            labelWarning.TextAlign = ContentAlignment.MiddleCenter;
                            labelWarning.ForeColor = Color.Red;
                            return;
                        }
                        else
                        {
                            if (!checkBytes())
                            {
                                labelWarning.Text = "Username or Password has over the limit of characters";
                                labelWarning.Font = ChangeFontSize(labelWarning.Font, 12);
                                labelWarning.TextAlign = ContentAlignment.MiddleCenter;
                                labelWarning.ForeColor = Color.Red;
                                return;
                            }
                            else
                            {
                                if (ExistUserName())
                                {
                                    labelWarning.Text = "That user name already exists";
                                    labelWarning.Font = ChangeFontSize(labelWarning.Font, 12);
                                    labelWarning.TextAlign = ContentAlignment.MiddleCenter;
                                    labelWarning.ForeColor = Color.Red;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            labelWarning.Text = "You have signed up successfully".ToUpper();
            labelWarning.Font = ChangeFontSize(labelWarning.Font, 12);
            labelWarning.TextAlign = ContentAlignment.MiddleCenter;
            labelWarning.ForeColor = Color.FromArgb(143, 228, 185);
            timerClosing.Start();
        }

        private bool EmptyTextBoxes()
        {
            if (string.IsNullOrEmpty(textBoxUserName.Texts) || string.IsNullOrEmpty(textBoxPassword.Texts) || string.IsNullOrEmpty(textBoxConfirmPassword.Texts))
            {
                return true;
            }
            return false;
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

        private bool checkBytes()
        {
            if (Encoding.Unicode.GetByteCount(textBoxPassword.Texts) < 128 && Encoding.Unicode.GetByteCount(textBoxUserName.Texts) < 64)
                return true;
            return false;
        }

        private bool ExistUserName()
        {
            return !AFriendClient.Signed_up(textBoxUserName.Texts, textBoxPassword.Texts);

        }

        private bool MatchPasswords()
        {
            if (textBoxPassword.Texts == textBoxConfirmPassword.Texts)
            {
                return true;
            }
            return false;
        }

        private void textBoxUserName_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                buttonSignUp.PerformClick();
            }
        }

        private void textBoxPassword_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                buttonSignUp.PerformClick();
            }
        }

        private void textBoxConfirmPassword_KeyDown(object sender, KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                buttonSignUp.PerformClick();
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

        private void textBoxConfirmPassword_Enter(object sender, EventArgs e)
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
            this.Close();
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

        private void FormSignUp_Load(object sender, EventArgs e)
        {
            this.Text = " ";
            this.ControlBox = false;
        }
    }
}
