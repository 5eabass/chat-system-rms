package network;

import chatsystem.ChatSystem;
import chatsystem.Controler;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.FileProposal;
import signals.Signal;

class TCPreceiver extends Thread {

    private Socket s0;
    private InputStream reader;
    private FileProposal fileProposal;

    public TCPreceiver(Socket s, FileProposal fp) {
        this.s0 = s;
        this.fileProposal = fp;
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
                ChatSystem.getControler().processTransmission(buffer, fileProposal);
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
