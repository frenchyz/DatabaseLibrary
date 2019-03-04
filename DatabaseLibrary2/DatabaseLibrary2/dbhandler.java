/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselibrary2;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xavier Cadot
 * 
 * This file is my connector handler
 * Anything that needs to connect to the mysql goes through this file
 */
public class dbhandler {
    Connection conn = null;
    //constructor for handler
    public dbhandler(){
        
        String dir= null;
         try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            dir= "jdbc:mysql://localhost:3306/utd_library"; //include Library when db created
            conn= DriverManager.getConnection(dir,"root", "Manbearpig24?");
            
            
        }
        catch(Exception e){
           System.err.println("doesnt exist");
           System.err.println(e.getMessage());
           
           //readinBooks();
        }
        
    }
    //most use query for simple use
    public void callQuerry(String q){
        //List<String> temp=new ArrayList<String>();
        try{
        Statement stmt=conn.createStatement();
        stmt.executeUpdate(q);
        System.out.println("printed: "+q);
        stmt.close();
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    //check if an ssn exist and returns a boolean
    public boolean checkSsn(String q){
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(q);
        System.out.println("printed: "+q);
        while(rs.next()){
            return true;
        }
        stmt.close();
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
            }
        return false;
    }
    //checks if the card id exist and returns a boolean
    public boolean checkcardid(String q){
        //List<String> temp=new ArrayList<String>();
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(q);
        System.out.println("printed: "+q);
        while(rs.next()){
            return true;
        }
        stmt.close();
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
               return false;
            }
        return false;
    }
    //update paid digit to 1
    public void updatepaid(String q){
        //List<String> temp=new ArrayList<String>();
        String qupdate="";
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(q);
        System.out.println("printed: "+q);
        while(rs.next()){
            qupdate=rs.getString("loan_id");
            callQuerry("update fines set paid = 1 where loan_id="+qupdate+";");
        }
        stmt.close();
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
            }
    }
    //read from the fines table
    public List<String> readfines(String q){
        List<String> temp=new ArrayList<String>();
        String loan_id="";
        double fine=0;
        String coFine;
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(q);
        System.out.println("printed: "+q);
        while(rs.next()){    
           loan_id=rs.getString(1);
            fine=rs.getDouble(2);
            coFine=""+fine;
            temp.add(loan_id);
            temp.add(coFine);
            System.out.println(loan_id);
            System.out.println(fine);
        }
        stmt.close();
        return temp;
        
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
            }
        return null;
    }
    //create a card id
    public String generatecard_Id(){
        //List<String> temp=new ArrayList<String>();
        String card_id="";
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select card_id From Borrower");
        //ResultSet rs=stmt.executeQuery("Select card_id From Borrower ORDER BY card_id DESC");
        System.out.println("printed: "+rs);
        while(rs.next()){
            card_id=rs.getString("card_id");
            //System.out.println(card_id);
        }
        stmt.close();
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
            }
        return card_id;
    }
    //check if card id exist
    public boolean validateecard(String checkcard){
        //List<String> temp=new ArrayList<String>();
        String card_id="";
        boolean results=false;
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select card_id From Borrower where card_id=\""+checkcard+"\";");
        //ResultSet rs=stmt.executeQuery("Select card_id From Borrower ORDER BY card_id DESC");
        System.out.println("printed: "+rs);
        results=rs.first();
        stmt.close();
        return results;
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        
    }
    //checks card amount
    public boolean cardCount(String q){
        //List<String> temp=new ArrayList<String>();
        System.out.println("here");
        String temp="";
        int count;
        boolean results=false;
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery(q);
        //ResultSet rs=stmt.executeQuery("Select card_id From Borrower ORDER BY card_id DESC");
        System.out.println("printed: "+rs);
        while(rs.next()){
            temp=rs.getString("total");
            //System.out.println(card_id);
        }
        System.out.println(temp);
        count=Integer.parseInt(temp);
        stmt.close();
        if(count>=3){
            return false;
        }
        return true;
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        
    }
    //check for loan id and return boolean
     public boolean checkloanId(String loanIdExist){
        String temp="";
        int count;
        boolean results=false;
        try{
        Statement stmt=conn.createStatement();
        ResultSet rs=stmt.executeQuery("Select Loan_id from fines where Loan_id= \""+loanIdExist+"\";");
        System.out.println("printed: "+rs);
        while(rs.next()){
            temp=rs.getString("loan_id");
            //System.out.println(card_id);
        }
        System.out.println(temp);
        stmt.close();
        if(temp.isEmpty()){
            return false;
        }
        return true;
        }
        
        catch(Exception e)
            {
                e.printStackTrace();
                return false;
            }
        
    }
     //creates table with search criteria 
     //call sub routine
    public int searchBooksProc (JTable table, String query) {
        try {
            ((DefaultTableModel)table.getModel()).setRowCount(0);
            CallableStatement cStmt = conn.prepareCall("{CALL SEARCHLIBRARY(?)}");
            cStmt.setString(1, query);
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
            }
            
            rs.close();
            //calls function to see if books are available
            CallableStatement cFunc = conn.prepareCall("{?= CALL isAvailable (?)}");
            cFunc.registerOutParameter(1, Types.INTEGER);
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            int rowCount = m.getRowCount();
            // LOOP THRU THE ISBNS
            for (int count=0; count < rowCount; count++){
                String isbn = (String) m.getValueAt(count, 0);

                cFunc.setString("isbn", isbn);
                cFunc.execute();
                String available = cFunc.getInt(1) == 0 ? "" : "OUT";
                m.setValueAt(available, count, 3);
            }
            return table.getModel().getRowCount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        
    }
    //Search for people who have books out
    public void searchBorrower (JTable table, String query) {
        try {
            ((DefaultTableModel)table.getModel()).setRowCount(0);
            CallableStatement cStmt = conn.prepareCall("{CALL SearchBorrower(?)}");
            cStmt.setString(1, query);
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
            }
            
            rs.close();
            //return table.getModel().getRowCount();
        } catch (Exception e) {
            e.printStackTrace();
            //return 0;
        }
    }
    //search fines and sums
     public void searchFines (JTable table, String query) {
        try {
            ((DefaultTableModel)table.getModel()).setRowCount(0);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int columns = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                ((DefaultTableModel) table.getModel()).insertRow(rs.getRow() - 1, row);
            }
            
            rs.close();
            //return table.getModel().getRowCount();
        } catch (Exception e) {
            e.printStackTrace();
            //return 0;
        }
    }
     //close connection
    public void closeConnection(){
        try{
        conn.close();
        }
        catch(Exception e){
           System.err.println("Connection doesnt close");
           System.err.println(e.getMessage());
        }
    }
}
