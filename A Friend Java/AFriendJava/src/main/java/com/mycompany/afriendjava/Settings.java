package com.mycompany.afriendjava;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class Settings extends javax.swing.JFrame {

    public Settings() {
        initComponents();
        addPlaceholderStyle(pFieldCurrentPassword);
        addPlaceholderStyle(pFieldNewPassword);
        addPlaceholderStyle(pFieldConfirmPassword);
        addPlaceholderStyle(textFieldNewUsername);
    }
    
    public void addPlaceholderStyle(JTextField textField) {
        textField.setForeground(Color.GRAY);
    }
    
    public void removePlaceholderStyle(JTextField textField) {
        textField.setForeground(Color.BLACK);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        labelUsername = new javax.swing.JLabel();
        buttonExit = new javax.swing.JButton();
        labelWarning = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        textFieldNewUsername = new javax.swing.JTextField();
        buttonSaveName = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        pFieldNewPassword = new javax.swing.JPasswordField();
        pFieldConfirmPassword = new javax.swing.JPasswordField();
        buttonSavePassword = new javax.swing.JButton();
        pFieldCurrentPassword = new javax.swing.JPasswordField();
        labelID = new javax.swing.JLabel();
        circleAvatar1 = new Custom.CircleAvatar();
        jButton1 = new javax.swing.JButton();
        labelChangeUsername = new javax.swing.JLabel();
        labelChangePassword = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        toggleButton1 = new Custom.ToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setResizable(false);
        setSize(new java.awt.Dimension(418, 662));

        labelUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 15)); // NOI18N
        labelUsername.setForeground(new java.awt.Color(0, 0, 0));
        labelUsername.setText("Username");

        buttonExit.setBackground(new java.awt.Color(213, 54, 41));
        buttonExit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        buttonExit.setForeground(new java.awt.Color(255, 255, 255));
        buttonExit.setText("Exit");

        labelWarning.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelWarning.setForeground(new java.awt.Color(255, 0, 0));
        labelWarning.setText("Please enter your password!");

        textFieldNewUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        textFieldNewUsername.setText("New Name");
        textFieldNewUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldNewUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textFieldNewUsernameFocusLost(evt);
            }
        });

        buttonSaveName.setBackground(new java.awt.Color(37, 75, 113));
        buttonSaveName.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        buttonSaveName.setForeground(new java.awt.Color(255, 255, 255));
        buttonSaveName.setText("Save name");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(textFieldNewUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonSaveName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textFieldNewUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSaveName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pFieldNewPassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        pFieldNewPassword.setText("New Password");
        pFieldNewPassword.setEchoChar('\u0000');
        pFieldNewPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pFieldNewPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pFieldNewPasswordFocusLost(evt);
            }
        });

        pFieldConfirmPassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        pFieldConfirmPassword.setText("Confirm Password");
        pFieldConfirmPassword.setEchoChar('\u0000');
        pFieldConfirmPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pFieldConfirmPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pFieldConfirmPasswordFocusLost(evt);
            }
        });

        buttonSavePassword.setBackground(new java.awt.Color(90, 198, 140));
        buttonSavePassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        buttonSavePassword.setForeground(new java.awt.Color(255, 255, 255));
        buttonSavePassword.setText("Save password");

        pFieldCurrentPassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        pFieldCurrentPassword.setText("Current Password");
        pFieldCurrentPassword.setEchoChar('\u0000');
        pFieldCurrentPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pFieldCurrentPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pFieldCurrentPasswordFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonSavePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pFieldNewPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pFieldConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pFieldCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pFieldCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pFieldNewPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pFieldConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSavePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        circleAvatar1.setBorderColor(new java.awt.Color(153, 153, 153));
        circleAvatar1.setBorderSize(2);
        circleAvatar1.setImage(new javax.swing.ImageIcon("C:\\Users\\Phuong Quyen\\project_nhom_10_LTTQ_UIT_2021-2022\\A Friend\\A Friend\\Resources\\newUser.png")); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Phuong Quyen\\Downloads\\camera-icon-35 (2).png")); // NOI18N
        circleAvatar1.add(jButton1);
        jButton1.setBounds(120, 130, 28, 24);

        labelChangeUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelChangeUsername.setForeground(new java.awt.Color(37, 75, 133));
        labelChangeUsername.setText("Change display name?");

        labelChangePassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelChangePassword.setForeground(new java.awt.Color(90, 198, 140));
        labelChangePassword.setText("Change password?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelUsername)
                .addGap(105, 105, 105))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelChangeUsername)
                            .addComponent(circleAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(labelChangePassword)
                        .addGap(80, 80, 80))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(labelID, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(labelWarning)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(circleAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelChangeUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelChangePassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelWarning)
                .addGap(18, 18, 18)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jTabbedPane2.addTab("Profile", jPanel1);

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel1.setText("Incognito Mode");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(toggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(toggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(651, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Mode", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pFieldCurrentPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldCurrentPasswordFocusGained
        if(pFieldCurrentPassword.getText().equals("Current Password")) {
            pFieldCurrentPassword.setText(null);
            pFieldCurrentPassword.requestFocus();
            pFieldCurrentPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldCurrentPassword);
        }
    }//GEN-LAST:event_pFieldCurrentPasswordFocusGained

    private void pFieldNewPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldNewPasswordFocusGained
        if(pFieldNewPassword.getText().equals("New Password")) {
            pFieldNewPassword.setText(null);
            pFieldNewPassword.requestFocus();
            pFieldNewPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldNewPassword);
        }
    }//GEN-LAST:event_pFieldNewPasswordFocusGained

    private void pFieldConfirmPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldConfirmPasswordFocusGained
        if(pFieldConfirmPassword.getText().equals("Confirm Password")) {
            pFieldConfirmPassword.setText(null);
            pFieldConfirmPassword.requestFocus();
            pFieldConfirmPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldConfirmPassword);
        }
    }//GEN-LAST:event_pFieldConfirmPasswordFocusGained

    private void textFieldNewUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldNewUsernameFocusGained
        if(textFieldNewUsername.getText().equals("New Name")) {
            textFieldNewUsername.setText(null);
            textFieldNewUsername.requestFocus();
            removePlaceholderStyle(textFieldNewUsername);
        }
    }//GEN-LAST:event_textFieldNewUsernameFocusGained

    private void pFieldCurrentPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldCurrentPasswordFocusLost
        if(pFieldCurrentPassword.getText().length() == 0) {
            addPlaceholderStyle(pFieldCurrentPassword);
            pFieldCurrentPassword.setText("Current Password");
            pFieldCurrentPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldCurrentPasswordFocusLost

    private void pFieldNewPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldNewPasswordFocusLost
        if(pFieldNewPassword.getText().length() == 0) {
            addPlaceholderStyle(pFieldNewPassword);
            pFieldNewPassword.setText("New Password");
            pFieldNewPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldNewPasswordFocusLost

    private void pFieldConfirmPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldConfirmPasswordFocusLost
        if(pFieldConfirmPassword.getText().length() == 0) {
            addPlaceholderStyle(pFieldConfirmPassword);
            pFieldConfirmPassword.setText("Confirm Password");
            pFieldConfirmPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldConfirmPasswordFocusLost

    private void textFieldNewUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldNewUsernameFocusLost
         if(textFieldNewUsername.getText().length() == 0) {
            addPlaceholderStyle(textFieldNewUsername);
            textFieldNewUsername.setText("New Username");
        }
    }//GEN-LAST:event_textFieldNewUsernameFocusLost

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
            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Settings.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Settings().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSaveName;
    private javax.swing.JButton buttonSavePassword;
    private Custom.CircleAvatar circleAvatar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelChangePassword;
    private javax.swing.JLabel labelChangeUsername;
    private javax.swing.JLabel labelID;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel labelWarning;
    private javax.swing.JPasswordField pFieldConfirmPassword;
    private javax.swing.JPasswordField pFieldCurrentPassword;
    private javax.swing.JPasswordField pFieldNewPassword;
    private javax.swing.JTextField textFieldNewUsername;
    private Custom.ToggleButton toggleButton1;
    // End of variables declaration//GEN-END:variables
}
