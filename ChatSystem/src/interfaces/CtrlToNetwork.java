package interfaces;

import java.io.File;
import java.util.ArrayList;
import signals.FileProposal;

/** 
     * This interface CtrlToNetwork representents the interface from the Controller to the Network
     * @return 
     */
public interface CtrlToNetwork {
    
    /**  Send a "hello" signal to the network   */
    public void sendHello(String localname);
     /**  Send a "helloOK" signal to the network   */
    public void sendHelloOk(String localname,String remoteName);
     /**  Send a "TextMessage" signal to the network   */
    public void processSendMessage(String message ,ArrayList<String> remoteName);
     /**  Send a "FileProposal" signal to the network   */
    public void processSendProposal(File file, long size, ArrayList<String> receivers);
     /**  Send a "FileTransferAccepted" signal to the network   */
    public void processAcceptTransfer(FileProposal fp);
     /**  Send a "FileTransferNotAccepted" signal to the network   */
    public void processRefuseTransfer(FileProposal fp);
     /**  Send a "goodbye" signal to the network   */
    public void sendGoodbye(String localName);
    
}
