package com.mycompany.afriendjava;

import java.awt.Color;
import javax.swing.JTextField;

public class SignUp extends javax.swing.JFrame {

    public SignUp() {
        initComponents();
        setLocationRelativeTo(null);
        addPlaceholderStyle(textFieldUsername);
        addPlaceholderStyle(pFieldPassword);
        addPlaceholderStyle(pFieldConfirmPassword);
    }
    
    public void addPlaceholderStyle(JTextField textField) {
        textField.setForeground(Color.GRAY);
    }

    public void removePlaceholderStyle(JTextField textField) {
        textField.setForeground(Color.BLACK);
    }

    private boolean IsEmptyTextField() {
        if (textFieldUsername.getText().length() == 0 || textFieldUsername.getText().equals("Username") || (pFieldConfirmPassword.getPassword().toString().length() == 0 || pFieldConfirmPassword.getPassword().toString().equals("Confirm Password")) || (pFieldPassword.getPassword().toString().length() == 0 || pFieldPassword.getPassword().toString().equals("Password"))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean MatchPassword() {
        if (pFieldPassword.getPassword().toString().equals(pFieldConfirmPassword.getPassword().toString())) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTitle = new javax.swing.JLabel();
        textFieldUsername = new javax.swing.JTextField();
        pFieldPassword = new javax.swing.JPasswordField();
        pFieldConfirmPassword = new javax.swing.JPasswordField();
        buttonSignUp = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        labelWarning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelTitle.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 36)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(121, 241, 214));
        labelTitle.setText("Sign Up");

        textFieldUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        textFieldUsername.setText("Username");
        textFieldUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                textFieldUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                textFieldUsernameFocusLost(evt);
            }
        });

        pFieldPassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        pFieldPassword.setText("Password");
        pFieldPassword.setEchoChar('\u0000');
        pFieldPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pFieldPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                pFieldPasswordFocusLost(evt);
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

        buttonSignUp.setBackground(new java.awt.Color(121, 241, 214));
        buttonSignUp.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        buttonSignUp.setForeground(new java.awt.Color(255, 255, 255));
        buttonSignUp.setText("SIGN UP");
        buttonSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSignUpMouseClicked(evt);
            }
        });

        buttonExit.setBackground(new java.awt.Color(213, 54, 41));
        buttonExit.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        buttonExit.setForeground(new java.awt.Color(255, 255, 255));
        buttonExit.setText("CANCEL");
        buttonExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonExitMouseClicked(evt);
            }
        });

        labelWarning.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelWarning.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(labelTitle)
                .addGap(80, 80, 80))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pFieldConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(pFieldPassword)
                    .addComponent(buttonExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(textFieldUsername))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pFieldConfirmPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(labelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUsernameFocusGained
        if (textFieldUsername.getText().equals("Username")) {
            textFieldUsername.setText(null);
            textFieldUsername.requestFocus();
            removePlaceholderStyle(textFieldUsername);
        }
    }//GEN-LAST:event_textFieldUsernameFocusGained

    private void textFieldUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUsernameFocusLost
        if (textFieldUsername.getText().length() == 0) {
            addPlaceholderStyle(textFieldUsername);
            textFieldUsername.setText("Username");
        }
    }//GEN-LAST:event_textFieldUsernameFocusLost

    private void pFieldPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldPasswordFocusGained
        if (pFieldPassword.getPassword().toString().equals("Password")) {
            pFieldPassword.setText(null);
            pFieldPassword.requestFocus();
            pFieldPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldPassword);
        }
    }//GEN-LAST:event_pFieldPasswordFocusGained

    private void pFieldPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldPasswordFocusLost
        if (pFieldPassword.getPassword().toString().length() == 0) {
            addPlaceholderStyle(pFieldPassword);
            pFieldPassword.setText("Password");
            pFieldPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldPasswordFocusLost

    private void pFieldConfirmPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldConfirmPasswordFocusGained
        if (pFieldConfirmPassword.getPassword().toString().equals("Confirm Password")) {
            pFieldConfirmPassword.setText(null);
            pFieldConfirmPassword.requestFocus();
            pFieldConfirmPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldConfirmPassword);
        }
    }//GEN-LAST:event_pFieldConfirmPasswordFocusGained

    private void pFieldConfirmPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldConfirmPasswordFocusLost
        if (pFieldConfirmPassword.getPassword().toString().length() == 0) {
            addPlaceholderStyle(pFieldConfirmPassword);
            pFieldConfirmPassword.setText("Confirm Password");
            pFieldConfirmPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldConfirmPasswordFocusLost

    private void buttonSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSignUpMouseClicked
        labelWarning.setHorizontalAlignment(JTextField.CENTER);
        if (IsEmptyTextField())
        labelWarning.setText("Please complete your sign up information.");
        else if (!MatchPassword())
        labelWarning.setText("Passwords do NOT match.");
        else {
            dispose();

        }
    }//GEN-LAST:event_buttonSignUpMouseClicked

    private void buttonExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonExitMouseClicked
        dispose();
    }//GEN-LAST:event_buttonExitMouseClicked

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
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSignUp;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel labelWarning;
    private javax.swing.JPasswordField pFieldConfirmPassword;
    private javax.swing.JPasswordField pFieldPassword;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
