package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controler implements ActionListener {
    
    public Controler() {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Listener menu
        if(e.getActionCommand().equals("Connexion")) {  
            System.out.println("! Connexion layout displayed");
            ChatSystem.getGUI().activeLogin();
        }
        if(e.getActionCommand().equals("Enter the chat")) {
            System.out.println("> Enter the chat button pressed");
            performHello(ChatSystem.getGUI().getUsername());
        }
    }
    
    public void performHello(String username) {
        ChatSystem.getNetwork().sendHello(username);
    }
    
}
