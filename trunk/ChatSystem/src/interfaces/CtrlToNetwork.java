package interfaces;

import java.io.File;
import java.util.ArrayList;

public interface CtrlToNetwork {
    
    public void sendHello(String localname);
    public void sendHelloOk(String localname,String remoteName);
    public void processSendMessage(String message ,ArrayList<String> remoteName);
    public void processSendFile(File file, long size, String remoteName);
    public void performRefuseTransfer();
    public void performAcceptTransfer();
    public void sendGoodbye(String localName);
    
}
