package interfaces;

import java.io.File;
import java.util.ArrayList;
import signals.FileProposal;

public interface CtrlToNetwork {
    
    public void sendHello(String localname);
    public void sendHelloOk(String localname,String remoteName);
    public void processSendMessage(String message ,ArrayList<String> remoteName);
    public void processSendProposal(File file, long size, ArrayList<String> receivers);
    public void performAcceptTransfer(FileProposal fp);
    public void performRefuseTransfer(FileProposal fp);
    public void sendGoodbye(String localName);
    
}
