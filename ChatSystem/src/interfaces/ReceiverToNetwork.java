package interfaces;

public interface ReceiverToNetwork {
    
    public void downloadingInfo(float ratio, String fileNme);
    public void receivedFile(byte[] buffer, String fileName);
    
}
