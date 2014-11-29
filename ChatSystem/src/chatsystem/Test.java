/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import network.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public Test() {
    }

    public static void main(String[] args) {
        try {
            TCPserver server = new TCPserver(1234);
            TCPsender sender = new TCPsender(InetAddress.getLocalHost(), 1234);
            sender.openClient();
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
