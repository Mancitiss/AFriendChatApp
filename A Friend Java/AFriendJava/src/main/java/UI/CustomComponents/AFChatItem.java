/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.customcomponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ComponentEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.lang.Math;
import java.awt.Graphics;
import java.awt.FlowLayout;

/**
 *
 * @author maima
 */
public class AFChatItem extends javax.swing.JPanel {

    public static void main(String[] args){
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
        AFChatItem chatItem2 = new AFChatItem("Kết quả sau 2 ngày nghiên cứu (thật ra làm có 15p à nhưng làm sai mất 2 ngày)", true);
        panel.add(ChatLayout.createChatItemBox(chatItem2));

        frame.setVisible(true);
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

        scroll = new JScrollPane(textBody);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.setBackground(new Color(0, 0, 0, 0));
        scroll.getViewport().setOpaque(false);
        scroll.getViewport().setBackground(new Color(0, 0, 0, 0));
        panelBody = new MessagePanel();
        panelBody.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
        panelBody.setLayout(new java.awt.FlowLayout());
        panelBody.add(scroll);
        //panelBody.setAlignmentX(0f);

        if (isMine) {
            this.setLayout(new FlowLayout(FlowLayout.TRAILING));
        }
        else {
            this.setLayout(new java.awt.FlowLayout(FlowLayout.LEADING));
        }
        this.add(panelBody);
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
        int height = textBody.getPreferredSize().height; Graphics g = textBody.getGraphics();
        int textWidth = g.getFontMetrics(textBody.getFont()).stringWidth(textBody.getText());
        //textBody.setSize(new java.awt.Dimension(this.getPreferredSize().width -20, textBody.getPreferredSize().height)); the original code
        int newWidth = (textWidth + 20 < width)? textWidth : width - 20;
        textBody.setSize(new java.awt.Dimension(newWidth + 10, height));
        panelBody.setSize(new java.awt.Dimension(newWidth+20, height));
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
        return new java.awt.Dimension(this.getParent().getWidth()*3/5, panelBody.getPreferredSize().height + panelBody.getInsets().top + panelBody.getInsets().bottom);
    }

    private String text;
    public boolean isMine;
    public JTextArea textBody;
    private JScrollPane scroll;
    private MessagePanel panelBody;
    
    public void startTimer(String file, long size) {
        //TODO start timer
    }
}
