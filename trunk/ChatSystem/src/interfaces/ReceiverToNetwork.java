package interfaces;

/** 
     * This interface ReceiverToNetwork representents the interface from the Receiver to the Network in order to receive a file
     * @return 
     */
public interface ReceiverToNetwork {
    
    public void receivedFile(byte[] buffer, String fileName);
    
}
