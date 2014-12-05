package network;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TCPserver extends Thread {

    private ServerSocket ss;
    private Socket s0;
    private Vector<TCPreceiver> rList;
    private int port, max_connexion;

    public TCPserver(int p, int c) {
        this.port = p;
        this.max_connexion = c;
    }

    @Override
    public void run() {
        try {
            // Ouverture d'une entrée socket sur le port 1313, acceptant 5 flux
            ss = new ServerSocket(port, max_connexion);

            for (;;) {
                // Acceptation d'un flux d'entrée socket
                s0 = ss.accept();
                rList.add(new TCPreceiver(s0));
                rList.lastElement().start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public Socket getPendingSocket(int i) {
        return rList.elementAt(i).getSocket();
    }
}
