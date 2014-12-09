package network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Signal;

public class TCPsender extends Thread {

    private Socket s1;
    private OutputStream writer;
    private InputStream reader;
    private byte[] buffer;
    private long size;
    private File file;

    public TCPsender(Socket s, File file, String remoteName, long size) {

        this.s1 = s;
        this.file = file;
        this.size = size;
        this.buffer = new byte[(int)size];
    }

    @Override
    public void run() {
        connectionEstablishement();
        sendFile();

        try {
            // Wainting for an ack from the remote server then close the connection
            reader.read(buffer);
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }

        connectionTearDown();
        // appeler transmission ( chez ctrl) dire fichier bien recu par le remote
    }

    private void connectionEstablishement() {
        try {
            writer = s1.getOutputStream();
            reader = s1.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DEBUG *** TCPsender : Connection established ***");
    }

    public void sendFile() {

        FileInputStream fis = null;
        try {
            byte[] buf = new byte[(int) size];
            fis = new FileInputStream(file);
            fis.read(buf);
            this.writer.write(buf);
            this.writer.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("DEBUG *** TCPsender : File sent ***");
    }

    public void connectionTearDown() {
        try {
            this.s1.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DEBUG *** TCPsender : Closing the connection ***");
    }
}
