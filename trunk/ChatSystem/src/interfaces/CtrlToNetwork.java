package interfaces;

public interface CtrlToNetwork {
    
    public void sendHello(String username);
    public void sendHelloOk(String username);
    public void processTextMessage();
    public void performRefuseTransfer();
    public void performAcceptTransfer();
    public void sendGoodbye(String localName);
    
}