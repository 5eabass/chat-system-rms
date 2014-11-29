package network;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

class Connection extends Thread {

    DataInputStream input;
    DataOutputStream output;
    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    public void run() {
        try {
            //  On lit le fichier et on l ecrit dans le buffer;

            FileWriter out = new FileWriter("test.txt");
            BufferedWriter bufWriter = new BufferedWriter(out);

            // On lit la taille du fichier
            int nb = input.readInt();
            System.out.println("Read Length" + nb);
            byte[] digit = new byte[nb];

            // On lit les bytes
            System.out.println("Writing.......");
            for (int i = 0; i < nb; i++) {
                digit[i] = input.readByte();
            }

            //String st = new String(digit);
            String st = input.readLine();
            bufWriter.append(st);
            bufWriter.close();
            System.out.println("receive from : "
                    + clientSocket.getInetAddress() + ":"
                    + clientSocket.getPort() + " message - " + st);

            // On envoie la taille
            output.writeInt(st.length());
            //Step 2 send length
            output.writeBytes(st); // UTF is a string encoding
            //  output.writeUTF(data);

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {/*close failed*/

            }
        }
    }

}
