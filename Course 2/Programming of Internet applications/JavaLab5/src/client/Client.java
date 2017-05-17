package client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static Socket get() throws IOException {

                Socket socket = new Socket("localhost", App.SERVER_PORT);

                return socket;
    }
}
