package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Signal;

public class TCPserver extends Thread {

    private ServerSocket s0;
    private int dPort;

    public TCPserver(int p) {

        try {
            this.s0 = new ServerSocket(p);
            this.dPort = p;
        } catch (IOException ex) {
            Logger.getLogger(TCPserver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {
        try {
            System.out.println("DEBUG *** TCPserver : run ***");
            // reception des paquets
            while (true) {

                Socket client = s0.accept();
                Connection c = new Connection(client);
                System.out.println("DEBUG *** TCPserver : listening connection ***");
                // ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
