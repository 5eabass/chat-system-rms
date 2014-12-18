package signals;

/**
 * This class FileTransferNotAccepted is a signal called when we refuse a file transfer
 * transfer from the remote user
 *
 * @return
 */
public class FileTransferNotAccepted extends Signal {

    private String fileName;
    private String remoteUsername;

    public FileTransferNotAccepted(String fileName, String remoteUsername) {
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
