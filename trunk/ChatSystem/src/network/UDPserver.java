package network;

import chatsystem.ChatSystem;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import signals.*;

public class UDPserver extends Thread {
    
    private DatagramPacket receivePacket;
    private DatagramSocket serverSocket;
    
    public UDPserver(DatagramSocket s) {
            this.serverSocket = s;
    }
    
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
