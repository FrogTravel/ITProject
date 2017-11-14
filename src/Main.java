import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String args[]) throws IOException, InterruptedException {

        byte[] message = {1, 2, 3, 42, 4, 5};

        RepetitionCoding conding = new RepetitionCoding();

        byte[] result = conding.encode(message, 3);

        byte[] decoded = conding.decode(result, 3);

        for (int i = 0; i < decoded.length; i++) {
            System.out.println(decoded[i]);

        }

        Messenger messenger = new Messenger();

        messenger.sendMessageByte(message);

        Thread.sleep(1000);
        messenger.sendMessageByte(message);

    }

}
