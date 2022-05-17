/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;

/**
 *
 * @author Χρήστος
 */
public class JDialogPayment extends javax.swing.JDialog {
    
    private boolean userChoosedOkFlag;

    /**
     * Creates new form JDialogPayment
     */
    public JDialogPayment(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        paintColorBackground();
                
        userChoosedOkFlag=false;
        
        jTextFieldBookingID.setEditable(false);
        
        jTextFieldFirstname.setEditable(false);
        jTextFieldLastname.setEditable(false);
        jTextFieldPhone.setEditable(false);
        
        jTextFieldAFM.setEditable(false);
        
        jTextFieldCheckIN.setEditable(false);
        jTextFieldCheckOUT.setEditable(true);
        
       
        jTextFieldNumOfDays.setEditable(false);
        
        jTextFieldPrice.setEditable(false);
        jTextFieldTotalAmount.setEditable(false);

        jButtonReset.setVisible(false);
        
         //Εμφανίζει το JDialogPayment στη μέση της οθονης   
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/YYYY");
        Calendar cal = Calendar.getInstance();
        jTextFieldCheckOUT.setText(myFormat.format(cal.getTime()));
        
    }
    
    public void paintColorBackground() {
        Color col = new Color(148, 222, 222);
        getContentPane().setBackground(col);
    }
    
    //int id=0;
    String Query;
    String rType;
    String rBeds;
    String roomID;
    String bID;
    
    
    
    /**
     * Delete Action when check out is done! 
     */
    public void deletePaymentAction() {
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
    public void loadPayment() {
        clearTable( (DefaultTableModel)jTablePayment.getModel() );
        String query = "SELECT bID, clientAFM, bFirstname, bLastname, bPhone, TO_CHAR(bCheckIN, 'dd/MM/YYYY'), bPersons, roomID, bRoomType, bRoomBeds, bPrice"
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
                String bID = searchRS.getString("bID");
                String clientsAFM = searchRS.getString("clientAFM");
                String firstname = searchRS.getString("bFirstname");
                String lastname = searchRS.getString("bLastname");
                String phone = searchRS.getString("bPhone");
                String checkIN = searchRS.getString(6);
                String Persons = searchRS.getString("bPersons");
                String rID = searchRS.getString("roomID");
                String roomType= searchRS.getString("bRoomType");
                String roomBeds = searchRS.getString("bRoomBeds");
                String price = searchRS.getString("bPrice");
                
                
                
                
                DefaultTableModel model = (DefaultTableModel)jTablePayment.getModel();
                model.addRow(
                    new Object [] {bID,clientsAFM,firstname,lastname,phone,checkIN,Persons,rID,roomType,roomBeds,price}
                );
                
            }
            
            if (searchRS.first()) {
                jTablePayment.changeSelection(0, 0, false, false);
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
    
   
    public void resetAction(){
        jTextFieldBookingID.setText("");
        jTextFieldFirstname.setText("");
        jTextFieldLastname.setText("");
        jTextFieldPhone.setText("");
        jTextFieldAFM.setText("");
        jTextFieldCheckIN.setText("");
        jTextFieldCheckOUT.setText("");
        jTextFieldNumOfDays.setText("");
        jTextFieldRoomID.setText("");
        jTextFieldPrice.setText("");
        jTextFieldTotalAmount.setText("");
       
        jTextFieldRoomID.setEditable(true);
        jButtonCheckOUT.setEnabled(false);

        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/YYYY");
        Calendar cal = Calendar.getInstance();
        jTextFieldCheckOUT.setText(myFormat.format(cal.getTime()));
        
    }
    

    
     /*Geters*/
    public String getBookingID() {
        return this.jTextFieldBookingID.getText();
    }
    
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
    
    public String getCheckIN() {
        return this.jTextFieldCheckIN.getText();
    }
    
    public String getCheckOUT() {
        return this.jTextFieldCheckOUT.getText();
    }
    
    public String getPrice() {
       return this.jTextFieldPrice.getText();
    }
    
    public String getTotalAmount() {
       return this.jTextFieldTotalAmount.getText();
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

        jButtonReset = new javax.swing.JButton();
        jTextFieldNumOfDays = new javax.swing.JTextField();
        jTextFieldCheckOUT = new javax.swing.JTextField();
        jLabelBookingID = new javax.swing.JLabel();
        jTextFieldCheckIN = new javax.swing.JTextField();
        jLabelBooking = new javax.swing.JLabel();
        jTextFieldBookingID = new javax.swing.JTextField();
        jLabelCheckOUT = new javax.swing.JLabel();
        jLabelCheckIN = new javax.swing.JLabel();
        jLabelNumberOfDays = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldRoomID = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jTextFieldFirstname = new javax.swing.JTextField();
        jTextFieldLastname = new javax.swing.JTextField();
        jTextFieldPrice = new javax.swing.JTextField();
        jTextFieldTotalAmount = new javax.swing.JTextField();
        jTextFieldPhone = new javax.swing.JTextField();
        jTextFieldAFM = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePayment = new javax.swing.JTable();
        jButtonCheckOUT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Check OUT and Payment Form");
        setBounds(new java.awt.Rectangle(200, 200, 200, 200));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jButtonReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-reset.png"))); // NOI18N
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

        jLabelBookingID.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBookingID.setText("Booking ID");

        jTextFieldCheckIN.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldCheckINCaretUpdate(evt);
            }
        });

        jLabelBooking.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabelBooking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-payment-check-out-.png"))); // NOI18N
        jLabelBooking.setText("Check Out and Payment");

        jTextFieldBookingID.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextFieldBookingIDCaretUpdate(evt);
            }
        });

        jLabelCheckOUT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCheckOUT.setText("Check Out (Today)");

        jLabelCheckIN.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelCheckIN.setText("Check In");

        jLabelNumberOfDays.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelNumberOfDays.setText("Number Of Days");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Client Firstname");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Client Lastname");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Client Phone");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Price Per Day");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Total Amount");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Room Number");

        jButtonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-search.png"))); // NOI18N
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Client AFM");

        jTablePayment.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTablePayment);

        jButtonCheckOUT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons-payment-check-out-.png"))); // NOI18N
        jButtonCheckOUT.setText("CHECK OUT");
        jButtonCheckOUT.setEnabled(false);
        jButtonCheckOUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCheckOUTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jLabelNumberOfDays)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldNumOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldTotalAmount))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAFM, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldLastname, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jTextFieldPhone)))
                    .addComponent(jLabelBookingID)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(jTextFieldBookingID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSearch)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelCheckOUT)
                            .addComponent(jLabelCheckIN, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldCheckIN)
                            .addComponent(jTextFieldCheckOUT, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(354, 354, 354))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonCheckOUT)
                        .addGap(313, 313, 313)))
                .addComponent(jButtonReset)
                .addGap(35, 35, 35))
            .addGroup(layout.createSequentialGroup()
                .addGap(556, 556, 556)
                .addComponent(jLabelBooking)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabelBooking, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldRoomID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCheckIN)
                            .addComponent(jTextFieldCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCheckOUT)
                            .addComponent(jTextFieldCheckOUT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelBookingID)
                            .addComponent(jTextFieldBookingID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldAFM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldFirstname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldLastname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNumberOfDays)
                            .addComponent(jTextFieldNumOfDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonReset)
                            .addComponent(jButtonCheckOUT, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        resetAction();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jTextFieldNumOfDaysCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldNumOfDaysCaretUpdate
        //okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldNumOfDaysCaretUpdate

    private void jTextFieldCheckOUTCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCheckOUTCaretUpdate
        //okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCheckOUTCaretUpdate

    private void jTextFieldCheckINCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldCheckINCaretUpdate
        //okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldCheckINCaretUpdate

    private void jTextFieldBookingIDCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextFieldBookingIDCaretUpdate
       // okEnableCheckAction();
    }//GEN-LAST:event_jTextFieldBookingIDCaretUpdate

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        loadPayment();
    }//GEN-LAST:event_formComponentShown

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
       try{
            ResultSet rs = Select.getData("SELECT bID, clientAFM, bFirstname, bLastname, bPhone, TO_CHAR(bCheckIN, 'DD/MM/YYYY'), bPersons, roomID, bRoomType, bRoomBeds, bPrice, bNumOfDays, bTotalAmount, TO_CHAR(bCheckOUT, 'DD/MM/YYYY') FROM BOOKING WHERE roomID="+jTextFieldRoomID.getText()+" and bCheckOUT is NULL");
            if(rs.next()){
                jTextFieldRoomID.setEditable(false);
                jButtonReset.setVisible(true);
                jButtonCheckOUT.setEnabled(true);
               // id=rs.getInt(1);
                jTextFieldBookingID.setText(rs.getString(1));
                jTextFieldAFM.setText(rs.getString(2));
                jTextFieldFirstname.setText(rs.getString(3));
                jTextFieldLastname.setText(rs.getString(4));
                jTextFieldPhone.setText(rs.getString(5));
                jTextFieldCheckIN.setText(rs.getString(6));
                jTextFieldPrice.setText(rs.getString(11));
                jTextFieldNumOfDays.setText(rs.getString(12));
                
                
                jTextFieldCheckOUT.setEditable(true);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     
                String date_in = rs.getString(6);
                java.util.Date din = dateFormat.parse(date_in);
                din.toString();
                
                String date_out = jTextFieldCheckOUT.getText();
                java.util.Date dout = dateFormat.parse(date_out);
                dout.toString();

                long difference = dout.getTime() - din.getTime();
                int numberOfDays = (int) (difference/(1000*60*60*24));
                if(numberOfDays==0)
                    numberOfDays=1;
                jTextFieldNumOfDays.setText(String.valueOf(numberOfDays));

                float price = Float.parseFloat(jTextFieldPrice.getText());
                jTextFieldTotalAmount.setText(String.valueOf(numberOfDays*price));

                rType = rs.getString(9);
                rBeds = rs.getString(10);
                
                if(numberOfDays < 0){
                    jTextFieldNumOfDays.setText("");
                    jTextFieldTotalAmount.setText("");
                    JOptionPane.showMessageDialog(rootPane, "Check OUT date must be equals or after from Check IN date!","Check OUT incorrect!",JOptionPane.ERROR_MESSAGE);
                    resetAction();
                }
              
            }
            else
                JOptionPane.showMessageDialog(rootPane, "Room Number is Not Booked or Room Number does Not Exist","Room Number ERROR",JOptionPane.ERROR_MESSAGE);
        
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null, e);
        }
       
    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonCheckOUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCheckOUTActionPerformed

         bID = jTextFieldBookingID.getText();
         String AFM = jTextFieldAFM.getText();
         String firstname = jTextFieldFirstname.getText();
         String lastname =  jTextFieldLastname.getText();
         String phone = jTextFieldPhone.getText();
         
         String totalAmount = jTextFieldTotalAmount.getText();
         
         roomID = jTextFieldRoomID.getText();
         Query = " UPDATE BOOKING SET bNumOfDays="+jTextFieldNumOfDays.getText()+", bTotalAmount="+jTextFieldTotalAmount.getText()+", bCheckOUT= TO_DATE('"+jTextFieldCheckOUT.getText()+"','DD/MM/YYYY') WHERE bID='"+jTextFieldBookingID.getText()+"' ";
         InsertUpdateDelete.setData(Query, "");
         Query = " UPDATE ROOM SET rStatus='Not Booked' WHERE rID="+jTextFieldRoomID.getText()+" ";
         InsertUpdateDelete.setData(Query, "");
         
         String path="D:\\HotelReservationApp\\Payment for BookingID_";
         com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
         try
         {
             PdfWriter.getInstance(doc, new FileOutputStream(path+""+bID+" and RoomID_"+roomID+".pdf"));
             doc.open();
             Paragraph paragraphTitle = new Paragraph("                                                      HOTEL RESEVATION APP\n");
             doc.add(paragraphTitle);
             Paragraph paragraphStars = new Paragraph("****************************************************************************************************************");
             doc.add(paragraphStars);
             Paragraph paragraph3 = new Paragraph("\tBooking ID: "+bID+"\n~Client Details~\nAFM: "+AFM+"\nFirstname: "+firstname+"\nLastname: "+lastname+"\nPhone: "+phone+"");
             doc.add(paragraph3);
             doc.add(paragraphStars);
             Paragraph paragraph4 = new Paragraph("\t~Room Details~\nRoom Number: "+roomID+"\nRoom Type: "+rType+"\nBeds: "+rBeds+"\nPrice Per Day: "+jTextFieldPrice.getText()+"");
             doc.add(paragraph4);
             doc.add(paragraphStars);
             PdfPTable table1 = new  PdfPTable(4);
             table1.addCell("Check IN Date: "+jTextFieldCheckIN.getText());
             table1.addCell("Check OUT Date: "+jTextFieldCheckOUT.getText());
             table1.addCell("Number of Days Stay: "+jTextFieldNumOfDays.getText());
             table1.addCell("Total Amount Paid: "+totalAmount);
             doc.add(table1);
             doc.add(paragraphStars);
             Paragraph paragraphThanks = new Paragraph("                  Thank you for your preference, it would be a pleasure for us to come back!");
             doc.add(paragraphThanks);

             
         }
         catch(Exception e)
         {
             JOptionPane.showMessageDialog(null, e);
         }
         doc.close();
         int a=JOptionPane.showConfirmDialog(null, "Do you want to print Bill?","Select", JOptionPane.YES_NO_OPTION);
         if(a==0)
         {
            try
            {
                 if((new File("D:\\"+bID+roomID+".pdf")).exists())
                 {
                     Process p = Runtime
                             .getRuntime()
                             .exec("rundll32 url.dll, FileProtocolHandler D:\\"+bID+roomID+".pdf");
                 }
                 else
                     System.out.println("File is not Exists!");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e);
            }
            
         }
         
        deletePaymentAction();
        setVisible(false);     
        JDialogPayment conPayment = new JDialogPayment(null, true);
        conPayment.setVisible(true);
        
    }//GEN-LAST:event_jButtonCheckOUTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
 
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogPayment dialog = new JDialogPayment(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonCheckOUT;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelBooking;
    private javax.swing.JLabel jLabelBookingID;
    private javax.swing.JLabel jLabelCheckIN;
    private javax.swing.JLabel jLabelCheckOUT;
    private javax.swing.JLabel jLabelNumberOfDays;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTablePayment;
    private javax.swing.JTextField jTextFieldAFM;
    private javax.swing.JTextField jTextFieldBookingID;
    private javax.swing.JTextField jTextFieldCheckIN;
    private javax.swing.JTextField jTextFieldCheckOUT;
    private javax.swing.JTextField jTextFieldFirstname;
    private javax.swing.JTextField jTextFieldLastname;
    private javax.swing.JTextField jTextFieldNumOfDays;
    private javax.swing.JTextField jTextFieldPhone;
    private javax.swing.JTextField jTextFieldPrice;
    private javax.swing.JTextField jTextFieldRoomID;
    private javax.swing.JTextField jTextFieldTotalAmount;
    // End of variables declaration//GEN-END:variables
}
