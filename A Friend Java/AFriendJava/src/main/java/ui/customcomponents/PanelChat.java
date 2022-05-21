package ui.customcomponents;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PanelChat {

    public ConcurrentLinkedQueue<String> filesToSend = new ConcurrentLinkedQueue<String>();
    public HashMap<Long, AFChatItem> messages = new HashMap<Long, AFChatItem>();

    public void removeMessage(Long messageNumber) {
    }
    
}
