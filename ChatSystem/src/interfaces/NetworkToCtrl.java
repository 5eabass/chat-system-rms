package interfaces;

public interface NetworkToCtrl {
    
    public void performHello(String remoteName);
    public void performHelloOk(String remoteName);
    public void performTextMessage(String message , String remoteName);
    public void processTransmission();
    public void processFileQuery();
    public void processReceipt();
    public void performGoodbye(String remoteName);   
     
}
