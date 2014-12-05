package network;

import chatsystem.ChatSystem;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.FileProposal;
import signals.FileTransfer;
import signals.FileTransferNOK;
import signals.FileTransferOK;
import signals.Signal;
import signals.SignalTooBigException;

public class TCPsender extends Thread {

    private Socket s1;
    private OutputStreamWriter out;
    private BufferedWriter writer;
    private InetAddress addrIp;
    private int dPort;
    private InputStream reader;
    private byte[] buffer;

    public TCPsender(Socket s, InetAddress a, int p) {

        this.s1 = s;
        this.addrIp = a;
        this.dPort = p;
    }

    @Override
    public void run() {
        ConnectionEstablishement();
        // Faire le sendFile ici sinon le thread s'arrete !
    }

    public void sendFile(File file, String remoteName, long size, String ipAdress, ArrayList<String> receiverList) {

        try {
            FileProposal(remoteName, size, ipAdress, receiverList);
            reader.read(buffer);
            Signal s = Signal.fromByteArray(buffer);
            if (s instanceof FileTransferNOK) {
                this.ConnectionTearDown();
            } else if (s instanceof FileTransferOK) {
                byte[] buf = new byte[(int) size];
                FileInputStream fis = new FileInputStream(file);
                fis.read(buf);
                FileTransfer(buf);
                reader.read(buffer);
                this.ConnectionTearDown();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void ConnectionEstablishement() {
        try {
            System.out.println("DEBUG *** TCPsender : TCP socket created on IP/port : " + addrIp + "/" + dPort + " ***");
            out = new OutputStreamWriter(s1.getOutputStream());
            writer = new BufferedWriter(out);
            reader = s1.getInputStream();
            buffer = new byte[Signal.MAX_SIZE];
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FileProposal(String remoteName, long size, String ipAdress, ArrayList<String> receiverList) {
        try {
            FileProposal fp = new FileProposal(remoteName, size, ipAdress, receiverList);
            byte[] buf = Signal.toByteArray(fp);
            this.writer.write(buf.toString());
            this.writer.newLine();
            this.writer.flush();
            System.out.println("DEBUG *** TCPsender : FileProposal signal sent ***");
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignalTooBigException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FileTransfer(byte[] buff) {
        try {
            this.writer.write(buff.toString());
            this.writer.newLine();
            this.writer.flush();
            System.out.println("DEBUG *** TCPsender : Transfer accepted, FileTransfer signal sent ***");
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void FileTransferOK() {
        try {
            FileTransferOK fto = new FileTransferOK();
            byte[] buf = Signal.toByteArray(fto);
            this.writer.write(buf.toString());
            this.writer.newLine();
            this.writer.flush();
            System.out.println("DEBUG *** TCPsender : Sending transfer agreed ***");
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignalTooBigException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void FileTransferNOK() {
        try {
            FileTransferNOK ftno = new FileTransferNOK();
            byte[] buf = Signal.toByteArray(ftno);
            this.writer.write(buf.toString());
            this.writer.newLine();
            this.writer.flush();
            System.out.println("DEBUG *** TCPsender : Sending transfer refused, closing ***");
            this.ConnectionTearDown();
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignalTooBigException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ConnectionTearDown() {
        try {
            this.s1.close();
            System.out.println("DEBUG *** TCPsender : Transfer refused, closing the connection ***");
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
