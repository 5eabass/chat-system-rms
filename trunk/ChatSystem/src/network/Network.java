package network;

import chatsystem.*;
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
import java.net.Socket;
import java.net.SocketException;
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
    private Vector<TCPsender> tcpSender;
    private Vector<TCPserver> tcpServer;
    private int ports, portd;

    private Vector<FileProposal> proposalList; // file proposal we sent

    public Network() {
        this.proposalList = new Vector<FileProposal>();
        this.ports = 4444;
        this.portd = 4445;
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

    public void openTCP(FileProposal fp) {
        this.tcpServer.add(new TCPserver(fp));
        this.tcpServer.lastElement().start();
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
    // Call when we send a file proposal
    public void processSendProposal(File file, long size, ArrayList<String> receivers) {
        System.out.println("DEBUG *** NETWORK : processSendProposal <= ask to send a file ***");
        ChatSystem.getModel().setFileToSend(file);
        for (String s : receivers) {
            try {
                proposalList.add(new FileProposal(file.getName(), size, ChatSystem.getModel().getLocalAdress(), receivers, portd, false));
                InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(s));
                udpSender.send(proposalList.lastElement(), addrIp);
            } catch (SignalTooBigException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }
            System.out.println("DEBUG *** NETWORK : processSendProposal <= Waiting for an answer ***");
        }
    }

    

    @Override
    //appelé quand on refuse le transfer
    public void performRefuseTransfer(FileProposal fp) {
         System.out.println("DEBUG *** NETWORK : performRefuseTransfer <= send that we refuse ***");
        try {     
            InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(fp.getFrom()));
            udpSender.send(fp, addrIp);
        } catch (SignalTooBigException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    //appelé quand on accepte le transfer
    public void performAcceptTransfer(FileProposal fp) {
        // Open a tcp Server
        System.out.println("DEBUG *** NETWORK : performAcceptTransfer <= send that we accept ***");
        openTCP(fp);
        try {
            fp.setState(true);
            InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(fp.getFrom()));
            udpSender.send(fp, addrIp);
        } catch (SignalTooBigException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
    
    // Called when we receive a FileProposal answers
    public void processSendFile(FileProposal fp) {
        System.out.println("DEBUG *** NETWORK : processSendFile <= send the file ***");
        if (this.proposalList.contains(fp)) {
            this.proposalList.remove(fp);
            File file = ChatSystem.getModel().getFileToSend();
            try {
                InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(fp.getFrom()));
                Socket s1 = new Socket(addrIp, fp.getPort());
                TCPsender tcpS = new TCPsender(s1, file, fp.getFrom(), fp.getSize());
                this.tcpSender.add(tcpS);
                this.tcpSender.lastElement().start();
                this.tcpSender.remove(tcpS);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }                    
    
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
            if (((FileProposal) s).getState()) {
                this.processSendFile(((FileProposal) s));
            } else {
                ChatSystem.getControler().processFileQuery(((FileProposal) s));
               // ((FileProposal) s).getMessage(), ((FileProposal) s).getFrom(), ((FileProposal) s).get()
               //ChatSystem.getControler().processFileQuery(((FileProposal) s).getFrom(),((FileProposal) s).getFileName(),((FileProposal) s).getSize());
                   
                // ChatSystem.getControler().processAcceptTransfer(((FileProposal) s));
            }
        }
    }

}
