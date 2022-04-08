/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import java.sql.*;
import static javax.swing.JOptionPane.showMessageDialog;


/**
 *
 * @author Χρήστος
 */
public class MainMenu extends javax.swing.JFrame {

   static Connection con;
     
  
     
    
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
    }
    
  
    public static Connection getCon() {
        return con;
    }
    
    
    /**
     * Connect Action JDialog 
     */
    public void connectAction() {
        JDialogConnect connect = new JDialogConnect(this, true);
        connect.setVisible(true);
        
        
       
        if (!connect.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("Username: "+ connect.getUsername()); 
            System.out.println("Password: "+ connect.getPassword());      
            System.out.println("Connection String: "+ connect.getConnectionString());
           
            try{
                Class.forName("oracle.jdbc.driver.OracleDriver");
                con = DriverManager.getConnection(connect.getConnectionString(), connect.getUsername(), connect.getPassword());
                showMessageDialog(this, "Connected Succesfully!"); 
                jMenuItemConnect.setEnabled(false);
                setActionStatus();
            }

            catch (ClassNotFoundException ex) {
               showMessageDialog(this, ex.getMessage());
            }
            catch (SQLException ex) {
               showMessageDialog(this, ex.getMessage()); 
            }
            
        }  
       
         /*   
        
            JDialogLogin login = new JDialogLogin(this, true);
            login.setVisible(true);
        */
            
         
    } 
    
    /**
     * Disconnect Action. 
     */
    public void disconnectAction(){
       try {
            con.close();
            showMessageDialog(this, "Disconnected!");
            jMenuItemConnect.setEnabled(true);
            setActionStatus();
       }
       catch (SQLException ex) {
               showMessageDialog(this, ex.getMessage()); 
       }
    }    
    
     public void setActionStatus(){
        if ( !jMenuItemConnect.isEnabled() ) {
            jMenuItemDisconnect.setEnabled(true);
            jMenuLogin.setEnabled(true);
            
            jButtonEmployee.setEnabled(true);
            jButtonClient.setEnabled(true);
            jButtonBooking.setEnabled(true);
            jButtonRoom.setEnabled(true);
            jButtonPayment.setEnabled(true);
            jButtonStorage.setEnabled(true);
            
            
        }
        else {
            
            jMenuItemDisconnect.setEnabled(false);
            jMenuLogin.setEnabled(false);
            
            jButtonEmployee.setEnabled(false);
            jButtonClient.setEnabled(false);
            jButtonBooking.setEnabled(false);
            jButtonRoom.setEnabled(false);
            jButtonPayment.setEnabled(false);
            jButtonStorage.setEnabled(false);
          
        }
    }
    
     /**
     * EMPLOYEE JDialog 
     */
    public void connectEmployeeAction() {
        JDialogEmployee conEmployee = new JDialogEmployee(this, true);
        conEmployee.setVisible(true);
        
        if (!conEmployee.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("Firstname: "+ conEmployee.getFirstname()); 
            System.out.println("Lastname: "+ conEmployee.getLastname());
            System.out.println("AFM: "+ conEmployee.getAFM());
            System.out.println("Hire Date: "+ conEmployee.getHireDate());
            System.out.println("Type: "+ conEmployee.getEType());
            System.out.println("Manages StorageID: "+ conEmployee.getManagesStorageID());
            
                              
        }
    }
    
    /**
     * Client JDialog 
     */
    public void connectClientAction() {
        JDialogClient conClient = new JDialogClient(this, true);
        conClient.setVisible(true);
        
        
        if (!conClient.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("AFM: "+ conClient.getAFM()); 
            System.out.println("Firstname: "+ conClient.getFirstname()); 
            System.out.println("Lastname: "+ conClient.getLastname()); 
            System.out.println("Phone: "+ conClient.getPhone()); 
            System.out.println("Country: "+ conClient.getCountry());
            System.out.println("City: "+ conClient.getCity());
            System.out.println("Street: "+ conClient.getStreet());
            System.out.println("Number: "+ conClient.getNumber());
            System.out.println("TK: "+ conClient.getTK());
                              
        }
    }
    
    /**
     * Booking JDialog 
     */
    public void connectBookingAction() {
        JDialogBooking conBooking = new JDialogBooking(this, true);
        conBooking.setVisible(true);
        
        
        if (!conBooking.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("ID: "+ conBooking.getID()); 
            System.out.println("Check IN: "+ conBooking.getCheckIN()); 
            System.out.println("Check Out: "+ conBooking.getCheckOUT()); 
            System.out.println("Persons: "+ conBooking.getPersons()); 
            System.out.println("Number Of Days: "+ conBooking.getNumOfDays());
            System.out.println("RoomID: "+ conBooking.getRoomID());
            System.out.println("Client's AFM: "+ conBooking.getClientsAFM());
        
                              
        }
    }
    
    /**
     * Room JDialog 
     */
    public void connectRoomAction() {
        JDialogRoom conRoom = new JDialogRoom(this, true);
        conRoom.setVisible(true);
        
        
        if (!conRoom.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("ID: "+ conRoom.getID()); 
            System.out.println("Type: "+ conRoom.getTType()); 
            System.out.println("Beds: "+ conRoom.getBeds()); 
            
                      
        }
    }
     
    public void exitAction(){
       this.dispose();
    }
    

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonEmployee = new javax.swing.JButton();
        jButtonStorage = new javax.swing.JButton();
        jButtonClient = new javax.swing.JButton();
        jButtonBooking = new javax.swing.JButton();
        jButtonRoom = new javax.swing.JButton();
        jButtonPayment = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuConnectDB = new javax.swing.JMenu();
        jMenuItemConnect = new javax.swing.JMenuItem();
        jMenuItemDisconnect = new javax.swing.JMenuItem();
        jMenuLogin = new javax.swing.JMenu();
        jMenuExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hotel Reservation App");
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));
        setPreferredSize(new java.awt.Dimension(1050, 610));
        setResizable(false);

        jButtonEmployee.setText("EMPLOYEE");
        jButtonEmployee.setEnabled(false);
        jButtonEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmployeeActionPerformed(evt);
            }
        });

        jButtonStorage.setText("STORAGE");
        jButtonStorage.setEnabled(false);

        jButtonClient.setText("CLIENT");
        jButtonClient.setEnabled(false);
        jButtonClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClientActionPerformed(evt);
            }
        });

        jButtonBooking.setText("BOOKING");
        jButtonBooking.setEnabled(false);
        jButtonBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBookingActionPerformed(evt);
            }
        });

        jButtonRoom.setText("ROOM");
        jButtonRoom.setEnabled(false);
        jButtonRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRoomActionPerformed(evt);
            }
        });

        jButtonPayment.setText("PAYMENT");
        jButtonPayment.setEnabled(false);

        jButtonExit.setText("EXIT");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        jMenuConnectDB.setText("ConnectDB");

        jMenuItemConnect.setText("Connect");
        jMenuItemConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConnectActionPerformed(evt);
            }
        });
        jMenuConnectDB.add(jMenuItemConnect);

        jMenuItemDisconnect.setText("Disconnect");
        jMenuItemDisconnect.setEnabled(false);
        jMenuItemDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDisconnectActionPerformed(evt);
            }
        });
        jMenuConnectDB.add(jMenuItemDisconnect);

        jMenuBar.add(jMenuConnectDB);

        jMenuLogin.setForeground(new java.awt.Color(0, 204, 0));
        jMenuLogin.setText("Login");
        jMenuLogin.setEnabled(false);
        jMenuLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuLoginActionPerformed(evt);
            }
        });
        jMenuBar.add(jMenuLogin);

        jMenuExit.setForeground(new java.awt.Color(255, 51, 51));
        jMenuExit.setText("Exit");
        jMenuExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuExitMouseClicked(evt);
            }
        });
        jMenuBar.add(jMenuExit);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClient, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonStorage, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(655, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExit)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jButtonEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonClient, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButtonBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButtonRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButtonStorage, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(jButtonExit)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConnectActionPerformed
       connectAction();
       
    }//GEN-LAST:event_jMenuItemConnectActionPerformed

    private void jMenuItemDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDisconnectActionPerformed
       disconnectAction();
    }//GEN-LAST:event_jMenuItemDisconnectActionPerformed

    private void jMenuExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuExitMouseClicked
        exitAction();
    }//GEN-LAST:event_jMenuExitMouseClicked

    private void jMenuLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuLoginActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        // TODO add your handling code here:
        exitAction();
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmployeeActionPerformed
        connectEmployeeAction();
    }//GEN-LAST:event_jButtonEmployeeActionPerformed

    private void jButtonClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClientActionPerformed
        connectClientAction();
    }//GEN-LAST:event_jButtonClientActionPerformed

    private void jButtonBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBookingActionPerformed
        connectBookingAction();
    }//GEN-LAST:event_jButtonBookingActionPerformed

    private void jButtonRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRoomActionPerformed
        connectRoomAction();
    }//GEN-LAST:event_jButtonRoomActionPerformed

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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBooking;
    private javax.swing.JButton jButtonClient;
    private javax.swing.JButton jButtonEmployee;
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonPayment;
    private javax.swing.JButton jButtonRoom;
    private javax.swing.JButton jButtonStorage;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuConnectDB;
    private javax.swing.JMenu jMenuExit;
    private javax.swing.JMenuItem jMenuItemConnect;
    private javax.swing.JMenuItem jMenuItemDisconnect;
    private javax.swing.JMenu jMenuLogin;
    // End of variables declaration//GEN-END:variables
}
