package interfaces;

import java.io.File;
import java.util.ArrayList;

public interface CtrlToNetwork {
    
    public void sendHello(String localname);
    public void sendHelloOk(String localname,String remoteName);
    public void processSendMessage(String message ,ArrayList<String> remoteName);
    public void processSendFile(File file, long size, ArrayList<String> remoteName);
    public void performRefuseTransfer(String remoteName);
    public void performAcceptTransfer(String remoteName);
    public void sendGoodbye(String localName);
    
}
