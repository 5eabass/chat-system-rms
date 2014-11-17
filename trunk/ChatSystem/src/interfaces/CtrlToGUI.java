package interfaces;

public interface CtrlToGUI {
    
    public void addUser(String remoteName);
    public void processTextMessage(String message,String remoteName);
    public void notifyTransmitted();
    public void notifyNotTransmitted();
    public void deleteUser(String remoteName);
    
}
