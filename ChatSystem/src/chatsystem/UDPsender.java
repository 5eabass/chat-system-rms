package chatsystem;

import signals.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPsender  {
    
    private int sendingPort;
    
    public UDPsender(int s) {
        this.sendingPort = s;
    }
    
    public void send(Signal s,InetAddress adrIP) throws SignalTooBigException, IOException {
        
        try{
            // on crée la socket udp
            DatagramSocket socket = new DatagramSocket(sendingPort);
            // on sérialize
            byte[] buf = Signal.toByteArray(s);
            //on crée notre datagram
            DatagramPacket packet = new DatagramPacket(buf, buf.length,adrIP, 1234);
            // on envoie
            socket.send(packet);
            
        }catch(SignalTooBigException e){
            System.err.println(e);
        }
        
    }
}