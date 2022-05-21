/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import Custom.TextField;

import javax.swing.SwingConstants;
import Custom.*;

public class MainUI extends javax.swing.JFrame {
public static String i;
    /**
     * Creates new form Main
     */
    public MainUI() {
    	setTitle("AFriend Chat");
    	setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\programer\\Eclipse\\MainUI\\Resoure\\logo.png"));
        try {
			initComponents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        getContentPane().setBackground(new Color(255, 255, 255));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * @throws IOException 
     */
    
    private void initComponents() throws IOException {

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        
        JPanel ContactList_Panel = new JPanel();
        ContactList_Panel.setMinimumSize(new Dimension(20000, 10));
        ContactList_Panel.setBackground(Color.WHITE);
        
        JPanel Chat_Panel = new JPanel();

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
        
        
        
        JPanel SearchBar_Panel = new JPanel();
        SearchBar_Panel.setBackground(new Color(0, 204, 204));
        
        JScrollPane ContactList_ScrollPanel = new JScrollPane();
        
        JPanel Button_Panel = new JPanel();
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
        
        JButton LogOut_Button = new JButton("");
        //LogOut_Button.setIcon(new ImageIcon("D:\\programer\\Eclipse\\MainUI\\Resources\\sign-out-option.png"));
        try {
        Image img = ImageIO.read(getClass().getResource("R/sign-out-option.png"));
        LogOut_Button.setIcon(new ImageIcon(img));
        
        } catch (Exception ex) {
            System.out.println(ex);
          }
        
        JButton Setting_Button = new JButton("");
        Setting_Button.setIcon(null);
        
        JButton AddFriend_Button = new JButton("");
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
        
        TextField SearchBar = new TextField();
        SearchBar.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusGained(FocusEvent e) {
        		SearchBar.removeItemSuggestion(SearchBar.getText());
        	}
        });
        SearchBar.setText("Search");
        SearchBar.setHorizontalAlignment(SwingConstants.LEFT);
        SearchBar.setBackground(Color.RED);
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

        i = SearchBar_Panel.getSize().toString();
        
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
        
        File imageCheck = new File("R/sign-out-option.png");
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