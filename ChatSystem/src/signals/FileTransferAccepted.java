
package signals;

/**
 * This class FileTransferAccepted is a signal called when we accept a file
 * transfer from the remote user
 *
 * @return
 */
public class FileTransferAccepted extends Signal{
    private String fileName;
    private String remoteUsername;

    public FileTransferAccepted(String fileName, String remoteUsername) {
        this.fileName = fileName;
        this.remoteUsername = remoteUsername;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRemoteUsername() {
        return remoteUsername;
    }
}

