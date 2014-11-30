package network;

import signals.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPsender {
    
    private DatagramSocket socket;
    private int port;
    
    public UDPsender(DatagramSocket s,int p) {
        this.port = p;    
        this.socket = s;
    }
    
    public void send(Signal s, InetAddress adrIP) throws SignalTooBigException, IOException {
        try {
            System.out.println("DEBUG *** UDPsender : send ***");
            // on crée la socket udp
            // on sérialize
            byte[] buf = Signal.toByteArray(s);
            System.out.println("DEBUG *** UDPsender : signal to byte array = " + buf.toString() + "  ***");
            //on crée notre datagram
            DatagramPacket packet = new DatagramPacket(buf, buf.length, adrIP, port);
            // on envoie
            socket.send(packet);
            System.out.println("DEBUG *** UDPsender : message sent "+adrIP+" ***");
        } catch (SignalTooBigException e) {
            System.err.println(e);
        }
    }
}
