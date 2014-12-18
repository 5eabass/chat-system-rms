package interfaces;

import java.util.ArrayList;
import signals.FileProposal;

/** 
     * This interface NetworkToCtrl representents the interface from the Network to the Controller
     * @return 
     */

public interface NetworkToCtrl {
    
    /**  When we receive a "hello" signal  */
    public void performHello(String username);
    /**  When we receive a "helloOK" signal  */
    public void performHelloOk(String remoteString);
    /**  When we receive a textMessage  */
    public void performTextMessage(String message , String remoteName, ArrayList<String> to);
    /**  When we receive a file transfer  */
    public void performTransmission(byte[] buffer, String fileName);
    /**  When we receive a file query  */
    public void performFileQuery(FileProposal fp);
    /**  When we receive a answer to a file query  */
    public void performFileAnswer(boolean b);
    /**  When we receive a file transfer notification (well received or not) */
    public void performTransferNotification(boolean b);
    /**  When we receive a "goodbye" signal  */
    public void performGoodbye(String remoteName);      
}
