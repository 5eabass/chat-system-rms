package model;

import java.util.ArrayList;

/* 
 * MODEL
*/

public class DataStored {
    private ArrayList<UserCouple> remoteTable; // info des remotes users
    private UserCouple localInfo ; // info du localuser
        
    public DataStored(ArrayList<UserCouple> remoteTable, UserCouple localInfo) {
        this.remoteTable = remoteTable;
        this.localInfo = localInfo;
    }

    public ArrayList<UserCouple> getRemoteTable() {
        return remoteTable;
    }

    public void setRemoteTable(ArrayList<UserCouple> remoteTable) {
        this.remoteTable = remoteTable;
    }

    public UserCouple getLocalInfo() {
        return localInfo;
    }

    public void setLocalInfo(UserCouple localInfo) {
        this.localInfo = localInfo;
    }
    
    
    
    
}
