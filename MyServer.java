package test;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;

public class MyServer {

    int port;
    ClientHandler ch;
    boolean stoped;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
    }

    void start() {
        stoped = false;
        new Thread(() -> initServer()).start();
    }

    private void initServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stoped) {
                try {
                    Socket client = server.accept();
                    ch.handleClient(client.getInputStream(), client.getOutputStream());
                    ch.close();
                    client.close();
                } catch (SocketTimeoutException e) {
                }
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ch.close();
        }
    }

    void close() {
        stoped = true;
    }

}