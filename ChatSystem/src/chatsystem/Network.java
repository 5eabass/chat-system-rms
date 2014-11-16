package chatsystem;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.System.in;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import signals.Hello;

public class Network {

    private String username;

    public Network() {

    }

    public void sendHello(String u) {

        Hello helloMessage = new Hello(username);
    }

    public void udpSocket(InetAddress aIp, int dPort) {
        try {
            Socket s0 = new Socket("127.0.0.1", dPort);
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*in = new InputStreamReader(client.getSocket().getInputStream());
        out = new OutputStreamWriter(client.getSocket().getOutputStream());
        writer = new BufferedWriter(out);
        reader = new BufferedReader(in);*/
    }

    public ArrayList getIps() {
        ArrayList ips = new ArrayList();

        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {  // carte reseau trouvee
                NetworkInterface interfaceN = (NetworkInterface) interfaces.nextElement();
                Enumeration<InetAddress> ienum = interfaceN.getInetAddresses();
                while (ienum.hasMoreElements()) {  // retourne l adresse IPv4 et IPv6
                    InetAddress ia = ienum.nextElement();
                    String adress = ia.getHostAddress().toString();

                    if (adress.length() < 16) {          //On s'assure ainsi que l'adresse IP est bien IPv4
                        if (adress.startsWith("127")) {  //Ce n'est pas l'adresse IP Local' 
                            System.out.println(ia.getHostAddress());
                        } else if (adress.indexOf(":") > 0) {
                            System.out.println(ia.getHostAddress()); // les ":" indique que c'est une IPv6"
                        }
                    }

                    ips.add(adress);
                }
            }
        } catch (Exception e) {
            System.out.println("pas de carte reseau");
            e.printStackTrace();
        }

        return ips;
    }
}
