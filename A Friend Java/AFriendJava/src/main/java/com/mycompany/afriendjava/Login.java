package com.mycompany.afriendjava;

import java.awt.Color;
import javax.swing.JTextField;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        setLocationRelativeTo(null);
        addPlaceholderStyle(textFieldUsername);
        addPlaceholderStyle(pFieldPassword);
    }

    public void addPlaceholderStyle(JTextField textField) {
        textField.setForeground(Color.GRAY);
    }

    public void removePlaceholderStyle(JTextField textField) {
        textField.setForeground(Color.BLACK);
    }

    private boolean IsEmptyTextField() {
        if (textFieldUsername.getText().length() == 0 || textFieldUsername.getText().equals("Username") || (pFieldPassword.getText().length() == 0 || pFieldPassword.getText().equals("Password"))) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitle = new javax.swing.JLabel();
        textFieldUsername = new javax.swing.JTextField();
        pFieldPassword = new javax.swing.JPasswordField();
        buttonLogIn = new javax.swing.JButton();
        buttonSignUp = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        labelWarning = new javax.swing.JLabel();
        labelForgotPassword = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setResizable(false);
        setSize(new java.awt.Dimension(418, 662));

        labelTitle.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 36)); // NOI18N
        labelTitle.setForeground(new java.awt.Color(37, 75, 133));
        labelTitle.setText("Log In");

        textFieldUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        textFieldUsername.setText("Username");
        textFieldUsername.setToolTipText("");
        textFieldUsername.setName(""); // NOI18N
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

        buttonLogIn.setBackground(new java.awt.Color(37, 75, 133));
        buttonLogIn.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        buttonLogIn.setForeground(new java.awt.Color(255, 255, 255));
        buttonLogIn.setText("LOG IN");
        buttonLogIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonLogInMouseClicked(evt);
            }
        });

        buttonSignUp.setBackground(new java.awt.Color(90, 198, 140));
        buttonSignUp.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        buttonSignUp.setForeground(new java.awt.Color(255, 255, 255));
        buttonSignUp.setText("SIGN UP");
        buttonSignUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSignUpMouseClicked(evt);
            }
        });

        buttonExit.setBackground(new java.awt.Color(213, 54, 41));
        buttonExit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        buttonExit.setForeground(new java.awt.Color(255, 255, 255));
        buttonExit.setText("EXIT");
        buttonExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonExitMouseClicked(evt);
            }
        });

        labelWarning.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelWarning.setForeground(new java.awt.Color(255, 0, 0));
        labelWarning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelForgotPassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelForgotPassword.setForeground(new java.awt.Color(37, 75, 133));
        labelForgotPassword.setText("Forgot Password?");
        labelForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelForgotPasswordMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSignUp, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(buttonLogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textFieldUsername)
                    .addComponent(pFieldPassword)
                    .addComponent(labelForgotPassword, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelForgotPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(labelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
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

    private void pFieldPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldPasswordFocusGained
        if (pFieldPassword.getText().equals("Password")) {
            pFieldPassword.setText(null);
            pFieldPassword.requestFocus();
            pFieldPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldPassword);
        }
    }//GEN-LAST:event_pFieldPasswordFocusGained

    private void textFieldUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUsernameFocusLost
        if (textFieldUsername.getText().length() == 0) {
            addPlaceholderStyle(textFieldUsername);
            textFieldUsername.setText("Username");
        }
    }//GEN-LAST:event_textFieldUsernameFocusLost

    private void pFieldPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldPasswordFocusLost
        if (pFieldPassword.getText().length() == 0) {
            addPlaceholderStyle(pFieldPassword);
            pFieldPassword.setText("Password");
            pFieldPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldPasswordFocusLost

    private void buttonLogInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonLogInMouseClicked
        if (IsEmptyTextField()) {
            labelWarning.setText("Please complete your login information.");
        }
        /*else {
            
        }*/
    }//GEN-LAST:event_buttonLogInMouseClicked

    private void labelForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelForgotPasswordMouseClicked
        hide();
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setVisible(true);
    }//GEN-LAST:event_labelForgotPasswordMouseClicked

    private void buttonSignUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSignUpMouseClicked
        hide();
        SignUp signUp = new SignUp();
        signUp.setVisible(true);
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonLogIn;
    private javax.swing.JButton buttonSignUp;
    private javax.swing.JLabel labelForgotPassword;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JLabel labelWarning;
    private javax.swing.JPasswordField pFieldPassword;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
