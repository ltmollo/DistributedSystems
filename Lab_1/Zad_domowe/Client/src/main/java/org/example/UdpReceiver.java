package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpReceiver extends Thread {
    private final int portNumber;
    private final DatagramSocket socket;
    private final InetAddress address;

    public UdpReceiver(DatagramSocket datagramSocket, InetAddress address, int portNumber) {
        this.socket = datagramSocket;
        this.portNumber = portNumber;
        this.address = address;
    }

    @Override
    public void run() {

        try {

            while (true) {

                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length, address, portNumber);
                ;
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("[UDP received response]: " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
