import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ekaterina on 11/4/17.
 */
public class Server {
    private ServerSocket server;
    private int port = 5600;

    public Server() {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.connection();
    }

    public void connection() {
        System.out.println("Waiting for client ...");
        try
        {
            Socket socket = server.accept();
            Socket socket1 = server.accept();
            System.out.println("Client accepted: " + socket + "\n" + socket1);

            DataInputStream dis = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            DataInputStream dis1 = new DataInputStream(
                    new BufferedInputStream(socket1.getInputStream()));

            DataOutputStream dos = new DataOutputStream(
                    new BufferedOutputStream(socket1.getOutputStream()));
            DataOutputStream dos1 = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean done = false;
                    while (!done)
                    {
                        try
                        {
                            Byte line = dis.readByte();
                            System.out.println("From first: " + line);
                            done = line.equals("bye");

                            dos.writeByte(line);
                            dos.flush();
                        }
                        catch(IOException ioe)
                        {
                            done = true;
                        }
                    }

                    try {
                        dis.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread secondThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean done = false;
                    while (!done)
                    {
                        try
                        {
                            Byte line = dis1.readByte();
                            System.out.println("From second: " + line);
                            done = line.equals("bye");

                            dos1.writeByte(line);
                            dos1.flush();
                        }
                        catch(IOException ioe)
                        {
                            done = true;
                        }
                    }

                    try {
                        dis.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            secondThread.start();

        }
        catch(IOException ioe)
        {
            System.out.println(ioe);
        }

    }
}