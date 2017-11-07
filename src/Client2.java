import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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
                    while(true){
                        try {
                            Byte line = socketInput.readByte();
                            System.out.println("From Server: " + line.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();

            String line = "";
            while (!line.equals("bye"))
            {
                try
                {
                    line = dis.readLine();

                    dos.writeUTF(line);
                    dos.flush();
                }
                catch(IOException ioe)
                {
                    System.out.println("Sending error: " + ioe.getMessage());
                }
            }

            dis.close();
            dos.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
