package chatsystem;

public class Tests {

    String remotename1 = "melina";
    String remotename2 = "sebastien";
    String remotename3 = "rémy";

    public void connexion() {
        // Vérifier quand on se connecte d'une machine A, si notre connexion est bien reçu sur une machine B
        
    }

    public void maj() {
        // Vérifier la liste de contacts – mise a jour ? 
    }

    public void deconnexion() {
        // Vérifier quand on se déconnecte d'une machine A, si notre déco est bien reçu sur une machine B
    }

    public void envoi_mess() {
        /*// Envoi bien reçu ? 
        ChatSystem.getNetwork().processSendMessage("essai message", remotename1);
        // Test avec un message très long
        ChatSystem.getNetwork().processSendMessage("essai messageaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", remotename1);
        // Test d’envoi de messages à plusieurs utilisateurs en même temps 
        ChatSystem.getNetwork().processSendMessage("essai message", remotename1);
        ChatSystem.getNetwork().processSendMessage("essai message", remotename2);
        ChatSystem.getNetwork().processSendMessage("essai message", remotename3);
        */
    }

    public void envoi_fichier() {
        // Envoi d'un fichier ? 
      //  ChatSystem.getNetwork().processSendFile();
        // Test avec un fichier très lourd – la taille max est – elle dépassée ? 
      //  ChatSystem.getNetwork().processSendFile();
        // Test de différents formats (.avi/.jpeg /.pdf etc)
      //  ChatSystem.getNetwork().processSendFile();
    }
}
