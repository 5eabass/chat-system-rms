package interfaces;

public interface NetworkToCtrl {
    
    public void performHello(String username);
    public void performHelloOk(String remoteString);
    public void performTextMessage(String message , String remoteName);
    public void processTransmission();
    public void processFileQuery();
    public void processReceipt();
    public void performGoodbye(String remoteName);   
     
}
