package ui.customcomponents;

import java.awt.Image;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.mycompany.afriendjava.MessageObject;

public class PanelChat extends javax.swing.JPanel{

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
