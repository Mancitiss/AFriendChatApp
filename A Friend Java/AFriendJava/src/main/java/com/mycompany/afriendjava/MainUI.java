package com.mycompany.afriendjava;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.*;

import com.mycompany.afriendjava.custom.TextField;
import ui.customcomponents.*;

import javax.swing.SwingConstants;

public class MainUI extends javax.swing.JFrame {
    
    // variables
    public PanelChat currentPanelChat;
    public HashMap<String, PanelChat> panelChats = new HashMap<String, PanelChat>();
    public HashMap<String, ContactItem> contactItems = new HashMap<String, ContactItem>();
    public TreeMap<Integer, String> orderOfContactItems = new TreeMap<Integer, String>();
    
    public static ConcurrentHashMap<String, JFrame> subForms = new ConcurrentHashMap<String, JFrame>();
    public static String currentID;

    private boolean check = true;
    private String searchText = "";
    public boolean loaded = false;
    private boolean priv = false;

    // forms
    // private FormContactRemoved formContactRemoved = new FormContactRemoved();
    // private FormGetStarted formGetStarted = new FormGetStarted();
    public Loading formLoading = new Loading();
    public Settings formSettings = new Settings();
    public FormAddContact formAddContact = new FormAddContact();
    public JPanel panelLoading = new JPanel();

    // initialization
    private JPanel ContactList_Panel;
    private JPanel Chat_Panel;
    private JPanel SearchBar_Panel;
    private JScrollPane ContactList_ScrollPanel;
    private JPanel Button_Panel;
    private JButton LogOut_Button;
    private JButton Setting_Button;
    private JButton AddFriend_Button;
    private TextField SearchBar;
    // end initialization
    
    public Image logoutIcon = (new ImageIcon(getClass().getResource("Resources/sign-out-option.png"))).getImage();
    private ContactItem currentContactItem;


    public boolean isThisPersonAdded(String id) {
        return contactItems.containsKey(id);
    }
    public synchronized void sortContactItems(){
        try{
            int length = contactItems.size();
            for(int i = 0; i < length; i++){
                String min = "";
                int j = 0;
                for(Entry<Integer, String> keyValuePair: orderOfContactItems.entrySet()){
                    if (j == length){
                        break;
                    }
                    if (min == ""){
                        min = keyValuePair.getValue();
                    }
                    else
                    {
                        if (panelChats.get(min).DateTimeOfLastMessage() > panelChats.get(keyValuePair.getValue()).DateTimeOfLastMessage()){
                            min = keyValuePair.getValue();
                        }
                    }
                    j = j + 1;
                }
                length = length - 1;
                bringContactItemToTop(min);
            }

            for(Entry<Integer, String> keyValuePair1: orderOfContactItems.entrySet()){
                ContactList_Panel.add(contactItems.get(keyValuePair1.getValue()));
            }

            loaded = true;

            //panelLoading.toBack(); //how?
            formLoading.stopSpinning();
            formLoading.dispose();
            panelLoading.setVisible(false);

            if(panelChats.size() > 0){
                showPanelChat(orderOfContactItems.lastEntry().getValue(), false);
                panelChats.get(orderOfContactItems.lastEntry().getValue()).scrollToBottom();
                this.currentContactItem = contactItems.get(orderOfContactItems.lastEntry().getValue());
                this.currentContactItem.clicked = true;
            }
            else {
                // clear controls of panelright
                //panelRight.removeAll();
                //TODO: clear controls of panelright
                SearchBar.setVisible(false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void bringContactItemToTop(String min) {
    }
    public synchronized void setAvatar(String id, Image img){
        if(panelChats.containsKey(id)){
            panelChats.get(id).avatar = img;
            contactItems.get(id).avatar = img;
        }
    }
    public synchronized void showLogin(){
        for(JFrame f: subForms.values()){
            f.dispose();
        }
        this.dispose();
        Login lg = new Login();
        lg.setVisible(true);
        Program.mainform = null;
        try{
            if (AFriendClient.user != null){
                AFriendClient.user.state = 0;
            }
        }
        catch(Exception e){
            AFriendClient.user = null;
        }
    }
    public synchronized void showPanelChat(String id, boolean force){
        //TODO show panel chat
    }
    public synchronized void addContactItem(Account acc){
        //TODO add contact item
    }
    public synchronized void addMessageItem(String str, boolean left){
        //TODO add message item
    }
    public synchronized void turnContactActiveState(String id, byte state){
        //TODO turn contact active state
    }


    /**
     * Creates new form Main
     */
    public MainUI() {
    	setTitle("AFriend Chat");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("Resources/logo.png"));
        try {
			initComponents();
		} catch (IOException e) {
			e.printStackTrace();
		}
        getContentPane().setBackground(new Color(255, 255, 255));
    }

    private void initComponents() throws IOException {

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        
        ContactList_Panel = new JPanel();
        ContactList_Panel.setMinimumSize(new Dimension(20000, 10));
        ContactList_Panel.setBackground(Color.WHITE);
        
        Chat_Panel = new JPanel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addComponent(ContactList_Panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(Chat_Panel, GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(ContactList_Panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        		.addComponent(Chat_Panel, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
        
        
        
        SearchBar_Panel = new JPanel();
        SearchBar_Panel.setBackground(new Color(0, 204, 204));
        
        ContactList_ScrollPanel = new JScrollPane();
        
        Button_Panel = new JPanel();
        Button_Panel.setBackground(Color.PINK);
        GroupLayout gl_ContactList_Panel = new GroupLayout(ContactList_Panel);
        gl_ContactList_Panel.setHorizontalGroup(
        	gl_ContactList_Panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_ContactList_Panel.createSequentialGroup()
        			.addGroup(gl_ContactList_Panel.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(Button_Panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
        				.addComponent(SearchBar_Panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(ContactList_ScrollPanel, Alignment.TRAILING))
        			.addGap(0))
        );
        gl_ContactList_Panel.setVerticalGroup(
        	gl_ContactList_Panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_ContactList_Panel.createSequentialGroup()
        			.addComponent(SearchBar_Panel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(ContactList_ScrollPanel, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(Button_Panel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        );
        
        LogOut_Button = new JButton("");
        LogOut_Button.setMinimumSize(new Dimension(40, 40));
        //LogOut_Button.setIcon(new ImageIcon("D:\\programer\\Eclipse\\MainUI\\Resources\\sign-out-option.png"));
        //LogOut_Button.setIcon(new ImageIcon(logoutIcon.getScaledInstance(LogOut_Button.getWidth(), LogOut_Button.getHeight(), Image.SCALE_SMOOTH)));
        LogOut_Button.addComponentListener(new ComponentAdapter() {         
            @Override
            public void componentResized(ComponentEvent e) {
                LogOut_Button.setIcon(new ImageIcon(logoutIcon.getScaledInstance(LogOut_Button.getWidth(), LogOut_Button.getHeight(), Image.SCALE_SMOOTH)));
            }
        });
        
        Setting_Button = new JButton("");
        Setting_Button.setIcon(null);
        
        AddFriend_Button = new JButton("");
        AddFriend_Button.setIcon(null);
        GroupLayout gl_Button_Panel = new GroupLayout(Button_Panel);
        gl_Button_Panel.setHorizontalGroup(
        	gl_Button_Panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_Button_Panel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(LogOut_Button, GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(Setting_Button, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(AddFriend_Button, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
        			.addGap(153))
        );
        gl_Button_Panel.setVerticalGroup(
        	gl_Button_Panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_Button_Panel.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_Button_Panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(LogOut_Button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(Setting_Button)
        				.addComponent(AddFriend_Button))
        			.addGap(0))
        );
        Button_Panel.setLayout(gl_Button_Panel);
        
        SearchBar = new TextField();
        SearchBar.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		SearchBar.removeItemSuggestion(SearchBar.getText());
        	}
        });
        SearchBar.setHorizontalAlignment(SwingConstants.LEFT);
        SearchBar.setBackground(Color.RED);
        TextPrompt tpSearch = new TextPrompt("Search", SearchBar);
        GroupLayout gl_SearchBar_Panel = new GroupLayout(SearchBar_Panel);
        gl_SearchBar_Panel.setHorizontalGroup(
        	gl_SearchBar_Panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_SearchBar_Panel.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(SearchBar, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_SearchBar_Panel.setVerticalGroup(
        	gl_SearchBar_Panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_SearchBar_Panel.createSequentialGroup()
        			.addGap(19)
        			.addComponent(SearchBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SearchBar_Panel.setLayout(gl_SearchBar_Panel);
        ContactList_Panel.setLayout(gl_ContactList_Panel);
        getContentPane().setLayout(layout);
        
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int W = (int)width;
        int H = (int)height;
        
        File imageCheck = new File("Resources/sign-out-option.png");
        if(imageCheck.exists()) 
            System.out.println("Image file found!");
        else 
            System.out.println("Image file not found!");
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JFrame f = new MainUI();
                f.setVisible(true);
                f.setBounds(W/4,H/4,W/2,H/2);
                f.setDefaultCloseOperation(HIDE_ON_CLOSE);
            }
        });
    }
}