import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Timer;

import javax.print.DocFlavor.BYTE_ARRAY;
import javax.sound.sampled.Port;

import java.net.DatagramSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
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
        String message = "";
        Random rand = new Random();
        int tcpPort = rand.nextInt(1000) + 5000;
        boolean host = false;
        boolean gamefound = false;
        boolean waited = false;
        try {
            // System.out.println(String.valueOf(tcpPort));
            DatagramPacket outPacket = new DatagramPacket(("NEW PLAYER "+String.valueOf(tcpPort)).getBytes(), 15, InetAddress.getLocalHost(), myPort);
            try (MulticastSocket multiSocket = new MulticastSocket(myPort)) {
                while (!gamefound) {
                    try {
                        if (waited) {
                            DatagramSocket socket = new DatagramSocket();
                            socket.setReuseAddress(true);
                            socket.send(outPacket);
                            System.out.println(outPacket.getPort()+"fhjslkjadl");
                            waited = false;
                            continue;
                        } else {
                            multiSocket.setSoTimeout(5000);
                            byte[] buf = new byte[15];
                            DatagramPacket packet = new DatagramPacket(buf, buf.length);
                            multiSocket.receive(packet);
                            message = new String(buf, StandardCharsets.UTF_8);
                            if (message.contains("NEW PLAYER") && packet.getPort() != myPort) {
                                System.out.println(message);
                                gamefound = true;
                            }
                            else{
                                joinGame(tcpPort);
                            }
                        }
                    } catch (SocketTimeoutException timeout) {
                        waited = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                hostGame(Integer.valueOf(message.substring(11, 15)));
                System.out.println(gamefound);
            }

        } catch (IOException e) {
            e.printStackTrace();
    }
}

    public void hostGame(int port){
        System.out.println(port);
        try(ServerSocket server = new ServerSocket(port)){
                Socket client = server.accept();
                System.out.println("helo");
                BufferedReader inMsg = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outMsg = new PrintWriter(client.getOutputStream(),true);
                outMsg.println("hello");
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public void joinGame(int port){
        try{
            Socket client = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
            BufferedReader inMsg = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter outMsg = new PrintWriter(client.getOutputStream(),true);
            String message;
            // while(!(message = inMsg.readLine()).equals("hello"));
            if(inMsg.readLine() != null)
            {
                System.out.println(inMsg.readLine());
            }
        }catch(Exception e){

        }
        
    }
}
