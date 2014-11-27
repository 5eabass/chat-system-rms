package interfaces;

import java.io.File;

public interface CtrlToNetwork {
    
    public void sendHello(String username);
    public void sendHelloOk(String username);
    public void processSendMessage(String message ,String remoteName);
    public void processSendFile(File file, long size, String remoteName);
    public void performRefuseTransfer();
    public void performAcceptTransfer();
    public void sendGoodbye(String localName);
    
}
