package org.example;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    Map<String, TcpClientHandler> tcpClients = new ConcurrentHashMap<>();
    Map<UdpTuple, String> udpClients = new ConcurrentHashMap<>();
    private static Server instance = null;


    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) {

        System.out.println("SERVER");
        int portNumber = 12345;

        Thread tcpServerThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    TcpClientHandler newTcpClient = new TcpClientHandler(clientSocket);
                    newTcpClient.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        tcpServerThread.start();

        try {
            DatagramSocket datagramSocket = new DatagramSocket(portNumber);
            UdpClientHandler clientHandler = new UdpClientHandler(datagramSocket);
            clientHandler.start();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewTcpClient(String username, TcpClientHandler tcpClientHandler) {
        this.tcpClients.put(username, tcpClientHandler);
    }

    public void deleteTcpClients(String username) {
        this.tcpClients.remove(username);
        System.out.println("Deleted: " + username);
    }

    public void sendMsgToOtherTcpClients(String username, String msg) {
        this.tcpClients.forEach((clientUsername, tcpClientHandler) -> {
            if (!clientUsername.equals(username)) {
                tcpClientHandler.sendMessage("[" + username + "]: " + msg);
            }
        });
    }

    public void addNewUdpClient(UdpTuple udpTuple, String username) {
        this.udpClients.put(udpTuple, username);
    }

    public void sendMsgToOtherUdpClients(UdpTuple udpTuple, DatagramSocket socket, String msg) {

        String senderUsername = this.udpClients.get(udpTuple);
        String msgToSend = "[" + senderUsername + "]: " + msg;
        byte[] sendBuffer = msgToSend.getBytes();

        this.udpClients.forEach((tuple, clientUsername) -> {
            if (!udpTuple.equals(tuple)) {
                System.out.println(tuple.address());
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, tuple.address(), tuple.port());
                try {
                    socket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean checkUniqueUsername(String username) {
        return !this.tcpClients.containsKey(username);
    }

    public void deleteUdpClients(String username) {
        udpClients.entrySet().forEach(entry -> {
            if (entry.getValue().equals(username)) {
                udpClients.remove(entry.getKey());
            }
        });
    }

    public void deleteClients(String username) {
        this.deleteTcpClients(username);
        this.deleteUdpClients(username);
    }

}
