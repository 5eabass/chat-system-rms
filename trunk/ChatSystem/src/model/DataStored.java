package model;

import java.util.*;
/*
 * MODEL
 */

public class DataStored {

    private ArrayList<String> remoteTable; // info des remotes users table de nom@ip
    private String localName; // nom local user
    private String localAdress; // @ip local user
    private String username; // nom@ip de l'utilisateur
    private String receiverName;

    public DataStored() {
        this.remoteTable = new ArrayList<String>();
        //this.localName = localName;
        //this.localAdress = localAdress;
    }

    public ArrayList<String> getRemoteTable() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername() {
        this.username = this.localName + "@" + this.localAdress;
    }

    //fonction qui permet de retrouver le nom a partir de la chaine nom@ip
    public String getRemoteIp(String remoteString) {
        String remoteIp[] = remoteString.split("@");
        return remoteIp[1];
    }

    @Override
    public String toString() {
        String table = new String();
        for (String e : remoteTable) {
            table += e + "\n";
        }
        return table;
    }

}
