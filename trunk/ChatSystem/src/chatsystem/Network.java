package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import signals.*;
import interfaces.*;
import java.io.File;
import java.util.ArrayList;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network implements CtrlToNetwork {

    //private String username;
    private UDPserver udpServer;
    private UDPsender udpSender;
    //private UDP_Server UDPclient;
    private DatagramSocket socket;
    private int ports = 4445;
    private int portd = 4444;

    public Network() {
      
    }
    public void openServer() {
        // Start the listening UDP server on port 4444 and with 1024 bytes packets size
        try {
            socket = new DatagramSocket(portd);
            this.udpServer = new UDPserver(socket);
            System.out.println("DEBUG *** UDPserver : socket created on port : " + portd + " ***");
            this.udpSender = new UDPsender(socket, ports);
            udpServer.start();
            } catch (IOException e) {
            System.err.println(e);
        }
        
    }
    // attention il me semble le thread client vient de l'entité emetrice
    /*
     public void openClient() {
     // Start the listening UDP client on port 1314 and with 1024 bytes packets size
    
     this.UDPclient = new UDP_Server(1313, 1024);
     UDPserver.start();
     }*/

    //appelé par le controler pour créer le localInfo
    // permet de retrouver notre adresse ip 
    public String getMyIP() {
        /*try {
         return InetAddress.getLocalHost().getHostAddress();
         } catch (IOException e) {
         System.err.println("no IP adress for local user");
         return null;
         }*/
        return "127.0.0.1";
    }
    
    public String getIP(){
        boolean notFound = true;
        InetAddress addrIP = null;
        try{
            Enumeration ni = NetworkInterface.getNetworkInterfaces();
            
            while (ni.hasMoreElements() && notFound){

                NetworkInterface i = (NetworkInterface) ni.nextElement();
                if ((i.getName().equals("eth0")) || (i.getName().equals("wlan0"))){                    
                for (Enumeration en = i.getInetAddresses(); en.hasMoreElements();){
                    InetAddress addr = (InetAddress) en.nextElement();
                    if(addr instanceof Inet4Address){
                             addrIP = addr ;
                             
                    }
                }
                }                                             
            }      
        }catch(IOException e){
            System.err.println(e);
        }
            return addrIP.getHostAddress();

      }

    // reconstruit l'adresse de broadcast quand on a l'adresse du réseau local
    public String getBroadcast() {
        String local_ip = ChatSystem.getModel().getLocalAdress();
        String broadcastIP = "";
        int cnt = 0;
        int i = 0;

        while (cnt != 3 && i < local_ip.length()) {
            if (local_ip.charAt(i) == '.') {
                cnt++;
            }
            broadcastIP += local_ip.charAt(i);
            i++;
        }
        //broadcastIP += "255";
        return "255.255.255.255";
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
        System.out.println("DEBUG *** NETWORK : sendHello , userName = " + u + " ***");
        Hello helloMessage = new Hello(u);
        try {
            // a utiliser quand on est en réseaux !!)
            udpSender.send(helloMessage,InetAddress.getByName(getBroadcast()));
        } catch (IOException ex){
            System.err.println(ex);
        } catch(SignalTooBigException ex) {           
            System.err.println(ex);
        }
    }

    @Override
    //appelé quand on a recu un hello
    public void sendHelloOk(String username) {
        System.out.println("DEBUG *** NETWORK : sendHelloOK , username = " + username + " ***");
        HelloOK helloOKmessage = new HelloOK(username);
        
        try {
            udpSender.send(helloOKmessage, InetAddress.getByName(ChatSystem.getModel().getRemoteIp(ChatSystem.getModel().getReceiverName())));
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        }
    }

    @Override
    // appelé quand on envoie un message
    public void processSendMessage(String message, String remoteName) {
        System.out.println("DEBUG *** NETWORK : processSendMessage , remoteName = " + remoteName + " message = " + message + " ***");
        ArrayList<String> receiverList = new ArrayList<String>(1);
        receiverList.add(remoteName);
        TextMessage m = new TextMessage(message, ChatSystem.getModel().getUsername(), receiverList);
        System.out.println(ChatSystem.getModel().getRemoteIp(remoteName));
        try {
            udpSender.send(m, InetAddress.getByName(ChatSystem.getModel().getRemoteIp(remoteName)));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            System.err.println(ex);
        } catch(SignalTooBigException ex) {           
            System.err.println(ex);
        }
    }

    @Override
    // appelé quand on veut envoyer un fichier
    public void processSendFile(File file,long size,String remoteName) {
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
    // appelé quand on se disconnect
    public void sendGoodbye(String username) {
        System.out.println("DEBUG *** NETWORK : sendGOODBYE , localName = " + username + " ***");
        
        Goodbye gb = new Goodbye(username);
        try {
            udpSender.send(gb, InetAddress.getByName(getBroadcast()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            System.err.println(ex);
        } catch(SignalTooBigException ex) {           
            System.err.println(ex);
        }
    }

    /*
     * FIN FROM CTRL
     */
    /*
     * Fonction qui recoit les signaux de l'udp server et envoie au ctrl les actions a faire
     */
    // traitement du signal
    public void signalProcess(Signal s) { // a modifier
        if (s instanceof Hello) {
            ChatSystem.getControler().performHello(((Hello) s).getUsername());
        } else if (s instanceof HelloOK) {
            ChatSystem.getControler().performHelloOk(((HelloOK) s).getUsername());
        } else if (s instanceof Goodbye) {
            ChatSystem.getControler().performGoodbye(((Goodbye) s).getUsername());
        } else if (s instanceof TextMessage) {
            ChatSystem.getControler().performTextMessage(((TextMessage) s).getMessage(), ((TextMessage) s).getFrom());
        }
    }

}
