package model;

import chatsystem.ChatSystem;
import java.io.File;
import java.util.*;
import javax.swing.DefaultListModel;
/*
* MODEL
*/

public class DataStored {
    
    private DefaultListModel remoteTable; // info des remotes users table de nom@ip
    private String localName; // nom local user
    private String localAdress; // @ip local user
    private String username; // nom@ip de l'utilisateur
    private String receivers;
    private String adresseBroadcast;
    private File fileToSend ;
    
    public DataStored() {
        this.remoteTable = new DefaultListModel();
        //this.receivers = new ArrayList<String>();
        //this.localName = localName;
        //this.localAdress = localAdress;
    }
    
    public DefaultListModel getRemoteTable() {
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
    
    public void setLocalAdress(String adr) {
        this.localAdress = adr;
    }
    
    //public ArrayList<String> getReceivers() {
    //    return receivers;
    //}
    
    public void setReceivers(ArrayList<String> receiverName) {
        //this.receivers = receiverName;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername() {
        this.username = this.localName + "@" + this.localAdress;
    }
    
    public String getAdresseBroadcast() {
        return adresseBroadcast;
    }
    
    public void setAdresseBroadcast(String adr) {
        this.adresseBroadcast = adr;
    }
    
    //fonction qui permet de retrouver le nom a partir de la chaine nom@ip
    public String getRemoteIp(String remoteString) {
        String remoteIp[] = remoteString.split("@");
        return remoteIp[1];
    }

    public File getFileToSend() {
        return fileToSend;
    }

    public void setFileToSend(File fileToSend) {
        this.fileToSend = fileToSend;
    }
    
             
    @Override
    public String toString() {
        String table = new String();
        for (int i=0; i<remoteTable.getSize();i++) {
            table += remoteTable.elementAt(i) + "\n";
        }
        return table;
    }
    
}
