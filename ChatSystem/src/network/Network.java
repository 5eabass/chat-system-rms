package network;

import chatsystem.ChatSystem;
import java.io.IOException;
import java.net.InetAddress;
import signals.*;
import interfaces.*;
import java.io.File;
import java.util.ArrayList;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network implements CtrlToNetwork {

    private DatagramSocket socket;
    private UDPserver udpServer;
    private UDPsender udpSender;
    private TCPserver tcpServer;
    private TCPsender tcpSender;
    private int ports, portd;

    public Network() {
        this.ports = 4445;
        this.portd = 4444;
        //this.setIPs(); pas utile , on set les ip des qu'on connect
    }

    public void openUDP() {
        try {
            socket = new DatagramSocket(portd);           
            this.udpServer = new UDPserver(socket);
            System.out.println("DEBUG *** Network : socket created on port : " + portd + " ***");
            this.udpSender = new UDPsender(socket,ports);
            udpServer.start();
        } catch (SocketException ex) {
            System.err.println(ex);
        }
    }

    public void openTCP() {
        this.tcpServer = new TCPserver(portd, 5);
        this.tcpServer.start();
    }

    public String getIPs() {
        boolean notFound = true;
        InetAddress addrIP = null;
        InetAddress broadcast = null;
        try {
            Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
            while (ni.hasMoreElements() && notFound) {
                NetworkInterface i = (NetworkInterface) ni.nextElement();
                if ((i.getName().equals("eth0")) || (i.getName().equals("wlan0") || (i.getName().equals("en1")))) { // a changer par eth0 sur ubuntu
                    notFound = false;
                    List<InterfaceAddress> list = i.getInterfaceAddresses();
                    Iterator<InterfaceAddress> it = list.iterator();
                    for (Enumeration en = i.getInetAddresses(); en.hasMoreElements();) {
                        InterfaceAddress ia = it.next();
                        InetAddress addr = (InetAddress) en.nextElement();
                        if (addr instanceof Inet4Address) {
                            addrIP = addr;
                            broadcast = ia.getBroadcast();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("error");
        }
        //this.ipAdress = addrIP.getHostAddress();
        //this.bcAdress = broadcast.getHostAddress();
        if (addrIP == null) {
            System.err.println("DEBUG *** Network setIP : could not find any localIP ***");
            return "";
        } else {
            return addrIP.getHostAddress() + '@' + broadcast.getHostAddress();
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
        System.out.println("DEBUG *** NETWORK : sendHello , userName = " + u + " ***");
        Hello helloMessage = new Hello(u);
        try {
            // a utiliser quand on est en réseaux !!)
            udpSender.send(helloMessage, InetAddress.getByName(ChatSystem.getModel().getAdresseBroadcast()));
        } catch (IOException | SignalTooBigException ex) {
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
        } catch (IOException | SignalTooBigException ex) {
            System.err.println(ex);
        }
    }

    @Override
    // appelé quand on envoie un message
    public void processSendMessage(String message, String remoteName) {
        System.out.println("DEBUG *** NETWORK : processSendMessage , remoteName = " + remoteName + " message = " + message + " ***");
        ArrayList<String> receiverList = new ArrayList<String>();
        receiverList.add(remoteName);
        TextMessage m = new TextMessage(message, ChatSystem.getModel().getUsername(), receiverList);
        try {
            udpSender.send(m, InetAddress.getByName(ChatSystem.getModel().getRemoteIp(remoteName)));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SignalTooBigException ex) {
            System.err.println(ex);
        }
    }

    @Override
    // appelé quand on veut envoyer un fichier
    public void processSendFile(File file, long size, String remoteName) {
        System.out.println("DEBUG *** NETWORK : processSendFile <= ask to send a file ***");
        ArrayList<String> receiverList = new ArrayList<String>();
        receiverList.add(remoteName);
        try {
            // Connection establishement
            this.tcpSender = new TCPsender(InetAddress.getByName(ChatSystem.getModel().getRemoteIp(remoteName)),ports);
            this.tcpSender.start();
            // File proposal
            this.tcpSender.FileProposal(remoteName, size, ChatSystem.getModel().getLocalAdress(), receiverList);
            // File transfer
            this.tcpSender.send(new FileTransfer(file));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            udpSender.send(gb, InetAddress.getByName(ChatSystem.getModel().getAdresseBroadcast()));
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException | SignalTooBigException ex) {
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
        } else if (s instanceof FileProposal) {
            ChatSystem.getControler().processFileQuery(((FileProposal) s).getFileName(), ((FileProposal) s).getSize(), ((FileProposal) s).getFrom());
        } else if (s instanceof FileTransfer) {
            //
        } else if (s instanceof FileTransferOK) {
            this.tcpSender.FileTransfer(((FileTransfer) s).getFile());
        } else if (s instanceof FileTransferNOK) {
            this.tcpSender.ConnectionTearDown();
        }
    }

}
