package network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import chatsystem.*;

public class TCPserver extends Thread {

    private ServerSocket ss;
    private Socket s0;
    private Vector<TCPreceiver> rList;
    private int port, max_connexion, size;
    private String fileName;

    public TCPserver(String fileName, int size, int port) {
        this.rList = new Vector<TCPreceiver>();
        this.port = port;
        this.size = size;
        this.fileName = fileName;
        this.max_connexion = 1;
    }

    @Override
    public void run() {
       // byte[] buffer = new byte[size];
        try {
            System.out.println("DEBUG *** TCPserver launched ***");
            ss = new ServerSocket(port, max_connexion);
            for (;;) {
                // waiting for connection
                s0 = ss.accept();
                System.out.println("DEBUG *** TCPserver Connexion detected ***");
                rList.add(new TCPreceiver(s0, fileName, size));
                rList.lastElement().receive();
                //ChatSystem.getNetwork().performReceivedFile(buffer,fileName);
                System.out.println("DEBUG *** TCPserver going to close ***");
                ss.close();
                break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
