import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Timer;

import javax.print.DocFlavor.BYTE_ARRAY;
import javax.sound.sampled.Port;

import java.net.DatagramSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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
    private Game game;

    public static void main(String args[]) {
        Client client = new Client();
        client.run(Integer.valueOf(args[0]));
    }

    public void run(int myPort) {
        System.out.println("Listening on UDP port " + myPort);
        String message = "";
        Random rand = new Random();
        int tcpPort = rand.nextInt(1000) + 5000;
        boolean done = false;
        boolean gamefound = false;
        boolean waited = false;
        try {
            // System.out.println(String.valueOf(tcpPort));
            DatagramPacket outPacket = new DatagramPacket(("NEW PLAYER " + String.valueOf(tcpPort)).getBytes(), 15,
                    InetAddress.getLocalHost(), myPort);
            try (MulticastSocket multiSocket = new MulticastSocket(myPort)) {
                while (!gamefound) {
                    try {
                        System.out.println("looping");
                        if (waited) {
                            DatagramSocket socket = new DatagramSocket();
                            socket.setReuseAddress(true);
                            socket.send(outPacket);
                            waited = false;
                            continue;
                        } else {
                            multiSocket.setSoTimeout(1000);
                            byte[] buf = new byte[15];
                            DatagramPacket packet = new DatagramPacket(buf, buf.length);
                            multiSocket.receive(packet);
                            message = new String(buf, StandardCharsets.UTF_8);
                            System.out.println(message.substring(11, 15));
                            if (message.contains("NEW PLAYER") && !(message.contains(String.valueOf(tcpPort)))) {
                                System.out.println(message.substring(11, 15) + " my tcp is " + tcpPort);
                                break;
                            }
                        }
                    } catch (SocketTimeoutException timeout) {
                        waited = true;
                        joinGame(tcpPort);
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

    public void playGame(Socket socket, BufferedReader input, PrintWriter output, boolean player1) {
        game = new Game();
        String message;
        BufferedReader moveRead = new BufferedReader(new InputStreamReader(System.in));
        boolean yourTurn = false;
        boolean gameOver = false;
        String result;
        String move;
        if (player1) {
            yourTurn = true;
        }
        while (!gameOver) {
        try {
                if (yourTurn) {
                    move = moveRead.readLine();
                    output.println(move);
                    message = wait(input);
                    System.out.println(message);
                    game.enemyBoard.addYourShot(message,move.charAt(0),Integer.valueOf(move.substring(1)));
                    game.printBoards();
                    yourTurn = false;
                } else {
                    System.out.println("waiting");
                    message = wait(input);
                    int y = message.charAt(1);
                    result = game.myBoard.doShot(message.charAt(0),Integer.valueOf(message.substring(1)));
                    output.println(result);
                    game.printBoards();
                    yourTurn = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            };
        } 
        // message = wait(input);
        // System.out.println(message);
        // char a = message.charAt(0);
        // char b = message.charAt(1);
        // System.out.println(a);
        // System.out.println(b);
        // result = game.myBoard.doShot(a,b);
        // System.out.println(result);
        // output.println(result);
        // wait(input);
        
        System.exit(0);
    }

    public void hostGame(int port) throws IOException {
        System.out.println(port);
        boolean playing = false;
        ServerSocket server = new ServerSocket(port);
        while (!playing) {
            try (
                    Socket client = server.accept();
                    BufferedReader inMsg = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter outMsg = new PrintWriter(client.getOutputStream(), true);

            ) {
                while (inMsg.readLine() == null)
                    ;
                playGame(client, inMsg, outMsg, true);
            } catch (Exception e) {
            }
        }
        System.out.println("Good Game");
    }

    public void joinGame(int port) {
        boolean playing = false;
        try (
                Socket client = new Socket(InetAddress.getLocalHost().getHostAddress(), port);
                BufferedReader inMsg = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outMsg = new PrintWriter(client.getOutputStream(), true);) {
            outMsg.println("ready");
            playGame(client, inMsg, outMsg, false);

        } catch (Exception e) {

        }

    }

    public String wait(BufferedReader input) {
        String msg;
        try {
            while ((msg = input.readLine()).isEmpty());
            return msg;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "fail";
    }

}
