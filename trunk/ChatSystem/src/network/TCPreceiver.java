package network;

import chatsystem.ChatSystem;
import chatsystem.Controler;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Signal;

class TCPreceiver extends Thread {

    private final BufferedReader reader;

    public TCPreceiver(BufferedReader r) {
        this.reader = r;
    }

    @Override
    public void run() {
        for (;;) {
            try {
                byte[] buffer = new byte[Signal.MAX_SIZE];
           //     reader.read(buffer);
                //envoie du signal au network
                ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
            } catch (IOException ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPreceiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
