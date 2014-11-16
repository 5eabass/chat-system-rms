package chatsystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI extends JFrame {

    private JButton[] buttons;
    private JTextArea login;
    private JPanel[] container;
    private JMenuBar toolbar;
    private JMenu[] menus;
    private JMenuItem[] options;

    public GUI() {
        initComponents();
        this.setTitle("Chat System");
        this.setSize(400, 300);
        this.setLocation(400, 300);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // Initialization
        toolbar = new JMenuBar();
        menus = new JMenu[2];
        options = new JMenuItem[2];
        buttons = new JButton[2];
        menus[0] = new JMenu("User");
        menus[1] = new JMenu("Exit");
        options[0] = new JMenuItem("Connexion");
        options[1] = new JMenuItem("Deconnexion");
        buttons[0] = new JButton("Connexion");
        buttons[1] = new JButton("Deconnexion");
        login = new JTextArea("Enter your login");
        login.setFocusable(true);
        // Controler binding
        for (int i = 0; i < 2; i++) {
            options[i].addActionListener(ChatSystem.getControler());
            buttons[i].addActionListener(ChatSystem.getControler());
        }
        // Scaling
        container = new JPanel[2];
        container[0] = new JPanel(new GridLayout(4, 1));
        container[0].setPreferredSize(new Dimension(200, 150));
        container[0].add(login);
        container[0].add(buttons[0]);
        // Layout        
        this.setLayout(new BorderLayout());
        this.setJMenuBar(toolbar);
        menus[0].add(options[0]);
        toolbar.add(menus[0]);
        toolbar.add(menus[1]);
    }

    public void activeLogin() {
        menus[0].remove(options[0]);
        menus[0].add(options[1]);
        this.setContentPane(container[0]);
        this.validate();
        this.repaint();
    }

    // Getters and setters
    public String getUserName() {
        return this.login.getText();
    }
}
