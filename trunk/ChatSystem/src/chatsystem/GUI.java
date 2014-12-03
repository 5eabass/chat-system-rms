package chatsystem;

import interfaces.*;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author seb
 */
public class GUI extends javax.swing.JFrame implements CtrlToGUI {
    
    /**
     * Creates new form EntryFrame
     */
    private StyledDocument doc;
    private Style chatSystemStyle, receiveStyle, sendStyle;
    private File file;
    private ArrayList<String> receiverList;
    
    public GUI() {
        initComponents();
        this.UsagePanel.setVisible(false);
        doc = receivedMessageArea.getStyledDocument();
        
        // initialisation des styles de police utilisé
        // rouge pour le System
        // bleu pour un message recu
        // noir pour les messages envoyé
        chatSystemStyle = receivedMessageArea.addStyle("chatSystemStyle", null);
        receiveStyle = receivedMessageArea.addStyle("fromStyle", null);
        sendStyle = receivedMessageArea.addStyle("toStyle", null);
        StyleConstants.setForeground(chatSystemStyle, Color.RED);
        StyleConstants.setForeground(receiveStyle, Color.BLUE);
        StyleConstants.setForeground(sendStyle, Color.BLACK);
        receiverList = new ArrayList<String>();
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        QueryFrame = new javax.swing.JInternalFrame();
        yesButton = new javax.swing.JButton();
        noButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        fileInfoTextArea = new javax.swing.JTextArea();
        questionLabel = new javax.swing.JLabel();
        UsagePanel = new javax.swing.JPanel();
        receivedMessagePanel = new javax.swing.JScrollPane();
        receivedMessageArea = new javax.swing.JTextPane();
        sendMessagePanel = new javax.swing.JScrollPane();
        sendMessageArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        fileChooseButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        userlistLabel = new javax.swing.JLabel();
        listPanel = new javax.swing.JScrollPane();
        connectedList = new javax.swing.JList();
        nameInfoLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        receiverTextField = new javax.swing.JTextField();
        toLabel = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        EntryFrame = new javax.swing.JInternalFrame();
        connectButton = new javax.swing.JButton();
        usernameArea = new javax.swing.JTextField();
        presentationLabel = new javax.swing.JLabel();
        proprietaryLabel = new javax.swing.JLabel();

        QueryFrame.setVisible(true);

        yesButton.setText("yes");
        yesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yesButtonActionPerformed(evt);
            }
        });

        noButton.setText("no");
        noButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noButtonActionPerformed(evt);
            }
        });

        fileInfoTextArea.setEditable(false);
        fileInfoTextArea.setBackground(new java.awt.Color(238, 238, 238));
        fileInfoTextArea.setColumns(20);
        fileInfoTextArea.setRows(5);
        fileInfoTextArea.setBorder(new javax.swing.border.MatteBorder(null));
        jScrollPane3.setViewportView(fileInfoTextArea);

        questionLabel.setText("   Do you accept to receive this file ?");

        javax.swing.GroupLayout QueryFrameLayout = new javax.swing.GroupLayout(QueryFrame.getContentPane());
        QueryFrame.getContentPane().setLayout(QueryFrameLayout);
        QueryFrameLayout.setHorizontalGroup(
            QueryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QueryFrameLayout.createSequentialGroup()
                .addGroup(QueryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(QueryFrameLayout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(yesButton)
                        .addGap(43, 43, 43)
                        .addComponent(noButton))
                    .addGroup(QueryFrameLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(questionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QueryFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        QueryFrameLayout.setVerticalGroup(
            QueryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QueryFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(questionLabel)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(QueryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yesButton)
                    .addComponent(noButton))
                .addGap(0, 34, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        UsagePanel.setPreferredSize(new java.awt.Dimension(650, 447));

        receivedMessageArea.setEditable(false);
        receivedMessagePanel.setViewportView(receivedMessageArea);

        sendMessageArea.setColumns(20);
        sendMessageArea.setRows(5);
        sendMessageArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        sendMessagePanel.setViewportView(sendMessageArea);

        sendButton.setText("SEND");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        fileChooseButton.setText("FILE");
        fileChooseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooseButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("DISCONNECT");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        userlistLabel.setText("Connected user");

        connectedList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                connectedListMousePressed(evt);
            }
        });
        listPanel.setViewportView(connectedList);

        nameInfoLabel.setText("My name :");

        usernameLabel.setText("username");

        receiverTextField.setEditable(false);

        toLabel.setText("To :");

        messageLabel.setText("Message :");

        javax.swing.GroupLayout UsagePanelLayout = new javax.swing.GroupLayout(UsagePanel);
        UsagePanel.setLayout(UsagePanelLayout);
        UsagePanelLayout.setHorizontalGroup(
            UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsagePanelLayout.createSequentialGroup()
                .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nameInfoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameLabel))
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(userlistLabel)))
                .addGap(29, 29, 29)
                .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addComponent(messageLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsagePanelLayout.createSequentialGroup()
                        .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, UsagePanelLayout.createSequentialGroup()
                                .addComponent(toLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(receiverTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(receivedMessagePanel, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UsagePanelLayout.createSequentialGroup()
                        .addComponent(sendMessagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(fileChooseButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(6, 6, 6))))
        );
        UsagePanelLayout.setVerticalGroup(
            UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UsagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameInfoLabel)
                            .addComponent(usernameLabel))
                        .addGap(43, 43, 43)
                        .addComponent(userlistLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(listPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(receivedMessagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(UsagePanelLayout.createSequentialGroup()
                        .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(receiverTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toLabel))
                        .addGap(1, 1, 1)
                        .addComponent(messageLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(UsagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(UsagePanelLayout.createSequentialGroup()
                                .addComponent(sendButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileChooseButton))
                            .addGroup(UsagePanelLayout.createSequentialGroup()
                                .addComponent(sendMessagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())))
                    .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        EntryFrame.setVisible(true);

        connectButton.setText("CONNECT");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        usernameArea.setText("username");
        usernameArea.setSelectedTextColor(new java.awt.Color(153, 153, 255));

        presentationLabel.setText("Welcome to the ChatSystem !");

        proprietaryLabel.setText("©RMS");

        javax.swing.GroupLayout EntryFrameLayout = new javax.swing.GroupLayout(EntryFrame.getContentPane());
        EntryFrame.getContentPane().setLayout(EntryFrameLayout);
        EntryFrameLayout.setHorizontalGroup(
            EntryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntryFrameLayout.createSequentialGroup()
                .addGroup(EntryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EntryFrameLayout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(EntryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameArea, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(connectButton)))
                    .addGroup(EntryFrameLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(presentationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EntryFrameLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(proprietaryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        EntryFrameLayout.setVerticalGroup(
            EntryFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EntryFrameLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(presentationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(usernameArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectButton)
                .addGap(24, 24, 24)
                .addComponent(proprietaryLabel))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(UsagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(EntryFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(UsagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(EntryFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        System.out.println("DEBUG *** GUI : pressed CONNECT ***");
        ChatSystem.getControler().createLocalInfo(usernameArea.getText());
        connectedList.setModel(ChatSystem.getModel().getRemoteTable());
        connectedList.validate();
        ChatSystem.getControler().performConnect();
        this.usernameLabel.setText(ChatSystem.getModel().getUsername());
        
        try {
            doc.insertString(doc.getLength(), "Welcome to the chat ! \nyour adress ip is : " + ChatSystem.getModel().getLocalAdress() + "\n", chatSystemStyle);
        } catch (BadLocationException e) {
            System.err.println(e);
        }
        
        this.EntryFrame.setVisible(false);
        this.UsagePanel.setVisible(true);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }//GEN-LAST:event_connectButtonActionPerformed
    
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        System.out.println("DEBUG *** GUI : pressed SEND ***");
        
        if (receiverTextField.getText().equals("")) {
            // cas ou le remoteName n'est pas dans notre table
            System.err.println("DEBUG *** GUI : no such remote user ***");
            erreurReceiver();
        } else {
            // cas nominal on envoie la requete et on affiche dans notre boite de dialogue
            try {
                for (String s : receiverList){
                    doc.insertString(doc.getLength(), "To " + s + " : " + sendMessageArea.getText() + "\n", sendStyle);
                    if(sendMessageArea.getText().contains("File :")){
                        System.out.println("DEBUG *** GUI : File transmitted to ctrl : " + sendMessageArea.getText() + " ***");
                        ChatSystem.getControler().performSendFile(file, s);
                        file = null;
                    }else{
                        System.out.println("DEBUG *** GUI : message transmitted to ctrl : " + sendMessageArea.getText() + " ***");
                        ChatSystem.getControler().performSendMessage(sendMessageArea.getText(), receiverList);
                    }
                }
            } catch (BadLocationException e) {
                System.err.println(e);
            }
            sendMessageArea.setText("");
            receiverTextField.setText("");
            receiverList.clear();
        }
    }//GEN-LAST:event_sendButtonActionPerformed
    
    private void fileChooseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooseButtonActionPerformed
        System.out.println("DEBUG *** GUI : pressed FILE ***");
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        file = chooser.getSelectedFile();
        sendMessageArea.setText("File : " + file.getAbsolutePath());
    }//GEN-LAST:event_fileChooseButtonActionPerformed
    
    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        System.out.println("DEBUG *** GUI : pressed DISCONNECT ***");
        ChatSystem.getControler().performDisconnect(usernameLabel.getText());
        receivedMessageArea.setText("");
        receiverTextField.setText("");
        this.UsagePanel.setVisible(false);
        this.EntryFrame.setVisible(true);
    }//GEN-LAST:event_disconnectButtonActionPerformed
    
    
    /*
    *for internal frame : queryFrame
    */
    
    private void yesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yesButtonActionPerformed
        ChatSystem.getControler().processAcceptTransfer();
        QueryFrame.setVisible(false);
    }//GEN-LAST:event_yesButtonActionPerformed
    
    private void noButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noButtonActionPerformed
        ChatSystem.getControler().processRefuseTransfer();
        QueryFrame.setVisible(false);
    }//GEN-LAST:event_noButtonActionPerformed
    
    private void connectedListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectedListMousePressed
        // TODO add your handling code here:
        System.out.println("DEBUG *** GUI : selected a receiver ***");
        if(!receiverList.contains((String) connectedList.getSelectedValue())){
            // si le user selectionné n'était pas déja selectionné
            System.out.println("DEBUG *** GUI : add to receivers ***");
            receiverList.add((String) connectedList.getSelectedValue());
            receiverTextField.setText(receiverList.toString());
        }else{
            System.out.println("DEBUG *** GUI : delete from receivers ***");
            receiverList.remove((String) connectedList.getSelectedValue());
        }
        receiverTextField.setText(arrayToString(receiverList));       
    }//GEN-LAST:event_connectedListMousePressed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame EntryFrame;
    private javax.swing.JInternalFrame QueryFrame;
    private javax.swing.JPanel UsagePanel;
    private javax.swing.JButton connectButton;
    private javax.swing.JList connectedList;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JButton fileChooseButton;
    private javax.swing.JTextArea fileInfoTextArea;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane listPanel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel nameInfoLabel;
    private javax.swing.JButton noButton;
    private javax.swing.JLabel presentationLabel;
    private javax.swing.JLabel proprietaryLabel;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JTextPane receivedMessageArea;
    private javax.swing.JScrollPane receivedMessagePanel;
    private javax.swing.JTextField receiverTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextArea sendMessageArea;
    private javax.swing.JScrollPane sendMessagePanel;
    private javax.swing.JLabel toLabel;
    private javax.swing.JLabel userlistLabel;
    private javax.swing.JTextField usernameArea;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JButton yesButton;
    // End of variables declaration//GEN-END:variables
    
    // toutes les fonctions à implémenter !
    @Override
    // appelé quand on recoit un hello ou hellook , il faut ajouter le nom a la list
    public void addUser(String remoteName) {
        System.out.println("DEBUG *** GUI : addUser <= when we receive a hello ***");
        
        try {
            doc.insertString(doc.getLength(), remoteName + " is now connected !\n", chatSystemStyle);
        } catch (BadLocationException e) {
            System.err.println(e);
        }
        connectedList.revalidate();
    }
    
    @Override
    // appelé quand on recoit un message
    public void processTextMessage(String message, String remoteName,ArrayList<String> to) {
        System.out.println("DEBUG *** GUI : processTextMessage <= when we receive a message ***");
        try {
            doc.insertString(doc.getLength(), "from " + remoteName + " : " + message + "\n", receiveStyle);
            if (to.size()>1){
                doc.insertString(doc.getLength(),"(sent to : "+arrayToString(to)+")\n" , receiveStyle);
            }
        } catch (BadLocationException e) {
            System.err.println(e);
        }
        System.out.println("DEBUG *** GUI : processTextMessage = "+message+" ***");
    }
    
    @Override
    // appelé quand le fichier est bien recu
    public void notifyTransmitted() {
        System.out.println("DEBUG *** GUI : notifyTransmitted <= when we have successfully received the file ***");
        
        try {
            doc.insertString(doc.getLength(), "File Transmitted ! " + "\n", chatSystemStyle);
        } catch (BadLocationException e) {
            System.err.println(e);
        }
    }
    
    @Override
    // appelé quand le fichier n'a pas été recu
    public void notifyNotTransmitted() {
        System.out.println("DEBUG *** GUI : notifyNotTransmitted <= when we haven't received the file ***");
        
        try {
            doc.insertString(doc.getLength(), "File failed to Transmit ! " + "\n", chatSystemStyle);
        } catch (BadLocationException e) {
            System.err.println(e);
        }
    }
    @Override
    //appelé quand on nous demande si on accepte la reception d'un fichier
    public void performFileQuery(String filename,long size,String remoteName){
        fileInfoTextArea.setText("File : " +filename+"\n File size : "+size+"\n From : "+remoteName+"\n");
        QueryFrame.setVisible(true);
    }
    
    @Override
    // appelé par le ctrl quand on recoit un goodbye il faut supprimer le nom de la liste
    public void deleteUser(String remoteName) {
        System.out.println("DEBUG *** GUI : deleteUser <= when we receive a goodBye ***");
        
        //on supprime l'élément de notre list(que l'utilisateur voit) et on met a jour la liste
        try {
            doc.insertString(doc.getLength(), remoteName + " is now disconnected !\n", chatSystemStyle);
        } catch (BadLocationException e) {
            System.err.println(e);
        }
        
        //listModel.removeElement(remoteName);
        connectedList.revalidate();
    }
    /*
    fonction utiles dans selection receiver
    */
    
    public String arrayToString(ArrayList<String> a){
        String result =new String();
        for (String s : a){
            if(!(s == null)){
                result += s +",";
            }
        }
        return result;
    }
    
    
    
    public void erreurReceiver() {
        System.out.println("DEBUG *** GUI : erreurReceiver <= when we didn't choose any receiver ***");
        
        try {
            doc.insertString(doc.getLength(), "Erreur : selectionnez un utilisateur distant\n", chatSystemStyle);
        } catch (BadLocationException e) {
            System.err.println(e);
        }
    }
}