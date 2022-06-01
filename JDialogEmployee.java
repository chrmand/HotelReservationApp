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
public class JDialogEmployee extends javax.swing.JDialog {

    private boolean userChoosedOkFlag;
    
    
    /**
     * Creates new form JDialogEmployee
     */
    public JDialogEmployee(java.awt.Frame parent, boolean modal) {
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
    public void insertEmployeeAction() {
        String query = "INSERT INTO EMPLOYEE(eAFM, eFirstname, eLastname, eHireDate) VALUES("+jTextFieldAFM.getText()+", '" +jTextFieldFirstname.getText()+ "', '" +jTextFieldLastname.getText()+ "', TO_DATE('"+jTextFieldHireDate.getText()+"','DD/MM/YYYY') )";
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
    public void updateEmployeeAction() {
        
        String query="UPDATE EMPLOYEE " +
                " SET eFirstname='" +jTextFieldFirstname.getText()+ "',"+
                "     eLastname='" +jTextFieldLastname.getText()+ "',"+
                "     eHireDate=TO_DATE('"+jTextFieldHireDate.getText()+"','DD/MM/YYYY') "+
                " WHERE eAFM="+jTextFieldAFM.getText()+" ";
        
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
    public void deleteEmployeeAction() {
        String query = "DELETE EMPLOYEE WHERE eAFM="+jTextFieldAFM.getText()+" ";
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
    public void loadEmployee() {
        clearTable( (DefaultTableModel)jTableEmployee.getModel() );
        String query = "SELECT eAFM, eFirstname, eLastname, TO_CHAR(eHireDate, 'DD/MM/YYYY')"
                + " FROM EMPLOYEE "
                + " ORDER BY eHireDate" ;
        System.out.println("Query: \t" + query);
        
        Statement searchStatement;
        ResultSet searchRS;
        try{
            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                             java.sql.ResultSet.CONCUR_READ_ONLY);
            searchRS = searchStatement.executeQuery(query);
            while (searchRS.next()){
                String afm = searchRS.getString("eAFM");
                String firstname = searchRS.getString("eFirstname");
                String lastname = searchRS.getString("eLastname");
                String hiredate = searchRS.getString(4);
    
                
                DefaultTableModel model = (DefaultTableModel)jTableEmployee.getModel();
                model.addRow(
                    new Object [] {afm,firstname,lastname,hiredate}
                );
               
            }
            
            if (searchRS.first()) {
                jTableEmployee.changeSelection(0, 0, false, false);
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
   
    
    public void employeeTableMouseClickedAction(){
        jTextFieldAFM.setText(""+jTableEmployee.getModel().getValueAt(jTableEmployee.getSelectedRow(), 0));
        jTextFieldFirstname.setText(""+jTableEmployee.getModel().getValueAt(jTableEmployee.getSelectedRow(), 1));
        jTextFieldLastname.setText(""+jTableEmployee.getModel().getValueAt(jTableEmployee.getSelectedRow(), 2));
        jTextFieldHireDate.setText(""+jTableEmployee.getModel().getValueAt(jTableEmployee.getSelectedRow(), 3));

        
    }
    
    public void resetAction(){
        jTextFieldAFM.setText("");
        jTextFieldFirstname.setText("");
        jTextFieldLastname.setText("");
        jTextFieldHireDate.setText("");
        
    }
    

    /**
     * 
     * Enabled or Disabled OK from textFields values
     */
    public void okEnableCheckAction(){
        jButtonInsert.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonReset.setEnabled(false);

        
        
        
        if(jTextFieldAFM.getText().length()>0) {
            jButtonDelete.setEnabled(true);
            if( (jTextFieldFirstname.getText().length()>0)  &&
                (jTextFieldLastname.getText().length()>0) &&
                (jTextFieldHireDate.getText().length()>0))    
           
            {
                
                jButtonInsert.setEnabled(true);
                jButtonUpdate.setEnabled(true);
                
              
            }
   
        }
         
            if( (jTextFieldFirstname.getText().length()>0)  ||
                (jTextFieldLastname.getText().length()>0) ||
                (jTextFieldHireDate.getText().length()>0) )
            {
                jButtonReset.setEnabled(true);
            } 
            
            
    }
    
    
    /*Geters*/
    public String getFirstname() {
        return this.jTextFieldFirstname.getText();
    }
    
    public String getLastname() {
        return this.jTextFieldLastname.getText();
    }
    
    public String getAFM() {
        return this.jTextFieldAFM.getText();
    }
    
    public String getHireDate() {
        return this.jTextFieldHireDate.getText();
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

        jLabelEmployee = new javax.swing.JLabel();
        jLabelFirstname = new javax.swing.JLabel();
        jTextFieldFirstname = new javax.swing.JTextField();
        jButtonInsert = new javax.swing.JButton();
        jLabelLastname = new javax.swing.JLabel();
        jButtonReset = new javax.swing.JButton();
        jTextFieldLastname = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jLabelAFM = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jTextFieldAFM = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableEmployee = new javax.swing.JTable();
        jLabelHireDate = new javax.swing.JLabel();
        jTextFieldHireDate = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Form");
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jLabelEmployee.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelEmployee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-employee-2.png"))); // NOI18N
        jLabelEmployee.setText("EMPLOYEE");

        jLabelFirstname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelFirstname.setText("Firstname");

        jTextFieldFirstname.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldFirstnameCaretUpdate(evt);
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

        jLabelLastname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLastname.setText("Lastname");

        jButtonReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-reset.png"))); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jTextFieldLastname.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldLastnameCaretUpdate(evt);
            }
        });

        jButtonUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/update.png"))); // NOI18N
        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jLabelAFM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelAFM.setText("AFM");

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-delete.png"))); // NOI18N
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jTextFieldAFM.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldAFMCaretUpdate(evt);
            }
        });

        jTableEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AFM", "Firstname", "Lastname", "Hire Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEmployeeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableEmployee);
        if (jTableEmployee.getColumnModel().getColumnCount() > 0) {
            jTableEmployee.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        jLabelHireDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelHireDate.setText("Hire Date");

        jTextFieldHireDate.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldHireDateCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelFirstname)
                                .addGap(45, 45, 45)
                                .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelLastname)
                                    .addComponent(jLabelAFM)
                                    .addComponent(jLabelHireDate))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldHireDate, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                    .addComponent(jTextFieldAFM)
                                    .addComponent(jTextFieldLastname))))
                        .addGap(312, 312, 312)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelEmployee)
                .addGap(221, 221, 221))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelEmployee)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonUpdate)
                            .addComponent(jLabelAFM)
                            .addComponent(jTextFieldAFM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFirstname)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLastname)
                            .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelHireDate)
                        .addComponent(jTextFieldHireDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jButtonReset)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldFirstnameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldFirstnameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldFirstnameCaretUpdate

    private void jButtonInsertComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonInsertComponentShown
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertComponentShown

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        insertEmployeeAction();
        loadEmployee();
        okEnableCheckAction();

    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        resetAction();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jTextFieldLastnameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldLastnameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldLastnameCaretUpdate

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateEmployeeAction();
        loadEmployee();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteEmployeeAction();
        loadEmployee();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTextFieldAFMCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldAFMCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldAFMCaretUpdate

    private void jTableEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEmployeeMouseClicked
        employeeTableMouseClickedAction();
    }//GEN-LAST:event_jTableEmployeeMouseClicked

    private void jTextFieldHireDateCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldHireDateCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldHireDateCaretUpdate

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadEmployee();
        employeeTableMouseClickedAction();
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
            java.util.logging.Logger.getLogger(JDialogEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogEmployee dialog = new JDialogEmployee(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelAFM;
    private javax.swing.JLabel jLabelEmployee;
    private javax.swing.JLabel jLabelFirstname;
    private javax.swing.JLabel jLabelHireDate;
    private javax.swing.JLabel jLabelLastname;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEmployee;
    private javax.swing.JTextField jTextFieldAFM;
    private javax.swing.JTextField jTextFieldFirstname;
    private javax.swing.JTextField jTextFieldHireDate;
    private javax.swing.JTextField jTextFieldLastname;
    // End of variables declaration//GEN-END:variables
}
