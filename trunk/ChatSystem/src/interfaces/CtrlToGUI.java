package interfaces;

import java.util.ArrayList;

public interface CtrlToGUI {
    
    public void addUser(String remoteName);
    public void processTextMessage(String message,String remoteName, ArrayList<String> to);
    public void notifyTransmitted();
    public void notifyNotTransmitted();
    public void performFileQuery(String filename, long size, String remoteName);
    public void deleteUser(String remoteName);
    
}
