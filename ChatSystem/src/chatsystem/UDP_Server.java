package chatsystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDP_Server extends Thread {

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private ByteArrayInputStream baos;
    private ObjectInputStream oos;
    private int listeningPort, len;

    public UDP_Server(int p, int l) {

        this.listeningPort = p;
        this.len = l;
    }

    @Override
    public void run() {

        openServer();
    }

    private void openServer() {

        try {
            serverSocket = new DatagramSocket(listeningPort);
            byte[] buffer = new byte[len];
            receivePacket = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(receivePacket);

            baos = new ByteArrayInputStream(buffer);
            oos = new ObjectInputStream(baos);
            // Hello h1 = (Hello) oos.readObject();

        } catch (SocketException ex) {
            Logger.getLogger(UDP_Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UDP_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObjectInputStream getObjectInputStream() {
        return oos;
    }
}
