package network;

import chatsystem.ChatSystem;
import java.io.BufferedWriter;
import java.io.File;
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
import signals.Signal;
import signals.SignalTooBigException;

public class TCPsender extends Thread {

    private Socket s1;
    private OutputStreamWriter out;
    private BufferedWriter writer;
    private InetAddress addrIp;
    private int dPort;
    private InputStream reader;

    public TCPsender(InetAddress a, int p) {

        this.s1 = null;
        this.addrIp = a;
        this.dPort = p;
    }

    @Override
    public void run() {
        ConnectionEstablishement();
    }

    public void send(Signal s) {
        try {
            byte[] buf = Signal.toByteArray(s);
            this.writer.write(buf.toString());
            this.writer.newLine();
            this.writer.flush();
            // We are waiting to read a byte ack from the remote user
            // ATTENTION A DEPLACER SINON FERME LA CONNECTION A CHAQUE ENVOI !
            reader = s1.getInputStream();
            byte[] buffer = new byte[1];
            reader.read(buffer);
            // Then we close the connection, means that the file as been well received
            this.ConnectionTearDown();
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SignalTooBigException ex) {
            System.err.println(ex);
        }
    }

    private void ConnectionEstablishement() {
        try {
            s1 = new Socket(addrIp, dPort);
            System.out.println("DEBUG *** TCPsender : TCP socket created on IP/port : " + addrIp + "/" + dPort + " ***");
            out = new OutputStreamWriter(s1.getOutputStream());
            writer = new BufferedWriter(out);
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
            reader = s1.getInputStream();
            byte[] buffer = new byte[Signal.MAX_SIZE];
            reader.read(buffer);
            //envoie du signal au network
            ChatSystem.getNetwork().signalProcess(Signal.fromByteArray(buffer));
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignalTooBigException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void FileTransfer(byte[] buff) {
        try {
            FileTransfer ft = new FileTransfer(buff);
            byte[] buf = Signal.toByteArray(ft);
            this.writer.write(buf.toString());
            this.writer.newLine();
            this.writer.flush();
            System.out.println("DEBUG *** TCPsender : Transfer accepted, FileTransfer signal sent ***");
            reader = s1.getInputStream();
            byte[] buffer = new byte[1];
            reader.read(buffer);
            // Then we close the connection, means that the file as been well received
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

    public String getRemoteName() {
        return this.addrIp.getHostName();
    }
}
