package interfaces;

public interface GUIToCtrl {
    
    public void performConnect();
    public void performSendMessage(String message, String remoteName);
    public void performSendFile();
    public void processAcceptTransfer();
    public void processRefuseTransfer();
    public void performDisconnect(String userName);
    
}
