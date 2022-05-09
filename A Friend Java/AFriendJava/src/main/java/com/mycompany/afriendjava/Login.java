package com.mycompany.afriendjava;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        addPlaceholderStyle(textFieldUsername);
        addPlaceholderStyle(passwordFieldPassword);
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

        labelTitle = new javax.swing.JLabel();
        textFieldUsername = new javax.swing.JTextField();
        passwordFieldPassword = new javax.swing.JPasswordField();
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

        passwordFieldPassword.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        passwordFieldPassword.setText("Password");
        passwordFieldPassword.setEchoChar('\u0000');
        passwordFieldPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                passwordFieldPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                passwordFieldPasswordFocusLost(evt);
            }
        });

        buttonLogIn.setBackground(new java.awt.Color(37, 75, 133));
        buttonLogIn.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        buttonLogIn.setForeground(new java.awt.Color(255, 255, 255));
        buttonLogIn.setText("LOG IN");

        buttonSignUp.setBackground(new java.awt.Color(90, 198, 140));
        buttonSignUp.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        buttonSignUp.setForeground(new java.awt.Color(255, 255, 255));
        buttonSignUp.setText("SIGN UP");

        buttonExit.setBackground(new java.awt.Color(213, 54, 41));
        buttonExit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        buttonExit.setForeground(new java.awt.Color(255, 255, 255));
        buttonExit.setText("EXIT");

        labelForgotPassword.setForeground(new java.awt.Color(37, 75, 133));
        labelForgotPassword.setText("Forgot Password?");

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
                    .addComponent(passwordFieldPassword)
                    .addComponent(labelForgotPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(labelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(passwordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelForgotPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelWarning, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSignUp, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUsernameFocusGained
        if(textFieldUsername.getText().equals("Username")) {
            textFieldUsername.setText(null);
            textFieldUsername.requestFocus();
            removePlaceholderStyle(textFieldUsername);
        }
    }//GEN-LAST:event_textFieldUsernameFocusGained

    private void passwordFieldPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldPasswordFocusGained
        if(passwordFieldPassword.getText().equals("Password")) {
            passwordFieldPassword.setText(null);
            passwordFieldPassword.requestFocus();
            passwordFieldPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(passwordFieldPassword);
        }
    }//GEN-LAST:event_passwordFieldPasswordFocusGained

    private void textFieldUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldUsernameFocusLost
        if(textFieldUsername.getText().length() == 0) {
            addPlaceholderStyle(textFieldUsername);
            textFieldUsername.setText("Username");
        }
    }//GEN-LAST:event_textFieldUsernameFocusLost

    private void passwordFieldPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_passwordFieldPasswordFocusLost
        if(passwordFieldPassword.getText().length() == 0) {
            addPlaceholderStyle(passwordFieldPassword);
            passwordFieldPassword.setText("Password");
            passwordFieldPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_passwordFieldPasswordFocusLost


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
    private javax.swing.JPasswordField passwordFieldPassword;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables
}
