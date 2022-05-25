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
    
    static int He;
    static int We;
    Dimension d;
    Dimension dMin;
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
    private JButton AddGroup_Button;
    // end initialization
    
    public Image appIcon = (new ImageIcon(getClass().getResource("/com/mycompany/afriendjava/Resources/logo.ico"))).getImage();
    public Image logoutIcon = (new ImageIcon(getClass().getResource("/com/mycompany/afriendjava/Resources/sign-out-option.png"))).getImage();
    public ContactItem currentContactItem;


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

    private void formComponentResized(java.awt.event.ComponentEvent evt) {                                      
        
        int SetHeight = getHeight();
        int SetWidth = getWidth();
        //System.out.println(SetHeight);
        ContactList_Panel.setSize(350,SetHeight);
        Chat_Panel.setSize(SetWidth - 350, SetHeight);
        Button_Panel.setLocation(0, SetHeight - 98);
        ContactList_ScrollPanel.setSize(350,SetHeight - 158);
    }       
    
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {   
        int SetHeight = getHeight();
        int SetWidth = getWidth();
        Button_Panel.setLocation(0, SetHeight - 98);
        ContactList_ScrollPanel.setSize(350,SetHeight - 158);
    }  
    
    private void TestMouseClicked(java.awt.event.MouseEvent evt) {                                      
        Button_Panel.setBackground(Color.white);
    }
    /**
     * Creates new form Main
     */
    public MainUI() {
    	setTitle("AFriend Chat");
    	setIconImage(appIcon);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        We = (int)width;
        He = (int)height;
        if((We/2<900)&(He/2<457)){
        setBounds(200,100,900,457);
        }
        else{
        setBounds(We/4,He/4,We/2,He/2);
        }
                
        setMinimumSize(new Dimension(900,457));
        setLayout(null);
        try {
			initComponents(We,He);
		} catch (IOException e) {
			e.printStackTrace();
		}
        //getContentPane().setBackground(new Color(0, 205, 205));
    }

    private void initComponents(int W,int H) throws IOException {

        //setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        //Container contentPane = this.getContentPane(); 
        //getContentPane().setMinimumSize(new Dimension(900,457));
        /*d = getSize();
        dMin = getMinimumSize();
        if(d.width<dMin.width)
        d.width=dMin.width;
        if(d.height<dMin.height)
        d.height=dMin.height;
        this.setSize(d);*/
        //getContentPane().setSize(700,457);
        
        ContactList_Panel = new JPanel();
        ContactList_Panel.setLayout(null);
        ContactList_Panel.setLocation(0,0);
        ContactList_Panel.setMinimumSize(new Dimension(0, 0));
        ContactList_Panel.setSize(350, getHeight());
        ContactList_Panel.setBackground(Color.WHITE);
        ContactList_Panel.setVisible(true);
        
        Chat_Panel = new JPanel();        
        Chat_Panel.setLocation(350,0);
        Chat_Panel.setBackground(Color.WHITE);
        Chat_Panel.setSize(getWidth() - 350, getHeight());
        Chat_Panel.setMinimumSize(new Dimension(0, 0));
        Chat_Panel.setVisible(true);
        
        getContentPane().add(ContactList_Panel);        
        getContentPane().add(Chat_Panel);
        
        
        SearchBar_Panel = new JPanel();
        SearchBar_Panel.setBackground(new Color(0, 204, 204));
        SearchBar_Panel.setLocation(0, 0);
        SearchBar_Panel.setSize(350, 60);
        
        ContactList_ScrollPanel = new JScrollPane();
        ContactList_ScrollPanel.setLocation(0, 60);
        ContactList_ScrollPanel.setBorder(null);
        ContactList_ScrollPanel.setDoubleBuffered(true);
        ContactList_ScrollPanel.setSize(350, 300);
        
        Button_Panel = new JPanel();
        Button_Panel.setBackground(Color.PINK);
        Button_Panel.setLocation(0, 360);
        Button_Panel.setSize(350, 60);
        Button_Panel.setDoubleBuffered(true);
        Button_Panel.setLayout(null);

        ContactList_Panel.add(SearchBar_Panel);        
        ContactList_Panel.add(ContactList_ScrollPanel);
        ContactList_Panel.add(Button_Panel);
        
        LogOut_Button = new JButton("");
        LogOut_Button.setMinimumSize(new Dimension(40, 40));
        LogOut_Button.setLocation(20, 10);
        LogOut_Button.setSize(40, 40);
        LogOut_Button.setIcon(new ImageIcon(logoutIcon.getScaledInstance(LogOut_Button.getWidth(), LogOut_Button.getHeight(), Image.SCALE_SMOOTH))); 
        LogOut_Button.setVisible(true);
        
        Setting_Button = new JButton("");
        Setting_Button.setMinimumSize(new Dimension(40, 40));
        Setting_Button.setLocation(70, 10);
        Setting_Button.setSize(40, 40);
        Setting_Button.setIcon(null);
        Setting_Button.setVisible(true);
        
        AddFriend_Button = new JButton("");
        AddFriend_Button.setMinimumSize(new Dimension(40, 40));
        AddFriend_Button.setLocation(120, 10);
        AddFriend_Button.setSize(40, 40);
        AddFriend_Button.setIcon(null);
        AddFriend_Button.setVisible(true);
        
        AddGroup_Button = new JButton("");
        AddGroup_Button.setMinimumSize(new Dimension(40, 40));
        AddGroup_Button.setLocation(170, 10);
        AddGroup_Button.setSize(40, 40);
        AddGroup_Button.setIcon(null);
        AddGroup_Button.setVisible(true);


        Button_Panel.add(LogOut_Button);
        Button_Panel.add(Setting_Button);
        Button_Panel.add(AddFriend_Button);
        Button_Panel.add(AddGroup_Button);
        
        SearchBar = new TextField();
        SearchBar.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		SearchBar.removeItemSuggestion(SearchBar.getText());
        	}
        });
        SearchBar.setBackground(Color.WHITE);
        SearchBar.setLocation(40, 10);
        SearchBar.setSize(270, 36);
        SearchBar.setOpaque(true);
        TextPrompt tpSearch = new TextPrompt("Search", SearchBar);

        SearchBar_Panel.setLayout(null);
        SearchBar_Panel.add(SearchBar);    
        
        //event
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        AddGroup_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TestMouseClicked(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            @Override
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        
        pack();
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
        
        /*
        File imageCheck = new File("/com/mycompany/afriendjava/Resources/sign-out-option.png");
        if(imageCheck.exists()) 
            System.out.println("Image file found!");
        else 
            System.out.println("Image file not found!");
        */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JFrame f = new MainUI();
                f.setVisible(true);       
                f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
}