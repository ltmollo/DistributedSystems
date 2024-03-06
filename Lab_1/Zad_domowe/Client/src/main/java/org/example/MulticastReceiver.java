package org.example;

import java.net.*;

public class MulticastReceiver extends Thread{
    private final MulticastSocket socket;
    public MulticastReceiver(MulticastSocket datagramSocket) {
        this.socket = datagramSocket;
    }
    @Override
    public void run() {

        int portNumber = 12346;
        InetAddress address = null;

        try {
            address = InetAddress.getByName("230.0.0.0");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try {

            while (true) {

                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length, address, portNumber);;
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("[Multicast received response]: " + msg);
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
