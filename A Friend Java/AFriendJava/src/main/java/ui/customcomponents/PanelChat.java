package ui.customcomponents;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.mycompany.afriendjava.MessageObject;

public class PanelChat {

    public ConcurrentLinkedQueue<String> filesToSend = new ConcurrentLinkedQueue<String>();
    public HashMap<Long, AFChatItem> messages = new HashMap<Long, AFChatItem>();

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
    
}