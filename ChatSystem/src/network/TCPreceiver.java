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
        } catch (IOException ex) {
            Logger.getLogger(TCPreceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void receive() {
        System.out.println("Receiver started ***************");
        try {
            int sizeRead = 0;
            byte[] buffer = new byte[size];
            while (sizeRead < size) {
                sizeRead += reader.read(buffer, sizeRead, (size - sizeRead));
                System.out.println("Received : " + sizeRead + "/" + size + "\n");
            }
            //ajouter l'enregistrement du fichier qui etait fait par processTransmission
            ChatSystem.getControler().processTransmission(buffer, fileName);
            reader.close();
            s0.close();
            System.out.println("Close on my side ***************");
            // appeler receipt pour notifier l'utilisateur comme quoi on a bien recu le fichier
        } catch (IOException ex) {
            Logger.getLogger(Controler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
