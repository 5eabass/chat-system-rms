package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import signals.Hello;
import interfaces.*;

public class Network implements CtrlToNetwork {

    private String username;
    private UDP_Server UDPserver;
    private UDP_Server UDPclient;

    public Network() {

        openServer();
    }

    public void openServer() {
        // Start the listening UDP server on port 1313 and with 1024 bytes packets size
        this.UDPserver = new UDP_Server(1313, 1024);
        UDPserver.start();
    }
    
    public void openClient() {
        // Start the listening UDP server on port 1313 and with 1024 bytes packets size
        this.UDPclient = new UDP_Server(1313, 1024);
        UDPserver.start();
    }

    //appelé par le controler pour créer le localInfo
    public String getIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (IOException e) {
            System.err.println("no IP adress for local user");
            return null;
        }
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
}
