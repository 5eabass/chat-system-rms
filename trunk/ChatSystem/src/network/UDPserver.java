package network;

import chatsystem.ChatSystem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import signals.*;

/** 
     * This class is the UDPserver thread 
     * @return 
     */
public class UDPserver extends Thread {
    
    private DatagramPacket receivePacket;
    private DatagramSocket serverSocket;
    
    /** 
     * This constructor creates a new socket  
     * @return 
     */
    public UDPserver(DatagramSocket s) {
            this.serverSocket = s;
    }
    
    /** 
     * This run method launch the reception of messages from remote users 
     * @return 
     */
    @Override
    public void run() {
        try {
            System.out.println("DEBUG *** UDPserver : run ***");
            // size initialisation
            byte[] buffer = new byte[Signal.MAX_SIZE];
            while (true) {
                // packet reception
                receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);
                System.out.println("DEBUG *** UDPserver : received packet = " + receivePacket + " ***");
                // send the signal to the network
                ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            }
        } catch (IOException ex){ 
            System.err.println(ex);
        }catch (ClassNotFoundException ex){
            System.err.println(ex);
        }
        
        
    }
    
}
