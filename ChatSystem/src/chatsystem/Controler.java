package chatsystem;

import interfaces.*;
import java.io.File;

public class Controler implements NetworkToCtrl, GUIToCtrl {
    
    public Controler() {
        
    }
    
    /*
    * ICI toutes les methodes à implémenter au fur et à mesure
    * il faut rajouter les arguments si nécessaire des fonctions et les return
    * car , pour un soucis de commodité je les ai toute créé : void function();
    * Il suffit pour une majorité de suivre les SDD voir par exemple : performHello
    */
    /*
    * FROM NETWORK
    */
    @Override
// appelé quand on recoit un Hello
    public void performHello(String username) {
        
        System.out.println("DEBUG *** CTRL : performHello <= when we receive a Hello ***");
        // si le hello vient pas de nous (broadcast) ou si l'utilisateur n'est pas déjà dans la table on ajoute
        if ((!username.equals(ChatSystem.getModel().getUsername())) && (!ChatSystem.getModel().getRemoteTable().contains(username)) ){
            System.out.println("DEBUG *** CTRL : ajout du remote user : " + username + " ***");
            ChatSystem.getModel().getRemoteTable().addElement(username);
            System.out.println("DEBUG *** CTRL : la table des remoteusers est : " + ChatSystem.getModel().toString() + " ***");
            ChatSystem.getGUI().addUser(username);
            ChatSystem.getModel().setReceiverName(username);
            ChatSystem.getNetwork().sendHelloOk(ChatSystem.getModel().getUsername());
        }else{
            System.err.println("DEBUG *** CTRL : remoteUser deja dans table ou est le localuser : " + username + " ***");
        }
    }
    
    @Override
// appelé quand on recoit un HelloOK
    public void performHelloOk(String username) {
        System.out.println("DEBUG *** CTRL : performHelloOK <= when we receive HelloOK ***");
        // si l'utilisateur n'est pas déjà dans la table on ajoute
        if (!ChatSystem.getModel().getRemoteTable().contains(username)){
            System.out.println("DEBUG *** CTRL : ajout du remote user : " + username + " ***");
            ChatSystem.getModel().getRemoteTable().addElement(username);
            System.out.println("DEBUG *** CTRL : la table des remote users est : " + ChatSystem.getModel().toString() + " ***");
            ChatSystem.getGUI().addUser(username);
        }else{
            System.err.println("DEBUG *** CTRL : remoteUser deja dans table : " + username + " ***");
        }
    }
    
    @Override
// appelé quand on recoit un message
    public void performTextMessage(String message, String remoteName) {
        System.out.println("DEBUG *** CTRL : performTextMessage <= when we receive a message ***");
        ChatSystem.getGUI().processTextMessage(message, remoteName);
    }
    
    @Override
// appelé quand on est interrogé pour recevoir un fichier
    public void processFileQuery(String filename,long size, String remoteName) {
        System.out.println("DEBUG *** CTRL : processFileQuery <= when we're asked to accept/refuse a file receiption***");
        ChatSystem.getGUI().performFileQuery(filename,size,remoteName);
    }
    
    @Override
// appelé pour tester si on a recu le fichier
    public void processReceipt() {
        System.out.println("DEBUG *** CTRL : processReceipt <= when the file transmission's is done ***");
    }
    
    @Override
// appelé quand on recoit un fichier
    public void processTransmission() {
        System.out.println("DEBUG *** CTRL : processTransmission <= when we receive a file ***");
    }
    
    @Override
// appelé quand on recoit un GOODBYE
    public void performGoodbye(String remoteName) {
        System.out.println("DEBUG *** CTRL : performGoodbye <= when receiving goodbye ***");
        ChatSystem.getModel().getRemoteTable().removeElement(remoteName);
        System.out.println("DEBUG *** CTRL : la table des remote users est : " + ChatSystem.getModel().toString() + " ***");
        ChatSystem.getGUI().deleteUser(remoteName);
    }
    
    /*
    * FIN DU FROM NETWORK
    */
    
    /*
    * FROM GUI
    */
    @Override
// pour créer les infos locales
    public void createLocalInfo(String localName) {
        String ips = ChatSystem.getNetwork().getIPs();
        System.out.println("DEBUG *** CTRL : createLocalInfo <= when we connect to chatsystem ***");
        ChatSystem.getModel().setLocalName(localName);
        System.out.println("DEBUG *** CTRL : localName set : " + localName + " ***");
        if(!ips.equals("")){
            String arrayIP[] = ips.split("@");
            ChatSystem.getModel().setLocalAdress(arrayIP[0]);
            ChatSystem.getModel().setAdresseBroadcast(arrayIP[1]);
            System.out.println("DEBUG *** CTRL : ip set :  ***");
        }
        //ChatSystem.getModel().setLocalAdress();
        // ChatSystem.getModel().setAdresseBroadcast();
        ChatSystem.getModel().setUsername();
        System.out.println("DEBUG *** CTRL : " + ChatSystem.getModel().getLocalName() + "/" + ChatSystem.getModel().getLocalAdress() + " ***");
    }
    
    @Override
// appelé quand on se connect au chatsystem
    public void performConnect() {
        System.out.println("DEBUG *** CTRL : performConnect <= when we connect to chatsystem ***");
        ////////// pour test avec un utilisateur dans la table
        ChatSystem.getModel().getRemoteTable().addElement("jack@192.168.0.3");
        /////////
        ChatSystem.getNetwork().openUDP();
        ChatSystem.getNetwork().openTCP();
        ChatSystem.getNetwork().sendHello(ChatSystem.getModel().getUsername());
    }
    
    @Override
// appelé quand on envoie un message
    public void performSendMessage(String message, String remoteName) {
        System.out.println("DEBUG *** CTRL : performSendMessage , remote : " + remoteName + " <= when we send a message ***");
        ChatSystem.getNetwork().processSendMessage(message, remoteName);
    }
    
    @Override
// appelé quand on envoie un fichié
    public void performSendFile(File file,String remoteName) {
        System.out.println("DEBUG *** CTRL : performSendFile <= when we send file ***");
        ChatSystem.getNetwork().processSendFile(file,file.length(),remoteName);
    }
    
    @Override
// appelé quand on accepte un transfer
    public void processAcceptTransfer() {
        System.out.println("DEBUG *** CTRL : processAcceptTransfer <= when we accept transfer ***");
        ChatSystem.getNetwork().performAcceptTransfer();
    }
    
    @Override
// appelé quand on refuse un transfer
    public void processRefuseTransfer() {
        System.out.println("DEBUG *** CTRL : processRefuseTransfer <= when we refuse transfer ***");
        ChatSystem.getNetwork().performRefuseTransfer();
    }
    
    @Override
// appelé quand on fait un disconnect
    public void performDisconnect(String userName) {
        System.out.println("DEBUG *** CTRL : performDisconnect <= when we disconnect ***");
        ChatSystem.getNetwork().sendGoodbye(userName);
        ChatSystem.getModel().getRemoteTable().clear();
    }
    
    /*
    * FIN FROM GUI
    */
}
