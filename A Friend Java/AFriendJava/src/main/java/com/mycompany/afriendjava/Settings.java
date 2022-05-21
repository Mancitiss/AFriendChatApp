package com.mycompany.afriendjava;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Settings extends javax.swing.JFrame {

    public Settings() {
        initComponents();
        setLocationRelativeTo(null);
        panelChangeName.setVisible(false);
        panelChangePassword.setVisible(false);
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

    private boolean IsEmptyTextField() {
        if (textFieldNewUsername.getText().length() == 0 || textFieldNewUsername.getText().equals("New Name")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean IsEmptyPasswordField() {
        if (pFieldNewPassword.getPassword().toString().length() == 0 || pFieldNewPassword.getPassword().toString().equals("New Password") || (pFieldConfirmPassword.getPassword().toString().length() == 0 || pFieldConfirmPassword.getPassword().toString().equals("Confirm Password")) || pFieldCurrentPassword.getPassword().toString().length() == 0 || pFieldCurrentPassword.getPassword().toString().equals("Current Password")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean MatchPassword() {
        if (pFieldNewPassword.getPassword().toString().equals(pFieldConfirmPassword.getPassword().toString())) {
            return true;
        } else {
            return false;
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        labelUsername = new javax.swing.JLabel();
        buttonExit = new javax.swing.JButton();
        labelWarning1 = new javax.swing.JLabel();
        panelChangeName = new javax.swing.JPanel();
        textFieldNewUsername = new javax.swing.JTextField();
        buttonSaveName = new javax.swing.JButton();
        labelID = new javax.swing.JLabel();
        circleAvatar1 = new custom.CircleAvatar();
        buttonAvatar = new javax.swing.JButton();
        labelChangeUsername = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        panelChangePassword = new javax.swing.JPanel();
        pFieldNewPassword = new javax.swing.JPasswordField();
        pFieldConfirmPassword = new javax.swing.JPasswordField();
        buttonSavePassword = new javax.swing.JButton();
        pFieldCurrentPassword = new javax.swing.JPasswordField();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        toggleButton1 = new custom.ToggleButton();
        labelChangePassword1 = new javax.swing.JLabel();
        labelWarning2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setResizable(false);
        setSize(new java.awt.Dimension(418, 662));

        labelUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 18)); // NOI18N
        labelUsername.setForeground(new java.awt.Color(0, 0, 0));
        labelUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelUsername.setText("Username");

        buttonExit.setBackground(new java.awt.Color(213, 54, 41));
        buttonExit.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        buttonExit.setForeground(new java.awt.Color(255, 255, 255));
        buttonExit.setText("Exit");
        buttonExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonExitMouseClicked(evt);
            }
        });

        labelWarning1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelWarning1.setForeground(new java.awt.Color(255, 0, 0));
        labelWarning1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
        buttonSaveName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSaveNameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelChangeNameLayout = new javax.swing.GroupLayout(panelChangeName);
        panelChangeName.setLayout(panelChangeNameLayout);
        panelChangeNameLayout.setHorizontalGroup(
            panelChangeNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChangeNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChangeNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChangeNameLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(textFieldNewUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonSaveName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelChangeNameLayout.setVerticalGroup(
            panelChangeNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChangeNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textFieldNewUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonSaveName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelID.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N

        circleAvatar1.setBorderColor(new java.awt.Color(153, 153, 153));
        circleAvatar1.setBorderSize(2);
        circleAvatar1.setImage(new javax.swing.ImageIcon("C:\\Users\\Phuong Quyen\\project_nhom_10_LTTQ_UIT_2021-2022\\A Friend\\A Friend\\Resources\\newUser.png")); // NOI18N

        buttonAvatar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Phuong Quyen\\Downloads\\camera-icon-35 (2).png")); // NOI18N
        buttonAvatar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonAvatarMouseClicked(evt);
            }
        });
        circleAvatar1.add(buttonAvatar);
        buttonAvatar.setBounds(120, 130, 28, 24);

        labelChangeUsername.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelChangeUsername.setForeground(new java.awt.Color(37, 75, 133));
        labelChangeUsername.setText("Change display name");
        labelChangeUsername.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelChangeUsernameMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(circleAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelChangeName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelID, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(labelChangeUsername)))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelWarning1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(circleAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelChangeUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelChangeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelWarning1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelID, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        jTabbedPane2.addTab("Profile", jPanel1);

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
        buttonSavePassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonSavePasswordMouseClicked(evt);
            }
        });

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

        javax.swing.GroupLayout panelChangePasswordLayout = new javax.swing.GroupLayout(panelChangePassword);
        panelChangePassword.setLayout(panelChangePasswordLayout);
        panelChangePasswordLayout.setHorizontalGroup(
            panelChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelChangePasswordLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonSavePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pFieldNewPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pFieldConfirmPassword, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelChangePasswordLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pFieldCurrentPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelChangePasswordLayout.setVerticalGroup(
            panelChangePasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelChangePasswordLayout.createSequentialGroup()
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

        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Incognito Mode");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(toggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(toggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelChangePassword1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelChangePassword1.setForeground(new java.awt.Color(90, 198, 140));
        labelChangePassword1.setText("Change password");
        labelChangePassword1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelChangePassword1MouseClicked(evt);
            }
        });

        labelWarning2.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        labelWarning2.setForeground(new java.awt.Color(255, 0, 0));
        labelWarning2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelChangePassword1))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelWarning2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelChangePassword1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelChangePassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelWarning2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(409, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldNewUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldNewUsernameFocusGained
        if (textFieldNewUsername.getText().equals("New Name")) {
            textFieldNewUsername.setText(null);
            textFieldNewUsername.requestFocus();
            removePlaceholderStyle(textFieldNewUsername);
        }
    }//GEN-LAST:event_textFieldNewUsernameFocusGained

    private void textFieldNewUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textFieldNewUsernameFocusLost
        if (textFieldNewUsername.getText().length() == 0) {
            addPlaceholderStyle(textFieldNewUsername);
            textFieldNewUsername.setText("New Username");
        }
    }//GEN-LAST:event_textFieldNewUsernameFocusLost

    private void labelChangeUsernameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelChangeUsernameMouseClicked
        panelChangeName.setVisible(true);
    }//GEN-LAST:event_labelChangeUsernameMouseClicked

    private void pFieldNewPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldNewPasswordFocusGained
        if (pFieldNewPassword.getPassword().toString().equals("New Password")) {
            pFieldNewPassword.setText(null);
            pFieldNewPassword.requestFocus();
            pFieldNewPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldNewPassword);
        }
    }//GEN-LAST:event_pFieldNewPasswordFocusGained

    private void pFieldNewPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldNewPasswordFocusLost
        if (pFieldNewPassword.getPassword().toString().length() == 0) {
            addPlaceholderStyle(pFieldNewPassword);
            pFieldNewPassword.setText("New Password");
            pFieldNewPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldNewPasswordFocusLost

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

    private void pFieldCurrentPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldCurrentPasswordFocusGained
        if (pFieldCurrentPassword.getPassword().toString().equals("Current Password")) {
            pFieldCurrentPassword.setText(null);
            pFieldCurrentPassword.requestFocus();
            pFieldCurrentPassword.setEchoChar('\u25CF');
            removePlaceholderStyle(pFieldCurrentPassword);
        }
    }//GEN-LAST:event_pFieldCurrentPasswordFocusGained

    private void pFieldCurrentPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pFieldCurrentPasswordFocusLost
        if (pFieldCurrentPassword.getPassword().toString().length() == 0) {
            addPlaceholderStyle(pFieldCurrentPassword);
            pFieldCurrentPassword.setText("Current Password");
            pFieldCurrentPassword.setEchoChar('\u0000');
        }
    }//GEN-LAST:event_pFieldCurrentPasswordFocusLost

    private void buttonAvatarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonAvatarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonAvatarMouseClicked

    private void buttonSaveNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSaveNameMouseClicked
        if (IsEmptyTextField())
            labelWarning1.setText("Please enter your new name.");
        else {
            panelChangeName.setVisible(false);
            labelWarning1.setText("");
            labelUsername.setText(textFieldNewUsername.getText());
        }
    }//GEN-LAST:event_buttonSaveNameMouseClicked

    private void buttonExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonExitMouseClicked
        dispose();
    }//GEN-LAST:event_buttonExitMouseClicked

    private void labelChangePassword1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelChangePassword1MouseClicked
        panelChangePassword.setVisible(true);
    }//GEN-LAST:event_labelChangePassword1MouseClicked

    private void buttonSavePasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSavePasswordMouseClicked
        if (IsEmptyPasswordField())
            labelWarning2.setText("Please complete your password.");
        else if (!MatchPassword())
            labelWarning2.setText("Passwords do NOT match.");
        else {
            panelChangePassword.setVisible(false);
            labelWarning2.setText("");
        }
    }//GEN-LAST:event_buttonSavePasswordMouseClicked

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
    private javax.swing.JButton buttonAvatar;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonSaveName;
    private javax.swing.JButton buttonSavePassword;
    private custom.CircleAvatar circleAvatar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelChangePassword1;
    private javax.swing.JLabel labelChangeUsername;
    private javax.swing.JLabel labelID;
    private javax.swing.JLabel labelUsername;
    private javax.swing.JLabel labelWarning1;
    private javax.swing.JLabel labelWarning2;
    private javax.swing.JPasswordField pFieldConfirmPassword;
    private javax.swing.JPasswordField pFieldCurrentPassword;
    private javax.swing.JPasswordField pFieldNewPassword;
    private javax.swing.JPanel panelChangeName;
    private javax.swing.JPanel panelChangePassword;
    private javax.swing.JTextField textFieldNewUsername;
    private custom.ToggleButton toggleButton1;
    // End of variables declaration//GEN-END:variables
}
