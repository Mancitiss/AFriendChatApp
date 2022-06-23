package com.mycompany.afriendjava;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.*;

import com.mycompany.afriendjava.custom.TextField;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
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
    public Image appIcon = (new ImageIcon(getClass().getResource("/com/mycompany/afriendjava/Resources/logo.ico"))).getImage();
    Image addIcon = (new ImageIcon(getClass().getResource("/com/mycompany/afriendjava/Resources/Add.png"))).getImage();
    Image settingIcon = (new ImageIcon(getClass().getResource("/com/mycompany/afriendjava/Resources/Cogs.png"))).getImage();
    Image logoutIcon = (new ImageIcon(getClass().getResource("/com/mycompany/afriendjava/Resources/sign-out-option.png"))).getImage();

    private JPanel ContactList_Panel;
    private JPanel panelLeft;
    private JPanel panelRight;
    private JPanel panelRight2;
    private JPanel panelTopLeft;
    private JScrollPane ContactList_ScrollPanel;
    private JPanel panelAdd;
    private JPanel Button_Panel;
    private JButton LogOut_Button;
    private JButton Setting_Button;
    private JButton AddFriend_Button;
    private JTextField SearchBar;
    private JButton AddGroup_Button;
    private JLabel labelWarning;
    private JTextField txtNewUser;
    // end initialization

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
                        if (panelChats.get(min).DateTimeOfLastMessage().getTime() > panelChats.get(keyValuePair.getValue()).DateTimeOfLastMessage().getTime()){
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
                // clear controls of chat panel
                panelRight.removeAll();
                SearchBar.setVisible(false);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void bringContactItemToTop(String id) {
        if (orderOfContactItems.size() <= 1){
            return;
        }
        int key = -1;
        for(Entry<Integer, String> keyValuePair: orderOfContactItems.entrySet()){
            if (keyValuePair.getValue().equals(id)){
                key = keyValuePair.getKey();
                break;
            }
        }

        if (key != -1){
            orderOfContactItems.remove(key);
            orderOfContactItems.put(orderOfContactItems.size(), id);
        }

        if (loaded){
            ui.customcomponents.ContactItem item = contactItems.get(id);
            if (searchText == ""){
                ContactList_Panel.remove(item);
                ContactList_Panel.add(item);
            }
        }
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
        ui.customcomponents.PanelChat item = panelChats.get(id);
        if (panelRight.getComponentCount() == 0)
        {
            /* 
            if (force || (getCurrentPanelChatId() == "") || !()){

            }*/
        }
    }
    public synchronized void addContactItem(Account acc){
        try{
            if (!panelChats.containsKey(acc.id)){
                System.out.println("add contact item");
                ContactItem ci = new ContactItem("Ai cũng đc", "ê thg kia", true);
                //ci.setUnread(true);
                if (loaded){
                    ContactList_Panel.add(ContactLayout.createContactItemBox(ci), ContactList_Panel.getComponentCount() - 1);
                }
                else{
                    ContactList_Panel.add(ContactLayout.createContactItemBox(ci), ContactList_Panel.getComponentCount() - 1);
                    System.out.println("unread");
                }
                System.out.println("added");
                if (orderOfContactItems.size() == 0){
                    orderOfContactItems.put(0, acc.id);
                }
                else{
                    orderOfContactItems.put(orderOfContactItems.lastKey() + 1, acc.id);
                }

                if (loaded){
                    // scroll ContactList_Panel to where the new contact item is
                    ContactList_ScrollPanel.getVerticalScrollBar().setValue(ci.getParent().getLocation().y);
                }
                contactItems.put(acc.id, ci);
                PanelChat panelChat = new PanelChat(acc);
                panelChats.put(acc.id, panelChat);
                System.out.println(ci.getVisibleRect().toString());
                System.out.println(((Box)ci.getParent()).getVisibleRect().toString());
                ContactList_Panel.revalidate();
                ContactList_Panel.repaint();

                panelChat.LoadMessage();
                panelChat.scrollToBottom();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    public synchronized void addMessageItem(String str, boolean left){
        //TODO add message item
    }
    public synchronized void turnContactActiveState(String id, byte state){
        //TODO turn contact active state
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
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) 
    {                                     
        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) 
        {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            Image image = Toolkit.getDefaultToolkit().getImage("src/com/mycompany/afriendjava/Resources/sign-out-option.png");
            // create a action listener to listen for default action executed on the tray icon
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // execute default action of the application
                    // ...
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();
            // create menu item for the default action
            MenuItem defaultItem = new MenuItem("Hello");
            defaultItem.addActionListener(listener);
            popup.add(defaultItem);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "Tray Demo", popup);
            // set the TrayIcon properties
            trayIcon.addActionListener(listener);
            // ...
            // add the tray image
            try 
            {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e);
            }
            // ...
        } 
        else {
            System.out.println("System tray not supported");
            
        }
        // disable tray option in your application or
        // perform other actions
            
        //}
        // ...
        // some time later
        // the application state has changed - update the image
            
        if (trayIcon != null) {
            //trayIcon.setImage(updatedImage);
        }
        // ...*/
                
    }
    /**
     * Creates new form Main
     */
    public MainUI() {
        initComponents();

    	setTitle("A Friend Chat App");
    	setIconImage(appIcon);

        //take screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        this.setBounds(width/4, height/4, width/2, height/2);

        //getContentPane().setBackground(new Color(0, 205, 205));

        panelRight = new JPanel();

    }

    private void initComponents(){

        AddFriend_Button = new JButton();
        AddFriend_Button.setMinimumSize(new Dimension(40, 40));
        AddFriend_Button.setMaximumSize(new Dimension(40, 40));
        AddFriend_Button.setLocation(110, 10);
        AddFriend_Button.setSize(40, 40);
        AddFriend_Button.setIcon(new ImageIcon(addIcon.getScaledInstance(AddFriend_Button.getWidth(), AddFriend_Button.getHeight(), Image.SCALE_SMOOTH)));
        AddFriend_Button.setVisible(true);
        AddFriend_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFriend(e);
            }
        });
        
        Setting_Button = new JButton("");
        Setting_Button.setMinimumSize(new Dimension(40, 40));
        Setting_Button.setMaximumSize(new Dimension(40, 40));
        Setting_Button.setLocation(60, 10);
        Setting_Button.setSize(40, 40);
        Setting_Button.setIcon(new ImageIcon(settingIcon.getScaledInstance(Setting_Button.getWidth(), Setting_Button.getHeight(), Image.SCALE_SMOOTH)));
        Setting_Button.setVisible(true);
        Setting_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setting(e);
            }
        });
        
        LogOut_Button = new JButton("");
        LogOut_Button.setMinimumSize(new Dimension(40, 40));
        LogOut_Button.setMaximumSize(new Dimension(40, 40));
        LogOut_Button.setLocation(10, 10);
        LogOut_Button.setSize(40, 40);
        LogOut_Button.setIcon(new ImageIcon(logoutIcon.getScaledInstance(LogOut_Button.getWidth(), LogOut_Button.getHeight(), Image.SCALE_SMOOTH))); 
        LogOut_Button.setVisible(true);
        LogOut_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout(e);
            }
        });

        /*
        
        AddGroup_Button = new JButton("");
        AddGroup_Button.setMinimumSize(new Dimension(40, 40));
        AddGroup_Button.setMaximumSize(new Dimension(40, 40));
        AddGroup_Button.setLocation(170, 10);
        AddGroup_Button.setSize(40, 40);
        AddGroup_Button.setIcon(null);
        AddGroup_Button.setVisible(true);

        */
        SearchBar = new JTextField();
        SearchBar.setBackground(Color.WHITE);
        SearchBar.setLocation(22, 9);
        SearchBar.setSize(258, 43);
        SearchBar.setOpaque(true);
        // text change listener
        SearchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                search(e);
            }
        });
        TextPrompt tpSearch = new TextPrompt("Search", SearchBar);
        
        panelTopLeft = new JPanel(){
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                // set color to gray
                g2d.setColor(Color.decode("#808080"));
                // set width of the line
                g2d.setStroke(new BasicStroke(1));
                // draw line
                g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() -1);
            }
        };
        panelTopLeft.setOpaque(true);
        panelTopLeft.setBackground(new Color(60, 141, 138));
        panelTopLeft.setLocation(0, 0);
        panelTopLeft.setSize(300, 60);
        panelTopLeft.setLayout(null);
        panelTopLeft.add(SearchBar);
        
        ContactList_Panel = new JPanel();
        ContactList_Panel.setLayout(new BoxLayout(ContactList_Panel, BoxLayout.Y_AXIS));
        ContactList_Panel.setLocation(0,0);
        ContactList_Panel.setMinimumSize(new Dimension(0, 0));
        ContactList_Panel.setSize(300, 514);
        ContactList_Panel.setBackground(new Color(247, 249, 255));
        ContactList_Panel.setVisible(true);
        ContactList_Panel.add(Box.createVerticalGlue());
        
        ContactList_ScrollPanel = new JScrollPane(ContactList_Panel);
        ContactList_ScrollPanel.setLocation(0, 60);
        ContactList_ScrollPanel.setBorder(null);
        ContactList_ScrollPanel.setDoubleBuffered(true);
        ContactList_ScrollPanel.setSize(300, 514);
        ContactList_ScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ContactList_ScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // scrollbar speed
        ContactList_ScrollPanel.getVerticalScrollBar().setUnitIncrement(19);

        labelWarning = new JLabel();
        labelWarning.setOpaque(true);
        labelWarning.setBackground(new Color(247, 249, 255));
        labelWarning.setLocation(0, 52);
        labelWarning.setSize(295, 25);
        // set font to microsoft sans serif, 10f, regular, gdicharset = 163
        labelWarning.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 14));
        labelWarning.setForeground(Color.RED);
        // align text to center
        labelWarning.setHorizontalAlignment(JLabel.CENTER);
        labelWarning.setText("This user does not exist");

        txtNewUser = new JTextField();
        txtNewUser.setBackground(Color.WHITE);
        txtNewUser.setLocation(17, 6);
        txtNewUser.setSize(263, 38);
        txtNewUser.setOpaque(true);
        // enter key listener
        txtNewUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addNewUser(e);
                }
            }
        });

        panelAdd = new JPanel();
        panelAdd.setOpaque(true);
        panelAdd.setLocation(0, 574);
        panelAdd.setSize(300, 78);
        panelAdd.setBackground(new Color(247, 249, 255));
        panelAdd.setLayout(null);
        panelAdd.add(txtNewUser);
        panelAdd.add(labelWarning);
        
        Button_Panel = new JPanel();
        Button_Panel.setOpaque(true);
        Button_Panel.setBackground(new Color(230, 244, 241));
        Button_Panel.setLocation(0, 652);
        Button_Panel.setSize(300, 60);
        Button_Panel.setDoubleBuffered(true);
        Button_Panel.setLayout(null);
        Button_Panel.add(LogOut_Button);
        Button_Panel.add(Setting_Button);
        Button_Panel.add(AddFriend_Button);
        //Button_Panel.add(AddGroup_Button);

        panelLeft = new JPanel();
        panelLeft.setLocation(0, 0);
        panelLeft.setSize(300, 712);
        panelLeft.setBackground(Color.white);
        panelLeft.setLayout(null);
        panelLeft.add(panelTopLeft);
        panelLeft.add(ContactList_ScrollPanel);
        panelLeft.add(panelAdd);
        panelLeft.add(Button_Panel);
        panelLeft.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = e.getComponent().getHeight();
                int width = e.getComponent().getWidth();
                ContactList_ScrollPanel.setSize(width, height - panelTopLeft.getHeight() - Button_Panel.getHeight() - panelAdd.getHeight());
                panelAdd.setLocation(0, ContactList_ScrollPanel.getY() + ContactList_ScrollPanel.getHeight());
                Button_Panel.setLocation(0, panelAdd.getY() + panelAdd.getHeight());
            }
        });
        
        panelRight = new JPanel();        
        panelRight.setLocation(350,0);
        panelRight.setBackground(Color.red);
        panelRight.setSize(getWidth() - 350, getHeight());
        panelRight.setMinimumSize(new Dimension(0, 0));
        panelRight.setVisible(true);
        
        this.setLayout(null);
        this.setMinimumSize(new Dimension(900,450));
        this.add(panelLeft);
        this.add(panelRight);
        
        //event
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {                                       
                int width = evt.getComponent().getWidth();
                // height without frame's title bar
                int height = evt.getComponent().getHeight() - ((MainUI)evt.getComponent()).getInsets().top;
                panelLeft.setBounds(0, 0, 300, height);
                panelRight.setBounds(300, 0, width - 300, height);
            }         
        });
        /* 
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
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        */
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void addNewUser(KeyEvent e) {
        try{
            System.out.println("adding");
            addContactItem(new Account("123", "123", txtNewUser.getText(), (byte)0));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    protected void search(DocumentEvent e) {
    }
    protected void logout(ActionEvent e) {
    }
    protected void setting(ActionEvent e) {
    }
    protected void addFriend(ActionEvent e) {
        if (ContactList_ScrollPanel.getSize().height == Button_Panel.getY() - (ContactList_ScrollPanel.getY() + ContactList_ScrollPanel.getHeight())) {
            ContactList_ScrollPanel.setSize(ContactList_ScrollPanel.getWidth(), ContactList_ScrollPanel.getHeight() - panelTopLeft.getHeight());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	JFrame f = new MainUI();
                f.setVisible(true);       
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}