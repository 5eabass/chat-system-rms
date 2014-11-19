package chatsystem;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import interfaces.*;
import java.util.*;
import model.*;

public class Controler implements ActionListener, NetworkToCtrl , GUIToCtrl {
    private DataStored model;
    
    public DataStored getModel() {
        return model;
    }
    
    public Controler() {
    }
    
    /*
    * pour le GUI de rémi
    */
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Listener menu
        if(e.getActionCommand().equals("Connexion")) {
            System.out.println("! Connexion layout displayed");
            //  ChatSystem.getGUI().activeLogin();
        }
        if(e.getActionCommand().equals("Enter the chat")) {
            System.out.println("> Enter the chat button pressed");
            //  performHello(ChatSystem.getGUI().getUsername());
        }
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
    public void performHello(String remoteName, String remoteIp) {
        System.out.println("DEBUG *** CTRL : performHello <= when we receive a Hello ***");
        model.getRemoteTable().put(remoteName,remoteIp);
        System.out.println("DEBUG *** CTRL : la table des remote users est : " + model.toString()+ " ***");
        ChatSystem.getGUI().addUser(remoteName);
        ChatSystem.getNetwork().sendHelloOk("localname"); // a changer localname par notre nom
    }
    
    @Override
    // appelé quand on recoit un HelloOK
    public void performHelloOk(String username, String remoteIp) {
        System.out.println("DEBUG *** CTRL : performHelloOK <= when we receive HelloOK***");
        model.getRemoteTable().put(username, remoteIp);
        System.out.println("DEBUG *** CTRL : la table des remote users est : " + model.toString()+ " ***");
        ChatSystem.getGUI().addUser(username);
    }
    
    @Override
    // appelé quand on recoit un message
    public void performTextMessage(String message, String remoteName) {
        System.out.println("DEBUG *** CTRL : performTextMessage <= when we receive a message ***");
        ChatSystem.getGUI().processTextMessage(message, remoteName);
    }
    
    
    @Override
    // appelé quand on est interrogé pour recevoir un fichier
    public void processFileQuery() {
        System.out.println("DEBUG *** CTRL : processFileQuery <= when we're asked to accept/refuse a file receiption***");
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
        model.getRemoteTable().remove(model.getRemoteTable().containsKey(remoteName));
        System.out.println("DEBUG *** CTRL : la table des remote users est : " + model.toString()+ " ***");
        ChatSystem.getGUI().deleteUser(remoteName);
    }
    
    /*
    * FIN DU FROM NETWORK
    */
    
    /*
    * FROM GUI
    */
    
    // pour créer les infos locales
    
    public void createLocalInfo(String username){
        System.out.println("DEBUG *** CTRL : createLocalInfo <= when we connect to chatsystem ***");
        model = new DataStored(username,ChatSystem.getNetwork().getIP());
        System.out.println("DEBUG *** CTRL : "+ model.getLocalName() + "/" + model.getLocalAdress()+ " ***");
    }
    
    
    @Override
    // appelé quand on se connect au chatsystem
    public void performConnect(String username) {
        System.out.println("DEBUG *** CTRL : performConnect <= when we connect to chatsystem ***");
        ChatSystem.getNetwork().sendHello(username);
    }
    
    @Override
    // appelé quand on envoie un message
    public void performSendMessage(String message, String remoteName) {
        System.out.println("DEBUG *** CTRL : performSendMessage , remote : " + remoteName + " <= when we send a message ***");
        
        // cas au tout début quand aucun receiver est selectionné le message
        // ne peut pas etre envoyé envoie erreur sur screen
        if (model.getRemoteTable().containsKey(remoteName) == false){
            System.out.println("no such remote user");
            //ChatSystem.getGUI().erreurReceiver();
        }else{
            ChatSystem.getNetwork().processSendMessage(message, remoteName);
        }
    }
    
    @Override
    // appelé quand on envoie un fichié
    public void performSendFile() {
        System.out.println("DEBUG *** CTRL : performSendFile <= when we send file ***");
        
    }
    
    @Override
    // appelé quand on accepte un transfer
    public void processAcceptTransfer() {
        System.out.println("DEBUG *** CTRL : processAcceptTransfer <= when we accept transfer ***");
    }
    
    @Override
    // appelé quand on refuse un transfer
    public void processRefuseTransfer() {
        System.out.println("DEBUG *** CTRL : processRefuseTransfer <= when we refuse transfer ***");
    }
    
    @Override
    // appelé quand on fait un disconnect
    public void performDisconnect(String localName) {
        System.out.println("DEBUG *** CTRL : performDisconnect <= when we disconnect ***");
        ChatSystem.getNetwork().sendGoodbye(localName);
    }
    
    /*
    * FIN FROM GUI
    */
    
    
    
}
