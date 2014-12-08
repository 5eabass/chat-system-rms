package interfaces;

import java.util.ArrayList;
import signals.FileProposal;

public interface NetworkToCtrl {
    
    public void performHello(String username);
    public void performHelloOk(String remoteString);
    public void performTextMessage(String message , String remoteName, ArrayList<String> to);
    public void processTransmission(byte[] buffer, String fileName);
    public void processFileQuery(FileProposal fp);
    public void processReceipt();
    public void performGoodbye(String remoteName);      
}
