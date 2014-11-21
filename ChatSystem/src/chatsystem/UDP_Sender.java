package chatsystem;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_Sender extends Thread {

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private ByteArrayOutputStream baos;
    private ObjectOutputStream oos;
    private int sendingPort, len;
    
    public UDP_Sender(int s, int l) {
        this.sendingPort = s;
        this.len = l;
    }

    public void run() {
        openClient();
    }

    private void openClient() {
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(c1);
        oos.flush();
        // get the byte array of the object
        byte[] Buf = baos.toByteArray();

        byte[] data = new byte[len];

        DatagramSocket socket = new DatagramSocket(sendingPort);
        DatagramPacket packet = new DatagramPacket(data, len, ChatSystem.getControler().getModel().getLocalAdress(), 1234);
        socket.send(packet);

        // now send the payload
        packet = new DatagramPacket(Buf, Buf.length, client, 1234);
        socket.send(packet);
    }

}
