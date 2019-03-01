package example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    Socket socket;
    Scanner in, stdin;
    BufferedWriter out;

    public Client() {
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Вы подключились к серверу!");
            in = new Scanner(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            stdin = new Scanner(System.in);

            while (in.hasNext()){
                String line = in.nextLine();
                System.out.println("Message from server: " + line);
                String query = stdin.nextLine();
                if(query.equals("quit")){
                    System.out.println("client exit");
                    break;
                }
                out.write("message from client: " + query);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
