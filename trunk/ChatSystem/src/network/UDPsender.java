package network;

import signals.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/** 
     * This class is the UDPsender class and permits to send messages in UDP to remote chat systems
     * @return 
     */
public class UDPsender {
    
    private DatagramSocket socket;
    private int port;
    
    /** 
     * This constructor creates a new port and socket to send a message 
     * @return 
     */
    
    public UDPsender(DatagramSocket s,int p) {
        this.port = p;    
        this.socket = s;
    }
    
    /** 
     * This methods is called when we send a message
     * @return 
     */
    public void send(Signal s, InetAddress adrIP) throws SignalTooBigException, IOException {
        try {
            System.out.println("DEBUG *** UDPsender : send ***");
            // serialization
            byte[] buf = Signal.toByteArray(s);
            System.out.println("DEBUG *** UDPsender : signal to byte array = " + buf.toString() + "  ***");
            // datagram packet creation + send
            DatagramPacket packet = new DatagramPacket(buf, buf.length, adrIP, port);
            socket.send(packet);
            System.out.println("DEBUG *** UDPsender : message sent "+adrIP+" ***");
        } catch (SignalTooBigException e) {
            System.err.println(e);
        }
    }
}
