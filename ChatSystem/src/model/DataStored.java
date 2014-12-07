package model;

import java.io.File;
import java.util.*;
import javax.swing.DefaultListModel;
import signals.FileProposal;
/*
* MODEL
*/

public class DataStored {
    
    private DefaultListModel remoteTable; // info des remotes users table de nom@ip  
    private DefaultListModel fileStringProposed; // liste des file que l'utilisateur voit
    private String localName; // nom local user
    private String localAdress; // @ip local user
    private String username; // nom@ip de l'utilisateur
    private String adresseBroadcast; // @broadcast du reseau dans lequel on est
    private File fileToSend ; // file qu'on send
    private HashMap<String,FileProposal> fileProposed; // fileproposal enregistré 
      
            
    public DataStored() {
        this.remoteTable = new DefaultListModel();
        this.fileProposed = new HashMap<>();
        this.fileStringProposed = new DefaultListModel();
        //this.receivers = new ArrayList<String>();
        //this.localName = localName;
        //this.localAdress = localAdress;
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
    
    
    
             
    @Override
    public String toString() {
        String table = new String();
        for (int i=0; i<remoteTable.getSize();i++) {
            table += remoteTable.elementAt(i) + "\n";
        }
        return table;
    }
    
}
