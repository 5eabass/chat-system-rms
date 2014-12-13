package interfaces;

import java.util.ArrayList;
import signals.FileProposal;

public interface NetworkToCtrl {
    
    public void performHello(String username);
    public void performHelloOk(String remoteString);
    public void performTextMessage(String message , String remoteName, ArrayList<String> to);
    public void performTransmission(byte[] buffer, String fileName);
    public void performFileQuery(FileProposal fp);
    public void performFileAnswer(boolean b);
    public void performDownloadingInfo(float ratio, String fileName);
    public void performTransferNotification(boolean b);
    public void performGoodbye(String remoteName);      
}
