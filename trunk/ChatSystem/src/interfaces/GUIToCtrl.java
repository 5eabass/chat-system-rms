package interfaces;
import java.io.File;

public interface GUIToCtrl {
    
    public void createLocalInfo(String username);
    public void performConnect();
    public void performSendMessage(String message, String remoteName);
    public void performSendFile(File file,String remoteName);
    public void processAcceptTransfer();
    public void processRefuseTransfer();
    public void performDisconnect(String userName);
    
}
