package chatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import signals.*;

public class UDPserver extends Thread {

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int listeningPort,len;

    public UDPserver(int p) {

        this.listeningPort = p;
        this.len = 1024;
    }

    @Override
    // lancement du thread
    public void run() {
        try{
            openServer();
        }catch(ClassNotFoundException ex){
            System.err.println(ex);
        }
        
    }

    private void openServer() throws ClassNotFoundException {

        try {
            // création socket receiptrice
            serverSocket = new DatagramSocket(listeningPort);
            //initialisation buffer avec la taille 
            byte[] buffer = new byte[len];
            // reception du datagram
            receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket); 
            // envoie du signal au network qui le traite
            ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            
        } catch (SocketException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
