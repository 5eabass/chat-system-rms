package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import signals.*;
import interfaces.*;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network implements CtrlToNetwork {
    
    //private String username;
    private UDPserver udpServer;
    private UDPsender udpSender;
    //private UDP_Server UDPclient;
    
    public Network() { 
        openServer();
    }
    
    public void openServer() {
        // Start the listening UDP server on port 1313 and with 1024 bytes packets size
        this.udpServer = new UDPserver(4445);
        this.udpSender = new UDPsender(4444);
        udpServer.start();
    }
    // attention il me semble le thread client vient de l'entité emetrice
    /*
    public void openClient() {
        // Start the listening UDP client on port 1314 and with 1024 bytes packets size
        
        this.UDPclient = new UDP_Server(1313, 1024);
        UDPserver.start();
    }*/
    
    //appelé par le controler pour créer le localInfo
    public String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (IOException e) {
            System.err.println("no IP adress for local user");
            return null;
        }
    }
    
    // reconstruit l'adresse de broadcast quand on a l'adresse du réseau local
    public String getBroadcast(){
        String local_ip = ChatSystem.getModel().getLocalAdress();
        String broadcastIP = "";
        int cnt = 0;
        int i=0;
        
        while (cnt!=3 && i<local_ip.length()) {
            if (local_ip.charAt(i)=='.'){
                cnt++;
            }
            broadcastIP += local_ip.charAt(i);
            i++;
        }
        broadcastIP += "255";
        return broadcastIP;          
    } 
    
    
    /*
    * ICI TOUTES LES FONCTIONS A IMPLEMENTER AU FUR ET A MESURE
    */
    
    /*
    * FROM CONTROLER
    */
    
    @Override
    // appelé quand on répond à un hello
    public void sendHello(String u) {
        System.out.println("DEBUG *** NETWORK : sendHello , localName = " + u + " ***");
        Hello helloMessage = new Hello(u);
        
        try{
             udpSender.send(helloMessage,InetAddress.getByName(getBroadcast()));
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    @Override
    //appelé quand on se connect
    public void sendHelloOk(String localName) {
        System.out.println("DEBUG *** NETWORK : sendHelloOK , localName = " + localName + " ***");
    }
    
    @Override
    // appelé quand on envoie un message
    public void processSendMessage(String message, String remoteName) {
        // a coder:la liste des receiver en global et la modifier ici
        // TextMessage m = new TextMessage(message, "localname", receivers);
        System.out.println("DEBUG *** NETWORK : processTextMessage , remoteName = " + remoteName + " message = " + message + " ***");
    }
    
    @Override
    // appelé quand on veut envoyer un fichier
    public void processSendFile() {
        System.out.println("DEBUG *** NETWORK : processSendFile <= ask to send a file ***");
    }
    
    @Override
    //appelé quand on refuse le transfer
    public void performRefuseTransfer() {
        System.out.println("DEBUG *** NETWORK : performRefuseTransfer <= send that we refuse ***");
    }
    
    @Override
    //appelé quand on accepte le transfer
    public void performAcceptTransfer() {
        System.out.println("DEBUG *** NETWORK : performAcceptTransfer <= send that we accepte ***");
    }
    
    @Override
    // appelé quand on disconnect par le ctrl
    public void sendGoodbye(String localName) {
        System.out.println("DEBUG *** NETWORK : sendGOODBYE , localName = " + localName + " ***");
    }
    
    /*
    * FIN FROM CTRL
    */
    
    
    /*
    * Fonction qui recoit les signaux de l'udp server et envoie au ctrl les actions a faire
    */
    
    // traitement du signal 
    public void signalProcess(Signal s){ // a modifier
      /*  if (s instanceof Hello){
            ChatSystem.getControler().performHello(s.getUsername());
        }else if(s instanceof HelloOk){
            
        }else if (s instanceof Goodbye){
            
        }*/
    
    }
    
    
}











