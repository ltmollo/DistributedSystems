package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TcpServer {

    Map<String, TcpClientHandler> tcpClients = new ConcurrentHashMap<>();
    private static TcpServer instance = null;


    public static TcpServer getInstance(){
        if ( instance == null) {
            instance = new TcpServer();
        }
        return instance;
    }

    public static void main(String[] args)  {

        System.out.println("JAVA TCP SERVER");
        int portNumber = 12345;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {


            while (true) {

                // accept client
                Socket clientSocket = serverSocket.accept();
                TcpClientHandler newTcpClient = new TcpClientHandler(clientSocket);
                newTcpClient.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewTcpClient(String username, TcpClientHandler tcpClientHandler){
        this.tcpClients.put(username, tcpClientHandler);
    }
    public void deleteTcpClients(String username) {
        this.tcpClients.remove(username);
        System.out.println("Deleted: " + username);
    }

    public void sendMsgToOtherClients(String username, String msg){
        this.tcpClients.forEach((clientUsername, tcpClientHandler) -> {
            if (!clientUsername.equals(username)) {
                tcpClientHandler.sendMessage(username + ": " + msg);
            }
        });
    }

    public boolean checkUniqueUsername(String username){
        return !this.tcpClients.containsKey(username);
    }

}
