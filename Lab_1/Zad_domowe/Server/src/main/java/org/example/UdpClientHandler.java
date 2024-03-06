package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClientHandler extends Thread {

    private final DatagramSocket socket;
    private final String SYNCHRONIZE_USERNAME = "SYNCHRONIZE_USERNAME";

    public UdpClientHandler(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            while (true) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = getMessage(receivePacket);

                InetAddress address = receivePacket.getAddress();
                int port = receivePacket.getPort();

                UdpTuple udpTuple = new UdpTuple(address, port);

                if (msg.startsWith(SYNCHRONIZE_USERNAME)) {
                    int endIndex = msg.indexOf("!");
                    String username = msg.substring((SYNCHRONIZE_USERNAME + " ").length(), endIndex).trim();
                    Server.getInstance().addNewUdpClient(udpTuple, username);
                    System.out.println("[UDP new] " + username);
                } else {
                    sendMessage(msg, udpTuple);
                    System.out.println("[UDP client handler]: " + msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    public void sendMessage(String msg, UdpTuple udpTuple) {
        Server.getInstance().sendMsgToOtherUdpClients(udpTuple, socket, msg);
    }

    private String getMessage(DatagramPacket receivePacket) {
        return new String(receivePacket.getData());
    }
}