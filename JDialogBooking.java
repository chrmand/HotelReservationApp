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
        
        autoID();
        userChoosedOkFlag=false;
        
         //Εμφανίζει το JDialogBooking στη μέση της οθονης   
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);

    }
   
    
    
    /**
     * Delete Action Button
     */
    public void deleteBookingAction() {
        
        String query = "DELETE BOOKING WHERE bID="+jTextFieldBookingID.getText()+" ";
        System.out.println("Query: \t" + query);
 
        String Query = "UPDATE ROOM SET rStatus= 'Not Booked' WHERE rID= "+jComboBoxRoomID.getSelectedItem()+" ";
        InsertUpdateDelete.setData(Query, "");
        
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
        String query = "SELECT bID, clientAFM, bFirstname, bLastname, bPhone, TO_CHAR(bCheckIN, 'DD/MM/YYYY'), bPersons, roomID, bRoomType, bRoomBeds, bPrice"
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
                String clientsAFM = searchRS.getString("clientAFM");
                String firstname = searchRS.getString("bFirstname");
                String lastname = searchRS.getString("bLastname");
                String phone = searchRS.getString("bPhone");
                String checkIN = searchRS.getString(6);
                String Persons = searchRS.getString("bPersons");
                String roomID = searchRS.getString("roomID");
                String roomType= searchRS.getString("bRoomType");
                String roomBeds = searchRS.getString("bRoomBeds");
                String price = searchRS.getString("bPrice");
              
                
                
                
                
                DefaultTableModel model = (DefaultTableModel)jTableBooking.getModel();
                model.addRow(
                    new Object [] {id,clientsAFM,firstname,lastname,phone,checkIN,Persons,roomID,roomType,roomBeds,price}
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
    
    public void comboBoxSelectGetValues(){
       DefaultTableModel model = (DefaultTableModel) jTableBooking.getModel();
       
       //jComboBoxBeds
       String comboBedsSelect = model.getValueAt(jTableBooking.getSelectedRow(), 9).toString();
       for (int i = 0; i < jComboBoxBeds.getItemCount(); i++) {
           if (jComboBoxBeds.getItemAt(i).equalsIgnoreCase(comboBedsSelect)) {
               jComboBoxBeds.setSelectedIndex(i);
           }
       }
      
   }
    
    
    public void bookingTableMouseClickedAction(){
        jTextFieldBookingID.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 0));
        jTextFieldClientAFM.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 1));
        jTextFieldFirstname.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 2));
        jTextFieldLastname.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 3));
        jTextFieldPhone.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 4));
        jTextFieldCheckIN.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 5));
        jTextFieldPersons.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 6));
        //jComboBoxRoomID.getSelectedItem() = 7
        //jComboBoxType.getSelectedItem() = 8
        jTextFieldPrice.setText(""+jTableBooking.getModel().getValueAt(jTableBooking.getSelectedRow(), 10));  
  
        comboBoxSelectGetValues();
        
    }
   
    public void resetAction(){
        jTextFieldBookingID.setText("");
        jTextFieldCheckIN.setText("");
        jTextFieldPersons.setText("");
        jTextFieldClientAFM.setText("");
       
        jTextFieldFirstname.setText("");
        jTextFieldLastname.setText("");
        jTextFieldPhone.setText("");
        
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
                    + " WHERE rType = '" + (String) jComboBoxType.getSelectedItem() + "' and "
                    + " rStatus= 'Not Booked' " 
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
    
    
    public void checkIn() {
        //int bID = 1;
        String Query = ("SELECT max(bID) FROM BOOKING");
        String price = jTextFieldPrice.getText();
        
        try {

            ResultSet rs = Select.getData(Query);
            while (rs.next()){ 
             // bID = rs.getInt(1);
           // bID = bID + 1;
               
            
                if (!price.equals("")) {
                    Query = "UPDATE ROOM SET rStatus= 'Booked' WHERE rID=" + (String) jComboBoxRoomID.getSelectedItem() + " ";
                    InsertUpdateDelete.setData(Query, "");
                    Query = "INSERT INTO BOOKING(bID, clientAFM, bFirstname, bLastname, bPhone, bCheckIN, bPersons, roomID, bRoomType, bRoomBeds, bPrice) VALUES('"+jTextFieldBookingID.getText()+"', "+jTextFieldClientAFM.getText()+", '"+jTextFieldFirstname.getText()+"', '"+jTextFieldLastname.getText()+"', "+jTextFieldPhone.getText()+",  TO_DATE('"+jTextFieldCheckIN.getText()+"','DD/MM/YYYY'), "+jTextFieldPersons.getText()+", "+(String)jComboBoxRoomID.getSelectedItem()+", '"+(String)jComboBoxType.getSelectedItem()+"', "+(String)jComboBoxBeds.getSelectedItem()+", "+jTextFieldPrice.getText()+") ";
                    InsertUpdateDelete.setData(Query, "Client \n" + jTextFieldClientAFM.getText() + "\n has CHECK IN successfully..!");                  
                    
                    
                    setVisible(false);
                    JDialogBooking conBooking = new JDialogBooking(null, true);
                    conBooking.setVisible(true);
                }
 
                else{
                JOptionPane.showMessageDialog(this, "Insert Booking Failed");
                }
            }
            rs.close();
        } catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
            
        }

    }

    
    public void autoID(){
        
        try{
            Statement st;
            st = MainMenu.con.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                    java.sql.ResultSet.CONCUR_READ_ONLY);
            
            ResultSet rs = st.executeQuery("SELECT MAX(bID) FROM BOOKING");
            rs.next();
            rs.getString("MAX(bID)");
            
            if(rs.getString("MAX(bID)") == null){
                jTextFieldBookingID.setText("1");
            }

            rs.close();
            
        }catch (SQLException ex) {
            showMessageDialog(this, ex.getMessage()); 
        }
    }
    
/**
* 
* Enabled or Disabled buttons, checks if field are empty from textFields values.
*/
    public void okEnableCheckAction(){
        jButtonInsert.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jButtonReset.setEnabled(false);
        
        if(jTextFieldBookingID.getText().length()>0) {
            jButtonDelete.setEnabled(true);
            if( (jTextFieldCheckIN.getText().length()>0)  &&
                (jTextFieldPersons.getText().length()>0)  &&
                (jTextFieldClientAFM.getText().length()>0)  &&
                (jTextFieldFirstname.getText().length()>0)  &&
                (jTextFieldLastname.getText().length()>0)  &&
                (jTextFieldPhone.getText().length()>0) )    
            {
                jButtonInsert.setEnabled(true);
            }    
        }
            if( (jTextFieldCheckIN.getText().length()>0)   ||
                (jTextFieldPersons.getText().length()>0)  ||
                (jTextFieldClientAFM.getText().length()>0)  ||
                (jTextFieldFirstname.getText().length()>0)  ||
                (jTextFieldLastname.getText().length()>0)  ||
                (jTextFieldPhone.getText().length()>0) )    
            {
                jButtonReset.setEnabled(true);
            }    
    }
    
   
    
    /*Geters*/
    public String getID() {
        return this.jTextFieldBookingID.getText();
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
    
    
    public String getCheckIN() {
        return this.jTextFieldCheckIN.getText();
    }
    
    public String getPersons() {
        return this.jTextFieldPersons.getText();
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
        return this.jTextFieldClientAFM.getText();
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
        jLabelCheckIN = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBooking = new javax.swing.JTable();
        jButtonReset = new javax.swing.JButton();
        jLabelBookingID = new javax.swing.JLabel();
        jTextFieldCheckIN = new javax.swing.JTextField();
        jLabelBooking = new javax.swing.JLabel();
        jLabelPersons = new javax.swing.JLabel();
        jButtonDelete = new javax.swing.JButton();
        jButtonInsert = new javax.swing.JButton();
        jButtonPayment = new javax.swing.JButton();
        jLabelClientsAFM = new javax.swing.JLabel();
        jTextFieldClientAFM = new javax.swing.JTextField();
        jComboBoxRoomID = new javax.swing.JComboBox<>();
        jComboBoxType = new javax.swing.JComboBox<>();
        jComboBoxBeds = new javax.swing.JComboBox<>();
        jLabelPrice = new javax.swing.JLabel();
        jLabelBeds = new javax.swing.JLabel();
        jTextFieldPrice = new javax.swing.JTextField();
        jLabelType = new javax.swing.JLabel();
        jTextFieldFirstname = new javax.swing.JTextField();
        jTextFieldLastname = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

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

        jLabelCheckIN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCheckIN.setText("Check In");

        jTableBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Booking ID", "AFM", "Firstname", "Lastname", "Phone", "Check In", "Persons", "RoomID", "Room Type", "Room Beds", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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

        jButtonReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-reset.png"))); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
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

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-delete.png"))); // NOI18N
        jButtonDelete.setText("Delete");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-save.png"))); // NOI18N
        jButtonInsert.setText("Save");
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

        jButtonPayment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-payment-check-out-.png"))); // NOI18N
        jButtonPayment.setText("Check OUT");
        jButtonPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPaymentActionPerformed(evt);
            }
        });

        jLabelClientsAFM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelClientsAFM.setText("Client AFM");

        jTextFieldClientAFM.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldClientAFMCaretUpdate(evt);
            }
        });

        jComboBoxRoomID.setEditable(true);
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
        jLabelBeds.setText("Room Beds");

        jTextFieldPrice.setEditable(false);

        jLabelType.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelType.setText("Room Type");

        jTextFieldFirstname.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldFirstnameCaretUpdate(evt);
            }
        });

        jTextFieldLastname.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldLastnameCaretUpdate(evt);
            }
        });

        jTextFieldPhone.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldPhoneCaretUpdate(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Client Firstname");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Client Lastname");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Client Phone");

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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelBookingID)
                                .addGap(50, 50, 50)
                                .addComponent(jTextFieldBookingID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(jTextFieldCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelPersons)
                                .addGap(67, 67, 67)
                                .addComponent(jTextFieldPersons, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(131, 131, 131)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabelClientsAFM))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldClientAFM, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldFirstname)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelType)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabelBeds)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabelPrice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelRoomID)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jComboBoxRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonInsert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonPayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabelBooking)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelType)
                            .addComponent(jComboBoxType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelBeds)
                            .addComponent(jComboBoxBeds, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPrice)
                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRoomID)
                            .addComponent(jComboBoxRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonPayment)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelBookingID)
                                    .addComponent(jTextFieldBookingID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelCheckIN)
                                    .addComponent(jTextFieldCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelPersons)
                                    .addComponent(jTextFieldPersons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addComponent(jButtonReset)
                        .addGap(56, 56, 56)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelClientsAFM)
                            .addComponent(jTextFieldClientAFM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19))
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
        //insertBookingAction();
        loadBooking();
        okEnableCheckAction();
        checkIn();
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadBooking();
        bookingTableMouseClickedAction();
    }//GEN-LAST:event_formComponentShown

    private void jButtonPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPaymentActionPerformed
        JDialogPayment conPayment = new JDialogPayment(null, true);
        setVisible(false);
        conPayment.setVisible(true);
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

    private void jTextFieldClientAFMCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldClientAFMCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldClientAFMCaretUpdate

    private void jTextFieldFirstnameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldFirstnameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldFirstnameCaretUpdate

    private void jTextFieldLastnameCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldLastnameCaretUpdate
        okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldLastnameCaretUpdate

    private void jTextFieldPhoneCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldPhoneCaretUpdate
       okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldPhoneCaretUpdate

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
    private javax.swing.JComboBox<String> jComboBoxBeds;
    private javax.swing.JComboBox<String> jComboBoxRoomID;
    private javax.swing.JComboBox<String> jComboBoxType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelBeds;
    private javax.swing.JLabel jLabelBooking;
    private javax.swing.JLabel jLabelBookingID;
    private javax.swing.JLabel jLabelCheckIN;
    private javax.swing.JLabel jLabelClientsAFM;
    private javax.swing.JLabel jLabelPersons;
    private javax.swing.JLabel jLabelPrice;
    private javax.swing.JLabel jLabelRoomID;
    private javax.swing.JLabel jLabelType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBooking;
    private javax.swing.JTextField jTextFieldBookingID;
    private javax.swing.JTextField jTextFieldCheckIN;
    private javax.swing.JTextField jTextFieldClientAFM;
    private javax.swing.JTextField jTextFieldFirstname;
    private javax.swing.JTextField jTextFieldLastname;
    private javax.swing.JTextField jTextFieldPersons;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldPrice;
    // End of variables declaration//GEN-END:variables
}
