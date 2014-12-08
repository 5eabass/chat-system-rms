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
    private String fileName;

    public TCPreceiver(Socket s, String f) {
        this.s0 = s;
        this.fileName = f;
        try {
            this.reader = s0.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(TCPreceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        System.out.println("Receiver started ***************");
        try {
            byte[] buffer = new byte[Signal.MAX_SIZE];
            reader.read(buffer);
            //ajouter l'enregistrement du fichier qui etait fait par processTransmission
            //ChatSystem.getControler().processTransmission(buffer, fileName);
            reader.close();
            // appeler receipt pour notifier l'utilisateur comme quoi on a bien recu le fichier
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
