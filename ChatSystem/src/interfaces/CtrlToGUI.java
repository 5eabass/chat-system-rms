package interfaces;

import java.util.ArrayList;
import signals.FileProposal;

/** 
     * This interface CtrlToGUI representents the interface from the Controller to the GUI
     * @return 
     */
public interface CtrlToGUI {
    
    /**  Add and display a new user in the Gui windows   */
    public void addUser(String remoteName);
    /**  Display the message to send in the Gui windows   */
    public void processTextMessage(String message,String remoteName, ArrayList<String> to);
    /**  Display the file notification received in the Gui windows   */
    public void notifyReceived(String fileName);
    /**  Display the file notification not received in the Gui windows   */
    public void notifyNotReceived(String fileName);
    /**  Display the file notification transmitted in the Gui windows   */
    public void notifyTransmitted();
     /**  Display the file notification not transmitted in the Gui windows   */
    public void notifyNotTransmitted();
     /**  Display the file notification acceptation in the Gui windows   */
    public void processFileAccepted();
    /**  Display the file notification non acceptation in the Gui windows   */
    public void processFileNotAccepted();
    /**  Display the file query in the Gui windows   */
    public void processFileQuery(FileProposal fp);
    /**  Delelte a user from the connected user list in the Gui windows   */
    public void deleteUser(String remoteName);
    
}
