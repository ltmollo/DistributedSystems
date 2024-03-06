package org.example;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Thread {
    private final MulticastSocket socket;
    private final InetAddress address;
    private final int portNumber;

    public MulticastReceiver(MulticastSocket datagramSocket, InetAddress groupAddress, int multicastPortNumber) {
        this.socket = datagramSocket;
        this.address = groupAddress;
        this.portNumber = multicastPortNumber;
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
                System.out.println("[Multicast received response]: " + msg);
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
