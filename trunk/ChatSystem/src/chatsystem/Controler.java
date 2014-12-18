package chatsystem;

import interfaces.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import signals.FileProposal;
//import sun.awt.OSInfo;

/** 
     * This class is the CONTROLER class and is in relation with the Network and the GUI 
     * @return 
     */

public class Controler implements NetworkToCtrl, GUIToCtrl {
    
    public Controler() {
    }
    
    /*
    * FROM NETWORK
    */
    
    
    
   
    /** 
     * FROM NETWORK - This method is calles when we receive a "hello" signal during the connection of the remote users 
     * @return 
     */
    public void performHello(String username) {
        
        System.out.println("DEBUG *** CTRL : performHello <= when we receive a Hello ***");
        // if hello is not our, or if the user is not yet in the list, we had the user, we send helloOK
        if ((!username.equals(ChatSystem.getModel().getUsername())) && (!ChatSystem.getModel().getRemoteTable().contains(username))) {
            System.out.println("DEBUG *** CTRL : ajout du remote user : " + username + " ***");
            ChatSystem.getModel().getRemoteTable().addElement(username);
            System.out.println("DEBUG *** CTRL : la table des remoteusers est : " + ChatSystem.getModel().toString() + " ***");
            ChatSystem.getGUI().addUser(username);
            ChatSystem.getNetwork().sendHelloOk(ChatSystem.getModel().getUsername(), username);
        } else {
            System.err.println("DEBUG *** CTRL : remoteUser deja dans table ou est le localuser : " + username + " ***");
        }
    }
    
    /** 
     * FROM NETWORK - This method is called when we receive a "helloOK" signal
     * @return 
     */
    @Override
    public void performHelloOk(String username) {
        System.out.println("DEBUG *** CTRL : performHelloOK <= when we receive HelloOK ***");
        // if the user is not yet in the table we had it
        if (!ChatSystem.getModel().getRemoteTable().contains(username)) {
            System.out.println("DEBUG *** CTRL : ajout du remote user : " + username + " ***");
            ChatSystem.getModel().getRemoteTable().addElement(username);
            System.out.println("DEBUG *** CTRL : la table des remote users est : " + ChatSystem.getModel().toString() + " ***");
            ChatSystem.getGUI().addUser(username);
        } else {
            System.err.println("DEBUG *** CTRL : remoteUser deja dans table : " + username + " ***");
        }
    }
    
    /** 
     * FROM NETWORK - This method is called when we receive a textMessage from the others users
     * @return 
     */
    // 
    @Override
    public void performTextMessage(String message, String remoteName, ArrayList<String> to) {
        System.out.println("DEBUG *** CTRL : performTextMessage <= when we receive a message ***");
        ChatSystem.getGUI().processTextMessage(message, remoteName, to);
    }
    
    /** 
     * FROM NETWORK - This method is called when we receive a file proposal from the others users
     * @return 
     */
    
    @Override
    public void performFileQuery(FileProposal fp) {
        System.out.println("DEBUG *** CTRL : performFileQuery <= when we're asked to accept/refuse a file receiption***");
        ChatSystem.getModel().addFileProposal(fp);
        ChatSystem.getGUI().processFileQuery(fp);
    }
    
    /** 
     * FROM NETWORK - This method is called when we receive the response to the file proposal from the others users
     * @return 
     */
     
    @Override
    public void performFileAnswer(boolean b){
        String s ;
        if(b){
            s = "accepted";
            System.err.println("DEBUG *** CTRL : performFileAnswer : " + s + " ***");
            ChatSystem.getGUI().processFileAccepted();
        } else{
            s = "refused";
            System.err.println("DEBUG *** CTRL : performFileAnswer : " + s + " ***");
            ChatSystem.getGUI().processFileNotAccepted();
        }
    }
    
    /** 
     * FROM NETWORK - This method is called when we receive a file
     * @return 
     */
    
    @Override
    public void performTransmission(byte[] buffer, String fileName) {
        System.out.println("DEBUG *** CTRL : performTransmission <= when we receive a file ***");
        String currentDir = System.getProperty("user.dir");
        File dirDownload = new File("./chatDownload/");
        try {                    
            if (!dirDownload.exists()){
                dirDownload.mkdir();
            }
            File fileOut = new File( currentDir+"/chatDownload/"+fileName);                     
            FileOutputStream fos = new FileOutputStream(fileOut);
            fos.write(buffer);
            fos.flush();
            fos.close();
            System.err.println("DEBUG *** CTRL : File size : "+fileOut.length()+"/"+buffer.length+ " ***");
            if (fileOut.length()!= buffer.length){           
                ChatSystem.getGUI().notifyNotReceived(fileName);               
            }else{
                ChatSystem.getGUI().notifyReceived(fileName);
            }      
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    /** 
     * FROM NETWORK - This method is called when we have sent a file
     * @return 
     */
    
    @Override
    public void performTransferNotification(boolean b){
        String s;
        if (b){
            s = "true";
            System.err.println("DEBUG *** CTRL : performTransferNotification : " + s + " ***");
            ChatSystem.getGUI().notifyTransmitted();
        }else{
            s = "false";
            System.err.println("DEBUG *** CTRL : performTransferNotification : " + s + " ***");
            ChatSystem.getGUI().notifyNotTransmitted();
        }
    }
    
    
       
    /** 
     * FROM NETWORK - This method is called when we receive a "goodbye" signal during the disconnection of the remote users
     * @return 
     */
    
    @Override
    public void performGoodbye(String remoteName) {
        System.out.println("DEBUG *** CTRL : performGoodbye <= when receiving goodbye ***");
        ChatSystem.getModel().getRemoteTable().removeElement(remoteName);
        ChatSystem.getGUI().deleteUser(remoteName);
        System.out.println("DEBUG *** CTRL : la table des remote users est : " + ChatSystem.getModel().toString() + " ***");
    }
    
    /*
    * FIN FROM NETWORK
    */
      
    /*
    * FROM GUI
    */
    
    
     /** 
     * FROM GUI - This method is called to create local info : username,adresse ip+broadcast, initialize lists
     * @return 
     */
    
    @Override
    public void createLocalInfo(String localName) {
        System.out.println("DEBUG *** CTRL : createLocalInfo  with localname :"+localName+"<= when we connect to chatsystem ***");
        String ips = ChatSystem.getNetwork().getIPs();
        ChatSystem.getModel().setLocalName(localName);
        if (!ips.equals("")) {
            String arrayIP[] = ips.split("@");
            ChatSystem.getModel().setLocalAdress(arrayIP[0]);
            ChatSystem.getModel().setAdresseBroadcast(arrayIP[1]);
            System.out.println("DEBUG *** CTRL ip set :"+ ChatSystem.getModel().getLocalAdress() +" ***");
        }
        ChatSystem.getModel().setUsername();
        System.out.println("DEBUG *** CTRL : "+ ChatSystem.getModel().getUsername()+" ***");
    }
    
    
    /** 
     * FROM GUI - This method is called when we connect to the chat
     * @return 
     */
   
    @Override
    public void performConnect() {
        System.out.println("DEBUG *** CTRL : performConnect <= when we connect to chatsystem ***");
        ChatSystem.getNetwork().openUDP();
        ChatSystem.getNetwork().sendHello(ChatSystem.getModel().getUsername());
    }
    
    
    /** 
     * FROM GUI - This method is called when we send a message
     * @return 
     */
  
    @Override
    public void performSendMessage(String message, ArrayList<String> remoteName) {
        System.out.println("DEBUG *** CTRL : performSendMessage  <= when we send a message ***");
        ChatSystem.getNetwork().processSendMessage(message, remoteName);
    }
    
    /** 
     * FROM GUI - This method is called when we want to send a file
     * @return 
     */
  
    @Override
    public void performSendFile(File file, ArrayList<String> remoteName) {
        System.out.println("DEBUG *** CTRL : performSendFile"+ file.getName() + " <= when we send file ***");
        ChatSystem.getNetwork().processSendProposal(file, file.length(), remoteName);
    }
    
    /** 
     * FROM GUI - This method is called when we accept a file transfer
     * @return 
     */

    @Override
    public void performAcceptTransfer(String fileName) {
        System.out.println("DEBUG *** CTRL : performAcceptTransfer <= when we accept transfer ***");
        ChatSystem.getNetwork().processAcceptTransfer(ChatSystem.getModel().getFileProposal(fileName));
        ChatSystem.getModel().removeFileProposal(fileName);
    }
    
    
    /** 
     * FROM GUI - This method is called when we refuse a file transfer
     * @return 
     */
  
    @Override
    public void performRefuseTransfer(String fileName) {
        System.out.println("DEBUG *** CTRL : performRefuseTransfer <= when we refuse transfer ***");
        ChatSystem.getNetwork().processRefuseTransfer(ChatSystem.getModel().getFileProposal(fileName));
        ChatSystem.getModel().removeFileProposal(fileName);
    }
    
    /** 
     * FROM GUI - This method is called when we disconnect
     * @return 
     */

    @Override
    public void performDisconnect(String userName) {
        System.out.println("DEBUG *** CTRL : performDisconnect <= when we disconnect ***");
        ChatSystem.getNetwork().sendGoodbye(userName);
        ChatSystem.getModel().getRemoteTable().clear();
        ChatSystem.getModel().getFileStringProposed().clear();
        ChatSystem.getModel().getFileProposed().clear();
    }
    
    /*
    * FIN FROM GUI
    */
}
