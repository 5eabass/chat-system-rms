package chatsystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controler implements ActionListener {
    GUI chatGUI;
    
    public Controler(GUI aGUI) {
        this.chatGUI = aGUI;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // Listener menu
        if(e.getActionCommand().equals("Connexion")) {  
            System.out.println("! Connexion layout displayed");
            chatGUI.activeLogin();
        }
        if(e.getActionCommand().equals("Enter the chat")) {
            System.out.println("> Enter the chat button pressed");
            
        }
    }
    
}
