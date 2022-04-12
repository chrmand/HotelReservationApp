/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;
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
        String query = "INSERT INTO BOOKING(bID, bCheckIN, bCheckOUT, bPersons, bNumOfDays, roomID, clientAFM) VALUES(" +jTextFieldBookingID.getText()+ ", TO_DATE('"+jTextFieldCheckIN.getText()+"','DD/MM/YYYY'), TO_DATE('"+jTextFieldCheckOUT.getText()+"','DD/MM/YYYY'), "+jTextFieldPersons.getText()+", "+jTextFieldNumOfDays.getText()+", "+jComboBoxRoomID.getSelectedItem()+", "+jTextFieldClientsAFM.getText()+")";
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
                "     roomID="+jComboBoxRoomID.getSelectedItem()+","+
                "     clientAFM="+jTextFieldClientsAFM.getText()+""+
                " WHERE  bID="+jTextFieldBookingID.getText()+"";
        
        
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
        String query = "DELETE BOOKING WHERE bID="+jTextFieldBookingID.getText()+" ";
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
        String query = "SELECT bID, TO_CHAR(bCheckIN, 'DD/MM/YYYY'), TO_CHAR(bCheckOUT, 'DD/MM/YYYY'), bPersons, bNumOfDays, roomID, clientAFM"
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
                String clientsAFM = searchRS.getString("clientAFM");
                
                
                
                DefaultTableModel model = (DefaultTableModel)jTableBooking.getModel();
                model.addRow(
                    new Object [] {id,checkIN,checkOUT,Persons,numOfDays,roomID,clientsAFM}
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
        jTextFieldBookingID.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 0));
        jTextFieldCheckIN.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 1));
        jTextFieldCheckOUT.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 2));
        jTextFieldPersons.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 3));
        jTextFieldNumOfDays.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 4));
        //jComboBoxRoomID.getSelectedItem().equals(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 5));
        jTextFieldClientsAFM.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 6));
        
       
       
    }
   
    public void resetAction(){
        jTextFieldBookingID.setText("");
        jTextFieldCheckIN.setText("");
        jTextFieldCheckOUT.setText("");
        jTextFieldPersons.setText("");
        jTextFieldNumOfDays.setText("");
        jTextFieldClientsAFM.setText("");
       
        
        jComboBoxType.setSelectedIndex(0);
        jComboBoxBeds.setSelectedIndex(0);
                
        jTextFieldPrice.setText("");
        

        //when button reset clicked--> setText RoomID comboBox = ID         
        String item = "ID";
        jComboBoxRoomID.addItem(item);
        jComboBoxRoomID.setSelectedIndex(0);
        
        
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/YYYY");
        Calendar cal = Calendar.getInstance();
        jTextFieldCheckIN.setText(myFormat.format(cal.getTime()));
        
    }
    

    /**
     * 
     * Παίρνει όλους τους αριθμούς των δωματίων και τους τοποθετεί στο comboBox ανάλογα από ποιον τύπο
     * δωματίου ανήκουν.
     */
    public void roomDetails(){

        jComboBoxRoomID.removeAllItems();
        
      
        jComboBoxBeds.setSelectedIndex(0);
        jTextFieldPrice.setText("");
        
        
        try {
            String query = " SELECT * FROM ROOM "
                    + " WHERE rType = '" + (String) jComboBoxType.getSelectedItem() + "' "
                    + " ORDER BY rID";

            System.out.println("Query: \t" + query);

            Statement searchStatement;
            ResultSet rs;

            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            rs = searchStatement.executeQuery(query);

            while (rs.next()) {

                jComboBoxRoomID.addItem(rs.getString(1));

            }

        } catch (Exception e) {

        }

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
        
        if(jTextFieldBookingID.getText().length()>0) {
            jButtonDelete.setEnabled(true);
            if( (jTextFieldCheckIN.getText().length()>0)  &&
                (jTextFieldCheckOUT.getText().length()>0)  &&
                (jTextFieldPersons.getText().length()>0)  &&
                (jTextFieldNumOfDays.getText().length()>0)  &&
                (jTextFieldClientsAFM.getText().length()>0) )    
            {
                jButtonInsert.setEnabled(true);
                jButtonUpdate.setEnabled(true);
            }    
        }
            if( (jTextFieldCheckIN.getText().length()>0)  ||
                (jTextFieldCheckOUT.getText().length()>0)  ||
                (jTextFieldPersons.getText().length()>0)  ||
                (jTextFieldNumOfDays.getText().length()>0)  ||
                (jTextFieldClientsAFM.getText().length()>0) )    
            {
                jButtonReset.setEnabled(true);
            }    
    }
    
   
    
    /*Geters*/
    public String getID() {
        return this.jTextFieldBookingID.getText();
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
        return (String) this.jComboBoxRoomID.getSelectedItem();
    }
    
    public String getTType() {
        return (String) this.jComboBoxType.getSelectedItem();
    }
    
    public String getBeds() {
        return (String) this.jComboBoxBeds.getSelectedItem();
    }
    
    public String getClientsAFM() {
        return this.jTextFieldClientsAFM.getText();
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
        jTextFieldBookingID = new javax.swing.JTextField();
        jLabelCheckOUT = new javax.swing.JLabel();
        jLabelCheckIN = new javax.swing.JLabel();
        jLabelNumberOfDays = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBooking = new javax.swing.JTable();
        jButtonReset = new javax.swing.JButton();
        jTextFieldNumOfDays = new javax.swing.JTextField();
        jTextFieldCheckOUT = new javax.swing.JTextField();
        jButtonUpdate = new javax.swing.JButton();
        jLabelBookingID = new javax.swing.JLabel();
        jTextFieldCheckIN = new javax.swing.JTextField();
        jLabelBooking = new javax.swing.JLabel();
        jLabelPersons = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();
        jButtonPayment = new javax.swing.JButton();
        jLabelClientsAFM = new javax.swing.JLabel();
        jTextFieldClientsAFM = new javax.swing.JTextField();
        jComboBoxRoomID = new javax.swing.JComboBox<>();
        jComboBoxType = new javax.swing.JComboBox<>();
        jComboBoxBeds = new javax.swing.JComboBox<>();
        jLabelPrice = new javax.swing.JLabel();
        jLabelBeds = new javax.swing.JLabel();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabelType = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Booking Form");
        setBounds(new java.awt.Rectangle(500, 200, 200, 200));
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

        jTextFieldBookingID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldBookingIDCaretUpdate(evt);
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
                "ID", "Check In", "Check Out", "Persons", "Number Of Days", "RoomID", "Client's AFM"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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

        jLabelBookingID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBookingID.setText("Booking ID");

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

        jButtonPayment.setText("PAYMENT");
        jButtonPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPaymentActionPerformed(evt);
            }
        });

        jLabelClientsAFM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelClientsAFM.setText("Client's AFM");

        jComboBoxRoomID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID" }));
        jComboBoxRoomID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRoomIDActionPerformed(evt);
            }
        });

        jComboBoxType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "type", "Monoklino", "Diklino", "Triklino" }));
        jComboBoxType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTypeActionPerformed(evt);
            }
        });

        jComboBoxBeds.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "beds", "1", "2", "3" }));

        jLabelPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPrice.setText("Price");

        jLabelBeds.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBeds.setText("Beds");

        jTextFieldPrice.setEditable(false);

        jLabelType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelType.setText("Type");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(361, 361, 361)
                .addComponent(jLabelBooking)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelNumberOfDays)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(jTextFieldNumOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelCheckOUT)
                                    .addComponent(jLabelCheckIN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldCheckIN, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                    .addComponent(jTextFieldCheckOUT)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelBookingID)
                                .addGap(50, 50, 50)
                                .addComponent(jTextFieldBookingID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelPersons)
                                .addGap(67, 67, 67)
                                .addComponent(jTextFieldPersons, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelType)
                                        .addGap(18, 18, 18)
                                        .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabelBeds))
                                    .addComponent(jLabelRoomID)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(115, 115, 115)
                                        .addComponent(jComboBoxRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelClientsAFM)
                                    .addComponent(jComboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 457, Short.MAX_VALUE)
                                .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldClientsAFM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabelPrice)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 408, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonInsert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabelBooking)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonInsert)
                    .addComponent(jLabelType)
                    .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelBeds)
                    .addComponent(jComboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPrice)
                    .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDelete)
                        .addGap(99, 99, 99)
                        .addComponent(jButtonPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRoomID)
                            .addComponent(jComboBoxRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelClientsAFM)
                            .addComponent(jTextFieldClientsAFM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBookingID)
                            .addComponent(jTextFieldBookingID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonReset)
                                .addGap(26, 26, 26))
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
                                .addGap(104, 104, 104)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldPersonsCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldPersonsCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldPersonsCaretUpdate

    private void jTextFieldBookingIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldBookingIDCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldBookingIDCaretUpdate

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

    private void jButtonPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPaymentActionPerformed
           
    }//GEN-LAST:event_jButtonPaymentActionPerformed

    private void jComboBoxTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTypeActionPerformed
        roomDetails();
    }//GEN-LAST:event_jComboBoxTypeActionPerformed

    private void jComboBoxRoomIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRoomIDActionPerformed
        
        try{
            String query = " SELECT * FROM ROOM WHERE rID= "+(String)jComboBoxRoomID.getSelectedItem()+" ";
               
        System.out.println("Query: \t" + query);
        
        Statement searchStatement;
        ResultSet searchRS;
        
            searchStatement = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                             java.sql.ResultSet.CONCUR_READ_ONLY);
            searchRS = searchStatement.executeQuery(query);
            if(searchRS.next()){
               
                jTextFieldPrice.setText(searchRS.getString("rPrice"));
                jComboBoxBeds.setSelectedItem(searchRS.getString("rBeds"));
                
            }
        }
        catch(Exception e){
            
        }
    }//GEN-LAST:event_jComboBoxRoomIDActionPerformed

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
    private javax.swing.JButton jButtonPayment;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox<String> jComboBoxBeds;
    private javax.swing.JComboBox<String> jComboBoxRoomID;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabelBeds;
    private javax.swing.JLabel jLabelBooking;
    private javax.swing.JLabel jLabelBookingID;
    private javax.swing.JLabel jLabelCheckIN;
    private javax.swing.JLabel jLabelCheckOUT;
    private javax.swing.JLabel jLabelClientsAFM;
    private javax.swing.JLabel jLabelNumberOfDays;
    private javax.swing.JLabel jLabelPersons;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelRoomID;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBooking;
    private javax.swing.JTextField jTextFieldBookingID;
    private javax.swing.JTextField jTextFieldCheckIN;
    private javax.swing.JTextField jTextFieldCheckOUT;
    private javax.swing.JTextField jTextFieldClientsAFM;
    private javax.swing.JTextField jTextFieldNumOfDays;
    private javax.swing.JTextField jTextFieldPersons;
    private javax.swing.JTextField jTextFieldPrice;
    // End of variables declaration//GEN-END:variables
}
