import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by ekaterina on 11/4/17.
 */
public class Client2 {
    public static void main(String[] args) {
        try {
            //
            // Create a connection to the server socket on the server application
            //

            InetAddress host = InetAddress.getLocalHost();
            Socket socket = new Socket(host.getHostName(), 5600);

            //
            // Send a message to the client application
            //
            DataOutputStream dos = new DataOutputStream(
                    socket.getOutputStream());

            DataInputStream dis = new DataInputStream(System.in);
            DataInputStream socketInput = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            int length = socketInput.readInt();
                            if (length > 0) {
                                byte[] message = new byte[length];
                                socketInput.readFully(message, 0, message.length);
                                for (int i = 0; i < length; i++) {
                                    System.out.println("Input: " + message[i]);
                                }
                            }
                            //Byte line = socketInput.readByte();
                            //System.out.println("From Server: " + line.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();

            Scanner scanner = new Scanner(System.in);
            byte[] outMessage = {10, 12, 14, 123, -14};

            while (true) {
                String userLine = scanner.nextLine();
                if (userLine.equals("send")) {
                    try {
                        //line = dis.readLine();

                        dos.writeInt(outMessage.length);
                        dos.write(outMessage);
                        dos.flush();
                    } catch (IOException ioe) {
                        System.out.println("Sending error: " + ioe.getMessage());
                    }
                }
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
