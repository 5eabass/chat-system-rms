
package chatsystem;


public class ChatSystem {

    private static Network n0;
    private static GUI g0;
    private static GUI_SEB g1;
    private static Controler c0;
    
    public ChatSystem() {
        
    }

    public static Network getNetwork() {
        return n0;
    }

    public static GUI_SEB getGUI() {
        return g1;
    }

    public static Controler getControler() {
        return c0;
    }
    
    
    public static void main(String[] args) {
        
        n0 = new Network();
        // g0 = new GUI();
        c0 = new Controler();
        GUI_SEB gui = new GUI_SEB();
        gui.setVisible(true);

       // System.out.println(n0.getIP());

       
    }
    
    
    
    
}
