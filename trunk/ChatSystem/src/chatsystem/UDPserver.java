package chatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.*;

public class UDPserver extends Thread {
    
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int len;
    
    public UDPserver(DatagramSocket s) {
        this.serverSocket= s;
        this.len = 1024;
    }
    
    @Override
    // lancement du thread
    public void run() {
        try{
            System.out.println("DEBUG *** UDPserver : run ***");
            //initialisation buffer avec la taille
            byte[] buffer = new byte[len];
            // reception du datagram
            while(true){
                receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);
                System.out.println("DEBUG *** UDPserver : received packet = " +receivePacket+" ***");
                ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
                
            }
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }
        // envoie du signal au network qui le traite
        
        
        
    }
    
}
