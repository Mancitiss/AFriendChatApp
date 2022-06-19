/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.customcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Timestamp;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.mycompany.afriendjava.AFriendClient;
import com.mycompany.afriendjava.MessageObject;

import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Component;

/**
 *
 * @author maima
 */
public class AFChatItem extends javax.swing.JPanel {
    public static int DELETEICON_WIDTH = 40;
    public static int DELETEICON_HEIGHT = 40; 
    private static String TESTSTRING = "Hum nay e chỉ làm thêm đc cái nút này @@...";

    public Image deleteIcon = (new ImageIcon(getClass().getResource("deleteIcon.png"))).getImage();

    public MessageObject messageObject;
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(100, 100, 500, 500);
                //frame.setLayout(null);
                //frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                JScrollPane scroll = new JScrollPane(panel);
                scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                frame.add(scroll);
                //AFChatItem chatItem = new AFChatItem("1234567890");
                //panel.add(ChatLayout.createChatItemBox(chatItem));
                AFChatItem chatItem2 = new AFChatItem(TESTSTRING, true);
                panel.add(ChatLayout.createChatItemBox(chatItem2), 0);
                AFChatItem chatItem3 = new AFChatItem(TESTSTRING, false);
                panel.add(ChatLayout.createChatItemBox(chatItem3), 0);

                frame.setVisible(true);
            }
        });
    }

    public void changeTextUpload(byte percent){
        //TODO change text upload
    }


    public AFChatItem(MessageObject messageObject){
        this.messageObject = messageObject;
        this.isMine = messageObject.sender? messageObject.id2 == AFriendClient.user.id : messageObject.id1 == AFriendClient.user.id;
        this.text = messageObject.message;
    }
    public AFChatItem(String text){
        this(text, false);
    }
    public AFChatItem(String text, boolean isMine) {
        this.text = text;
        this.isMine = isMine;
        initComponents();
    }
    public void setMessage(String mess)
    {

    }

    String datetimeNow = new Timestamp(System.currentTimeMillis()).toString();

    private void initComponents() {
        

        authorBody = new JTextArea();
        authorBody.setLineWrap(true);
        authorBody.setWrapStyleWord(true);
        authorBody.setEditable(false);
        authorBody.setFont(new Font("Arial", Font.PLAIN, 12));
        authorBody.setMargin(new Insets(0, 0, 0, 0));
        authorBody.setOpaque(true);
        authorBody.setBackground(Color.PINK);

        topPanel = new JPanel();
        topPanel.setBackground(Color.blue);
        topPanel.setOpaque(true);
        topPanel.setBorder(null);
        if (isMine) {
            topPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
            authorBody.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }
        else {
            topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        }
        topPanel.add(authorBody);

        textBody = new javax.swing.JTextArea();
        textBody.setOpaque(false);
        textBody.setBackground(new Color(0, 0, 0, 0));
        textBody.setLineWrap(true);
        textBody.setWrapStyleWord(true);
        textBody.setFont(new Font("Arial", Font.PLAIN, 14));
        textBody.setMargin(new Insets(5, 5, 5, 5));
        textBody.setBorder(null);
        textBody.setText(text);

        scroll = new JScrollPane(textBody);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.setBackground(new Color(0, 0, 0, 0));
        scroll.getViewport().setOpaque(false);
        scroll.getViewport().setBackground(new Color(0, 0, 0, 0));
        scroll.removeMouseWheelListener(scroll.getMouseWheelListeners()[0]);

        panelBody = new MessagePanel();
        panelBody.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        panelBody.setLayout(new java.awt.FlowLayout());
        panelBody.add(scroll);
        //panelBody.setAlignmentX(0f);

        buttonDelete = new javax.swing.JButton();
        buttonDelete.setBorder(null);
        buttonDelete.setContentAreaFilled(false);
        buttonDelete.setBorderPainted(false);
        buttonDelete.setPreferredSize(new Dimension(DELETEICON_WIDTH, DELETEICON_HEIGHT));
        buttonDelete.setMaximumSize(new Dimension(DELETEICON_WIDTH, DELETEICON_HEIGHT));
        buttonDelete.setMinimumSize(new Dimension(DELETEICON_WIDTH, DELETEICON_HEIGHT));
        buttonDelete.setVisible(false);
        buttonDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chatItemMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chatItemMouseExited(evt);
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonDeleteMouseClicked(evt);
            }
        });


        bottomPanel = new javax.swing.JPanel();
        bottomPanel.setBorder(null);
        bottomPanel.setOpaque(false);
        bottomPanel.setBackground(Color.YELLOW);

        try{
            //Resize the image to fit the button
            Image newImg = new ImageIcon(deleteIcon.getScaledInstance(DELETEICON_WIDTH, DELETEICON_HEIGHT, Image.SCALE_SMOOTH)).getImage();
            buttonDelete.setIcon(new ImageIcon(newImg));
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        
        // make textBody readonly but display cursor when mouse hover
        // start copilot code
        textBody.setEditable(false);
        textBody.setFocusable(true);
        textBody.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textBody.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textBody.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
                buttonDelete.setVisible(true);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textBody.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                buttonDelete.setVisible(false);
            }
        });
        // end copilot code


        if (isMine) {
            //this.setLayout(new FlowLayout(FlowLayout.TRAILING));
            bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
            bottomPanel.add(buttonDelete);
            bottomPanel.add(panelBody);
        }
        else {
            //this.setLayout(new java.awt.FlowLayout(FlowLayout.LEADING));
            bottomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            bottomPanel.add(panelBody);
            bottomPanel.add(buttonDelete);
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(topPanel);
        this.add(bottomPanel);
        //this.add(panelBody);
        this.setBackground(Color.RED);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                chatItemComponentResized(evt);
            }
        });

        // mouse hover event
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chatItemMouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chatItemMouseExited(evt);
            }
        });
    }

    protected void buttonDeleteMouseClicked(MouseEvent evt) {
        // display dialog
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this message?", "Delete Message", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // delete message
            this.setVisible(false);
            this.removeAll();
            this.revalidate();
            this.repaint();
        }
    }

    public void updateDateTime(){
        
        authorBody.setText("Author" + "\n" + datetimeNow);
    }

    protected void chatItemMouseExited(MouseEvent evt) {
        // make buttonDelete invisible
        buttonDelete.setVisible(false);
        //System.out.println(textBody.getSize().width);
        //System.out.println(panelBody.getSize().width);

    }

    protected void chatItemMouseEntered(MouseEvent evt) {
        // make buttonDelete visible
        buttonDelete.setVisible(true);
        //System.out.println(textBody.getSize().width);
        //System.out.println(panelBody.getSize().width);
    }

    protected void chatItemComponentResized(ComponentEvent evt) {
        int width = this.getParent().getWidth()*3/5-20;
        int height = textBody.getPreferredSize().height; 
        Graphics g = textBody.getGraphics();
        int textWidth = g.getFontMetrics(textBody.getFont()).stringWidth(textBody.getText());
        //textBody.setSize(new java.awt.Dimension(this.getPreferredSize().width -20, textBody.getPreferredSize().height)); the original code
        int newWidth = (textWidth + 20 < width)? textWidth : width - 20;
        textBody.setSize(new java.awt.Dimension(newWidth + 10, height));
        panelBody.setSize(new java.awt.Dimension(newWidth + 30, height + 20));

        int authorHeight = authorBody.getPreferredSize().height;
        int authorWidth = g.getFontMetrics(authorBody.getFont()).stringWidth(authorBody.getText());
        int newAuthorWidth = (authorWidth < width)? authorWidth : width ;
        authorBody.setSize(new java.awt.Dimension(newAuthorWidth , authorHeight));
        topPanel.setSize(new java.awt.Dimension(newWidth + 40 + (buttonDelete.isVisible()? DELETEICON_WIDTH : 0), authorHeight + 10));
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
        return new java.awt.Dimension(this.getParent().getWidth()*3/5 + (buttonDelete.isVisible()? DELETEICON_WIDTH : 0), panelBody.getPreferredSize().height + panelBody.getInsets().top + panelBody.getInsets().bottom + topPanel.getSize().height);
    }

    private String text;
    public boolean isMine;
    public JTextArea textBody;
    private JScrollPane scroll;
    private MessagePanel panelBody;
    private JPanel bottomPanel;
    private JButton buttonDelete;
    private JPanel topPanel;
    private JTextArea authorBody;
    
    public void startTimer(String file, long size) {
        //TODO start timer
    }
}
