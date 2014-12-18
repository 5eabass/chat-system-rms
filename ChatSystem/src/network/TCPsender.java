package network;

import chatsystem.ChatSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
     * This class is the TCPsender class and permits to send files in TCP to remote chat systems
     * @return 
     */
public class TCPsender extends Thread {
    
    private Socket s1;
    private OutputStream writer;
    private InputStream reader;
    private byte[] buffer;
    private long size;
    private File file;
    
  /** 
     * This constructor initializes in emission the socket, the buffer, the name of the file and his size
     * @return 
     */
    public TCPsender(Socket s, File file, String remoteName, long size) {
        
        this.s1 = s;
        this.file = file;
        this.size = size;
        this.buffer = new byte[(int) size];
    }
    
    /** 
     * This method send a new connection establishement, send the file to the remote users and close the connection
     * @return 
     */
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
    
    /** 
     * This method is called when we are sending a new connection establishement 
     * @return 
     */

    private void connectionEstablishement() {
        try {
            writer = s1.getOutputStream();
            //reader = s1.getInputStream();
            System.out.println("DEBUG *** TCPsender : Connection established ***");
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /** 
     * This method is called when we send the file
     * @return 
     */

    public void sendFile() {
        System.out.println("DEBUG *** TCPsender : start sending ***");
        try {
            int sizeWroten = 0;
            byte[] buf = new byte[(int) size];
            FileInputStream fis = new FileInputStream(file);
            sizeWroten = fis.read(buf);
            this.writer.write(buf);
            this.writer.flush();
            if (sizeWroten != size){
                ChatSystem.getNetwork().transferNotification(false);
            }else{
                ChatSystem.getNetwork().transferNotification(true);
            }
            fis.close();
        } catch (FileNotFoundException ex) {
            ChatSystem.getNetwork().transferNotification(false);
            System.err.println(ex);
        } catch (IOException ex) {
            ChatSystem.getNetwork().transferNotification(false);
            System.err.println(ex);
        }
        System.out.println("DEBUG *** TCPsender : File sent ***");
    }
    
    /** 
     * This method is called when we close the connection
     * @return 
     */
    
    public void connectionTearDown() {
        try {
            this.s1.close();
            System.out.println("DEBUG *** TCPsender : Closing the connection ***");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        
    }
}
