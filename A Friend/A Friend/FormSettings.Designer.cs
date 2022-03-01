
namespace A_Friend
{
    partial class FormSettings
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormSettings));
            this.labelUsername = new System.Windows.Forms.Label();
            this.panelPassword = new System.Windows.Forms.Panel();
            this.textBoxConfirmPassword = new A_Friend.CustomControls.CustomTextBox();
            this.buttonSavePassword = new A_Friend.CustomControls.CustomButton();
            this.textBoxCurrentPassword = new A_Friend.CustomControls.CustomTextBox();
            this.textBoxNewPassword = new A_Friend.CustomControls.CustomTextBox();
            this.panelUsername = new System.Windows.Forms.Panel();
            this.customTextBoxUsername = new A_Friend.CustomControls.CustomTextBox();
            this.buttonSaveUsername = new A_Friend.CustomControls.CustomButton();
            this.labelWarning = new System.Windows.Forms.Label();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.circlePictureBox1 = new A_Friend.CustomControls.CirclePictureBox();
            this.customButtonPassword = new A_Friend.CustomControls.CustomButton();
            this.customButtonExit = new A_Friend.CustomControls.CustomButton();
            this.customButtonAvatar = new A_Friend.CustomControls.CustomButton();
            this.customButtonUsername = new A_Friend.CustomControls.CustomButton();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.panel1 = new System.Windows.Forms.Panel();
            this.toggleButton1 = new A_Friend.CustomControls.ToggleButton();
            this.label1 = new System.Windows.Forms.Label();
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.label2 = new System.Windows.Forms.Label();
            this.panelPassword.SuspendLayout();
            this.panelUsername.SuspendLayout();
            this.tabControl1.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.circlePictureBox1)).BeginInit();
            this.tabPage2.SuspendLayout();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelUsername
            // 
            this.labelUsername.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.labelUsername.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.labelUsername.Location = new System.Drawing.Point(-3, 212);
            this.labelUsername.Margin = new System.Windows.Forms.Padding(0);
            this.labelUsername.Name = "labelUsername";
            this.labelUsername.Size = new System.Drawing.Size(302, 25);
            this.labelUsername.TabIndex = 26;
            this.labelUsername.Text = "Username";
            this.labelUsername.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // panelPassword
            // 
            this.panelPassword.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.panelPassword.Controls.Add(this.textBoxConfirmPassword);
            this.panelPassword.Controls.Add(this.buttonSavePassword);
            this.panelPassword.Controls.Add(this.textBoxCurrentPassword);
            this.panelPassword.Controls.Add(this.textBoxNewPassword);
            this.panelPassword.Location = new System.Drawing.Point(2, 306);
            this.panelPassword.Margin = new System.Windows.Forms.Padding(0);
            this.panelPassword.Name = "panelPassword";
            this.panelPassword.Size = new System.Drawing.Size(300, 195);
            this.panelPassword.TabIndex = 28;
            // 
            // textBoxConfirmPassword
            // 
            this.textBoxConfirmPassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxConfirmPassword.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxConfirmPassword.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.textBoxConfirmPassword.BorderRadius = 20;
            this.textBoxConfirmPassword.BorderSize = 2;
            this.textBoxConfirmPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.textBoxConfirmPassword.Location = new System.Drawing.Point(25, 101);
            this.textBoxConfirmPassword.Margin = new System.Windows.Forms.Padding(5, 5, 5, 5);
            this.textBoxConfirmPassword.Multiline = false;
            this.textBoxConfirmPassword.Name = "textBoxConfirmPassword";
            this.textBoxConfirmPassword.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.textBoxConfirmPassword.PasswordChar = true;
            this.textBoxConfirmPassword.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.textBoxConfirmPassword.PlaceholderText = "Confirm Password";
            this.textBoxConfirmPassword.Size = new System.Drawing.Size(250, 38);
            this.textBoxConfirmPassword.TabIndex = 7;
            this.textBoxConfirmPassword.Texts = "";
            this.textBoxConfirmPassword.UnderlinedStyle = false;
            // 
            // buttonSavePassword
            // 
            this.buttonSavePassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.buttonSavePassword.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.buttonSavePassword.BorderRadius = 20;
            this.buttonSavePassword.BorderSize = 0;
            this.buttonSavePassword.FlatAppearance.BorderSize = 0;
            this.buttonSavePassword.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonSavePassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.buttonSavePassword.ForeColor = System.Drawing.Color.White;
            this.buttonSavePassword.Location = new System.Drawing.Point(25, 146);
            this.buttonSavePassword.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.buttonSavePassword.Name = "buttonSavePassword";
            this.buttonSavePassword.Size = new System.Drawing.Size(250, 41);
            this.buttonSavePassword.TabIndex = 8;
            this.buttonSavePassword.Text = "Save password";
            this.buttonSavePassword.UseVisualStyleBackColor = false;
            this.buttonSavePassword.Click += new System.EventHandler(this.buttonSavePassword_Click_1);
            // 
            // textBoxCurrentPassword
            // 
            this.textBoxCurrentPassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxCurrentPassword.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxCurrentPassword.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.textBoxCurrentPassword.BorderRadius = 20;
            this.textBoxCurrentPassword.BorderSize = 2;
            this.textBoxCurrentPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.textBoxCurrentPassword.Location = new System.Drawing.Point(25, 5);
            this.textBoxCurrentPassword.Margin = new System.Windows.Forms.Padding(5, 5, 5, 5);
            this.textBoxCurrentPassword.Multiline = false;
            this.textBoxCurrentPassword.Name = "textBoxCurrentPassword";
            this.textBoxCurrentPassword.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.textBoxCurrentPassword.PasswordChar = true;
            this.textBoxCurrentPassword.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.textBoxCurrentPassword.PlaceholderText = "Current Password";
            this.textBoxCurrentPassword.Size = new System.Drawing.Size(250, 38);
            this.textBoxCurrentPassword.TabIndex = 5;
            this.textBoxCurrentPassword.Texts = "";
            this.textBoxCurrentPassword.UnderlinedStyle = false;
            this.textBoxCurrentPassword.Enter += new System.EventHandler(this.textBoxCurrentPassword_Enter);
            // 
            // textBoxNewPassword
            // 
            this.textBoxNewPassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxNewPassword.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxNewPassword.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.textBoxNewPassword.BorderRadius = 20;
            this.textBoxNewPassword.BorderSize = 2;
            this.textBoxNewPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.textBoxNewPassword.Location = new System.Drawing.Point(25, 53);
            this.textBoxNewPassword.Margin = new System.Windows.Forms.Padding(5, 5, 5, 5);
            this.textBoxNewPassword.Multiline = false;
            this.textBoxNewPassword.Name = "textBoxNewPassword";
            this.textBoxNewPassword.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.textBoxNewPassword.PasswordChar = true;
            this.textBoxNewPassword.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.textBoxNewPassword.PlaceholderText = "New Password";
            this.textBoxNewPassword.Size = new System.Drawing.Size(250, 38);
            this.textBoxNewPassword.TabIndex = 6;
            this.textBoxNewPassword.Texts = "";
            this.textBoxNewPassword.UnderlinedStyle = false;
            // 
            // panelUsername
            // 
            this.panelUsername.Controls.Add(this.customTextBoxUsername);
            this.panelUsername.Controls.Add(this.buttonSaveUsername);
            this.panelUsername.Location = new System.Drawing.Point(-3, 309);
            this.panelUsername.Margin = new System.Windows.Forms.Padding(0);
            this.panelUsername.Name = "panelUsername";
            this.panelUsername.Size = new System.Drawing.Size(325, 100);
            this.panelUsername.TabIndex = 31;
            // 
            // customTextBoxUsername
            // 
            this.customTextBoxUsername.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.customTextBoxUsername.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.customTextBoxUsername.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(37)))), ((int)(((byte)(75)))), ((int)(((byte)(133)))));
            this.customTextBoxUsername.BorderRadius = 20;
            this.customTextBoxUsername.BorderSize = 2;
            this.customTextBoxUsername.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.customTextBoxUsername.Location = new System.Drawing.Point(25, 5);
            this.customTextBoxUsername.Margin = new System.Windows.Forms.Padding(5, 5, 5, 5);
            this.customTextBoxUsername.Multiline = false;
            this.customTextBoxUsername.Name = "customTextBoxUsername";
            this.customTextBoxUsername.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.customTextBoxUsername.PasswordChar = false;
            this.customTextBoxUsername.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.customTextBoxUsername.PlaceholderText = "New Name";
            this.customTextBoxUsername.Size = new System.Drawing.Size(250, 38);
            this.customTextBoxUsername.TabIndex = 3;
            this.customTextBoxUsername.Texts = "";
            this.customTextBoxUsername.UnderlinedStyle = false;
            // 
            // buttonSaveUsername
            // 
            this.buttonSaveUsername.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(37)))), ((int)(((byte)(75)))), ((int)(((byte)(133)))));
            this.buttonSaveUsername.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.buttonSaveUsername.BorderRadius = 20;
            this.buttonSaveUsername.BorderSize = 0;
            this.buttonSaveUsername.FlatAppearance.BorderSize = 0;
            this.buttonSaveUsername.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonSaveUsername.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.buttonSaveUsername.ForeColor = System.Drawing.Color.White;
            this.buttonSaveUsername.Location = new System.Drawing.Point(25, 50);
            this.buttonSaveUsername.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.buttonSaveUsername.Name = "buttonSaveUsername";
            this.buttonSaveUsername.Size = new System.Drawing.Size(250, 41);
            this.buttonSaveUsername.TabIndex = 4;
            this.buttonSaveUsername.Text = "Save name";
            this.buttonSaveUsername.UseVisualStyleBackColor = false;
            this.buttonSaveUsername.Click += new System.EventHandler(this.buttonSaveUsername_Click_1);
            // 
            // labelWarning
            // 
            this.labelWarning.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.labelWarning.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.labelWarning.ForeColor = System.Drawing.Color.Red;
            this.labelWarning.Location = new System.Drawing.Point(-3, 511);
            this.labelWarning.Name = "labelWarning";
            this.labelWarning.Size = new System.Drawing.Size(302, 20);
            this.labelWarning.TabIndex = 32;
            this.labelWarning.Text = "Please enter your password!";
            this.labelWarning.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(300, 640);
            this.tabControl1.TabIndex = 33;
            // 
            // tabPage1
            // 
            this.tabPage1.BackColor = System.Drawing.Color.White;
            this.tabPage1.Controls.Add(this.label2);
            this.tabPage1.Controls.Add(this.labelUsername);
            this.tabPage1.Controls.Add(this.circlePictureBox1);
            this.tabPage1.Controls.Add(this.panelPassword);
            this.tabPage1.Controls.Add(this.customButtonPassword);
            this.tabPage1.Controls.Add(this.panelUsername);
            this.tabPage1.Controls.Add(this.customButtonExit);
            this.tabPage1.Controls.Add(this.labelWarning);
            this.tabPage1.Controls.Add(this.customButtonAvatar);
            this.tabPage1.Controls.Add(this.customButtonUsername);
            this.tabPage1.Location = new System.Drawing.Point(4, 25);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage1.Size = new System.Drawing.Size(292, 611);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Profile";
            // 
            // circlePictureBox1
            // 
            this.circlePictureBox1.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.circlePictureBox1.BorderCapStyle = System.Drawing.Drawing2D.DashCap.Flat;
            this.circlePictureBox1.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(37)))), ((int)(((byte)(75)))), ((int)(((byte)(133)))));
            this.circlePictureBox1.BorderColor2 = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.circlePictureBox1.BorderLineStyle = System.Drawing.Drawing2D.DashStyle.Solid;
            this.circlePictureBox1.BorderSize = 2;
            this.circlePictureBox1.GradientAngle = 50F;
            this.circlePictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("circlePictureBox1.Image")));
            this.circlePictureBox1.Location = new System.Drawing.Point(48, 3);
            this.circlePictureBox1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.circlePictureBox1.Name = "circlePictureBox1";
            this.circlePictureBox1.Size = new System.Drawing.Size(200, 200);
            this.circlePictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.circlePictureBox1.TabIndex = 25;
            this.circlePictureBox1.TabStop = false;
            this.circlePictureBox1.DoubleClick += new System.EventHandler(this.customButtonAvatar_Click_1);
            // 
            // customButtonPassword
            // 
            this.customButtonPassword.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.customButtonPassword.BackColor = System.Drawing.Color.Transparent;
            this.customButtonPassword.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.customButtonPassword.BorderRadius = 15;
            this.customButtonPassword.BorderSize = 0;
            this.customButtonPassword.FlatAppearance.BorderSize = 0;
            this.customButtonPassword.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.customButtonPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.customButtonPassword.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.customButtonPassword.Location = new System.Drawing.Point(48, 273);
            this.customButtonPassword.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.customButtonPassword.Name = "customButtonPassword";
            this.customButtonPassword.Size = new System.Drawing.Size(200, 30);
            this.customButtonPassword.TabIndex = 27;
            this.customButtonPassword.Text = "Change password?";
            this.customButtonPassword.UseVisualStyleBackColor = false;
            this.customButtonPassword.Click += new System.EventHandler(this.customButtonPassword_Click_1);
            // 
            // customButtonExit
            // 
            this.customButtonExit.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.customButtonExit.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(213)))), ((int)(((byte)(54)))), ((int)(((byte)(41)))));
            this.customButtonExit.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.customButtonExit.BorderRadius = 20;
            this.customButtonExit.BorderSize = 0;
            this.customButtonExit.FlatAppearance.BorderSize = 0;
            this.customButtonExit.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.customButtonExit.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.customButtonExit.ForeColor = System.Drawing.Color.White;
            this.customButtonExit.Location = new System.Drawing.Point(23, 543);
            this.customButtonExit.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.customButtonExit.Name = "customButtonExit";
            this.customButtonExit.Size = new System.Drawing.Size(250, 39);
            this.customButtonExit.TabIndex = 24;
            this.customButtonExit.Text = "Exit ";
            this.customButtonExit.UseVisualStyleBackColor = false;
            this.customButtonExit.Click += new System.EventHandler(this.customButtonExit_Click_1);
            // 
            // customButtonAvatar
            // 
            this.customButtonAvatar.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.customButtonAvatar.BackColor = System.Drawing.Color.White;
            this.customButtonAvatar.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("customButtonAvatar.BackgroundImage")));
            this.customButtonAvatar.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.customButtonAvatar.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.customButtonAvatar.BorderRadius = 24;
            this.customButtonAvatar.BorderSize = 0;
            this.customButtonAvatar.FlatAppearance.BorderSize = 0;
            this.customButtonAvatar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.customButtonAvatar.ForeColor = System.Drawing.Color.White;
            this.customButtonAvatar.Location = new System.Drawing.Point(218, 173);
            this.customButtonAvatar.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.customButtonAvatar.Name = "customButtonAvatar";
            this.customButtonAvatar.Size = new System.Drawing.Size(30, 30);
            this.customButtonAvatar.TabIndex = 29;
            this.toolTip.SetToolTip(this.customButtonAvatar, "Choose an avatar");
            this.customButtonAvatar.UseVisualStyleBackColor = false;
            this.customButtonAvatar.Click += new System.EventHandler(this.customButtonAvatar_Click_1);
            // 
            // customButtonUsername
            // 
            this.customButtonUsername.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.customButtonUsername.BackColor = System.Drawing.Color.Transparent;
            this.customButtonUsername.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.customButtonUsername.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.customButtonUsername.BorderRadius = 15;
            this.customButtonUsername.BorderSize = 0;
            this.customButtonUsername.FlatAppearance.BorderSize = 0;
            this.customButtonUsername.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.customButtonUsername.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(37)))), ((int)(((byte)(75)))), ((int)(((byte)(133)))));
            this.customButtonUsername.Location = new System.Drawing.Point(48, 239);
            this.customButtonUsername.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.customButtonUsername.Name = "customButtonUsername";
            this.customButtonUsername.Size = new System.Drawing.Size(200, 30);
            this.customButtonUsername.TabIndex = 30;
            this.customButtonUsername.Text = "Change display name?";
            this.customButtonUsername.UseVisualStyleBackColor = false;
            this.customButtonUsername.Click += new System.EventHandler(this.customButtonUsername_Click);
            // 
            // tabPage2
            // 
            this.tabPage2.BackColor = System.Drawing.Color.White;
            this.tabPage2.Controls.Add(this.panel1);
            this.tabPage2.Location = new System.Drawing.Point(4, 25);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(3);
            this.tabPage2.Size = new System.Drawing.Size(292, 611);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Mode";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.toggleButton1);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(292, 50);
            this.panel1.TabIndex = 2;
            // 
            // toggleButton1
            // 
            this.toggleButton1.AutoSize = true;
            this.toggleButton1.Location = new System.Drawing.Point(215, 10);
            this.toggleButton1.MinimumSize = new System.Drawing.Size(60, 30);
            this.toggleButton1.Name = "toggleButton1";
            this.toggleButton1.OffBackColor = System.Drawing.Color.Gray;
            this.toggleButton1.OffToggleColor = System.Drawing.Color.Gainsboro;
            this.toggleButton1.OnBackColor = System.Drawing.Color.SteelBlue;
            this.toggleButton1.OnToggleColor = System.Drawing.Color.WhiteSmoke;
            this.toggleButton1.Size = new System.Drawing.Size(60, 30);
            this.toggleButton1.TabIndex = 0;
            this.toggleButton1.UseVisualStyleBackColor = true;
            this.toggleButton1.CheckedChanged += new System.EventHandler(this.toggleButton1_CheckedChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 10.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.label1.ForeColor = System.Drawing.Color.Gray;
            this.label1.Location = new System.Drawing.Point(8, 14);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(104, 17);
            this.label1.TabIndex = 1;
            this.label1.Text = "Incognito Mode";
            // 
            // label2
            // 
            this.label2.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.label2.Location = new System.Drawing.Point(3, 588);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(286, 20);
            this.label2.TabIndex = 33;
            this.label2.Text = "0000000000000000000";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // FormSettings
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(300, 640);
            this.Controls.Add(this.tabControl1);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "FormSettings";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "FormSettings";
            this.Load += new System.EventHandler(this.FormSettings_Load);
            this.panelPassword.ResumeLayout(false);
            this.panelUsername.ResumeLayout(false);
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.circlePictureBox1)).EndInit();
            this.tabPage2.ResumeLayout(false);
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label labelUsername;
        private System.Windows.Forms.Panel panelPassword;
        private CustomControls.CustomTextBox textBoxConfirmPassword;
        private CustomControls.CustomButton buttonSavePassword;
        private CustomControls.CustomTextBox textBoxCurrentPassword;
        private CustomControls.CustomTextBox textBoxNewPassword;
        private System.Windows.Forms.Panel panelUsername;
        private CustomControls.CustomTextBox customTextBoxUsername;
        private CustomControls.CustomButton buttonSaveUsername;
        private System.Windows.Forms.Label labelWarning;
        private CustomControls.CustomButton customButtonUsername;
        private CustomControls.CustomButton customButtonAvatar;
        private CustomControls.CustomButton customButtonExit;
        private CustomControls.CustomButton customButtonPassword;
        private CustomControls.CirclePictureBox circlePictureBox1;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.Panel panel1;
        private CustomControls.ToggleButton toggleButton1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ToolTip toolTip;
        private System.Windows.Forms.Label label2;
    }
}