package example;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    ConcurrentLinkedQueue<ClientHandler> clients;
    BufferedWriter out;
    Scanner in;
    int clientCount;

    public Server() {
        clientCount = 0;
        try {
            serverSocket = new ServerSocket(8080);
            clients = new ConcurrentLinkedQueue<>();
            while (true) {
                socket = serverSocket.accept();
                //in = new Scanner(socket.getInputStream());
                //out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ClientHandler client = new ClientHandler(socket, this);
                clients.add(client);
                clientCount++;
                System.out.println(clientCount + " clients on server");
                new Thread(client).start();
            }
        }catch (Exception e){}
    }

    void sendAllMessages(String msg){
        for(ClientHandler client : clients){
            client.send(msg);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
