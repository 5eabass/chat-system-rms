package model;

import java.util.*;
import java.util.Map.Entry;
/*
* MODEL
*/

public class DataStored {
    private HashMap<String,String> remoteTable; // info des remotes users
    // hashmap : <name , @ip>
    private String localName ; // info de l'utilisateur qui s'initialise à la connection
    private String localAdress ;
    private String receiverName;
    
    public DataStored(String localName,String localAdress) {
        this.remoteTable = new HashMap<String,String>();
        this.localName = localName;
        this.localAdress = localAdress;
    }
    
    public HashMap<String,String> getRemoteTable() {
        return remoteTable;
    }
    
    public String getLocalName() {
        return localName;
    }
    
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    
    public String getLocalAdress() {
        return localAdress;
    }
    
    public void setLocalAdress(String localAdress) {
        this.localAdress = localAdress;
    }
    
    public String getReceiverName() {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
      
    
    public String toString(){
        String table = new String();
         for (Entry<String,String> e : remoteTable.entrySet()) {
                table += e.getKey() + " / " + e.getValue() + "\n" ;
            }
         return table;
    }
    
    
    
    
    
}
