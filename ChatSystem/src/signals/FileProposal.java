package signals;

import java.util.ArrayList;

public class FileProposal extends Signal {

    private String fileName;
    private long size;
    private String from;
    private ArrayList<String> to;
    private int port;
    private boolean state;

    public FileProposal(String fileName, long size, String from, ArrayList<String> to, int port, boolean state) {
        super();
        this.fileName = fileName;
        this.size = size;
        this.from = from;
        this.to = to;
        this.port = port;
        this.state = state;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public int getPort() {
        return port;
    }

    public void setSize(int port) {
        this.port = port;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}
