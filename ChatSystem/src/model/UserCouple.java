package model;

// class qui représente le couple : nom / @IP
public class UserCouple {
        private String name;
        private String adress;

    public UserCouple(String name, String adress) {
        this.name = name;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
        
    
}
