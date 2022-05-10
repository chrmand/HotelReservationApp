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
public class JDialogClient extends javax.swing.JDialog  {
    
    private boolean userChoosedOkFlag;

    /**
     * Creates new form JDialogClient
     */
    public JDialogClient(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        userChoosedOkFlag=false;
    }
    
    /**
     * Insert Action Button
     */
    public void insertClientAction() {
        String query = "INSERT INTO CLIENT(cAFM, cFirstname, cLastname, cPhone, cCountry, cCity, cStreet, cNum, cTK) VALUES(" +jTextFieldAFM.getText()+ ", '"+jTextFieldFirstname.getText()+"', '"+jTextFieldLastname.getText()+"', "+jTextFieldPhone.getText()+", '"+jTextFieldCountry.getText()+"', '"+jTextFieldCity.getText()+"', '"+jTextFieldStreet.getText()+"', "+jTextFieldNumber.getText()+", "+jTextFieldTK.getText()+")";
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
    public void updateClientAction() {
        
        String query="UPDATE CLIENT " +
                " SET cFirstname='"+jTextFieldFirstname.getText()+"',"+
                "     cLastname='"+jTextFieldLastname.getText()+"',"+
                "     cPhone="+jTextFieldPhone.getText()+","+
                "     cCountry='"+jTextFieldCountry.getText()+"',"+
                "     cCity='"+jTextFieldCity.getText()+"',"+
                "     cStreet='"+jTextFieldStreet.getText()+"',"+
                "     cNum="+jTextFieldNumber.getText()+","+
                "     cTK="+jTextFieldTK.getText()+" "+
                " WHERE  cAFM="+jTextFieldAFM.getText()+"";
        
        
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
    public void deleteClientAction() {
        String query = "DELETE CLIENT WHERE cAFM="+jTextFieldAFM.getText()+" ";
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
    public void loadClient() {
        clearTable( (DefaultTableModel)jTableClient.getModel() );
        String query = "SELECT cAFM, cFirstname, cLastname, cPhone, cCountry, cCity, cStreet, cNum, cTK"
                + " FROM CLIENT "
                + " ORDER BY cAFM, cCountry" ;
        System.out.println("Query: \t" + query);
        
        Statement searchStatement;
        ResultSet searchRS;
        try{
            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                             java.sql.ResultSet.CONCUR_READ_ONLY);
            searchRS = searchStatement.executeQuery(query);
            while (searchRS.next()){
                String afm = searchRS.getString("cAFM");
                String firstname = searchRS.getString("cFirstname");
                String lastname = searchRS.getString("cLastname");
                String phone = searchRS.getString("cPhone");
                String country = searchRS.getString("cCountry");
                String city = searchRS.getString("cCity");
                String street = searchRS.getString("cStreet");
                String number = searchRS.getString("cNum");
                String tk = searchRS.getString("cTK");
                
                
                DefaultTableModel model = (DefaultTableModel)jTableClient.getModel();
                model.addRow(
                    new Object [] {afm,firstname,lastname,phone,country,city,street,number,tk}
                );
                
            }
            
            if (searchRS.first()) {
                jTableClient.changeSelection(0, 0, false, false);
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
    
    
     public void clientTableMouseClickedAction(){
        jTextFieldAFM.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 0));
        jTextFieldFirstname.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 1));
        jTextFieldLastname.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 2));
        jTextFieldPhone.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 3));
        jTextFieldCountry.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 4));
        jTextFieldCity.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 5));
        jTextFieldStreet.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 6));
        jTextFieldNumber.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 7));
        jTextFieldTK.setText(""+jTableClient.getModel().getValueAt(jTableClient.getSelectedRow(), 8));
    }
    
    public void resetAction(){
        jTextFieldAFM.setText("");
        jTextFieldFirstname.setText("");
        jTextFieldLastname.setText("");
        jTextFieldPhone.setText("");
        jTextFieldCountry.setText("");
        jTextFieldCity.setText("");
        jTextFieldStreet.setText("");
        jTextFieldNumber.setText("");
        jTextFieldTK.setText("");
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
        
        if(jTextFieldAFM.getText().length()>0) {
            jButtonDelete.setEnabled(true);
            if( (jTextFieldFirstname.getText().length()>0)  &&
                (jTextFieldLastname.getText().length()>0)  &&
                (jTextFieldPhone.getText().length()>0)  &&
                (jTextFieldCountry.getText().length()>0)  &&
                (jTextFieldCity.getText().length()>0) &&
                (jTextFieldStreet.getText().length()>0) &&
                (jTextFieldNumber.getText().length()>0)&&
                (jTextFieldTK.getText().length()>0) )    
            {
                jButtonInsert.setEnabled(true);
                jButtonUpdate.setEnabled(true);
            }    
        }
            if( (jTextFieldFirstname.getText().length()>0)  ||
                (jTextFieldLastname.getText().length()>0)  ||
                (jTextFieldPhone.getText().length()>0)  ||
                (jTextFieldCountry.getText().length()>0)  ||
                (jTextFieldCity.getText().length()>0) ||
                (jTextFieldStreet.getText().length()>0) ||
                (jTextFieldNumber.getText().length()>0) ||
                (jTextFieldTK.getText().length()>0) )    
            {
                jButtonReset.setEnabled(true);
            }    
    }
    
    
 
    /*Geters*/
    public String getAFM() {
        return this.jTextFieldAFM.getText();
    }
    
    public String getFirstname() {
        return this.jTextFieldFirstname.getText();
    }
    
    public String getLastname() {
        return this.jTextFieldLastname.getText();
    }
    
    public String getPhone() {
        return this.jTextFieldPhone.getText();
    }
    
    public String getCountry() {
        return this.jTextFieldCountry.getText();
    }
    
    public String getCity() {
        return this.jTextFieldCity.getText();
    }
    
    public String getStreet() {
        return this.jTextFieldStreet.getText();
    }
    
    public String getNumber() {
        return this.jTextFieldNumber.getText();
    }

    public String getTK() {
        return this.jTextFieldTK.getText();
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

        jTextFieldCity = new javax.swing.JTextField();
        jTextFieldFirstname = new javax.swing.JTextField();
        jLabelPhone = new javax.swing.JLabel();
        jButtonInsert = new javax.swing.JButton();
        jTextFieldPhone = new javax.swing.JTextField();
        jLabelLastname = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jButtonReset = new javax.swing.JButton();
        jTextFieldCountry = new javax.swing.JTextField();
        jTextFieldLastname = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jLabelAFM = new javax.swing.JLabel();
        jLabelClient = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jLabelCity = new javax.swing.JLabel();
        jTextFieldAFM = new javax.swing.JTextField();
        jLabelFirstname = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClient = new javax.swing.JTable();
        jLabelStreet = new javax.swing.JLabel();
        jLabelTK = new javax.swing.JLabel();
        jLabelNumber = new javax.swing.JLabel();
        jTextFieldStreet = new javax.swing.JTextField();
        jTextFieldNumber = new javax.swing.JTextField();
        jTextFieldTK = new javax.swing.JTextField();
        jLabelPhoneError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Client Form");
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));
        setPreferredSize(new java.awt.Dimension(593, 692));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTextFieldCity.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCityCaretUpdate(evt);
            }
        });

        jTextFieldFirstname.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldFirstnameCaretUpdate(evt);
            }
        });

        jLabelPhone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPhone.setText("Phone");

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

        jTextFieldPhone.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldPhoneCaretUpdate(evt);
            }
        });

        jLabelLastname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelLastname.setText("Lastname");

        jLabelCountry.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCountry.setText("Country");

        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jTextFieldCountry.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCountryCaretUpdate(evt);
            }
        });

        jTextFieldLastname.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldLastnameCaretUpdate(evt);
            }
        });

        jButtonUpdate.setText("Update");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jLabelAFM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelAFM.setText("AFM");

        jLabelClient.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelClient.setText("CLIENT");

        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jLabelCity.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCity.setText("City");

        jTextFieldAFM.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldAFMCaretUpdate(evt);
            }
        });

        jLabelFirstname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelFirstname.setText("Firstname");

        jTableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "AFM", "Firstname", "Lastname", "Phone", "Country", "City", "Street", "Number", "TK"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableClientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableClient);

        jLabelStreet.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelStreet.setText("Street");

        jLabelTK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelTK.setText("TK");

        jLabelNumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNumber.setText("Number");

        jTextFieldStreet.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldStreetCaretUpdate(evt);
            }
        });

        jTextFieldNumber.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldNumberCaretUpdate(evt);
            }
        });

        jTextFieldTK.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldTKCaretUpdate(evt);
            }
        });

        jLabelPhoneError.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelLastname)
                            .addComponent(jLabelFirstname))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(jTextFieldLastname)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCountry)
                            .addComponent(jLabelPhone)
                            .addComponent(jLabelCity)
                            .addComponent(jLabelStreet)
                            .addComponent(jLabelTK)
                            .addComponent(jLabelNumber)
                            .addComponent(jLabelAFM))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPhoneError)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldAFM)
                                .addComponent(jTextFieldCity)
                                .addComponent(jTextFieldPhone)
                                .addComponent(jTextFieldCountry, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                                .addComponent(jTextFieldStreet)
                                .addComponent(jTextFieldNumber)
                                .addComponent(jTextFieldTK)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelClient)
                .addGap(214, 214, 214))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelClient)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelAFM)
                            .addComponent(jTextFieldAFM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFirstname)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelLastname)
                            .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPhone)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelPhoneError)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCountry)
                            .addComponent(jTextFieldCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCity)
                            .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonReset))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelStreet))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelNumber)
                                    .addComponent(jTextFieldNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelTK)
                                    .addComponent(jTextFieldTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jButtonInsert)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCityCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCityCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCityCaretUpdate

    private void jTextFieldFirstnameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldFirstnameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldFirstnameCaretUpdate

    private void jButtonInsertComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jButtonInsertComponentShown
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertComponentShown

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        insertClientAction();
        loadClient();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jTextFieldPhoneCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldPhoneCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldPhoneCaretUpdate

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        resetAction();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jTextFieldCountryCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCountryCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCountryCaretUpdate

    private void jTextFieldLastnameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldLastnameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldLastnameCaretUpdate

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        updateClientAction();
        loadClient();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        deleteClientAction();
        loadClient();
        okEnableCheckAction();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jTextFieldAFMCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldAFMCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldAFMCaretUpdate

    private void jTableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientMouseClicked
        clientTableMouseClickedAction();
    }//GEN-LAST:event_jTableClientMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadClient();
        clientTableMouseClickedAction();
    }//GEN-LAST:event_formComponentShown

    private void jTextFieldStreetCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldStreetCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldStreetCaretUpdate

    private void jTextFieldNumberCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNumberCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldNumberCaretUpdate

    private void jTextFieldTKCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldTKCaretUpdate
       okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldTKCaretUpdate

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
            java.util.logging.Logger.getLogger(JDialogClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogClient dialog = new JDialogClient(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelCity;
    private javax.swing.JLabel jLabelClient;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelFirstname;
    private javax.swing.JLabel jLabelLastname;
    private javax.swing.JLabel jLabelNumber;
    private javax.swing.JLabel jLabelPhone;
    private javax.swing.JLabel jLabelPhoneError;
    private javax.swing.JLabel jLabelStreet;
    private javax.swing.JLabel jLabelTK;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableClient;
    private javax.swing.JTextField jTextFieldAFM;
    private javax.swing.JTextField jTextFieldCity;
    private javax.swing.JTextField jTextFieldCountry;
    private javax.swing.JTextField jTextFieldFirstname;
    private javax.swing.JTextField jTextFieldLastname;
    private javax.swing.JTextField jTextFieldNumber;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldStreet;
    private javax.swing.JTextField jTextFieldTK;
    // End of variables declaration//GEN-END:variables
}
