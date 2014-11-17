package interfaces;

public interface CtrlToGUI {
    
    public void addUser(String remoteName);
    public void processTextMessage();
    public void notifyTransmitted();
    public void notifyNotTransmitted();
    public void deleteUser(String remoteName);
    
}
