package ui.customcomponents;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class ContactItem extends JComponent{

    public Image avatar;
    public boolean Clicked;
    public Image defaultFriendPicture = (new ImageIcon(getClass().getResource("Resources/newUser.png"))).getImage();

    private JLabel labelLastMessage;
    private JLabel labelName;
    private custom.CircleAvatar friendPicture;

    public void setUnread(boolean b) {
        //TODO setUnread
    }
    
    public ContactItem(){
        initializeComponent();
    }

    private void initializeComponent() {
        labelLastMessage = new JLabel();
        labelName = new JLabel();
        friendPicture = new custom.CircleAvatar();

        // label last message
        labelLastMessage.setBackground(new Color(0f, 0f, 0f, 1f));
        labelLastMessage.setForeground(new Color(696969));
        // set font to arial, regular, 11
        labelLastMessage.setFont(new java.awt.Font("Arial", 0, 11));
        // set location to 75 36
        labelLastMessage.setLocation(75, 36);
        // set maximumsize to 197 20
        labelLastMessage.setMaximumSize(new java.awt.Dimension(197, 20));
        // set minimumsize to 197 20
        labelLastMessage.setMinimumSize(new java.awt.Dimension(197, 20));
        // set size to 197 20
        labelLastMessage.setSize(197, 20);
        // set text to "You: last text here"
        labelLastMessage.setText("You: last text here");
        // set click event
        labelLastMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelLastMessageMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelLastMessageMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelLastMessageMouseExited(evt);
            }
        });

        // label name
        labelName.setBackground(new Color(0f, 0f, 0f, 1f));
        // set font to arial, regular, 11
        labelName.setFont(new java.awt.Font("Arial", 0, 12));
        // set location to 75 36
        labelName.setLocation(75, 13);
        // set maximumsize to 197 20
        labelName.setMaximumSize(new java.awt.Dimension(197, 20));
        // set minimumsize to 197 20
        labelName.setMinimumSize(new java.awt.Dimension(197, 20));
        // set size to 197 20
        labelName.setSize(197, 20);
        // set text to "You: last text here"
        labelName.setText("Friend name");
        // set click event
        labelName.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelNameMouseClicked(evt);
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelNameMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelNameMouseExited(evt);
            }
        });
        // add resize event
        labelName.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                labelNameComponentResized(evt);
            }
        });

        // friend picture
        friendPicture.setBackground(new Color(0f, 0f, 0f, 1f));
        //TODO continue
    }

    protected void labelNameComponentResized(ComponentEvent evt) {
    }

    protected void labelNameMouseExited(MouseEvent evt) {
    }

    protected void labelNameMouseEntered(MouseEvent evt) {
    }

    protected void labelNameMouseClicked(MouseEvent evt) {
    }

    protected void labelLastMessageMouseExited(MouseEvent evt) {
    }

    protected void labelLastMessageMouseEntered(MouseEvent evt) {
    }

    protected void labelLastMessageMouseClicked(MouseEvent evt) {
    }
}
