/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.customcomponents;

import java.awt.Component;

/**
 *
 * @author maima
 */
public class AFChatItem extends javax.swing.JPanel {

    public void changeTextUpload(byte percent){
        //TODO change text upload
    }

    /**
     * Creates new form AFChatItem
     */
    public AFChatItem() {
        initComponents();
    }
    public void setMessage(String mess)
    {
        String temp= "<html><table><tr><td width='300'>"+mess+"</td></tr></table></html>";
        labelLeft.setText(temp);
    }
    public void hideLeft()
    {
        //PanelBodyLeft.setVisible(!PanelBodyLeft.isVisible());
        TopPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }
    public void hideRight()
    {
        PanelBodyRight.setVisible(!PanelBodyRight.isVisible());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TopPanel = new javax.swing.JPanel();
        PanelBodyLeft = new javax.swing.JPanel();
        labelLeft = new javax.swing.JLabel();
        PanelButton = new javax.swing.JPanel();
        ButtonCopy = new javax.swing.JButton();
        ButtonDelete = new javax.swing.JButton();
        PanelBodyRight = new javax.swing.JPanel();
        labelRight = new javax.swing.JLabel();
        BottomPanel = new javax.swing.JPanel();
        LabelAuthor = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(773, 160));
        setLayout(new java.awt.CardLayout());

        TopPanel.setPreferredSize(new java.awt.Dimension(749, 132));
        TopPanel.setLayout(new javax.swing.BoxLayout(TopPanel, javax.swing.BoxLayout.LINE_AXIS));

        PanelBodyLeft.setMinimumSize(new java.awt.Dimension(290, 100));
        PanelBodyLeft.setLayout(new java.awt.CardLayout());

        labelLeft.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        PanelBodyLeft.add(labelLeft, "card2");

        TopPanel.add(PanelBodyLeft);

        PanelButton.setMinimumSize(new java.awt.Dimension(290, 100));

        ButtonCopy.setBackground(new java.awt.Color(255, 255, 255));
        ButtonCopy.setForeground(new java.awt.Color(255, 255, 255));

        ButtonDelete.setBackground(new java.awt.Color(255, 255, 255));
        ButtonDelete.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelButtonLayout = new javax.swing.GroupLayout(PanelButton);
        PanelButton.setLayout(PanelButtonLayout);
        PanelButtonLayout.setHorizontalGroup(
            PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelButtonLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(ButtonCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        PanelButtonLayout.setVerticalGroup(
            PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelButtonLayout.createSequentialGroup()
                .addContainerGap(157, Short.MAX_VALUE)
                .addGroup(PanelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButtonCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        TopPanel.add(PanelButton);

        PanelBodyRight.setMinimumSize(new java.awt.Dimension(290, 100));

        javax.swing.GroupLayout PanelBodyRightLayout = new javax.swing.GroupLayout(PanelBodyRight);
        PanelBodyRight.setLayout(PanelBodyRightLayout);
        PanelBodyRightLayout.setHorizontalGroup(
            PanelBodyRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBodyRightLayout.createSequentialGroup()
                .addComponent(labelRight, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        PanelBodyRightLayout.setVerticalGroup(
            PanelBodyRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBodyRightLayout.createSequentialGroup()
                .addComponent(labelRight, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TopPanel.add(PanelBodyRight);

        add(TopPanel, "card2");

        LabelAuthor.setText("Time-Date");

        javax.swing.GroupLayout BottomPanelLayout = new javax.swing.GroupLayout(BottomPanel);
        BottomPanel.setLayout(BottomPanelLayout);
        BottomPanelLayout.setHorizontalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addComponent(LabelAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        BottomPanelLayout.setVerticalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BottomPanelLayout.createSequentialGroup()
                .addGap(0, 13, Short.MAX_VALUE)
                .addComponent(LabelAuthor))
        );

        add(BottomPanel, "card3");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BottomPanel;
    private javax.swing.JButton ButtonCopy;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JLabel LabelAuthor;
    private javax.swing.JPanel PanelBodyLeft;
    private javax.swing.JPanel PanelBodyRight;
    private javax.swing.JPanel PanelButton;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel labelLeft;
    private javax.swing.JLabel labelRight;
    // End of variables declaration//GEN-END:variables
    public void startTimer(String file, long size) {
        //TODO start timer
    }
}
