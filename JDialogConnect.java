/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

/**
 *
 * @author Χρήστος
 */
public class JDialogConnect extends javax.swing.JDialog {
    
    private boolean userChoosedOkFlag;

    /**
     * Creates new form JDialogConnect
     */
    public JDialogConnect(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        userChoosedOkFlag=false;
    }
    
     /**
     * Connect Button
     */
    public void okAction() {
        userChoosedOkFlag=true;
        this.dispose();
    
    }
    
    /**
     * 
     * Enabled or Disabled OK from textFields values
     */
    public void okEnableCheckAction() {
        if (
                (jTextFieldUsername.getText().length() >0) &&
                (jTextFieldConnectionString.getText().length() >0)&&
                (jPasswordFieldPassword.getText().length() >0) 
                
            ) 
        {
            jButtonConnect.setEnabled(true);
            String passField = new String(jPasswordFieldPassword.getPassword());
            int length = passField.length();
        }
        else {
            jButtonConnect.setEnabled(false);
        }
    }
    
    /*Geters*/
    public String getUsername() {
        return this.jTextFieldUsername.getText();
    }
    
     public String getPassword() {
        return this.jPasswordFieldPassword.getText();
    }
    
    public String getConnectionString() {
        return this.jTextFieldConnectionString.getText();
    }
    
  
    public boolean getUserChoosedOkFlag() {
        return this.userChoosedOkFlag;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelConnect = new javax.swing.JLabel();
        jLabelUsername = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabelPassword = new javax.swing.JLabel();
        jPasswordFieldPassword = new javax.swing.JPasswordField();
        jLabelConnectionString = new javax.swing.JLabel();
        jTextFieldConnectionString = new javax.swing.JTextField();
        jButtonConnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));

        jLabelConnect.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelConnect.setText("Connect");

        jLabelUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelUsername.setText("Username");

        jTextFieldUsername.setText("c##MAnd");
        jTextFieldUsername.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldUsernameCaretUpdate(evt);
            }
        });

        jLabelPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPassword.setText("Password");

        jPasswordFieldPassword.setText("chris123");
        jPasswordFieldPassword.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jPasswordFieldPasswordCaretUpdate(evt);
            }
        });
        jPasswordFieldPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordFieldPasswordActionPerformed(evt);
            }
        });

        jLabelConnectionString.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelConnectionString.setText("Connection String");

        jTextFieldConnectionString.setEditable(false);
        jTextFieldConnectionString.setText("jdbc:oracle:thin:@127.0.0.1:1521:xe");
        jTextFieldConnectionString.setEnabled(false);
        jTextFieldConnectionString.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldConnectionStringCaretUpdate(evt);
            }
        });

        jButtonConnect.setText("CONNECT");
        jButtonConnect.setEnabled(false);
        jButtonConnect.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jButtonConnectComponentShown(evt);
            }
        });
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(171, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelUsername))
                    .addComponent(jLabelConnectionString))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jTextFieldConnectionString, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(76, 76, 76))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabelConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(210, 210, 210))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(224, 224, 224))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabelConnect)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConnectionString)
                    .addComponent(jTextFieldConnectionString, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldUsernameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldUsernameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldUsernameCaretUpdate

    private void jPasswordFieldPasswordCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jPasswordFieldPasswordCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jPasswordFieldPasswordCaretUpdate

    private void jPasswordFieldPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordFieldPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordFieldPasswordActionPerformed

    private void jTextFieldConnectionStringCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldConnectionStringCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldConnectionStringCaretUpdate

    private void jButtonConnectComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonConnectComponentShown
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonConnectComponentShown

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        okAction();
    }//GEN-LAST:event_jButtonConnectActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogConnect.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogConnect dialog = new JDialogConnect(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JLabel jLabelConnect;
    private javax.swing.JLabel jLabelConnectionString;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPasswordField jPasswordFieldPassword;
    private javax.swing.JTextField jTextFieldConnectionString;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables
}