
namespace A_Friend
{
    partial class FormAddContact
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
            this.labelWarning = new System.Windows.Forms.Label();
            this.txtNewUser = new A_Friend.CustomControls.CustomTextBox();
            this.SuspendLayout();
            // 
            // labelWarning
            // 
            this.labelWarning.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.labelWarning.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(247)))), ((int)(((byte)(249)))), ((int)(((byte)(255)))));
            this.labelWarning.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(163)));
            this.labelWarning.ForeColor = System.Drawing.Color.Red;
            this.labelWarning.Location = new System.Drawing.Point(0, 52);
            this.labelWarning.Name = "labelWarning";
            this.labelWarning.Size = new System.Drawing.Size(295, 25);
            this.labelWarning.TabIndex = 5;
            this.labelWarning.Text = "This username does not exist";
            this.labelWarning.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // txtNewUser
            // 
            this.txtNewUser.BackColor = System.Drawing.SystemColors.Window;
            this.txtNewUser.BorderColor = System.Drawing.Color.DarkGray;
            this.txtNewUser.BorderFocusColor = System.Drawing.Color.FromArgb(((int)(((byte)(141)))), ((int)(((byte)(161)))), ((int)(((byte)(252)))));
            this.txtNewUser.BorderRadius = 20;
            this.txtNewUser.BorderSize = 3;
            this.txtNewUser.Location = new System.Drawing.Point(17, 6);
            this.txtNewUser.Margin = new System.Windows.Forms.Padding(8);
            this.txtNewUser.Multiline = false;
            this.txtNewUser.Name = "txtNewUser";
            this.txtNewUser.Padding = new System.Windows.Forms.Padding(18, 12, 18, 12);
            this.txtNewUser.PasswordChar = false;
            this.txtNewUser.PlaceholderColor = System.Drawing.Color.DarkGray;
            this.txtNewUser.PlaceholderText = "";
            this.txtNewUser.Size = new System.Drawing.Size(263, 38);
            this.txtNewUser.TabIndex = 0;
            this.txtNewUser.Texts = "";
            this.txtNewUser.UnderlinedStyle = false;
            this.txtNewUser._TextChanged += new System.EventHandler(this.txtNewUser__TextChanged);
            this.txtNewUser.KeyDown += new System.Windows.Forms.KeyEventHandler(this.txtNewUser_KeyDown);
            // 
            // FormAddContact
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(247)))), ((int)(((byte)(249)))), ((int)(((byte)(255)))));
            this.ClientSize = new System.Drawing.Size(297, 78);
            this.Controls.Add(this.labelWarning);
            this.Controls.Add(this.txtNewUser);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Margin = new System.Windows.Forms.Padding(2);
            this.Name = "FormAddContact";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "FormAddContact";
            this.Shown += new System.EventHandler(this.FormAddContact_Shown);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.FormAddContact_Paint);
            this.ResumeLayout(false);

        }

        #endregion
        private CustomControls.CustomTextBox txtNewUser;
        private System.Windows.Forms.Label labelWarning;
    }
}