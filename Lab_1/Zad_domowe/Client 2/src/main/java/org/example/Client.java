package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        String hostName = "localhost";
        int portNumber = 12345;
        int multicastPortNumber = 12346;
        Socket socket;
        DatagramSocket datagramSocket;
        MulticastSocket multicastSocket;
        InetAddress group = InetAddress.getByName("230.0.0.0");

        final String SYNCHRONIZE_USERNAME = "SYNCHRONIZE_USERNAME";

        System.out.println("JAVA CLIENT");


        try {

            socket = new Socket(hostName, portNumber);
            datagramSocket = new DatagramSocket();
            multicastSocket = new MulticastSocket(multicastPortNumber);
            multicastSocket.joinGroup(group);

            InetAddress address = InetAddress.getByName("localhost");

            // in & out streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            final Sender sender = new Sender(out, socket, datagramSocket, multicastSocket, address,
                    portNumber, group, multicastPortNumber);
            final TcpReceiver tcpReceiver = new TcpReceiver(in);
            final UdpReceiver udpReceiver = new UdpReceiver(datagramSocket);
            final MulticastReceiver multicastReceiver = new MulticastReceiver(multicastSocket);

            // enter username
            while (true) {
                System.out.println("Enter your nick: ");
                String msg = scanner.nextLine();
                out.println(msg);

                // check if unique
                String response = in.readLine();
                System.out.println("Received: " + response);
                if (!response.contains("ERROR")) {

                    byte[] sendBuffer = (SYNCHRONIZE_USERNAME + " " + msg + "!").getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                    datagramSocket.send(sendPacket);
                    sender.setUsername(msg);
                    break;
                }
            }

            System.out.println("Now you can send messages: ");
            sender.start();
            tcpReceiver.start();
            udpReceiver.start();
            multicastReceiver.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
