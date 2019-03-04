/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaselibrary2;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Xavier Cadot
 * creates new borrower 
 * 
 */
//borrower constructor
public class newBorrower {
        String ssn;
        String name;
        String address;
        String phone;
        dbhandler call=new dbhandler();
        int currentid;
    public newBorrower(){
        
    }
    //checking the if the SSn exist and returning a boolean
    public boolean insertSsn(String temp){
        //List<String> returnList=new ArrayList<String>();
        String checkq="Select ssn from borrower where ssn = \""+temp+"\";";
        boolean exist=call.checkSsn(checkq);
        if(exist){
            System.out.println("ssn already exist");
            return true;
        }
        else{
        ssn=temp;
        System.out.println("created SSN");
        return false;
        }
    }
    public void insertName(String inputName){
        name=inputName;
    }
    public void insertAddress(String inputAddress){
        address=inputAddress;
    }
    public void insertPhone(String inputPhone){
        phone=inputPhone;
    }
    //creats a new borrower id
    public void createsId(){
        //finding the last cardid
        String lastcardid=call.generatecard_Id();
        lastcardid=lastcardid.substring(2);
        System.out.println("lastcard= "+lastcardid);
        currentid=Integer.parseInt(lastcardid);
        currentid++;
        //inserting the new card id intow the borrower table
        String inputID = "ID"+String.format("%06d", currentid);
        System.out.println("inputID= "+inputID);
        String idQuerry="INSERT INTO BORROWER (card_id,ssn,bname,address,phone) VALUES('"+inputID+"','"+ssn+"','"+name+"','"+address+"','"+phone+"');";
        call.callQuerry(idQuerry);
        call.callQuerry("use library;");
    }
}
