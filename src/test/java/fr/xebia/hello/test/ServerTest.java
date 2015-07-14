package fr.xebia.hello.test;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerTest {

    public static int findAvailablePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }
}
