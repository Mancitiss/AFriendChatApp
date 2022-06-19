package ui.customcomponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.AlphaComposite;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.event.ActionEvent;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.mycompany.afriendjava.AFriendClient;
import com.mycompany.afriendjava.Account;
import com.mycompany.afriendjava.MessageObject;
import com.mycompany.afriendjava.Tools;

import custom.CircleAvatar;

public class PanelChat extends javax.swing.JPanel{

    private JPanel panelTop;
    private CircleAvatar friendPicture;
    public JLabel labelFriendName;
    private JLabel labelState;
    private JButton buttonDelete;


    public JPanel panelChat;


    private JPanel panelBottom;
    private JTextArea textboxWriting;
    JScrollPane textboxScroll;
    private JButton buttonSend;
    private JButton sendImageButton;
    private JButton sendFileButton;
    
    private java.util.Timer timerChat;

    private String id;
    private Color stateColor = Color.decode("#DCDCDC");
    private java.awt.BasicStroke stroke1 = new java.awt.BasicStroke(1.0f);

    public ConcurrentLinkedQueue<String> filesToSend = new ConcurrentLinkedQueue<String>();
    public HashMap<Long, AFChatItem> messages = new HashMap<Long, AFChatItem>();
    public Image avatar =  (new ImageIcon(getClass().getResource("newUser.png"))).getImage();
    public Image deleteIcon = (new ImageIcon(getClass().getResource("deleteIcon.png"))).getImage();
    public Image sendIcon = (new ImageIcon(getClass().getResource("paper-plane-regular.png"))).getImage();
    public Image sendImageIcon = (new ImageIcon(getClass().getResource("camera-outline.png"))).getImage();
    public Image sendFileIcon = (new ImageIcon(getClass().getResource("file_icon_207228.png"))).getImage();

    private static final String TEXT_SUBMIT = "text-submit";

    private void initializeComponent(){
        
        friendPicture = new CircleAvatar();
        labelFriendName = new JLabel();
        labelState = new JLabel();
        buttonDelete = new JButton();
        panelChat = new JPanel(){
            @Override
            public java.awt.Dimension getMinimumSize(){
                return getPreferredSize();
            }

            @Override
            public java.awt.Dimension getMaximumSize(){
                return getPreferredSize();
            }

            @Override
            public java.awt.Dimension getPreferredSize(){
                return new java.awt.Dimension(this.getParent().getSize().width, this.getParent().getSize().height - 60 - 144);
            }

            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.GRAY);
                g2d.setStroke(stroke1);
                g2d.drawLine(0, 0, 0, panelChat.getSize().height);
            }
        };
        panelBottom = new JPanel(){
            @Override
            public java.awt.Dimension getMinimumSize(){
                return getPreferredSize();
            }

            @Override
            public java.awt.Dimension getMaximumSize(){
                return getPreferredSize();
            }

            @Override
            public java.awt.Dimension getPreferredSize(){
                return new java.awt.Dimension(this.getParent().getSize().width, 144);
            }

            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.GRAY);
                g2d.setStroke(stroke1);
                g2d.drawLine(0, 1, panelBottom.getSize().width, 1);
                g2d.drawLine(0, 0, 0, panelBottom.getSize().height);
            }
        };

        friendPicture.setBounds(18, 7, 45, 45);
        friendPicture.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                System.out.println("entered");
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                System.out.println("exited");
            }
        });

        labelFriendName.setBounds(72, 10, 80, 18);
        labelFriendName.setFont(new java.awt.Font("Arial", 0, 12));
        labelFriendName.setText("ADSADSADSADSADDASDDASDSAASDASDASDASADSADSADSASSD ASD ADSASD ASD ASD DS ASD ADS ASD AD SASD ASD AD SASD ");

        labelState.setBounds(73, 32, 49, 18);
        labelState.setFont(new java.awt.Font("Arial", 0, 12));
        labelState.setText("offline");

        buttonDelete.setOpaque(false);
        buttonDelete.setBackground(new Color(230, 244, 241));
        buttonDelete.setBounds(860, 10, 40, 40);
        try{
            //Resize the image to fit the button
            Image newImg = new ImageIcon(deleteIcon.getScaledInstance(buttonDelete.getSize().width - 6, buttonDelete.getSize().height - 6, Image.SCALE_SMOOTH)).getImage();
            buttonDelete.setIcon(new ImageIcon(newImg));
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }


        panelTop = new JPanel(){
            @Override
            public java.awt.Dimension getMinimumSize(){
                return getPreferredSize();
            }

            @Override
            public java.awt.Dimension getMaximumSize(){
                return getPreferredSize();
            }

            @Override
            public java.awt.Dimension getPreferredSize(){
                return new java.awt.Dimension(this.getParent().getSize().width, 60);
            }

            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                // graphics2D object
                Graphics2D g2d = (Graphics2D) g;
                //set thickness of the stroke
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setStroke(stroke1);
                g2d.setColor(stateColor);
                g2d.drawOval(friendPicture.getLocation().x - 1, friendPicture.getLocation().y - 1, friendPicture.getWidth() + 1, friendPicture.getHeight() + 1);
                g2d.setColor(Color.gray);
                g2d.drawLine(0, panelTop.getSize().height - 1, panelTop.getSize().width, panelTop.getSize().height - 1);
                g2d.drawLine(0, panelTop.getSize().height, 0, 0);
            }
        };

        panelTop.setLayout(null);
        panelTop.setOpaque(false);
        //panelTop.setBackground(new Color(46, 140, 130, 150));
        panelTop.add(friendPicture);
        panelTop.add(labelFriendName);
        panelTop.add(labelState);
        panelTop.add(buttonDelete);
        panelTop.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                panelTopComponentResized(evt);
            }
        });

        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
        panelChat.setOpaque(false);

        
        textboxWriting = new JTextArea();
        textboxWriting.setBorder(null);
        textboxWriting.setOpaque(false);
        textboxWriting.setLineWrap(true);
        textboxWriting.setWrapStyleWord(true);
        textboxWriting.setFont(new java.awt.Font("Arial", 0, 12));
        textboxWriting.setBounds(0, 0, 912, 120);
        InputMap input = textboxWriting.getInputMap();
    KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
    KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
    input.put(shiftEnter, "insert-break");  // input.get(enter)) = "insert-break"
    input.put(enter, TEXT_SUBMIT);

    ActionMap actions = textboxWriting.getActionMap();
    actions.put(TEXT_SUBMIT, new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            submitText();
        }
    });
        // make textboxWriting scrollable
        textboxScroll = new JScrollPane(textboxWriting);
        textboxScroll.setOpaque(false);
        textboxScroll.setBorder(null);
        textboxScroll.setBounds(0, 0, 912, 120);
        textboxScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textboxScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textboxScroll.setWheelScrollingEnabled(true);
        textboxScroll.getViewport().setOpaque(false);

        buttonSend = new JButton();
        buttonSend.setOpaque(false);
        buttonSend.setBackground(new Color(230, 244, 241));
        buttonSend.setBounds(872, 126, 40, 20);
        try{
            //Resize the image to fit the button
            Image newImg = new ImageIcon(sendIcon.getScaledInstance(buttonSend.getSize().width - 8, buttonSend.getSize().height - 4, Image.SCALE_SMOOTH)).getImage();
            buttonSend.setIcon(new ImageIcon(newImg));
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        sendImageButton = new JButton();
        sendFileButton = new JButton();

        panelBottom.setLayout(null);
        panelBottom.setOpaque(false);
        panelBottom.add(textboxScroll);
        panelBottom.add(buttonSend);
        panelBottom.add(sendImageButton);
        panelBottom.add(sendFileButton);
        panelBottom.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                panelBottomComponentResized(evt);
            }
        });

        this.setLayout(null);
        this.setOpaque(false);
        this.setBackground(Color.WHITE);

        this.setSize(new Dimension(912, 681));
        this.add(panelTop);
        this.add(panelChat);
        this.add(panelBottom);
        // resize event
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                panelChat_Resize(evt);
            }
        });
    }

    protected void submitText() {
        String text = textboxWriting.getText();
        if(text != null && !text.isBlank()){
            textboxWriting.setText("");
            textboxWriting.requestFocus();
            // send text
            //sendText(text);
        }
    }

    protected void panelBottomComponentResized(ComponentEvent evt) {
        textboxScroll.setBounds(0, 0, panelBottom.getSize().width, panelBottom.getSize().height - 22);
        buttonSend.setBounds(panelBottom.getSize().width - 40, panelBottom.getSize().height + 2 - 20, 40, 20);
    }

    protected void panelTopComponentResized(ComponentEvent evt) {
        buttonDelete.setLocation(panelTop.getSize().width - 52, 10);
        labelFriendName.setSize(buttonDelete.getLocation().x - labelFriendName.getLocation().x - 50, 18);
    }

    private void panelChat_Resize(java.awt.event.ComponentEvent evt) {
        int width = this.getParent().getSize().width;
        int height = this.getParent().getSize().height;

        this.setSize(width, height);
        currentBackgroundImage = new ImageIcon(avatar.getScaledInstance(width, height, Image.SCALE_SMOOTH)).getImage();
        currentBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.panelTop.setBounds(0, 0, width, 60);
        this.panelChat.setBounds(0, 60, width, height - 60 - 144);
        this.panelBottom.setBounds(0, height - 144, width, 144);
    }

    Image currentBackgroundImage = null;
    BufferedImage currentBufferedImage = null;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentBackgroundImage == null || currentBufferedImage == null) {
            return;
        }
        Graphics2D g2d = currentBufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getSize().width, this.getSize().height);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.19f));
        g2d.drawImage(currentBackgroundImage, 0, 0, this.getSize().width, this.getSize().height, this);
        g2d.dispose();
        // set the background to the buffered image
        g.drawImage(currentBufferedImage, 0, 0, this);
    }

    @Override
    public java.awt.Dimension getMinimumSize(){
        return getPreferredSize();
    }

    @Override
    public java.awt.Dimension getMaximumSize(){
        return getPreferredSize();
    }

    @Override
    public java.awt.Dimension getPreferredSize(){
        return new java.awt.Dimension(this.getParent().getSize().width, this.getParent().getSize().height);
    }

    private void mustInit(){
        initializeComponent();
        this.setDoubleBuffered(true);
    }
    public PanelChat(){
        mustInit();
    }

    public PanelChat(Account acc) {
    }

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
        if (panelChat.getComponentCount() > 0){
        
        }
    }
    
    public void LoadMessage(){
        AFriendClient.queueCommand(("6475" + this.id + "0120").getBytes(StandardCharsets.UTF_16LE));
    }

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 912, 681);;
                frame.add(new PanelChat());
                frame.setVisible(true);
            }
        });
    }
}
