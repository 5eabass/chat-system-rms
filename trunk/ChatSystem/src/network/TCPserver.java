package network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TCPserver extends Thread {

    private ServerSocket ss;
    private Socket s0;
    private Vector<TCPreceiver> rList;
    private int port, max_connexion;
    private String fileName;

    public TCPserver(String fileName, int port) {
        this.rList = new Vector<TCPreceiver>();
        this.port = port;
        this.fileName = fileName;
        this.max_connexion = 1;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(port, max_connexion);
            for (;;) {
                // Acceptation d'un flux d'entrée socket
                s0 = ss.accept();
                System.out.println("Connexion detected ***************");
                rList.add(new TCPreceiver(s0, fileName));
                rList.lastElement().start();
                if (!rList.lastElement().isAlive()) {
                    ss.close();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
