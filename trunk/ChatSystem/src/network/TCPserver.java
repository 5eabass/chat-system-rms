package network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver extends Thread {

    private ServerSocket ss;
    private Socket s0;
    private InputStream in;
    private TCPreceiver r0;
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
                in = s0.getInputStream();
                // out = new OutputStreamWriter(s0.getOutputStream());
                // writer = new BufferedWriter(out);

                r0 = new TCPreceiver(in);
                r0.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
