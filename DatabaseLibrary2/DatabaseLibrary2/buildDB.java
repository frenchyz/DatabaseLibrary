/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselibrary2;
import databaselibrary2.dbhandler;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.util.Scanner;
import databaselibrary2.dbhandler;
import java.util.Map;
import java.util.*;  

/**
 *
 * @author Xavier Cadot
 *  File that was used and to build the book table db. 
 */
public class buildDB {
    public buildDB(){
      
    }
    public static void main(String args[]){
        readinBooks();
    } 
    public static void readinBooks(){
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = "\t";
    String tempBook= "";
    String tempAuthor= "";
    String author="";
    String title="";
    int header=0;
    int count=0;
    
    HashMap<String,Integer> authorMap=new HashMap<String,Integer>();
    HashMap<Integer, List<String>> Isbnmap = new HashMap<Integer, List<String>>();
    File filebooks = new File("books.csv");
    File fileborrowers = new File("borrowers.csv");
    
    try {
            
            br = new BufferedReader(new FileReader(filebooks));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(header!=0){
                String[] booksArray = line.split(cvsSplitBy);
                //System.out.println("Booktest [ISBN= " + booksArray[0] + " , Book Title= " + booksArray[2] + " , Book Arthors= "+ booksArray[3]+ "]");
                title=booksArray[2].replace("\'","");
                author=booksArray[3].replace("\'","");
                author=author.toUpperCase();
                if (authorMap.get(author) != null) {
                    
                } else {
                    authorMap.put(author, count);
                    count++;
                }
                System.out.println (tempAuthor);

                }
                header++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
    
    }
}
