package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpSender extends Thread{

    private final PrintWriter out;
    private final Socket socket;
    Scanner scanner = new Scanner(System.in);
    public TcpSender(PrintWriter out, Socket socket) {
        this.out = out;
        this.socket = socket;
    }

    @Override
    public void run(){
        while (true) {
            String msg = scanner.nextLine();
            out.println(msg);

            if(msg.equals("stop")){
                try {
                    this.socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
    }
}
