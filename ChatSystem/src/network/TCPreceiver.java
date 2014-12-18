package network;


import chatsystem.ChatSystem;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/** 
     * This class is the TCPreceiver class and permits to receive files in TCP from remote chat systems
     * @return 
     */
class TCPreceiver {
    
    private Socket s0;
    private InputStream reader;
    private String fileName;
    private int size;
    
    /** 
     * This constructor initializes in reception the socket, the bufferReader, the name of the file and his size
     * @return 
     */
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
    
    /** 
     * This method is called when we receive a file
     * @return 
     */
    public void receive() {
        System.out.println("DEBUG *** TCPreceiver started ***");
        int sizeRead = 0;
        int reading = 0;
        byte[] buffer = new byte[size];
        try {                                              
            // read the whole buffer received
            while ( (sizeRead < size) && ((reading=reader.read(buffer, sizeRead, (size - sizeRead))) != -1)){ 
              // if ((reading=reader.read(buffer,sizeRead,(size-sizeRead)))!=-1){}                          
               // sizeRead = sizeRead + reading ;
                   //sizeRead += reader.read(buffer,0,sizeRead); 
                   sizeRead = sizeRead + reading;                
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
