package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpClientHandler extends Thread{
    private String username;
    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    public TcpClientHandler(Socket socket) {
        this.clientSocket = socket;

        // in & out streams
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){

        try {
            String msg;
            while (true) {
                msg = getMessage();
                if (checkUniqueUsername(msg)) {
                    break;
                } else {
                    sendMessage("ERROR: You have to pass a unique username: " + msg + " is already taken");
                }
            }
            System.out.println("[TCP Client Handler] " + msg + " connected");
            sendMessage("Hello " + msg);
            username = msg;
            Server.getInstance().addNewTcpClient(username, this);

            // read msg, send response
            while (true) {
                msg = getMessage();
                System.out.println("[TCP Client Handler] " + username + ": " + msg);
                if(msg == null || msg.equals("stop")){
                    break;
                }
                Server.getInstance().sendMsgToOtherTcpClients(username, msg);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("[TCP Client Handler] " + username + " disconnected");
        } finally {
            try {
                clientSocket.close();
                Server.getInstance().deleteTcpClients(username);
                System.out.println("stopped");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getMessage() throws IOException {
        return in.readLine();
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public boolean checkUniqueUsername(String username) {
        return Server.getInstance().checkUniqueUsername(username);
    }

}
