package model;

import java.io.File;
import java.util.*;
import javax.swing.DefaultListModel;
import signals.FileProposal;

/*
* MODEL
*/

/** 
     * This class DataStored is the Model in the MVC, it containts the main datas used in the chatsystem
     * @return 
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
      
            /** 
     * This constructor contains the remote table of the connected users, the hashmap of the file proposed and the list of the file proposed 
     * @return 
     */
    public DataStored() {
        this.remoteTable = new DefaultListModel();
        this.fileProposed = new HashMap();
        this.fileStringProposed = new DefaultListModel();
    }
    
    /** 
     * This method allows to add a file proposal to the list
     * @return 
     */
    public void addFileProposal(FileProposal fp) {
        String fsp = "From: "+ fp.getFrom() + " File Name: "+ fp.getFileName() + " Size: "+fp.getSize();
        this.fileProposed.put(fsp,fp);    
        this.fileStringProposed.addElement(fsp);
    }
    
    /** 
     * This method allows to remove a file proposal from the list 
     * @return 
     */
    public void removeFileProposal(String fsp) {
        this.fileProposed.remove(fsp);
        this.fileStringProposed.removeElement(fsp);
    }
    
    /** 
     * This method returns a file proposal from the list 
     * @return 
     */
    public FileProposal getFileProposal(String k) {
        return this.fileProposed.get(k);
    }
    
    /** 
     * This method returns the remote table of the remote connected users
     * @return 
     */
    public DefaultListModel getRemoteTable() {
        return remoteTable;
    }
    
    /** 
     * This method returns the local user name
     * @return 
     */
    public String getLocalName() {
        return localName;
    }
    
    /** 
     * This method permits to set the local user name
     * @return 
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    
    /** 
     * This method returns the local IP adress of the user
     * @return 
     */
    
    public String getLocalAdress() {
        return localAdress;
    }
    
    /** 
     * This method permits to store the local ip adress of the user
     * @return 
     */
    public void setLocalAdress(String adr) {
        this.localAdress = adr;
    }  
    
    /** 
     * This method returns the username
     * @return 
     */
    public String getUsername() {
        return username;
    }
    
    /** 
     * This method permits to storesthe username 
     * @return 
     */
    public void setUsername() {
        this.username = this.localName + "@" + this.localAdress;
    }
    
    /** 
     * This method returns the broadcast ip adress 
     * @return 
     */
    public String getAdresseBroadcast() {
        return adresseBroadcast;
    }
    
    /** 
     * This method permits to store the broadcast ip adress
     * @return 
     */
    public void setAdresseBroadcast(String adr) {
        this.adresseBroadcast = adr;
    }
    
    /** 
     * This method returns the nickname of the remote user (nickname@ip)
     * @return 
     */
  
    public String getRemoteIp(String remoteString) {
        String remoteIp[] = remoteString.split("@");
        return remoteIp[1];
    }

    /** 
     * This method returns the file stored to send 
     * @return 
     */
    public File getFileToSend() {
        return fileToSend;
    }

    /** 
     * This method permits to store the file to send
     * @return 
     */
    public void setFileToSend(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    public DefaultListModel getFileStringProposed() {
        return fileStringProposed;
    }

    public void setFileStringProposed(DefaultListModel fileStringProposed) {
        this.fileStringProposed = fileStringProposed;
    }

    /** 
     * This method returns the hashmap of the file proposals from the remote users 
     * @return 
     */
    public HashMap<String, FileProposal> getFileProposed() {
        return fileProposed;
    }

    /** 
     * This method permits to store the file proposals from the remote users
     * @return 
     */
    public void setFileProposed(HashMap<String, FileProposal> fileProposed) {
        this.fileProposed = fileProposed;
    }
    
    /** 
     * This method returns the remoteTable and print it
     * @return 
     */ 
    @Override
    public String toString() {
        String table = new String();
        for (int i=0; i<remoteTable.getSize();i++) {
            table += remoteTable.elementAt(i) + "\n";
        }
        return table;
    }
    
}
