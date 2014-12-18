
 package chatsystem;

import network.Network;
import model.*;

 /** 
     * This class creates the MVS model (Gui, Network,Controler, Model) 
     */
public class ChatSystem {

    private static Network n0;
    private static GUI g0;
    private static Controler c0;
    private static DataStored model;

    public ChatSystem() {

    }

    /** 
     * This method returns the network class
     * @return 
     */
    public static Network getNetwork() {
        return n0;
    }
    
/** 
     * This method returns the GUI
     * @return 
     */
    public static GUI getGUI() {
        return g0;
    }

    /** 
     * This method returns the controler
     * @return 
     */
    public static Controler getControler() {
        return c0;
    }

    /** 
     * This method returns the model
     * @return 
     */
    public static DataStored getModel() {
        return model;
    }

    /** 
     * This main creates a new model, network, GUI and Controler and displays the GUI windows
     * @return 
     */
    public static void main(String[] args) {

        model = new DataStored();
        n0 = new Network();
        g0 = new GUI();
        c0 = new Controler();
        g0.setVisible(true);
    }

}
