package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.*;
import interfaces.*;

public class Controler implements ActionListener, NetworkToCtrl , GUIToCtrl {
    
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
            ChatSystem.getGUI().activeLogin();
        }
        if(e.getActionCommand().equals("Enter the chat")) {
            System.out.println("> Enter the chat button pressed");
            performHello(ChatSystem.getGUI().getUsername());
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
    public void performHello(String remoteName) {
        ChatSystem.getGUI().addUser(remoteName);
        ChatSystem.getNetwork().sendHelloOk("localname"); // a changer localname par notre nom
    }

    @Override
    // appelé quand on recoit un HelloOK 
    public void performHelloOk(String username) {
          ChatSystem.getGUI().addUser(username);  
    }

    @Override
    // appelé quand on recoit un message 
    public void performTextMessage(String message, String remoteName) {
      ChatSystem.getGUI().processTextMessage(message, remoteName);
    }


    @Override
    public void processFileQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processReceipt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTransmission() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    // appelé quand on recoit un GOODBYE
    public void performGoodbye(String remoteName) {
        ChatSystem.getGUI().deleteUser(remoteName);
    }

    /*
     * FIN DU FROM NETWORK 
    */
    
    /*
     * FROM GUI
    */
    
    @Override
    // appelé quand on se connect au chatsystem
    public void performConnect(String username) {
        ChatSystem.getNetwork().sendHello(username);    
    }

    @Override
    // appelé quand on envoie un message
    public void performSendMessage(String message, String remoteName) {
        ChatSystem.getNetwork().processTextMessage(message, remoteName);
    }

    @Override
    public void performSendFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processAcceptTransfer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processRefuseTransfer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    // appelé quand on fait un disconnect 
    public void performDisconnect(String localName) {
        ChatSystem.getNetwork().sendGoodbye(localName);
    }
    
    /*
     * FIN FROM GUI
    */
    
    
    
}
