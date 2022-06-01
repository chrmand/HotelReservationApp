/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;


import com.sun.javafx.applet.Splash;
import java.awt.*;
import java.sql.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JProgressBar;


/**
 *
 * @author Χρήστος
 */
public class MainMenu extends javax.swing.JFrame {

   static Connection con;
   progressBar t1;
  
     
    
    
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        paintColorBackground();

     //Εμφανίζει το JFrame MainMenu στη μέση της οθονης   
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        
        jProgressBar.setVisible(false);
        jLabelProgressBarPercentage.setVisible(false);
       
    }
    
    
  
    public static Connection getCon() {
        return con;
    }
    
    public void paintColorBackground() {
        Color col = new Color(148,222,222);
        getContentPane().setBackground(col);
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
                JOptionPane.showMessageDialog(rootPane, "Connected Succesfully!","", JOptionPane.INFORMATION_MESSAGE);
                jMenuItemConnect.setEnabled(false);
                

              //Progress Bar loading action if connected is successfully!
                if(connect.getUserChoosedOkFlag() == true){
                    
                    jProgressBar.setVisible(true);
                    jLabelProgressBarPercentage.setVisible(true);
                    jButtonExit.setEnabled(false);
                    jMenuExit.setEnabled(false);
                    jMenuConnectDB.setEnabled(false);
                                 
                    t1 = new MainMenu.progressBar(jProgressBar);
                    t1.start();
     
                }

            }

            catch (ClassNotFoundException ex) {
               showMessageDialog(this, ex.getMessage());
            }
            catch (SQLException ex) {
               showMessageDialog(this, ex.getMessage()); 
            }
            
        }  

    }
    /**
     * Progress Bar Action. 
     */  
    
   class progressBar extends Thread {
        JProgressBar pBar;
         
        progressBar(JProgressBar pBar){
            
            pBar = jProgressBar;
        }
        
        public void run(){

                try{
                    for(int i = 0; i <= 101; i++){
                        Thread.sleep(101);
                        jProgressBar.setValue(i);
                        jLabelProgressBarPercentage.setText(String.valueOf(i)+ "%");
                        
                        
                      if(i==101){
                        jProgressBar.setVisible(false);
                        jLabelProgressBarPercentage.setVisible(false);
                        
                        jButtonExit.setEnabled(true);
                        jMenuExit.setEnabled(true);
                        jMenuConnectDB.setEnabled(true);
                        
                        setActionStatus();
                      }

                    }  
                    
                }catch(Exception e){
                  
                }  
               
        }
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
            
            jButtonEmployee.setEnabled(true);
            jButtonClient.setEnabled(true);
            jButtonBooking.setEnabled(true);
            jButtonRoom.setEnabled(true);
            jButtonPayment.setEnabled(true);
            jButtonStorage.setEnabled(true);
            
            
        }
        else {
            
            jMenuItemDisconnect.setEnabled(false);
            
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
            System.out.println("Phone: "+ conBooking.getPhone());
            System.out.println("Firstname: "+ conBooking.getFirstname()); 
            System.out.println("Lastname: "+ conBooking.getLastname());
            System.out.println("Check IN: "+ conBooking.getCheckIN());
            System.out.println("Persons: "+ conBooking.getPersons());
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
            System.out.println("Price: "+ conRoom.getPrice()); 
            
                      
        }
    }
    
    /**
     * Payment JDialog 
     */
    public void connectPaymentAction() {
        JDialogPayment conPayment = new JDialogPayment(this, true);
        conPayment.setVisible(true);
        
        if (!conPayment.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("Booking ID: "+ conPayment.getBookingID()); 
            System.out.println("AFM: "+ conPayment.getAFM());
            System.out.println("Phone: "+ conPayment.getPhone());
            System.out.println("Firstname: "+ conPayment.getFirstname()); 
            System.out.println("Lastname: "+ conPayment.getLastname());
            System.out.println("Check IN: "+ conPayment.getCheckIN());
            System.out.println("Check OUT: "+ conPayment.getCheckOUT());
            System.out.println("Price Per Day: "+ conPayment.getPrice());
            System.out.println("Total Amount: "+ conPayment.getTotalAmount());
            System.out.println("Num Of Days: "+ conPayment.getNumOfDays());
            System.out.println("Room ID: "+ conPayment.getRoomID());
                              
        }
    }
    
     /**
     * Storage JDialog 
     */
    public void connectStorageAction() {
        JDialogStorage conStorage = new JDialogStorage(this, true);
        conStorage.setVisible(true);
        
        if (!conStorage.getUserChoosedOkFlag()) {
            System.out.println("User Pressed Cancel!");
            return;
        }
        
        else {
            System.out.println("User Pressed OK!");
            
            System.out.println("Storage Name: "+ conStorage.getStorageName()); 
            System.out.println("ID: "+ conStorage.getStorageID()); 
            System.out.println("Category: "+ conStorage.getCategory());
            System.out.println("Product Name: "+ conStorage.getProductName());
            System.out.println("Quantity: "+ conStorage.getQuantity()); 
                              
        }
    }
     
    public void exitAction(){
        int a = JOptionPane.showConfirmDialog(null, "Do you want to EXIT?", "Select", JOptionPane.YES_NO_OPTION);
        if(a == 0) 
        {
          this.dispose();
        }
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
        jLabelWelcomeLogo = new javax.swing.JLabel();
        jProgressBar = new javax.swing.JProgressBar();
        jLabelProgressBarPercentage = new javax.swing.JLabel();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuConnectDB = new javax.swing.JMenu();
        jMenuItemConnect = new javax.swing.JMenuItem();
        jMenuItemDisconnect = new javax.swing.JMenuItem();
        jMenuExit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hotel Reservation App");
        setBackground(new java.awt.Color(46, 224, 242));
        setBounds(new java.awt.Rectangle(200, 200, 200, 200));
        setResizable(false);

        jButtonEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-employee-2.png"))); // NOI18N
        jButtonEmployee.setText("EMPLOYEE");
        jButtonEmployee.setEnabled(false);
        jButtonEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmployeeActionPerformed(evt);
            }
        });

        jButtonStorage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-storage.png"))); // NOI18N
        jButtonStorage.setText("STORAGE");
        jButtonStorage.setEnabled(false);
        jButtonStorage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStorageActionPerformed(evt);
            }
        });

        jButtonClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-client.png"))); // NOI18N
        jButtonClient.setText("CLIENT");
        jButtonClient.setEnabled(false);
        jButtonClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClientActionPerformed(evt);
            }
        });

        jButtonBooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-booking.png"))); // NOI18N
        jButtonBooking.setText("BOOKING");
        jButtonBooking.setEnabled(false);
        jButtonBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBookingActionPerformed(evt);
            }
        });

        jButtonRoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-room.png"))); // NOI18N
        jButtonRoom.setText("ROOM");
        jButtonRoom.setEnabled(false);
        jButtonRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRoomActionPerformed(evt);
            }
        });

        jButtonPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-payment-check-out-.png"))); // NOI18N
        jButtonPayment.setText("PAYMENT");
        jButtonPayment.setEnabled(false);
        jButtonPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPaymentActionPerformed(evt);
            }
        });

        jButtonExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exitIcon.png"))); // NOI18N
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        jLabelWelcomeLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/background/welcome-background-transparent.png"))); // NOI18N
        jLabelWelcomeLogo.setPreferredSize(new java.awt.Dimension(1153, 1040));

        jProgressBar.setForeground(new java.awt.Color(73, 70, 70));

        jLabelProgressBarPercentage.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelProgressBarPercentage.setText("0%");

        jMenuConnectDB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/databIcon.png"))); // NOI18N
        jMenuConnectDB.setText("Database");

        jMenuItemConnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/conIcon.png"))); // NOI18N
        jMenuItemConnect.setText("Connect");
        jMenuItemConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConnectActionPerformed(evt);
            }
        });
        jMenuConnectDB.add(jMenuItemConnect);

        jMenuItemDisconnect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/disIcon.png"))); // NOI18N
        jMenuItemDisconnect.setText("Disconnect");
        jMenuItemDisconnect.setEnabled(false);
        jMenuItemDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDisconnectActionPerformed(evt);
            }
        });
        jMenuConnectDB.add(jMenuItemDisconnect);

        jMenuBar.add(jMenuConnectDB);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonBooking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRoom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonStorage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEmployee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClient, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPayment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(112, 112, 112)
                .addComponent(jLabelWelcomeLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
            .addComponent(jProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelProgressBarPercentage)
                .addGap(583, 583, 583))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabelWelcomeLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addComponent(jLabelProgressBarPercentage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonExit)
                        .addGap(79, 79, 79))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jButtonEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(jButtonClient, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jButtonBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jButtonRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jButtonStorage, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
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

    private void jButtonPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPaymentActionPerformed
        connectPaymentAction();
    }//GEN-LAST:event_jButtonPaymentActionPerformed

    private void jButtonStorageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStorageActionPerformed
        connectStorageAction();
    }//GEN-LAST:event_jButtonStorageActionPerformed

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
    private javax.swing.JLabel jLabelProgressBarPercentage;
    private javax.swing.JLabel jLabelWelcomeLogo;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuConnectDB;
    private javax.swing.JMenu jMenuExit;
    private javax.swing.JMenuItem jMenuItemConnect;
    private javax.swing.JMenuItem jMenuItemDisconnect;
    private javax.swing.JProgressBar jProgressBar;
    // End of variables declaration//GEN-END:variables
}
