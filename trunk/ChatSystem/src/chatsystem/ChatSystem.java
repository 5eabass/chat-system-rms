package chatsystem;

import network.Network;
import model.*;

public class ChatSystem {

    private static Network n0;
    private static GUI g0;
    private static Controler c0;
    private static DataStored model;

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

    public static DataStored getModel() {
        return model;
    }

    public static void main(String[] args) {

        model = new DataStored();
        n0 = new Network();
        g0 = new GUI();
        c0 = new Controler();
        g0.setVisible(true);
    }

}
