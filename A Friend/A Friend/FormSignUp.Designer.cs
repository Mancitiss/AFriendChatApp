namespace A_Friend
{
    partial class FormSignUp
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FormSignUp));
            this.labelTittle = new System.Windows.Forms.Label();
            this.labelWarning = new System.Windows.Forms.Label();
            this.timerClosing = new System.Windows.Forms.Timer(this.components);
            this.textBoxConfirmPassword = new A_Friend.CustomControls.CustomTextBox();
            this.buttonSignUp = new A_Friend.CustomControls.CustomButton();
            this.textBoxPassword = new A_Friend.CustomControls.CustomTextBox();
            this.textBoxUserName = new A_Friend.CustomControls.CustomTextBox();
            this.buttonCancel = new A_Friend.CustomControls.CustomButton();
            this.timerDisconnect = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // labelTittle
            // 
            this.labelTittle.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.labelTittle.AutoSize = true;
            this.labelTittle.Font = new System.Drawing.Font("Microsoft Sans Serif", 20F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelTittle.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.labelTittle.Location = new System.Drawing.Point(95, 23);
            this.labelTittle.Name = "labelTittle";
            this.labelTittle.Size = new System.Drawing.Size(110, 31);
            this.labelTittle.TabIndex = 0;
            this.labelTittle.Text = "Sign Up";
            // 
            // labelWarning
            // 
            this.labelWarning.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.labelWarning.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelWarning.ForeColor = System.Drawing.Color.Red;
            this.labelWarning.Location = new System.Drawing.Point(2, 302);
            this.labelWarning.Name = "labelWarning";
            this.labelWarning.Size = new System.Drawing.Size(300, 112);
            this.labelWarning.TabIndex = 0;
            this.labelWarning.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // timerClosing
            // 
            this.timerClosing.Interval = 1000;
            this.timerClosing.Tick += new System.EventHandler(this.timerClosing_Tick);
            // 
            // textBoxConfirmPassword
            // 
            this.textBoxConfirmPassword.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.textBoxConfirmPassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxConfirmPassword.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxConfirmPassword.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.textBoxConfirmPassword.BorderRadius = 20;
            this.textBoxConfirmPassword.BorderSize = 2;
            this.textBoxConfirmPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxConfirmPassword.Location = new System.Drawing.Point(25, 195);
            this.textBoxConfirmPassword.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxConfirmPassword.Multiline = false;
            this.textBoxConfirmPassword.Name = "textBoxConfirmPassword";
            this.textBoxConfirmPassword.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.textBoxConfirmPassword.PasswordChar = true;
            this.textBoxConfirmPassword.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.textBoxConfirmPassword.PlaceholderText = "Confirm Password";
            this.textBoxConfirmPassword.Size = new System.Drawing.Size(250, 38);
            this.textBoxConfirmPassword.TabIndex = 3;
            this.textBoxConfirmPassword.Texts = "";
            this.textBoxConfirmPassword.UnderlinedStyle = false;
            this.textBoxConfirmPassword.Enter += new System.EventHandler(this.textBoxConfirmPassword_Enter);
            this.textBoxConfirmPassword.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBoxConfirmPassword_KeyDown);
            // 
            // buttonSignUp
            // 
            this.buttonSignUp.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.buttonSignUp.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.buttonSignUp.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.buttonSignUp.BorderRadius = 20;
            this.buttonSignUp.BorderSize = 0;
            this.buttonSignUp.FlatAppearance.BorderSize = 0;
            this.buttonSignUp.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonSignUp.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonSignUp.ForeColor = System.Drawing.Color.White;
            this.buttonSignUp.Location = new System.Drawing.Point(25, 260);
            this.buttonSignUp.Name = "buttonSignUp";
            this.buttonSignUp.Size = new System.Drawing.Size(250, 40);
            this.buttonSignUp.TabIndex = 4;
            this.buttonSignUp.Text = "SIGN UP";
            this.buttonSignUp.UseVisualStyleBackColor = false;
            this.buttonSignUp.Click += new System.EventHandler(this.buttonSignUp_Click);
            // 
            // textBoxPassword
            // 
            this.textBoxPassword.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.textBoxPassword.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxPassword.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxPassword.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.textBoxPassword.BorderRadius = 20;
            this.textBoxPassword.BorderSize = 2;
            this.textBoxPassword.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxPassword.Location = new System.Drawing.Point(25, 135);
            this.textBoxPassword.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxPassword.Multiline = false;
            this.textBoxPassword.Name = "textBoxPassword";
            this.textBoxPassword.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.textBoxPassword.PasswordChar = true;
            this.textBoxPassword.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.textBoxPassword.PlaceholderText = "Password";
            this.textBoxPassword.Size = new System.Drawing.Size(250, 38);
            this.textBoxPassword.TabIndex = 2;
            this.textBoxPassword.Texts = "";
            this.textBoxPassword.UnderlinedStyle = false;
            this.textBoxPassword.Enter += new System.EventHandler(this.textBoxPassword_Enter);
            this.textBoxPassword.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBoxPassword_KeyDown);
            // 
            // textBoxUserName
            // 
            this.textBoxUserName.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.textBoxUserName.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxUserName.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(242)))), ((int)(((byte)(242)))), ((int)(((byte)(242)))));
            this.textBoxUserName.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(143)))), ((int)(((byte)(228)))), ((int)(((byte)(185)))));
            this.textBoxUserName.BorderRadius = 20;
            this.textBoxUserName.BorderSize = 2;
            this.textBoxUserName.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textBoxUserName.Location = new System.Drawing.Point(25, 75);
            this.textBoxUserName.Margin = new System.Windows.Forms.Padding(4);
            this.textBoxUserName.Multiline = false;
            this.textBoxUserName.Name = "textBoxUserName";
            this.textBoxUserName.Padding = new System.Windows.Forms.Padding(16, 10, 16, 10);
            this.textBoxUserName.PasswordChar = false;
            this.textBoxUserName.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.textBoxUserName.PlaceholderText = "User name";
            this.textBoxUserName.Size = new System.Drawing.Size(250, 38);
            this.textBoxUserName.TabIndex = 1;
            this.textBoxUserName.Texts = "";
            this.textBoxUserName.UnderlinedStyle = false;
            this.textBoxUserName.Enter += new System.EventHandler(this.textBoxUserName_Enter);
            this.textBoxUserName.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textBoxUserName_KeyDown);
            // 
            // buttonCancel
            // 
            this.buttonCancel.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.buttonCancel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(213)))), ((int)(((byte)(54)))), ((int)(((byte)(41)))));
            this.buttonCancel.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.buttonCancel.BorderRadius = 20;
            this.buttonCancel.BorderSize = 0;
            this.buttonCancel.FlatAppearance.BorderSize = 0;
            this.buttonCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonCancel.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.buttonCancel.ForeColor = System.Drawing.Color.White;
            this.buttonCancel.Location = new System.Drawing.Point(25, 418);
            this.buttonCancel.Name = "buttonCancel";
            this.buttonCancel.Size = new System.Drawing.Size(250, 40);
            this.buttonCancel.TabIndex = 5;
            this.buttonCancel.Text = "CANCEL";
            this.buttonCancel.UseVisualStyleBackColor = false;
            this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
            // 
            // timerDisconnect
            // 
            this.timerDisconnect.Interval = 19000;
            // 
            // FormSignUp
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(302, 500);
            this.Controls.Add(this.textBoxConfirmPassword);
            this.Controls.Add(this.labelTittle);
            this.Controls.Add(this.buttonSignUp);
            this.Controls.Add(this.textBoxPassword);
            this.Controls.Add(this.textBoxUserName);
            this.Controls.Add(this.buttonCancel);
            this.Controls.Add(this.labelWarning);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FormSignUp";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Load += new System.EventHandler(this.FormSignUp_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelTittle;
        private CustomControls.CustomButton buttonSignUp;
        private CustomControls.CustomTextBox textBoxPassword;
        private CustomControls.CustomTextBox textBoxUserName;
        private CustomControls.CustomButton buttonCancel;
        private CustomControls.CustomTextBox textBoxConfirmPassword;
        private System.Windows.Forms.Label labelWarning;
        private System.Windows.Forms.Timer timerClosing;
        private System.Windows.Forms.Timer timerDisconnect;
    }
}