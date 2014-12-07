package network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import signals.FileProposal;

public class TCPserver extends Thread {

    private ServerSocket ss;
    private Socket s0;
    private Vector<TCPreceiver> rList;
    private int port, max_connexion;
    private FileProposal fileProposal;

    public TCPserver(FileProposal fp) {
        this.rList = new Vector<TCPreceiver>();
        this.port = fp.getPort();
        this.fileProposal = fp;
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
                rList.add(new TCPreceiver(s0, fileProposal));
                rList.lastElement().start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
