package interfaces;

import java.util.ArrayList;
import signals.FileProposal;

public interface CtrlToGUI {
    
    public void addUser(String remoteName);
    public void processTextMessage(String message,String remoteName, ArrayList<String> to);
    public void notifyTransmitted();
    public void notifyNotTransmitted();
    public void performFileQuery(FileProposal fp);
    public void deleteUser(String remoteName);
    
}
