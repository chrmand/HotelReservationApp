/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationapp;

import java.sql.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Χρήστος
 */
public class InsertUpdateDelete {
    public static void setData(String Query, String msg){
        Connection con = null;
        Statement st = null;
        try{
            
            con = MainMenu.getCon();
            st = con.createStatement();
            st.executeUpdate(Query);
            if(!msg.equals(""))
                JOptionPane.showMessageDialog(null, msg);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            try{}
            catch(Exception e){}
        }
    }
}
