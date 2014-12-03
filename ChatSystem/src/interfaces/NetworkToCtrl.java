package interfaces;

import java.util.ArrayList;

public interface NetworkToCtrl {
    
    public void performHello(String username);
    public void performHelloOk(String remoteString);
    public void performTextMessage(String message , String remoteName, ArrayList<String> to);
    public void processTransmission();
    public void processFileQuery(String filename,long size, String remoteName);
    public void processReceipt();
    public void performGoodbye(String remoteName);      
}
