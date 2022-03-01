namespace A_Friend.CustomControls
{
    partial class ContactItem
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
            account = null;
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ContactItem));
            this.labelLastMessage = new System.Windows.Forms.Label();
            this.labelName = new System.Windows.Forms.Label();
            this.friendPicture = new A_Friend.CustomControls.CirclePictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.friendPicture)).BeginInit();
            this.SuspendLayout();
            // 
            // labelLastMessage
            // 
            this.labelLastMessage.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.labelLastMessage.BackColor = System.Drawing.Color.Transparent;
            this.labelLastMessage.Font = new System.Drawing.Font("Arial", 11F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelLastMessage.ForeColor = System.Drawing.Color.DimGray;
            this.labelLastMessage.Location = new System.Drawing.Point(75, 36);
            this.labelLastMessage.MaximumSize = new System.Drawing.Size(197, 20);
            this.labelLastMessage.Name = "labelLastMessage";
            this.labelLastMessage.Size = new System.Drawing.Size(197, 16);
            this.labelLastMessage.TabIndex = 2;
            this.labelLastMessage.Text = "You: last text here";
            this.labelLastMessage.TextChanged += new System.EventHandler(this.labelLastMessage_TextChanged);
            this.labelLastMessage.Click += new System.EventHandler(this.labelLastMessage_Click);
            this.labelLastMessage.MouseEnter += new System.EventHandler(this.ContactItem_MouseEnter);
            this.labelLastMessage.MouseLeave += new System.EventHandler(this.ContactItem_Leave);
            // 
            // labelName
            // 
            this.labelName.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.labelName.BackColor = System.Drawing.Color.Transparent;
            this.labelName.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelName.Location = new System.Drawing.Point(75, 13);
            this.labelName.MaximumSize = new System.Drawing.Size(197, 20);
            this.labelName.Name = "labelName";
            this.labelName.Size = new System.Drawing.Size(197, 18);
            this.labelName.TabIndex = 3;
            this.labelName.Text = "Friend Name";
            this.labelName.TextChanged += new System.EventHandler(this.labelName_TextChanged);
            this.labelName.Click += new System.EventHandler(this.labelName_Click);
            this.labelName.MouseEnter += new System.EventHandler(this.ContactItem_MouseEnter);
            this.labelName.MouseLeave += new System.EventHandler(this.ContactItem_Leave);
            this.labelName.Resize += new System.EventHandler(this.labelName_Resize);
            // 
            // friendPicture
            // 
            this.friendPicture.BackColor = System.Drawing.Color.Transparent;
            this.friendPicture.BorderCapStyle = System.Drawing.Drawing2D.DashCap.Flat;
            this.friendPicture.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(58)))), ((int)(((byte)(206)))), ((int)(((byte)(58)))));
            this.friendPicture.BorderColor2 = System.Drawing.Color.FromArgb(((int)(((byte)(180)))), ((int)(((byte)(236)))), ((int)(((byte)(180)))));
            this.friendPicture.BorderLineStyle = System.Drawing.Drawing2D.DashStyle.Solid;
            this.friendPicture.BorderSize = 0;
            this.friendPicture.GradientAngle = 50F;
            this.friendPicture.Image = ((System.Drawing.Image)(resources.GetObject("friendPicture.Image")));
            this.friendPicture.Location = new System.Drawing.Point(20, 10);
            this.friendPicture.Name = "friendPicture";
            this.friendPicture.Size = new System.Drawing.Size(45, 45);
            this.friendPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.friendPicture.TabIndex = 5;
            this.friendPicture.TabStop = false;
            this.friendPicture.Click += new System.EventHandler(this.friendPicture_Click);
            this.friendPicture.MouseEnter += new System.EventHandler(this.ContactItem_MouseEnter);
            this.friendPicture.MouseLeave += new System.EventHandler(this.ContactItem_Leave);
            // 
            // ContactItem
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.Controls.Add(this.labelName);
            this.Controls.Add(this.labelLastMessage);
            this.Controls.Add(this.friendPicture);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "ContactItem";
            this.Padding = new System.Windows.Forms.Padding(10, 5, 10, 5);
            this.Size = new System.Drawing.Size(300, 65);
            this.Click += new System.EventHandler(this.ContactItem_Click);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.ContactItem_Paint);
            this.MouseEnter += new System.EventHandler(this.ContactItem_MouseEnter);
            this.MouseLeave += new System.EventHandler(this.ContactItem_Leave);
            this.Resize += new System.EventHandler(this.ContactItem_Resize);
            ((System.ComponentModel.ISupportInitialize)(this.friendPicture)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Label labelLastMessage;
        private System.Windows.Forms.Label labelName;
        private CirclePictureBox friendPicture;
    }
}
