package model;

import java.util.*;
/* 
 * MODEL
*/

public class DataStored {
    private HashMap<String,String> remoteTable; // info des remotes users
    // hashmap : <name , @ip>
    private String localName ; // info de l'utilisateur qui s'initialise à la connection
    private String localAdress ;
    private String receiverName ; /* champ utilisé pour enregistré le destinataire de notre message
                                   via le gui 
                                   */  
    
    public DataStored(String localName,String localAdress) {
        this.remoteTable = new HashMap<String,String>();
        this.localName = localName;
        this.localAdress = localAdress;
        this.receiverName = new String();
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
    
    
    
    
}
