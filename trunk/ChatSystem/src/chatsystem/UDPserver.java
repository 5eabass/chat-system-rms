package chatsystem;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import signals.*;

public class UDPserver extends Thread {

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int len;

    public UDPserver(DatagramSocket s) {
        this.serverSocket = s;
        this.len = 1024;
    }

    @Override
    // lancement du thread
    public void run() {
        try {
            //initialisation buffer avec la taille
            System.out.println("DEBUG *** UDPserver : run ***");
            byte[] buffer = new byte[len];
            // reception du datagram
            while (true) {
                System.err.println("Waiting... ... ...");
                receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);
                System.out.println("DEBUG *** UDPserver : received packet = " + receivePacket + " ***");
                ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        }
        // envoie du signal au network qui le traite

        // envoie du signal au network qui le traite
    }

}
