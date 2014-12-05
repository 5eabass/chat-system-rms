package network;

import chatsystem.ChatSystem;
import chatsystem.Controler;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Signal;

class TCPreceiver extends Thread {

    private Socket s0;
    private InputStream reader;

    public TCPreceiver(Socket s) {
        this.s0 = s;
        try {
            this.reader = s0.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(TCPreceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        for (;;) {
            try {
                byte[] buffer = new byte[Signal.MAX_SIZE];
                reader.read(buffer);
                //envoie du signal au network
                ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            } catch (IOException ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPreceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Socket getSocket() {
        return s0;
    }
}
