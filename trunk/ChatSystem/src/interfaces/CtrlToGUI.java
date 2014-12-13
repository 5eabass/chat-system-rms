package interfaces;

import java.util.ArrayList;
import signals.FileProposal;

public interface CtrlToGUI {
    
    public void addUser(String remoteName);
    public void processTextMessage(String message,String remoteName, ArrayList<String> to);
    public void informDownloadingRatio(float ratio, String fileName);
    public void notifyReceived(String fileName);
    public void notifyNotReceived(String fileName);
    public void notifyTransmitted();
    public void notifyNotTransmitted();
    public void processFileAccepted();
    public void processFileNotAccepted();
    public void processFileQuery(FileProposal fp);
    public void deleteUser(String remoteName);
    
}
