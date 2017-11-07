import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String args[]) throws IOException, InterruptedException {
        Messenger messenger = new Messenger();

        byte[] message = {1, 2, 3, 42, 4, 5};
        messenger.sendMessageByte(message);

        Thread.sleep(1000);
        messenger.sendMessageByte(message);

    }

}
