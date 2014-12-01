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
    // lancement du thread
    public void run() {
        try {
            //initialisation buffer avec la taille
            System.out.println("DEBUG *** UDPserver : run ***");
            byte[] buffer = new byte[Signal.MAX_SIZE];
            // reception du datagram
            while (true) {
                receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);
                System.out.println("DEBUG *** UDPserver : received packet = " + receivePacket + " ***");
                //envoie du signal au network
                ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            }
        } catch (IOException ex){ 
            System.err.println(ex);
        }catch (ClassNotFoundException ex){
            System.err.println(ex);
        }
        
        
    }
    
}
