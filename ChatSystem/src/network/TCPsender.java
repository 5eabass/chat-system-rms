package network;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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
        } catch (IOException | SignalTooBigException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
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
        FileProposal fp = new FileProposal(remoteName, size, ipAdress, receiverList);
        this.send(fp);
        System.out.println("DEBUG *** TCPsender : FileProposal signal sent ***");
    }
    
    public void FileTransfer(File f) {
        this.send(new FileTransfer(f));
        System.out.println("DEBUG *** TCPsender : Transfer accepted, FileTransfer signal sent ***");
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
