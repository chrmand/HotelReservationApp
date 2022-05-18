/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Χρήστος
 */
public class JDialogStorage extends javax.swing.JDialog {
    
     private boolean userChoosedOkFlag;

    /**
     * Creates new form JDialogStorage
     */
    public JDialogStorage(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        paintColorBackground();
        
        userChoosedOkFlag=false;
        
         //Εμφανίζει το JDialogEmployee στη μέση της οθονης   
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);        
    }
    
     public void paintColorBackground() {
        Color col = new Color(148, 222, 222);
        getContentPane().setBackground(col);
    }
     
      /**
     * Insert Action Button
     */
    public void insertStorageAction() {
        String query = "INSERT INTO STORAGE(sStorageName, sID, sCategory, sProductName, sQuantity) VALUES('" +jTextFieldStorageName.getText()+ "', '" +jTextFieldStorageID.getText()+ "', '" +jTextFieldCategory.getText()+ "', '" +jTextFieldProductName.getText()+ "',  "+jTextFieldQuantity.getText()+")";
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
    public void updateStorageAction() {
        
        String query="UPDATE STORAGE " +
                " SET sStorageName='" +jTextFieldStorageName.getText()+ "',"+
                "     sCategory='" +jTextFieldCategory.getText()+ "',"+
                "     sProductName='" +jTextFieldProductName.getText()+ "',"+
                "     sQuantity=" +jTextFieldQuantity.getText()+ " "+
                " WHERE sID='" +jTextFieldStorageID.getText()+ "' ";
        
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
    public void deleteStorageAction() {
        String query = "DELETE STORAGE WHERE sID='" +jTextFieldStorageID.getText()+ "' ";
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
    public void loadStorage() {
        clearTable( (DefaultTableModel)jTableStorage.getModel() );
        String query = "SELECT sStorageName, sID, sCategory, sProductName, sQuantity"
                + " FROM STORAGE "
                + " ORDER BY sID, sCategory" ;
        System.out.println("Query: \t" + query);
        
        Statement searchStatement;
        ResultSet searchRS;
        try{
            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                             java.sql.ResultSet.CONCUR_READ_ONLY);
            searchRS = searchStatement.executeQuery(query);
            while (searchRS.next()){
                String sStorageName = searchRS.getString("sStorageName");
                String sID = searchRS.getString("sID");
                String sCategory = searchRS.getString("sCategory");
                String sProductName = searchRS.getString("sProductName");
                String sQuantity = searchRS.getString("sQuantity");
                
                
                DefaultTableModel model = (DefaultTableModel)jTableStorage.getModel();
                model.addRow(
                    new Object [] {sStorageName,sID,sCategory,sProductName,sQuantity}
                );
               
            }
            
            if (searchRS.first()) {
                jTableStorage.changeSelection(0, 0, false, false);
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
    
     public void storageTableMouseClickedAction(){
        jTextFieldStorageName.setText(""+jTableStorage.getModel().getValueAt(jTableStorage.getSelectedRow(), 0));
        jTextFieldStorageID.setText(""+jTableStorage.getModel().getValueAt(jTableStorage.getSelectedRow(), 1));
        jTextFieldCategory.setText(""+jTableStorage.getModel().getValueAt(jTableStorage.getSelectedRow(), 2));
        jTextFieldProductName.setText(""+jTableStorage.getModel().getValueAt(jTableStorage.getSelectedRow(), 3));
        jTextFieldQuantity.setText(""+jTableStorage.getModel().getValueAt(jTableStorage.getSelectedRow(), 4));
       
    }
  
    public void resetAction(){
        jTextFieldStorageName.setText("");
        jTextFieldStorageID.setText("");
        jTextFieldCategory.setText("");
        jTextFieldProductName.setText("");
        jTextFieldQuantity.setText("");
    }
    
/**
* 
* Enabled or Disabled buttons, checks if field are empty from textFields values.
*/
    public void okEnableCheckAction(){
        jButtonInsert.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonReset.setEnabled(false);
        
        if(jTextFieldStorageID.getText().length()>0) {
            jButtonDelete.setEnabled(true);
            if( (jTextFieldStorageName.getText().length()>0)  &&
                (jTextFieldCategory.getText().length()>0)  && 
                (jTextFieldProductName.getText().length()>0)  &&
                (jTextFieldQuantity.getText().length()>0) )    
            {
                jButtonInsert.setEnabled(true);
                jButtonUpdate.setEnabled(true);
            }    
        }
            if( (jTextFieldStorageName.getText().length()>0) ||
                (jTextFieldCategory.getText().length()>0)   ||
                (jTextFieldProductName.getText().length()>0)  ||
                (jTextFieldQuantity.getText().length()>0) )    
            {
                jButtonReset.setEnabled(true);
            }    
    }
    
   
    
    /*Geters*/
     public String getStorageName() {
        return this.jTextFieldStorageName.getText();
    }
    
    public String getStorageID() {
        return this.jTextFieldStorageID.getText();
    }
    
    public String getCategory() {
        return this.jTextFieldCategory.getText();
    }
    
    public String getProductName() {
        return this.jTextFieldProductName.getText();
    }
    
    public String getQuantity() {
        return this.jTextFieldQuantity.getText();
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

        jTextFieldStorageID = new javax.swing.JTextField();
        jLabelQuantity = new javax.swing.JLabel();
        jButtonInsert = new javax.swing.JButton();
        jTextFieldQuantity = new javax.swing.JTextField();
        jLabelCategory = new javax.swing.JLabel();
        jButtonReset = new javax.swing.JButton();
        jTextFieldCategory = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jLabelProductName = new javax.swing.JLabel();
        jLabelEmployee = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jTextFieldProductName = new javax.swing.JTextField();
        jLabelStorageID = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableStorage = new javax.swing.JTable();
        jLabelStorageName = new javax.swing.JLabel();
        jTextFieldStorageName = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTextFieldStorageID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldStorageIDCaretUpdate(evt);
            }
        });

        jLabelQuantity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelQuantity.setText("Quantity");

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

        jTextFieldQuantity.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldQuantityCaretUpdate(evt);
            }
        });

        jLabelCategory.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCategory.setText("Category");

        jButtonReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-reset.png"))); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jTextFieldCategory.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCategoryCaretUpdate(evt);
            }
        });

        jButtonUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/update.png"))); // NOI18N
        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jLabelProductName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelProductName.setText("Product Name");

        jLabelEmployee.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-storage.png"))); // NOI18N
        jLabelEmployee.setText("STORAGE");

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-delete.png"))); // NOI18N
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jTextFieldProductName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldProductNameCaretUpdate(evt);
            }
        });

        jLabelStorageID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelStorageID.setText("ID");

        jTableStorage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Storage Name", "ID", "Category", "Product Name", "Quantity"
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
        jTableStorage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableStorageMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableStorage);
        if (jTableStorage.getColumnModel().getColumnCount() > 0) {
            jTableStorage.getColumnModel().getColumn(3).setPreferredWidth(130);
            jTableStorage.getColumnModel().getColumn(4).setPreferredWidth(20);
        }

        jLabelStorageName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelStorageName.setText("Storage Name");

        jTextFieldStorageName.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldStorageNameCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelEmployee)
                        .addGap(50, 50, 50))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelQuantity)
                                    .addComponent(jLabelCategory)
                                    .addComponent(jLabelProductName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldProductName, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                    .addComponent(jTextFieldCategory)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelStorageName)
                                    .addComponent(jLabelStorageID))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldStorageID, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldStorageName))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jButtonInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonUpdate)
                            .addComponent(jLabelStorageName)
                            .addComponent(jTextFieldStorageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabelEmployee)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStorageID)
                    .addComponent(jTextFieldStorageID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCategory)
                            .addComponent(jTextFieldCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelProductName)
                            .addComponent(jTextFieldProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelQuantity)
                            .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                        .addComponent(jButtonReset)
                        .addGap(36, 36, 36)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldStorageIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldStorageIDCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldStorageIDCaretUpdate

    private void jButtonInsertComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonInsertComponentShown
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertComponentShown

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        insertStorageAction();
        loadStorage();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jTextFieldQuantityCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldQuantityCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldQuantityCaretUpdate

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        resetAction();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jTextFieldCategoryCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCategoryCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCategoryCaretUpdate

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateStorageAction();
        loadStorage();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteStorageAction();
        loadStorage();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTextFieldProductNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldProductNameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldProductNameCaretUpdate

    private void jTableStorageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableStorageMouseClicked
        storageTableMouseClickedAction();
    }//GEN-LAST:event_jTableStorageMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadStorage();
        storageTableMouseClickedAction();
    }//GEN-LAST:event_formComponentShown

    private void jTextFieldStorageNameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldStorageNameCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStorageNameCaretUpdate

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
            java.util.logging.Logger.getLogger(JDialogStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogStorage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogStorage dialog = new JDialogStorage(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelCategory;
    private javax.swing.JLabel jLabelEmployee;
    private javax.swing.JLabel jLabelProductName;
    private javax.swing.JLabel jLabelQuantity;
    private javax.swing.JLabel jLabelStorageID;
    private javax.swing.JLabel jLabelStorageName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableStorage;
    private javax.swing.JTextField jTextFieldCategory;
    private javax.swing.JTextField jTextFieldProductName;
    private javax.swing.JTextField jTextFieldQuantity;
    private javax.swing.JTextField jTextFieldStorageID;
    private javax.swing.JTextField jTextFieldStorageName;
    // End of variables declaration//GEN-END:variables
}
