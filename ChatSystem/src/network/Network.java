package network;

import chatsystem.*;
import java.io.IOException;
import java.net.InetAddress;
import signals.*;
import interfaces.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network implements CtrlToNetwork {

    private DatagramSocket socket;
    private UDPserver udpServer;
    private UDPsender udpSender;
    private TCPserver tcpServer;
    private Vector<TCPsender> tcpSender;
    private int ports, portd;

    public Network() {
        this.ports = 4444;
        this.portd = 4444;
        //this.setIPs(); pas utile , on set les ip des qu'on connect
    }

    public void openUDP() {
        try {
            socket = new DatagramSocket(portd);
            this.udpServer = new UDPserver(socket);
            System.out.println("DEBUG *** Network : socket created on port : " + portd + " ***");
            this.udpSender = new UDPsender(socket, ports);
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
        } catch (IOException ex) {

            System.err.println(ex);
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        }

    }

    @Override
    //appelé quand on a recu un hello
    public void sendHelloOk(String localName, String remoteName) {
        System.out.println("DEBUG *** NETWORK : sendHelloOK , username = " + localName + "to remoteName :" + remoteName + " ***");
        HelloOK helloOKmessage = new HelloOK(localName);

        try {
            udpSender.send(helloOKmessage, InetAddress.getByName(ChatSystem.getModel().getRemoteIp(remoteName)));
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    @Override
    // appelé quand on envoie un message
    public void processSendMessage(String message, ArrayList<String> receivers) {
        System.out.println("DEBUG *** NETWORK : processSendMessage ,message : " + message + " ***");
        TextMessage m = new TextMessage(message, ChatSystem.getModel().getUsername(), receivers);
        try {
            for (String s : receivers) {
                udpSender.send(m, InetAddress.getByName(ChatSystem.getModel().getRemoteIp(s)));
                System.out.println("DEBUG *** NETWORK : message sent to : " + s + " ***");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        }
    }

    @Override
    // appelé quand on veut envoyer un fichier
    public void processSendFile(File file, long size, ArrayList<String> receivers) {
        System.out.println("DEBUG *** NETWORK : processSendFile <= ask to send a file ***");
        try {
            for (String s : receivers) {
            // Connection establishement
                this.tcpSender.add(new TCPsender(InetAddress.getByName(ChatSystem.getModel().getRemoteIp(s)), ports));
                this.tcpSender.lastElement().start();
                // File proposal
                this.tcpSender.lastElement().FileProposal(s, size, ChatSystem.getModel().getLocalAdress(), receivers);
                // File transfer, conversion into bytearray for signal
                byte[] buf = new byte[(int)size];
                FileInputStream fis = new FileInputStream(file);
                fis.read(buf);
                this.tcpSender.lastElement().send(new FileTransfer(buf));
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    //appelé quand on refuse le transfer
    public void performRefuseTransfer(String remoteName) {
        System.out.println("DEBUG *** NETWORK : performRefuseTransfer <= send that we refuse ***");
        try {
            System.out.println("DEBUG *** NETWORK : performAcceptTransfer <= send that we accepte ***");
            this.tcpSender.add(new TCPsender(InetAddress.getByName(ChatSystem.getModel().getRemoteIp(remoteName)), ports));
            this.tcpSender.lastElement().start();
            this.tcpSender.lastElement().send(new FileTransferNOK());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    //appelé quand on accepte le transfer
    public void performAcceptTransfer(String remoteName) {
        try {
            System.out.println("DEBUG *** NETWORK : performAcceptTransfer <= send that we accept ***");
            this.tcpSender.add(new TCPsender(InetAddress.getByName(ChatSystem.getModel().getRemoteIp(remoteName)), ports));
            this.tcpSender.lastElement().start();
            this.tcpSender.lastElement().send(new FileTransferOK());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    // appelé quand on se disconnect
    public void sendGoodbye(String username) {
        System.out.println("DEBUG *** NETWORK : sendGOODBYE , localName = " + username + " ***");
        Goodbye gb = new Goodbye(username);
        try {
            udpSender.send(gb, InetAddress.getByName(ChatSystem.getModel().getAdresseBroadcast()));
            // Fermeture du socket UDP
            udpServer.stop();
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SignalTooBigException ex) {
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
            ChatSystem.getControler().performTextMessage(((TextMessage) s).getMessage(), ((TextMessage) s).getFrom(), ((TextMessage) s).getTo());
        } else if (s instanceof FileProposal) {
            ChatSystem.getControler().processFileQuery(((FileProposal) s).getFileName(), ((FileProposal) s).getSize(), ((FileProposal) s).getFrom());
        } else if (s instanceof FileTransfer) {
            // create a file on our computer in a folder
        } else if (s instanceof FileTransferOK) {
            this.tcpSender.lastElement().FileTransfer(((FileTransfer) s).getFile());
            this.tcpSender.lastElement().ConnectionTearDown();
            System.out.println("DEBUG *** NETWORK : FileTransfer granted, send and close when done ***");
        } else if (s instanceof FileTransferNOK) {
            this.tcpSender.lastElement().ConnectionTearDown();
            System.out.println("DEBUG *** NETWORK : FileTransfer refused, close connection ***");
        }
    }

}
