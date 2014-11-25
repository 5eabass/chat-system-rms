package chatsystem;

import signals.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPsender  {
    
    private DatagramSocket socket;
    
    public UDPsender(DatagramSocket s) {
        this.socket=s;
    }
    
    public void send(Signal s,InetAddress adrIP) throws SignalTooBigException, IOException {
        
        try{
            System.out.println("DEBUG *** UDPsender : send ***");
            // on crée la socket udp
            // on sérialize
            byte[] buf = Signal.toByteArray(s);
            System.out.println("DEBUG *** UDPsender : signal to byte array = "+buf.toString()+"  ***");
            //on crée notre datagram
            DatagramPacket packet = new DatagramPacket(buf, buf.length,adrIP, 1234);
            // on envoie
            socket.send(packet);
            System.out.println("DEBUG *** UDPsender : message sent ***");
        }catch(SignalTooBigException e){
            System.err.println(e);
        }  
    }
}