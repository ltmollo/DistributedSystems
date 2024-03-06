package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        String HOSTNAME = "localhost";
        String MULTICAST_NAME = "230.0.0.0";

        int PORT_NUMBER = 12345;
        int MULTICAST_PORT_NUMBER = 12346;

        Socket socket;
        DatagramSocket datagramSocket;
        MulticastSocket multicastSocket;

        InetAddress address = InetAddress.getByName(HOSTNAME);
        InetAddress groupAddress = InetAddress.getByName(MULTICAST_NAME);

        final String SYNCHRONIZE_USERNAME = "SYNCHRONIZE_USERNAME";

        System.out.println("CLIENT");

        try {

            socket = new Socket(HOSTNAME, PORT_NUMBER);
            datagramSocket = new DatagramSocket();
            multicastSocket = new MulticastSocket(MULTICAST_PORT_NUMBER);
            multicastSocket.joinGroup(groupAddress);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            final Sender sender = new Sender(out, socket, datagramSocket, multicastSocket, address,
                    PORT_NUMBER, groupAddress, MULTICAST_PORT_NUMBER);
            final TcpReceiver tcpReceiver = new TcpReceiver(in);
            final UdpReceiver udpReceiver = new UdpReceiver(datagramSocket, address, PORT_NUMBER);
            final MulticastReceiver multicastReceiver = new MulticastReceiver(multicastSocket, groupAddress,
                    MULTICAST_PORT_NUMBER);

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
                    DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, PORT_NUMBER);
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
