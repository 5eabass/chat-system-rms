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

public class Network implements CtrlToNetwork,ReceiverToNetwork {
    
    private DatagramSocket socket; // the udp socket
    private UDPserver udpServer;
    private UDPsender udpSender;
    private TCPsender tcpSender;
    private TCPserver tcpServer;
    private int ports, portd; // we differenciate 2 ports to test on the same laptop
    private Vector<FileProposal> proposalList; // file proposal we sent
    
    public Network() {
        this.proposalList = new Vector<FileProposal>();
        this.ports = 4444;
        this.portd = 4444;
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
    
    // function that find our @ip and the broadcast@ depending on our network
    public String getIPs() {
        boolean notFound = true;
        InetAddress addrIP = null;
        InetAddress broadcast = null;
        String os = System.getProperty("os.name");
        
        try {
            // if we have Mac os X or Windows the following works
            if (os.contains("Windows") || os.contains("Mac")){
                // we find our @ip
                boolean found = false;
                addrIP = InetAddress.getLocalHost();
                NetworkInterface i = NetworkInterface.getByInetAddress(addrIP);
                
                // we enumerate interface's @ip to find the broadcast one
                List<InterfaceAddress> list = i.getInterfaceAddresses();
                Iterator<InterfaceAddress> it = list.iterator();
                Enumeration en = i.getInetAddresses();
                while(en.hasMoreElements() && !found) {
                    InterfaceAddress ia = it.next();
                    InetAddress addr = (InetAddress) en.nextElement();
                    
                    // we choose the ipv4 one and its @broadcast associated
                    if (addr instanceof Inet4Address) {
                        found = true;
                        broadcast = ia.getBroadcast();
                        break;
                    }
                }
                // else several Ubuntu versions doesn't fit with the previous lines so we need to do this :
            }else if (os.contains("Linux")){
                
                //enumeration of the interface we have
                Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
                while (ni.hasMoreElements() && notFound) {
                    NetworkInterface i = (NetworkInterface) ni.nextElement();
                    // if the interface we are checking is eth0 or wlan0 then we suppose it's found
                    // note : we suppose wlan0 and eth0 or not active in the same time
                    if ((i.getName().equals("eth0")) || (i.getName().equals("wlan0"))){
                        notFound = false ;
                        List<InterfaceAddress> list = i.getInterfaceAddresses();
                        Iterator<InterfaceAddress> it = list.iterator();
                        
                        //we enumerate each @IP of the interface , we can have ipv6 and ipv4
                        for (Enumeration en = i.getInetAddresses(); en.hasMoreElements();) {
                            InterfaceAddress ia = it.next();
                            InetAddress addr = (InetAddress) en.nextElement();
                            
                            // we choose the ipv4 one and its @broadcast associated
                            if (addr instanceof Inet4Address) {
                                addrIP = addr;
                                broadcast = ia.getBroadcast();
                                
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("error");
        }
        
        if (addrIP == null) {
            System.err.println("DEBUG *** Network setIP : could not find any localIP ***");
            return "";
        } else {
            return addrIP.getHostAddress() + '@' + broadcast.getHostAddress();
        }
    }
    
    /*
    * FROM CONTROLER
    */
    
    // called when we send a Hello
    @Override
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
    
    // called when we send a helloOk
    @Override
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
    
    // called when we send a Message
    @Override
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
    
    // Called when we send a file proposal
    @Override
    public void processSendProposal(File file, long size, ArrayList<String> receivers) {
        System.out.println("DEBUG *** NETWORK : processSendProposal "+ file.getName() +" <= ask to send a file ***");
        ChatSystem.getModel().setFileToSend(file);
        for (String s : receivers) {
            try {
                proposalList.add(new FileProposal(file.getName(), size, ChatSystem.getModel().getUsername(), receivers));
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
    
    // called when we refuse a transfer
    @Override
    public void processRefuseTransfer(FileProposal fp) {
        System.out.println("DEBUG *** NETWORK : processRefuseTransfer <= send that we refuse ***");
        FileTransferNotAccepted ftr = new FileTransferNotAccepted(fp.getFileName(), ChatSystem.getModel().getUsername());
        
        try {
            InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(fp.getFrom()));
            udpSender.send(ftr, addrIp);
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    // called when we accept a transfer
    @Override
    public void processAcceptTransfer(FileProposal fp) {
        System.out.println("DEBUG *** NETWORK : processAcceptTransfer <= send that we accept ***");
        FileTransferAccepted fta = new FileTransferAccepted(fp.getFileName(), ChatSystem.getModel().getUsername());
        tcpServer = new TCPserver(fp.getFileName(),(int)fp.getSize(), portd);
        tcpServer.start();
        if (tcpServer.isAlive()){
            try {
                InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(fp.getFrom()));
                udpSender.send(fta, addrIp);
            } catch (SignalTooBigException ex) {
                System.err.println(ex);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        else{
            processRefuseTransfer(fp);
            transferNotification(false);
        }
        
    }
    
    // called when we send a goodbye
    @Override
    public void sendGoodbye(String username) {
        System.out.println("DEBUG *** NETWORK : send GOODBYE , name sent = " + username + " ***");
        Goodbye gb = new Goodbye(username);
        try {
            udpSender.send(gb, InetAddress.getByName(ChatSystem.getModel().getAdresseBroadcast()));
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
    * function that receive Signals and manage them
    */
    
    public void signalProcess(Signal s) { // a modifier
        System.out.println("DEBUG *** NETWORK : signal processing ***");
        if (s instanceof Hello) {
            System.out.println("DEBUG *** NETWORK : received Hello ***");
            ChatSystem.getControler().performHello(((Hello) s).getUsername());
        } else if (s instanceof HelloOK) {
            System.out.println("DEBUG *** NETWORK : received HelloOk ***");
            ChatSystem.getControler().performHelloOk(((HelloOK) s).getUsername());
        } else if (s instanceof Goodbye) {
            System.out.println("DEBUG *** NETWORK : received goodbye ***");
            ChatSystem.getControler().performGoodbye(((Goodbye) s).getUsername());
        } else if (s instanceof TextMessage) {
            System.out.println("DEBUG *** NETWORK : received textMessage ***");
            ChatSystem.getControler().performTextMessage(((TextMessage) s).getMessage(), ((TextMessage) s).getFrom(), ((TextMessage) s).getTo());
        } else if (s instanceof FileProposal) {
            System.out.println("DEBUG *** NETWORK : received a fileProposal ***");
            ChatSystem.getControler().performFileQuery(((FileProposal) s));
        } else if (s instanceof FileTransferAccepted) {
            System.out.println("DEBUG *** NETWORK : transfer accepted ***");
            ChatSystem.getControler().performFileAnswer(true);
            this.processSendFile(((FileTransferAccepted) s));
        } else if(s instanceof FileTransferNotAccepted){
            System.out.println("DEBUG *** NETWORK : transfer refused ***");
            ChatSystem.getControler().performFileAnswer(false);
        }
    }
    
    // Called when we receive an accept to our file query
    public void processSendFile(FileTransferAccepted fa) {
        System.out.println("DEBUG *** NETWORK : processSendFile received accept for :"+fa.getFileName()+" ***");
        
        File file = ChatSystem.getModel().getFileToSend();
        if (file.getName().equals(fa.getFileName())){
            System.out.println("DEBUG *** NETWORK : sending " + file.getName() + " <= send the file ***");
            try {
                InetAddress addrIp = InetAddress.getByName(ChatSystem.getModel().getRemoteIp(fa.getRemoteUsername()));
                Socket s1 = new Socket(addrIp, portd);
                // nouvelle partie
                tcpSender = new TCPsender(s1, file, addrIp.getHostName(), file.length());
                tcpSender.start();
                tcpSender = null;
                // this.tcpSender.add(tcpS);
                // this.tcpSender.lastElement().start();
                // this.tcpSender.remove(tcpS);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }else{
            transferNotification(false);
        }
    }
    
    public void transferNotification(boolean b){
        System.out.println("DEBUG *** NETWORK : transferNotification <= when we sent or not a file ***");
        ChatSystem.getControler().performTransferNotification(b);
    }
    
    /*
    * From TCPreceiver
    */
    
    //send the buffer to the controler to create the file
    @Override
    public void receivedFile(byte[] buffer,String fileName){
        System.out.println("DEBUG *** NETWORK : receivedFile ==>transfer done sending to ctrl ***");
        ChatSystem.getControler().performTransmission(buffer,fileName);
    }
}