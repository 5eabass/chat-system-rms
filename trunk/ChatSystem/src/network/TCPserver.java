package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver extends Thread {

    private ServerSocket ss;
    private Socket s0;
    private BufferedReader reader;
    // private BufferedWriter writer;
    private InputStreamReader in;
    private OutputStreamWriter out;
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
                in = new InputStreamReader(s0.getInputStream());
                // out = new OutputStreamWriter(s0.getOutputStream());
                // writer = new BufferedWriter(out);
                reader = new BufferedReader(in);

                r0 = new TCPreceiver(reader);
                r0.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
