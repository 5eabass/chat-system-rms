package network;

import chatsystem.ChatSystem;
import chatsystem.Controler;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class TCPreceiver {
    
    private Socket s0;
    private InputStream reader;
    private String fileName;
    private int size;
    
    public TCPreceiver(Socket s, String f, int size) {
        this.s0 = s;
        this.size = size;
        this.fileName = f;
        try {
            this.reader = s0.getInputStream();
            System.out.println("DEBUG *** TCPreceiver created ***");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void receive() {
        System.out.println("DEBUG *** TCPreceiver started ***");
        int sizeRead = 0;
        byte[] buffer = new byte[size];
        try {
            // read the whole buffer received
            while (sizeRead < size) {
                sizeRead += reader.read(buffer, sizeRead, (size - sizeRead));
                System.out.println("DEBUG *** TCPreceiver received :" + sizeRead + "/" + size + " ***");
            }
            /*
            *
            * a changer : passer par le NI puis ctrl ( on a juste a retourner le buffer et le fileName
            *
            */
            // send the buffer to the controler to make the file
            ChatSystem.getControler().processTransmission(buffer, fileName);
            reader.close();
            s0.close();
            System.out.println("DEBUG *** TCPreceiver closing ***");
            // appeler receipt pour notifier l'utilisateur comme quoi on a bien recu le fichier
            // ChatSystem.getControler().processReceipt();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
