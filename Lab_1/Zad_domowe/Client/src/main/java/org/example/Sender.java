package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class Sender extends Thread{

    private final PrintWriter out;
    private final Socket socket;
    private final DatagramSocket datagramSocket;
    private final InetAddress address;
    private final int portNumber;

    Scanner scanner = new Scanner(System.in);
    public Sender(PrintWriter out, Socket socket, DatagramSocket datagramSocket,InetAddress address, int port) throws UnknownHostException {
        this.out = out;
        this.socket = socket;
        this.datagramSocket = datagramSocket;
        this.portNumber = port;
        this.address = address;

    }

    @Override
    public void run(){
        while (true) {
            String msg = scanner.nextLine();
            if (msg.startsWith("U")){
                msg = msg.substring(("U ").length());
                byte[] sendBuffer = msg.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                try {
                    datagramSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                out.println(msg);
            }
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
