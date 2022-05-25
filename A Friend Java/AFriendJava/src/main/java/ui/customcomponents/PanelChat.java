package ui.customcomponents;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.mycompany.afriendjava.MessageObject;

import custom.CircleAvatar;
import net.miginfocom.swing.MigLayout;

public class PanelChat extends javax.swing.JPanel{

    private JPanel panelTopRight;
    public JLabel labelFriendName;
    private JLabel label3;
    private CircleAvatar friendPicture;
    private JButton buttonDelete;
    public JPanel panelChat;
    private JPanel panelBottomRight;
    private JTextArea textboxWriting;
    private JButton buttonSend;
    private java.util.Timer timerChat;
    private JLabel labelState;
    private JButton sendImageButton;
    private JButton sendFileButton;

    private void initializeComponent(){
        panelTopRight = new JPanel();
        labelFriendName = new JLabel();
        label3 = new JLabel();
        friendPicture = new CircleAvatar();
        buttonDelete = new JButton();
        panelChat = new JPanel();
        panelBottomRight = new JPanel();
        textboxWriting = new JTextArea();
        buttonSend = new JButton();
        labelState = new JLabel();
        sendImageButton = new JButton();
        sendFileButton = new JButton();

        panelTopRight.setLayout(new MigLayout());
        panelTopRight.setBackground(new Color(46, 140, 130));
        panelTopRight.add(labelState);
        panelTopRight.add(labelFriendName);
        panelTopRight.add(label3);
        panelTopRight.add(friendPicture);
        panelTopRight.add(buttonDelete);

    
    }
    private void mustInit(){

    }
    public PanelChat(){

    }

    public ConcurrentLinkedQueue<String> filesToSend = new ConcurrentLinkedQueue<String>();
    public HashMap<Long, AFChatItem> messages = new HashMap<Long, AFChatItem>();
    public Image avatar;

    public void removeMessage(Long messageNumber) {
        //TODO removeMessage
    }

    public boolean isLastMessageFromYou() {
        return false;
    }

    public void addMessage(MessageObject msgobj) {
        //TODO add message
    }

    public void loadMessages(MessageObject[] messageObjects) {
        //TODO load messages
    }

    public int DateTimeOfLastMessage() {
        return 0;
    }

    public void scrollToBottom() {
    }
    

}
