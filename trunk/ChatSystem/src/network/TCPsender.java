package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPsender extends Thread {

    final int TAILLE_MAX = 5; // Taille max de fichier de 5 Mo 
    private Socket s0;
    private InetAddress addrIp;
    private int dPort;

    public TCPsender(InetAddress a, int p) {

        this.s0 = null;
        this.addrIp = a;
        this.dPort = p;

        openClient();
    }

    TCPsender(double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void openClient() {

        try {
            s0 = new Socket(addrIp, dPort);
        } catch (IOException ex) {
            Logger.getLogger(TCPsender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendFichier(File fichier) {
        try {
            DataInputStream input = new DataInputStream(s0.getInputStream());
            DataOutputStream output = new DataOutputStream(s0.getOutputStream());
            System.out.println(">>> Waiting for some file to send...");
            FileReader reader;
            reader = new FileReader("chanson.txt");
            // On envoie la taille du fichier
            if (fichier.length() > TAILLE_MAX) {
                System.out.println("Le fichier que vous essayez d'envoyer dépasse la taille requise");
            } else {
                System.out.println("Length" + fichier.length());

                // output.writeInt((int) fichier.length());
                
                System.out.println("Writing.......");
                output.writeUTF(reader.toString());
               // output.writeBytes(fichier.toString());

                //Step 1 read length
                int nb = input.readInt();
                byte[] digit = new byte[nb];
                //Step 2 read byte
                for (int i = 0; i < nb; i++) {
                    digit[i] = input.readByte();
                }

                String st = new String(digit);
                System.out.println("Received: " + st);
            }
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (s0 != null) {
                try {
                    s0.close();
                } catch (IOException e) {/*close failed*/

                }
            }
        }
    }
}
