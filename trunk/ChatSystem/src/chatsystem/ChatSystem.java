package chatsystem;

public class ChatSystem {

    private static Network n0;
    private static GUI g0;
    private static Controler c0;
    
    public ChatSystem() {
        
    }
    
    

    public static Network getNetwork() {
        return n0;
    }

    public static GUI getGUI() {
        return g0;
    }

    public static Controler getControler() {
        return c0;
    }
    
    
    public static void main(String[] args) {
       
       n0 = new Network();
       g0 = new GUI();
       c0 = new Controler();
       
    }
    
    
    
    
}
