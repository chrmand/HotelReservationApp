/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Χρήστος
 */
public class JDialogBooking extends javax.swing.JDialog {
    
    private boolean userChoosedOkFlag;
    
    /**
     * Creates new form JDialogBooking
     */
    public JDialogBooking(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        userChoosedOkFlag=false;
    }
    
    /**
     * Insert Action Button
     */
    public void insertBookingAction() {
        String query = "INSERT INTO BOOKING(bID, bCheckIN, bCheckOUT, bPersons, bNumOfDays, roomID) VALUES(" +jTextFieldID.getText()+ ", TO_DATE('"+jTextFieldCheckIN.getText()+"','DD/MM/YYYY'), TO_DATE('"+jTextFieldCheckOUT.getText()+"','DD/MM/YYYY'), "+jTextFieldPersons.getText()+", "+jTextFieldNumOfDays.getText()+", "+jTextFieldRoomID.getText()+")";
        System.out.println("Query: \t" + query);
        Statement insertStatement;
        
        try{
            insertStatement = MainMenu.con.createStatement();
            insertStatement.executeUpdate(query);
            insertStatement.close();
        } 
        catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
        }
    }
    
           
    
    /**
     * Update Action Button
     */
    public void updateBookingAction() {
        
        String query="UPDATE BOOKING " +
                " SET bCheckIN=TO_DATE('"+jTextFieldCheckIN.getText()+"','DD/MM/YYYY'),"+
                "     bCheckOUT=TO_DATE('"+jTextFieldCheckOUT.getText()+"','DD/MM/YYYY'),"+
                "     bPersons="+jTextFieldPersons.getText()+","+
                "     bNumOfDays="+jTextFieldNumOfDays.getText()+","+
                "     roomID="+jTextFieldRoomID.getText()+""+
                " WHERE  bID="+jTextFieldID.getText()+"";
        
        
        System.out.println("Query: \t" + query);
        Statement updateStatement;
        
        try{
            updateStatement = MainMenu.con.createStatement();
            updateStatement.executeUpdate(query);
            updateStatement.close();
        } 
        catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
        }
    }
    
    /**
     * Delete Action Button
     */
    public void deleteBookingAction() {
        String query = "DELETE BOOKING WHERE bID="+jTextFieldID.getText()+" ";
        System.out.println("Query: \t" + query);
        Statement deleteStatement;
        
        try{
            deleteStatement = MainMenu.con.createStatement();
            deleteStatement.executeUpdate(query);
            deleteStatement.close();
        } 
        catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
        }
    }
    
    /**
     * Φορτώνει τα δεδομένα στον πίνακα.
     */
    public void loadBooking() {
        clearTable( (DefaultTableModel)jTableBooking.getModel() );
        String query = "SELECT bID, TO_CHAR(bCheckIN, 'DD/MM/YYYY'), TO_CHAR(bCheckOUT, 'DD/MM/YYYY'), bPersons, bNumOfDays, roomID"
                + " FROM BOOKING "
                + " ORDER BY bID, bCheckIN, roomID" ;
        System.out.println("Query: \t" + query);
        
        Statement searchStatement;
        ResultSet searchRS;
        try{
            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                             java.sql.ResultSet.CONCUR_READ_ONLY);
            searchRS = searchStatement.executeQuery(query);
            while (searchRS.next()){
                String id = searchRS.getString("bID");
                String checkIN = searchRS.getString(2);
                String checkOUT = searchRS.getString(3);
                String Persons = searchRS.getString("bPersons");
                String numOfDays = searchRS.getString("bNumOfDays");
                String roomID = searchRS.getString("roomID");
                
                
                
                DefaultTableModel model = (DefaultTableModel)jTableBooking.getModel();
                model.addRow(
                    new Object [] {id,checkIN,checkOUT,Persons,numOfDays,roomID}
                );
                
            }
            
            if (searchRS.first()) {
                jTableBooking.changeSelection(0, 0, false, false);
            }
            
            searchRS.close();
            searchStatement.close();
        }
        catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
        }

    }

    /**
     * 
     * Σβήσιμο των γραμμών του Model του πίνακα.
     */
    public void clearTable(DefaultTableModel model){
        int numrows = model.getRowCount();
        for (int i=numrows-1; i>=0; i--) {
            model.removeRow(i);
        }    
    }
    
    
     public void bookingTableMouseClickedAction(){
        jTextFieldID.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 0));
        jTextFieldCheckIN.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 1));
        jTextFieldCheckOUT.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 2));
        jTextFieldPersons.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 3));
        jTextFieldNumOfDays.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 4));
        jTextFieldRoomID.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 5));
       
    }
   
    public void resetAction(){
        jTextFieldID.setText("");
        jTextFieldCheckIN.setText("");
        jTextFieldCheckOUT.setText("");
        jTextFieldPersons.setText("");
        jTextFieldNumOfDays.setText("");
        jTextFieldRoomID.setText("");
        
    }
    
    
/**
* 
* Enabled or Disabled buttons, checks if field is emplty from textFields values.
*/
    public void okEnableCheckAction(){
        jButtonInsert.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonReset.setEnabled(false);
        
        if(jTextFieldID.getText().length()>0) {
            jButtonDelete.setEnabled(true);
            if( (jTextFieldCheckIN.getText().length()>0)  &&
                (jTextFieldCheckOUT.getText().length()>0)  &&
                (jTextFieldPersons.getText().length()>0)  &&
                (jTextFieldNumOfDays.getText().length()>0)  &&
                (jTextFieldRoomID.getText().length()>0) )    
            {
                jButtonInsert.setEnabled(true);
                jButtonUpdate.setEnabled(true);
            }    
        }
            if( (jTextFieldCheckIN.getText().length()>0)  ||
                (jTextFieldCheckOUT.getText().length()>0)  ||
                (jTextFieldPersons.getText().length()>0)  ||
                (jTextFieldNumOfDays.getText().length()>0)  ||
                (jTextFieldRoomID.getText().length()>0) )    
            {
                jButtonReset.setEnabled(true);
            }    
    }
    
    /*Geters*/
    public String getID() {
        return this.jTextFieldID.getText();
    }
    
    public String getCheckIN() {
        return this.jTextFieldCheckIN.getText();
    }
    
    public String getCheckOUT() {
        return this.jTextFieldCheckOUT.getText();
    }
    
    public String getPersons() {
        return this.jTextFieldPersons.getText();
    }
    
    public String getNumOfDays() {
        return this.jTextFieldNumOfDays.getText();
    }
    
    public String getRoomID() {
        return this.jTextFieldRoomID.getText();
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

        jLabelRoomID = new javax.swing.JLabel();
        jTextFieldPersons = new javax.swing.JTextField();
        jTextFieldID = new javax.swing.JTextField();
        jLabelCheckOUT = new javax.swing.JLabel();
        jLabelCheckIN = new javax.swing.JLabel();
        jLabelNumberOfDays = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBooking = new javax.swing.JTable();
        jButtonReset = new javax.swing.JButton();
        jTextFieldNumOfDays = new javax.swing.JTextField();
        jTextFieldCheckOUT = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jTextFieldRoomID = new javax.swing.JTextField();
        jLabelID = new javax.swing.JLabel();
        jTextFieldCheckIN = new javax.swing.JTextField();
        jLabelBooking = new javax.swing.JLabel();
        jLabelPersons = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Booking Form");
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));
        setPreferredSize(new java.awt.Dimension(600, 680));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabelRoomID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelRoomID.setText("RoomID");

        jTextFieldPersons.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldPersonsCaretUpdate(evt);
            }
        });

        jTextFieldID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldIDCaretUpdate(evt);
            }
        });

        jLabelCheckOUT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCheckOUT.setText("Check Out");

        jLabelCheckIN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCheckIN.setText("Check In");

        jLabelNumberOfDays.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNumberOfDays.setText("Number Of Days");

        jTableBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Check In", "Check Out", "Persons", "Number Of Days", "RoomID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBookingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableBooking);

        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jTextFieldNumOfDays.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNumOfDaysCaretUpdate(evt);
            }
        });

        jTextFieldCheckOUT.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCheckOUTCaretUpdate(evt);
            }
        });

        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jTextFieldRoomID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldRoomIDCaretUpdate(evt);
            }
        });

        jLabelID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelID.setText("ID");

        jTextFieldCheckIN.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCheckINCaretUpdate(evt);
            }
        });

        jLabelBooking.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelBooking.setText("BOOKING");

        jLabelPersons.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPersons.setText("Persons");

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonInsert.setText("Insert");
        jButtonInsert.setEnabled(false);
        jButtonInsert.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jButtonInsertComponentShown(evt);
            }
        });
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNumberOfDays)
                            .addComponent(jLabelRoomID))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldNumOfDays, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(jTextFieldRoomID))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelPersons)
                                    .addComponent(jLabelID))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jTextFieldPersons, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(57, 57, 57))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCheckOUT)
                                    .addComponent(jLabelCheckIN))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldCheckIN)
                                    .addComponent(jTextFieldCheckOUT))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31))))
            .addGroup(layout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addComponent(jLabelBooking)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jButtonInsert))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabelBooking)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelID)
                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCheckIN)
                            .addComponent(jTextFieldCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCheckOUT)
                            .addComponent(jTextFieldCheckOUT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPersons)
                            .addComponent(jTextFieldPersons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumberOfDays)
                            .addComponent(jTextFieldNumOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRoomID)
                            .addComponent(jTextFieldRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonReset)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPersonsCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldPersonsCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldPersonsCaretUpdate

    private void jTextFieldIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldIDCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldIDCaretUpdate

    private void jTableBookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBookingMouseClicked
        bookingTableMouseClickedAction();
    }//GEN-LAST:event_jTableBookingMouseClicked

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        resetAction();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jTextFieldNumOfDaysCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNumOfDaysCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldNumOfDaysCaretUpdate

    private void jTextFieldCheckOUTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCheckOUTCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCheckOUTCaretUpdate

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateBookingAction();
        loadBooking();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jTextFieldRoomIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldRoomIDCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldRoomIDCaretUpdate

    private void jTextFieldCheckINCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCheckINCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCheckINCaretUpdate

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteBookingAction();
        loadBooking();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonInsertComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonInsertComponentShown
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertComponentShown

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        insertBookingAction();
        loadBooking();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadBooking();
        bookingTableMouseClickedAction();
    }//GEN-LAST:event_formComponentShown

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
            java.util.logging.Logger.getLogger(JDialogBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogBooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogBooking dialog = new JDialogBooking(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabelBooking;
    private javax.swing.JLabel jLabelCheckIN;
    private javax.swing.JLabel jLabelCheckOUT;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNumberOfDays;
    private javax.swing.JLabel jLabelPersons;
    private javax.swing.JLabel jLabelRoomID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBooking;
    private javax.swing.JTextField jTextFieldCheckIN;
    private javax.swing.JTextField jTextFieldCheckOUT;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNumOfDays;
    private javax.swing.JTextField jTextFieldPersons;
    private javax.swing.JTextField jTextFieldRoomID;
    // End of variables declaration//GEN-END:variables
}
