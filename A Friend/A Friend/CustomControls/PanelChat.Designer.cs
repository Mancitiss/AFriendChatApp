
namespace A_Friend.CustomControls
{
    partial class PanelChat
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
            messages = null;
            files_to_send = null;
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(PanelChat));
            this.panelTopRight = new System.Windows.Forms.Panel();
            this.labelState = new System.Windows.Forms.Label();
            this.labelFriendName = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.friendPicture = new A_Friend.CustomControls.CirclePictureBox();
            this.buttonDelete = new A_Friend.CustomControls.CustomButton();
            this.panel_Chat = new System.Windows.Forms.Panel();
            this.panelBottomRight = new System.Windows.Forms.Panel();
            this.SendFileButton = new A_Friend.CustomControls.CustomButton();
            this.sendImageButton = new A_Friend.CustomControls.CustomButton();
            this.textboxWriting = new TranslucentRichTextBox();
            this.buttonSend = new A_Friend.CustomControls.CustomButton();
            this.timerChat = new System.Windows.Forms.Timer(this.components);
            this.toolTip = new System.Windows.Forms.ToolTip(this.components);
            this.panelTopRight.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.friendPicture)).BeginInit();
            this.panelBottomRight.SuspendLayout();
            this.SuspendLayout();
            // 
            // panelTopRight
            // 
            this.panelTopRight.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(46)))), ((int)(((byte)(140)))), ((int)(((byte)(130)))));
            this.panelTopRight.Controls.Add(this.labelState);
            this.panelTopRight.Controls.Add(this.labelFriendName);
            this.panelTopRight.Controls.Add(this.label3);
            this.panelTopRight.Controls.Add(this.friendPicture);
            this.panelTopRight.Controls.Add(this.buttonDelete);
            this.panelTopRight.Dock = System.Windows.Forms.DockStyle.Top;
            this.panelTopRight.Location = new System.Drawing.Point(0, 0);
            this.panelTopRight.Margin = new System.Windows.Forms.Padding(0);
            this.panelTopRight.Name = "panelTopRight";
            this.panelTopRight.Size = new System.Drawing.Size(912, 60);
            this.panelTopRight.TabIndex = 1;
            this.panelTopRight.TabStop = true;
            this.panelTopRight.Click += new System.EventHandler(this.panelTopRight_Click);
            this.panelTopRight.Paint += new System.Windows.Forms.PaintEventHandler(this.panelTopRight_Paint);
            this.panelTopRight.Resize += new System.EventHandler(this.panelTopRight_Resize);
            // 
            // labelState
            // 
            this.labelState.AutoSize = true;
            this.labelState.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelState.ForeColor = System.Drawing.Color.Gainsboro;
            this.labelState.Location = new System.Drawing.Point(73, 32);
            this.labelState.Name = "labelState";
            this.labelState.Size = new System.Drawing.Size(49, 18);
            this.labelState.TabIndex = 2;
            this.labelState.Text = "offline";
            // 
            // labelFriendName
            // 
            this.labelFriendName.AutoSize = true;
            this.labelFriendName.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelFriendName.ForeColor = System.Drawing.SystemColors.ControlText;
            this.labelFriendName.Location = new System.Drawing.Point(72, 10);
            this.labelFriendName.Name = "labelFriendName";
            this.labelFriendName.Size = new System.Drawing.Size(80, 18);
            this.labelFriendName.TabIndex = 0;
            this.labelFriendName.Text = "Username";
            this.labelFriendName.Click += new System.EventHandler(this.panelTopRight_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(72, 12);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(13, 20);
            this.label3.TabIndex = 3;
            this.label3.Text = " ";
            // 
            // friendPicture
            // 
            this.friendPicture.BorderCapStyle = System.Drawing.Drawing2D.DashCap.Flat;
            this.friendPicture.BorderColor = System.Drawing.Color.GhostWhite;
            this.friendPicture.BorderColor2 = System.Drawing.Color.Snow;
            this.friendPicture.BorderLineStyle = System.Drawing.Drawing2D.DashStyle.Solid;
            this.friendPicture.BorderSize = 0;
            this.friendPicture.GradientAngle = 50F;
            this.friendPicture.Image = global::A_Friend.Properties.Resources.newUser;
            this.friendPicture.Location = new System.Drawing.Point(18, 7);
            this.friendPicture.Name = "friendPicture";
            this.friendPicture.Size = new System.Drawing.Size(45, 45);
            this.friendPicture.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.friendPicture.TabStop = false;
            this.friendPicture.Click += new System.EventHandler(this.panelTopRight_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.buttonDelete.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(244)))), ((int)(((byte)(241)))));
            this.buttonDelete.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("buttonDelete.BackgroundImage")));
            this.buttonDelete.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.buttonDelete.BorderColor = System.Drawing.Color.PaleVioletRed;
            this.buttonDelete.BorderRadius = 15;
            this.buttonDelete.BorderSize = 0;
            this.buttonDelete.FlatAppearance.BorderSize = 0;
            this.buttonDelete.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonDelete.ForeColor = System.Drawing.Color.White;
            this.buttonDelete.Location = new System.Drawing.Point(860, 10);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new System.Drawing.Size(40, 40);
            this.buttonDelete.TabStop = false;
            this.toolTip.SetToolTip(this.buttonDelete, "Delete conversation");
            this.buttonDelete.UseVisualStyleBackColor = false;
            this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
            // 
            // panel_Chat
            // 
            this.panel_Chat.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.panel_Chat.AutoScroll = true;
            this.panel_Chat.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(247)))), ((int)(((byte)(249)))), ((int)(((byte)(255)))));
            this.panel_Chat.Location = new System.Drawing.Point(0, 60);
            this.panel_Chat.Margin = new System.Windows.Forms.Padding(0);
            this.panel_Chat.Name = "panel_Chat";
            this.panel_Chat.Padding = new System.Windows.Forms.Padding(2);
            this.panel_Chat.Size = new System.Drawing.Size(912, 474);
            this.panel_Chat.TabIndex = 2;
            this.panel_Chat.Scroll += new System.Windows.Forms.ScrollEventHandler(this.panel_Chat_Scroll);
            this.panel_Chat.Click += new System.EventHandler(this.panel_Chat_Click);
            this.panel_Chat.ControlAdded += new System.Windows.Forms.ControlEventHandler(this.panel_Chat_ControlAdded);
            this.panel_Chat.ControlRemoved += new System.Windows.Forms.ControlEventHandler(this.panel_Chat_ControlRemoved);
            this.panel_Chat.Paint += new System.Windows.Forms.PaintEventHandler(this.panel_Chat_Paint);
            // 
            // panelBottomRight
            // 
            this.panelBottomRight.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.panelBottomRight.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(244)))), ((int)(((byte)(241)))));
            this.panelBottomRight.Controls.Add(this.SendFileButton);
            this.panelBottomRight.Controls.Add(this.sendImageButton);
            this.panelBottomRight.Controls.Add(this.textboxWriting);
            this.panelBottomRight.Controls.Add(this.buttonSend);
            this.panelBottomRight.Location = new System.Drawing.Point(0, 534);
            this.panelBottomRight.Margin = new System.Windows.Forms.Padding(0);
            this.panelBottomRight.Name = "panelBottomRight";
            this.panelBottomRight.Size = new System.Drawing.Size(912, 144);
            this.panelBottomRight.TabIndex = 0;
            this.panelBottomRight.TabStop = true;
            this.panelBottomRight.Click += new System.EventHandler(this.panelTopRight_Click);
            this.panelBottomRight.Paint += new System.Windows.Forms.PaintEventHandler(this.panelBottomRight_Paint);
            this.panelBottomRight.Resize += new System.EventHandler(this.panelBottomRight_Resize);
            // 
            // SendFileButton
            // 
            this.SendFileButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.SendFileButton.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(244)))), ((int)(((byte)(241)))));
            this.SendFileButton.BackgroundImage = global::A_Friend.Properties.Resources.file_icon_207228;
            this.SendFileButton.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.SendFileButton.BorderColor = System.Drawing.Color.Empty;
            this.SendFileButton.BorderRadius = 15;
            this.SendFileButton.BorderSize = 0;
            this.SendFileButton.FlatAppearance.BorderSize = 0;
            this.SendFileButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.SendFileButton.ForeColor = System.Drawing.Color.White;
            this.SendFileButton.Location = new System.Drawing.Point(21, 126);
            this.SendFileButton.MaximumSize = new System.Drawing.Size(20, 20);
            this.SendFileButton.MinimumSize = new System.Drawing.Size(20, 20);
            this.SendFileButton.Name = "SendFileButton";
            this.SendFileButton.Size = new System.Drawing.Size(20, 20);
            this.SendFileButton.TabIndex = 3;
            this.sendImageButton.TabStop = true;
            this.toolTip.SetToolTip(this.SendFileButton, "Send File");
            this.SendFileButton.UseVisualStyleBackColor = false;
            this.SendFileButton.Click += new System.EventHandler(this.SendFileButton_Click);
            // 
            // sendImageButton
            // 
            this.sendImageButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.sendImageButton.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(244)))), ((int)(((byte)(241)))));
            this.sendImageButton.BackgroundImage = global::A_Friend.Properties.Resources.camera_outline;
            this.sendImageButton.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.sendImageButton.BorderColor = System.Drawing.Color.Empty;
            this.sendImageButton.BorderRadius = 15;
            this.sendImageButton.BorderSize = 0;
            this.sendImageButton.FlatAppearance.BorderSize = 0;
            this.sendImageButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.sendImageButton.ForeColor = System.Drawing.Color.White;
            this.sendImageButton.Location = new System.Drawing.Point(1, 126);
            this.sendImageButton.MaximumSize = new System.Drawing.Size(20, 20);
            this.sendImageButton.MinimumSize = new System.Drawing.Size(20, 20);
            this.sendImageButton.Name = "sendImageButton";
            this.sendImageButton.Size = new System.Drawing.Size(20, 20);
            this.sendImageButton.TabIndex = 2;
            this.sendImageButton.TabStop = true;
            this.toolTip.SetToolTip(this.sendImageButton, "Send images");
            this.sendImageButton.UseVisualStyleBackColor = false;
            this.sendImageButton.Click += new System.EventHandler(this.sendImageButton_Click);
            // 
            // textboxWriting
            // 
            this.textboxWriting.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            //this.textboxWriting.BackColor = System.Drawing.SystemColors.Window;
            this.textboxWriting.Font = new System.Drawing.Font("Arial", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.textboxWriting.Location = new System.Drawing.Point(0, 0);
            this.textboxWriting.Margin = new System.Windows.Forms.Padding(0);
            this.textboxWriting.Name = "textboxWriting";
            this.textboxWriting.Size = new System.Drawing.Size(912, 120);
            this.textboxWriting.TabIndex = 0;
            this.textboxWriting.TabStop = true;
            this.textboxWriting.Text = "";
            this.textboxWriting.Click += new System.EventHandler(this.panelTopRight_Click);
            this.textboxWriting.SizeChanged += new System.EventHandler(this.textboxWriting_SizeChanged);
            this.textboxWriting.TextChanged += new System.EventHandler(this.textboxWriting__TextChanged);
            this.textboxWriting.KeyDown += new System.Windows.Forms.KeyEventHandler(this.textboxWriting_KeyDown);
            // 
            // buttonSend
            // 
            this.buttonSend.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonSend.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(230)))), ((int)(((byte)(244)))), ((int)(((byte)(241)))));
            this.buttonSend.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("buttonSend.BackgroundImage")));
            this.buttonSend.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.buttonSend.BorderColor = System.Drawing.Color.Empty;
            this.buttonSend.BorderRadius = 15;
            this.buttonSend.BorderSize = 0;
            this.buttonSend.FlatAppearance.BorderSize = 0;
            this.buttonSend.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.buttonSend.ForeColor = System.Drawing.Color.White;
            this.buttonSend.Location = new System.Drawing.Point(872, 126);
            this.buttonSend.MaximumSize = new System.Drawing.Size(40, 20);
            this.buttonSend.MinimumSize = new System.Drawing.Size(40, 20);
            this.buttonSend.Name = "buttonSend";
            this.buttonSend.Size = new System.Drawing.Size(40, 20);
            this.buttonSend.TabIndex = 1;
            this.buttonSend.TabStop = true;
            this.toolTip.SetToolTip(this.buttonSend, "Send message");
            this.buttonSend.UseVisualStyleBackColor = false;
            this.buttonSend.Click += new System.EventHandler(this.buttonSend_Click);
            // 
            // timerChat
            // 
            this.timerChat.Interval = 3000;
            this.timerChat.Tick += new System.EventHandler(this.timerChat_Tick);
            // 
            // PanelChat
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.panelBottomRight);
            this.Controls.Add(this.panel_Chat);
            this.Controls.Add(this.panelTopRight);
            this.DoubleBuffered = true;
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(0);
            this.Name = "PanelChat";
            this.Size = new System.Drawing.Size(912, 681);
            this.Load += new System.EventHandler(this.PanelChat_Load);
            this.panelTopRight.ResumeLayout(false);
            this.panelTopRight.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.friendPicture)).EndInit();
            this.panelBottomRight.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Panel panelTopRight;
        internal System.Windows.Forms.Label labelFriendName;
        private System.Windows.Forms.Label label3;
        private CirclePictureBox friendPicture;
        private CustomButton buttonDelete;
        internal System.Windows.Forms.Panel panel_Chat;
        private System.Windows.Forms.Panel panelBottomRight;
        private TranslucentRichTextBox textboxWriting;
        private CustomButton buttonSend;
        private System.Windows.Forms.Timer timerChat;
        private System.Windows.Forms.Label labelState;
        private System.Windows.Forms.ToolTip toolTip;
        private CustomButton sendImageButton;
        private CustomButton SendFileButton;
    }
}
