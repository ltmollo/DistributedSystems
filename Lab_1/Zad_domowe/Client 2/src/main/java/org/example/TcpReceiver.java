package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class TcpReceiver extends Thread{
    private final BufferedReader in;
    public TcpReceiver(BufferedReader in){
        this.in = in;
    }

    @Override
    public void run(){
        String response;

        while (true) {
            try {
                response = in.readLine();
            } catch (IOException e) {
                System.out.println("[TcpReceiver] Closed connection");
                System.exit(0);
                break;
            }
            System.out.println("[TCP] Received response: " + response);
        }
    }
}
