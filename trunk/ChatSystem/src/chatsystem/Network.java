package chatsystem;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.System.in;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Hello;
import interfaces.*;
import signals.TextMessage;

public class Network implements CtrlToNetwork{

    private String username;

    public Network() {

    }

    public void udpSocket(InetAddress aIp, int dPort) {
        try {
            Socket s0 = new Socket("127.0.0.1", dPort);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*in = new InputStreamReader(client.getSocket().getInputStream());
        out = new OutputStreamWriter(client.getSocket().getOutputStream());
        writer = new BufferedWriter(out);
        reader = new BufferedReader(in);*/
    }
    
    // il suffit pas de faire InetAddress.getLocalHost().getHostAddress() ?

    public ArrayList getIps() {
        ArrayList ips = new ArrayList();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {  // carte reseau trouvee
                NetworkInterface interfaceN = (NetworkInterface) interfaces.nextElement();
                Enumeration<InetAddress> ienum = interfaceN.getInetAddresses();
                while (ienum.hasMoreElements()) {  // retourne l adresse IPv4 et IPv6
                    InetAddress ia = ienum.nextElement();
                    String adress = ia.getHostAddress().toString();

                    if (adress.length() < 16) {          //On s'assure ainsi que l'adresse IP est bien IPv4
                        if (adress.startsWith("127")) {  //Ce n'est pas l'adresse IP Local' 
                            System.out.println(ia.getHostAddress());
                        } else if (adress.indexOf(":") > 0) {
                            System.out.println(ia.getHostAddress()); // les ":" indique que c'est une IPv6"
                        }
                    }

                    ips.add(adress);
                }
            }
            // print de ce que je dis plus haut 
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
            System.out.println("pas de carte reseau");
            e.printStackTrace();
        }

        return ips;
    }

    //appelé par le controler pour créer le localInfo 
    
    public String getIP(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch(IOException e){
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
    public void processSendMessage(String message ,String remoteName) {
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
