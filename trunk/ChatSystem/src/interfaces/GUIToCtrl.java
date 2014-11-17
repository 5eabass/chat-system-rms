package interfaces;

public interface GUIToCtrl {
    
    public void performConnect(String localName);
    public void performSendMessage();
    public void performSendFile();
    public void processAcceptTransfer();
    public void processRefuseTransfer();
    public void performDisconnect(String localName);
    
    
}
