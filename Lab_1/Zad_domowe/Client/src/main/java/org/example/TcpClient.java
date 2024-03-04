package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 12345;
        Socket socket = null;

        System.out.println("JAVA TCP CLIENT");


        try {

            socket = new Socket(hostName, portNumber);

            // in & out streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            final TcpSender tcpSender = new TcpSender(out, socket);
            final TcpReceiver tcpReceiver = new TcpReceiver(in);

            // enter username
            while (true) {
                System.out.println("Enter your nick: ");
                String msg = scanner.nextLine();
                out.println(msg);

                // check if unique
                String response = in.readLine();
                System.out.println("Received: " + response);
                if (!response.contains("ERROR")) {
                    break;
                }
            }

            // send msg, read response

            if (true) {
                System.out.println("Now you can send messages: ");
                tcpSender.start();
                tcpReceiver.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
