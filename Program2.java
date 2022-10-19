import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.net.SocketTimeoutException;
import java.util.Random;

public class Program2 {
    public static void main(String args[])
    {
        try {
            // Get our hostname from the command line that we are going to 'scan'
            InetAddress dAdd = InetAddress.getByName(args[0]);

            // Pick the random port we are going to listen on that Program2 is going to have to 'hunt' for
            Random rand = new Random();
            int myPort = rand.nextInt(1000) + 5000;
            System.out.println("Listening on UDP port " + myPort);

            DatagramSocket socket = new DatagramSocket(myPort);
            // We are going to wait 1 second for a response before giving up and moving to the
            // next port number.
            socket.setSoTimeout(100);
            byte[] request = {'P', 'I', 'N', 'G'};

            for (int dPort = 4000; dPort <= 5000; dPort++) {
                System.out.println("Testing port " + dPort);
                DatagramPacket outPacket = new DatagramPacket(request, 4, dAdd, dPort);
                socket.send(outPacket);

                // Sets up a buffer to receive the response UDP packet
                byte[] buf = new byte[4];
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length);

                // This is where we have our race condition. What is program 1 receives
                // the UDP packet sent above and responds BEFORE we get to run the
                // blocking .receive() method below ? Ideally we would move the "listening"
                // to a dedicated thread but inter-thread communication isn't the topic of the day
                try {
                    socket.receive(packet);
                }
                catch (SocketTimeoutException e)
                {
                    // Keep looping as we timed-out waiting for a response on that port
                    continue;
                }

                // If we get to here then we have received a packet
                String s = new String(buf, StandardCharsets.UTF_8);

                System.out.println("Received " + s);

                if ("PONG".equals(s)) {
                    System.out.println("Response received from " + packet.getPort());

                    // No more searching needed. Quit.
                    System.exit(0);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
