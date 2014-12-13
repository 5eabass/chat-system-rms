package network;


import chatsystem.ChatSystem;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

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
            System.out.println("DEBUG *** TCPreceiver created ***");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void receive() {
        System.out.println("DEBUG *** TCPreceiver started ***");
        int sizeRead = 0;
        byte[] buffer = new byte[size];
        try {
            // read the whole buffer received
            while (sizeRead < size) {
                sizeRead += reader.read(buffer, sizeRead, (size - sizeRead));
                System.out.println("DEBUG *** TCPreceiver received :" + sizeRead + "/" + size + " ***");
            }        
            reader.close();
            s0.close();
            ChatSystem.getNetwork().receivedFile(buffer,fileName);
            System.out.println("DEBUG *** TCPreceiver closing ***");
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
