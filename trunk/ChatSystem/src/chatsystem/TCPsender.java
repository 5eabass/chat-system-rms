package chatsystem;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class TCPsender extends Thread {

    private Socket s0;
    private InetAddress addrIp;
    private int dPort;

    public TCPsender(InetAddress a, int p) {

        this.s0 = null;
        this.addrIp = a;
        this.dPort = p;

        openClient();
    }

    private void openClient() {

        try {
            s0 = new Socket(addrIp, dPort);
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        while (true) {            
            try {
                sleep(3000);
                System.out.println(">>> Waiting for some file to send...");
            } catch (InterruptedException ex) {
                Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

}
