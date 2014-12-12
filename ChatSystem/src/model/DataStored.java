package model;

import java.io.File;
import java.util.*;
import javax.swing.DefaultListModel;
import signals.FileProposal;

/*
* MODEL
*/

public class DataStored {
    
    private DefaultListModel remoteTable; // table storing info about remote users nom@ip  
    private DefaultListModel fileStringProposed; // list of file proposition the user can see
    private String localName; // nickname of local user
    private String localAdress; // @ip of local user
    private String username; // nickname@ip of local user
    private String adresseBroadcast; // network's @broadcast
    private File fileToSend ; // file we are sending
    private HashMap<String,FileProposal> fileProposed; // stored file proposition
      
            
    public DataStored() {
        this.remoteTable = new DefaultListModel();
        this.fileProposed = new HashMap();
        this.fileStringProposed = new DefaultListModel();
    }
    
    public void addFileProposal(FileProposal fp) {
        String fsp = "From: "+ fp.getFrom() + " File Name: "+ fp.getFileName() + " Size: "+fp.getSize();
        this.fileProposed.put(fsp,fp);    
        this.fileStringProposed.addElement(fsp);
    }
    
    public void removeFileProposal(String fsp) {
        this.fileProposed.remove(fsp);
        this.fileStringProposed.removeElement(fsp);
    }
    
    public FileProposal getFileProposal(String k) {
        return this.fileProposed.get(k);
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
    
    //function that retreive juste the nickname of a username (nickname@ip)
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

    public DefaultListModel getFileStringProposed() {
        return fileStringProposed;
    }

    public void setFileStringProposed(DefaultListModel fileStringProposed) {
        this.fileStringProposed = fileStringProposed;
    }

    public HashMap<String, FileProposal> getFileProposed() {
        return fileProposed;
    }

    public void setFileProposed(HashMap<String, FileProposal> fileProposed) {
        this.fileProposed = fileProposed;
    }
    
    // print the remoteTable 
    @Override
    public String toString() {
        String table = new String();
        for (int i=0; i<remoteTable.getSize();i++) {
            table += remoteTable.elementAt(i) + "\n";
        }
        return table;
    }
    
}
