import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Timer;

import javax.print.DocFlavor.BYTE_ARRAY;

import java.net.DatagramSocket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client {
    public static void main(String args[]) {
        Client client = new Client();
        client.run(Integer.valueOf(args[0]));
    }

    public void run(int myPort) {
        System.out.println("Listening on UDP port " + myPort);
        Random rand = new Random();
        int tcpPort = rand.nextInt(1000) + 5000;
        boolean gamefound = false;
        boolean waited = false;
        try {
            DatagramPacket outPacket = new DatagramPacket(("NEW PLAYER "+String.valueOf(tcpPort)).getBytes(), 15, InetAddress.getLocalHost(), myPort);
            MulticastSocket multiSocket = new MulticastSocket(myPort);
            while (!gamefound) {
                try {
                    if (waited) {
                        DatagramSocket socket = new DatagramSocket();
                        socket.setReuseAddress(true);
                        socket.send(outPacket);
                        waited = false;
                        continue;
                    } else {
                        multiSocket.setSoTimeout(5000);
                        // Loop forever so we can run Program2 multiple times without restarting
                        // Program1
                        byte[] buf = new byte[15];
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);

                        // Blocking call that will wait for a UDP packet on our port
                        multiSocket.receive(packet);

                        // Java's UDP interface works on byte array's - so we convert to String to make
                        // life easy.
                        String message = new String(buf, StandardCharsets.UTF_8);
                        if ("NEW PLAYER".contains(message)) {

                            System.out.println(message);
                            gamefound = true;
                            break;
                                // Send our response back. These two parameters are the SOURCE IP and SOURCE
                            // PORT
                            // of the packet we received - so we are going to reflect them as the
                            // DESTINATION
                            // IP and DESTINATION PORT of the packet we send back.
                            // InetAddress dAdd = packet.getAddress();
                            // int dPort = packet.getPort();

                            // System.out.println("Responding to " + dAdd + ":" + dPort);

                            // // Sure, we could use a String and conver~t to a byte-array, or just use a
                            // byte
                            // // array directly as per below.
                            // byte[] response = {'P', 'O', 'N', 'G'};
                            // DatagramPacket outPacket = new DatagramPacket(response, 4, dAdd, dPort);
                            // socket.send(outPacket);
                        }
                    }
                } catch (SocketTimeoutException timeout) {
                    waited = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(gamefound);
            }
            System.out.println("hello check");
        } catch (IOException e) {
            e.printStackTrace();
    }
}

    public void findGame() {

    }
}
