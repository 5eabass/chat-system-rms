package interfaces;
import java.io.File;
import java.util.ArrayList;
import signals.FileProposal;

/** 
     * This interface GUIToController representents the interface from the GUI to the Controller
     * @return 
     */
public interface GUIToCtrl {
    
     /**  Send the users infos to store to the controller  */
    public void createLocalInfo(String username);
    /**  Send the connection to the controller  */
    public void performConnect();
    /**  Send the message sending to the controller  */
    public void performSendMessage(String message, ArrayList<String> remoteName);
    /**  Send the file sending to the controller  */
    public void performSendFile(File file, ArrayList<String> remoteName);
    /**  Send the transfer accepted to the controller  */
    public void performAcceptTransfer(String fileToString);
    /**  Send the transfer refused to the controller  */
    public void performRefuseTransfer(String fileToString);
    /**  Send the disconnection to the controller  */
    public void performDisconnect(String userName);
    
}
