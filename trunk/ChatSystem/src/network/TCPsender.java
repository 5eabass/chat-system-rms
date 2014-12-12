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
        this.buffer = new byte[(int) size];
    }

    @Override
    public void run() {
        System.out.println("DEBUG *** TCPsender launch ***");
        connectionEstablishement();
        sendFile();
        connectionTearDown();
        
        /*try {
            // Wainting for an ack from the remote server then close the connection
            System.out.println("DEBUG *** TCPsender : closing ***");
            reader.read(buffer);
            System.out.println("DEBUG *** TCPsender : ack received closing ***");
            connectionTearDown();
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }       */
        // appeler transmission ( chez ctrl) dire fichier bien recu par le remote
    }

    // establish the connection
    private void connectionEstablishement() {
        try {
            writer = s1.getOutputStream();
            //reader = s1.getInputStream();
            System.out.println("DEBUG *** TCPsender : Connection established ***");
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Send the file 
    public void sendFile() {
        System.out.println("DEBUG *** TCPsender : start sending ***");
        FileInputStream fis = null;
        try {
            byte[] buf = new byte[(int) size];
            fis = new FileInputStream(file);
            fis.read(buf);
            this.writer.write(buf);
            this.writer.flush();
            fis.close();
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        System.out.println("DEBUG *** TCPsender : File sent ***");
    }

    // close the connection
    public void connectionTearDown() {
        try {
            this.s1.close();
            System.out.println("DEBUG *** TCPsender : Closing the connection ***");
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }
}
