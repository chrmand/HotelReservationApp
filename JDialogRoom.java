/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Χρήστος
 */
public class JDialogRoom extends javax.swing.JDialog {
    
    private boolean userChoosedOkFlag;

    /**
     * Creates new form JDialogRoom
     */
    public JDialogRoom(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        userChoosedOkFlag=false;
    
         //Εμφανίζει το JDialogRoom στη μέση της οθονης   
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
    }
    
    /**
     * Insert Action Button
     */
    public void insertRoomAction() {
        String query = "INSERT INTO ROOM(rID, rType, rBeds, rPrice, rStatus) VALUES(" +jTextFieldID.getText()+ ", '"+jComboBoxType.getSelectedItem()+"', "+jComboBoxBeds.getSelectedItem()+", '"+jTextFieldPrice.getText()+"','Not Booked' )";
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
    public void updateRoomAction() {
        
        String query="UPDATE ROOM " +
                " SET rType='"+jComboBoxType.getSelectedItem()+"',"+
                "     rBeds="+jComboBoxBeds.getSelectedItem()+", "+
                "     rPrice='"+jTextFieldPrice.getText()+"', "+ 
                "     rStatus= 'Not Booked' "+ 
                " WHERE  rID="+jTextFieldID.getText()+" ";
        
        
        System.out.println("Query: \t" + query);
        Statement updateStatement;
        
        try{
            updateStatement = MainMenu.con.createStatement();
            updateStatement.executeUpdate(query);
            updateStatement.close();
        } 
        catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
            JOptionPane.showMessageDialog(null, "Error, you can't change the ID...");
        }
    }
    
    /**
     * Delete Action Button
     */
    public void deleteRoomAction() {
        String query = "DELETE ROOM WHERE rID="+jTextFieldID.getText()+" ";
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
    public void loadRoom() {
        clearTable( (DefaultTableModel)jTableRoom.getModel() );
        String query = "SELECT rID, rType, rBeds, rPrice, rStatus"
                + " FROM ROOM "
                + " ORDER BY rID, rType, rStatus" ;
        System.out.println("Query: \t" + query);
        
        Statement searchStatement;
        ResultSet searchRS;
        try{
            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                             java.sql.ResultSet.CONCUR_READ_ONLY);
            searchRS = searchStatement.executeQuery(query);
            while (searchRS.next()){
                String ID = searchRS.getString("rID");
                String type = searchRS.getString("rType");
                String beds = searchRS.getString("rBeds");
                String price = searchRS.getString("rPrice");
                String status = searchRS.getString("rStatus");
                
                
                
                DefaultTableModel model = (DefaultTableModel)jTableRoom.getModel();
                model.addRow(
                    new Object [] {ID,type,beds,price,status}
                );
                
            }
            
            if (searchRS.first()) {
                jTableRoom.changeSelection(0, 0, false, false);
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
    
    /**
     * 
     * Εμφάνιση των δεδομένων από τα comboBox του Model του πίνακα όταν επιλέγουμε κάποια γραμμή.
     */
   public void comboBoxSelectGetValues(){
       DefaultTableModel model = (DefaultTableModel) jTableRoom.getModel();
     
       //jComboBoxType
       String comboTypeSelect = model.getValueAt(jTableRoom.getSelectedRow(), 1).toString();
       for (int i = 0; i < jComboBoxType.getItemCount(); i++) {
           if (jComboBoxType.getItemAt(i).equalsIgnoreCase(comboTypeSelect)) {
               jComboBoxType.setSelectedIndex(i);
           }
       }
       
       //jComboBoxBeds
       String comboBedsSelect = model.getValueAt(jTableRoom.getSelectedRow(), 2).toString();
       for (int i = 0; i < jComboBoxBeds.getItemCount(); i++) {
           if (jComboBoxBeds.getItemAt(i).equalsIgnoreCase(comboBedsSelect)) {
               jComboBoxBeds.setSelectedIndex(i);
           }
       }
   }
    
    public void roomTableMouseClickedAction(){
        jTextFieldID.setText(""+jTableRoom.getModel().getValueAt(jTableRoom.getSelectedRow(), 0));
        jTextFieldPrice.setText(""+jTableRoom.getModel().getValueAt(jTableRoom.getSelectedRow(), 3));
        
        comboBoxSelectGetValues();
    }
    
    public void resetAction(){
        jTextFieldID.setText("");
        jComboBoxType.setSelectedIndex(-1);
        jComboBoxBeds.setSelectedIndex(-1);
        jTextFieldPrice.setText("");
        
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
            if( (jComboBoxType.getSelectedItem().equals("Monoklino") == (jComboBoxBeds.getSelectedItem().equals("1")) )  &&
                (jComboBoxType.getSelectedItem().equals("Diklino") == (jComboBoxBeds.getSelectedItem().equals("2")) )  &&
                (jComboBoxType.getSelectedItem().equals("Triklino") == (jComboBoxBeds.getSelectedItem().equals("3")) &&
                (jTextFieldPrice.getText().length()>0) )  
                
              )    
            {
                jButtonInsert.setEnabled(true);
                jButtonUpdate.setEnabled(true);
            }    
        }
            if( (jComboBoxType.getSelectedItem().equals("Monoklino") == (jComboBoxBeds.getSelectedItem().equals("1")) )  ||
                (jComboBoxType.getSelectedItem().equals("Diklino") == (jComboBoxBeds.getSelectedItem().equals("2")) )  ||
                (jComboBoxType.getSelectedItem().equals("Triklino") == (jComboBoxBeds.getSelectedItem().equals("3")) ||
                (jTextFieldPrice.getText().length()>0) )    
              )    
            {
                jButtonReset.setEnabled(true);
            }    
    }
    

    
    /*Geters*/
    public String getID() {
        return this.jTextFieldID.getText();
    }
    
    public String getTType() {
        return (String) this.jComboBoxType.getSelectedItem();
    }
    
    public String getBeds() {
        return (String) this.jComboBoxBeds.getSelectedItem();
    }
    
    public String getPrice() {
        return this.jTextFieldPrice.getText();
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

        jTextFieldID = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();
        jLabelBeds = new javax.swing.JLabel();
        jLabelType = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableRoom = new javax.swing.JTable();
        jButtonReset = new javax.swing.JButton();
        jLabelBooking = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jComboBoxType = new javax.swing.JComboBox<>();
        jComboBoxBeds = new javax.swing.JComboBox<>();
        jLabelPrice = new javax.swing.JLabel();
        jTextFieldPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Room Form");
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));
        setPreferredSize(new java.awt.Dimension(557, 766));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTextFieldID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldIDCaretUpdate(evt);
            }
        });

        jButtonUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/update.png"))); // NOI18N
        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/insert.png"))); // NOI18N
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

        jLabelBeds.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBeds.setText("Beds");

        jLabelType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelType.setText("Type");

        jLabelID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelID.setText("ID");

        jTableRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Type", "Beds", "Price", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableRoomMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableRoom);
        if (jTableRoom.getColumnModel().getColumnCount() > 0) {
            jTableRoom.getColumnModel().getColumn(0).setResizable(false);
            jTableRoom.getColumnModel().getColumn(1).setResizable(false);
            jTableRoom.getColumnModel().getColumn(2).setResizable(false);
            jTableRoom.getColumnModel().getColumn(3).setResizable(false);
            jTableRoom.getColumnModel().getColumn(4).setResizable(false);
        }

        jButtonReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-reset.png"))); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jLabelBooking.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelBooking.setText("ROOM");

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-delete.png"))); // NOI18N
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monoklino", "Diklino", "Triklino" }));
        jComboBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTypeActionPerformed(evt);
            }
        });

        jComboBoxBeds.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        jComboBoxBeds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBedsActionPerformed(evt);
            }
        });

        jLabelPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPrice.setText("Price");

        jTextFieldPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPriceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelBooking)
                .addGap(261, 261, 261))
            .addGroup(layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelBeds)
                    .addComponent(jLabelType)
                    .addComponent(jLabelID)
                    .addComponent(jLabelPrice))
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(jComboBoxType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxBeds, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBooking)
                .addGap(62, 62, 62)
                .addComponent(jButtonInsert)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelID)
                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelType)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBeds)
                            .addComponent(jComboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPrice)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(jButtonReset)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldIDCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldIDCaretUpdate

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateRoomAction();
        loadRoom();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonInsertComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonInsertComponentShown
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertComponentShown

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        insertRoomAction();
        loadRoom();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jTableRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableRoomMouseClicked
        roomTableMouseClickedAction();
    }//GEN-LAST:event_jTableRoomMouseClicked

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        resetAction();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteRoomAction();
        loadRoom();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadRoom();
        roomTableMouseClickedAction();
    }//GEN-LAST:event_formComponentShown

    private void jComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTypeActionPerformed
        
    }//GEN-LAST:event_jComboBoxTypeActionPerformed

    private void jComboBoxBedsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBedsActionPerformed
       
    }//GEN-LAST:event_jComboBoxBedsActionPerformed

    private void jTextFieldPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPriceActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogRoom dialog = new JDialogRoom(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> jComboBoxBeds;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabelBeds;
    private javax.swing.JLabel jLabelBooking;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableRoom;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldPrice;
    // End of variables declaration//GEN-END:variables
}
