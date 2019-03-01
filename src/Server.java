import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Server {

    static int PORT = 8080;
    private ConcurrentLinkedQueue<ClientHandler> clients
            = new ConcurrentLinkedQueue<>();

    public Server(int port) {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        new Thread(()->{
            Scanner in = new Scanner(System.in);
            while(true){
                String query = in.next();
                if(query.equals("quit")){
                    System.out.println("Работа завершена");
                    System.exit(0);
                }
            }
        }).start();

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен!");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                System.out.println("IP : " + client.getClientSocket().getInetAddress());
                clients.add(client);
                new Thread(client).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (clientSocket != null)
                    clientSocket.close();
                System.out.println("Сервер остановлен");
                if (serverSocket != null)
                    serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void showClients(){
        for (ClientHandler cl : clients)
            System.out.println(cl);
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public void removeClient(int pos) {
        int p = 0;
        if (pos > clients.size() || pos < 0) return;
        ClientHandler client = null;
        for (ClientHandler c : clients){
            p++;
            if(p == pos){
                client = c;
                break;
            }
        }
        clients.remove(client);
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        if (args.length == 0)
            new Server(8080);
        else
            new Server(Integer.parseInt(args[0]));
    }

}