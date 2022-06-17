/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.customcomponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Graphics;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Image;

/**
 *
 * @author maima
 */
public class AFChatItem extends javax.swing.JPanel {
    public static int DELETEICON_WIDTH = 40;
    public static int DELETEICON_HEIGHT = 40; 

    private static String TESTSTRING = "Hum nay e chỉ làm thêm đc cái nút này @@...";
    public Image deleteIcon = (new ImageIcon(getClass().getResource("deleteIcon.png"))).getImage();
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

    /**
     * Creates new form AFChatItem
     */
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

    private void initComponents() {

        textBody = new javax.swing.JTextArea();
        textBody.setOpaque(false);
        textBody.setBackground(new Color(0, 0, 0, 0));
        textBody.setLineWrap(true);
        textBody.setWrapStyleWord(true);
        textBody.setFont(new Font("Arial", Font.PLAIN, 14));
        textBody.setMargin(new Insets(5, 5, 5, 5));
        textBody.setBorder(null);
        textBody.setText(text);
        // make textBody readonly but display cursor when mouse hover
        // start copilot code
        textBody.setEditable(false);
        textBody.setFocusable(true);
        textBody.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textBody.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                textBody.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                textBody.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });
        // end copilot code

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
        this.add(bottomPanel);
        //this.add(panelBody);
        this.setBackground(Color.RED);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                chatItemComponentResized(evt);
            }
        });
    }

    protected void chatItemComponentResized(ComponentEvent evt) {
        int width = this.getParent().getWidth()*3/5-20;
        int height = textBody.getPreferredSize().height; 
        Graphics g = textBody.getGraphics();
        int textWidth = g.getFontMetrics(textBody.getFont()).stringWidth(textBody.getText());
        //textBody.setSize(new java.awt.Dimension(this.getPreferredSize().width -20, textBody.getPreferredSize().height)); the original code
        int newWidth = (textWidth + 20 < width)? textWidth : width - 20;
        textBody.setSize(new java.awt.Dimension(newWidth + 10, height));
        panelBody.setSize(new java.awt.Dimension(newWidth + 20, height + 20));
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
        return new java.awt.Dimension(this.getParent().getWidth()*3/5 + DELETEICON_WIDTH, panelBody.getPreferredSize().height + panelBody.getInsets().top + panelBody.getInsets().bottom);
    }

    private String text;
    public boolean isMine;
    public JTextArea textBody;
    private JScrollPane scroll;
    private MessagePanel panelBody;
    private JPanel bottomPanel;
    private JButton buttonDelete;
    
    public void startTimer(String file, long size) {
        //TODO start timer
    }
}
