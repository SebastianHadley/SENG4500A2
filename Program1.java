import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Program1 {
    public static void main(String args[])
    {
        // Pick the random port we are going to listen on that Program2 is going to have to 'hunt' for
        Random rand = new Random();
        int myPort = rand.nextInt(1000) + 5000;
        System.out.println("Listening on UDP port " + myPort);

        try
        {
            DatagramSocket socket = new DatagramSocket(myPort);
            // Loop forever so we can run Program2 multiple times without restarting Program1
            while (true) {
                byte[] buf = new byte[4];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                // Blocking call that will wait for a UDP packet on our port
                socket.receive(packet);

                // Java's UDP interface works on byte array's - so we convert to String to make life easy.
                String s = new String(buf, StandardCharsets.UTF_8);
                System.out.println("Received " + s);

                if ("PING".equals(s)) {
                    // Send our response back. These two parameters are the SOURCE IP and SOURCE PORT
                    // of the packet we received - so we are going to reflect them as the DESTINATION
                    // IP and DESTINATION PORT of the packet we send back.
                    InetAddress dAdd = packet.getAddress();
                    int dPort = packet.getPort();

                    System.out.println("Responding to " + dAdd + ":" + dPort);

                    // Sure, we could use a String and convert to a byte-array, or just use a byte
                    // array directly as per below.
                    byte[] response = {'P', 'O', 'N', 'G'};
                    DatagramPacket outPacket = new DatagramPacket(response, 4, dAdd, dPort);
                    socket.send(outPacket);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
