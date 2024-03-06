package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpReceiver extends Thread{
    private final DatagramSocket socket;
    public UdpReceiver(DatagramSocket datagramSocket) {
        this.socket = datagramSocket;
    }
    @Override
    public void run() {

        int portNumber = 12345;
        InetAddress address = null;

        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try {

            while (true) {

                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length, address, portNumber);;
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("[UDP received response]: " + msg);
            }


        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
