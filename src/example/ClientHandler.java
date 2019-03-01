package example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    Server server;
    Socket socket;
    Scanner in, stdin;
    BufferedWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            in = new Scanner(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (Exception e){}
    }

    @Override
    public void run() {
        while (true){
            String msg = in.nextLine();
            try {
                out.write(msg);
                out.flush();
            }catch (Exception e){}
        }
    }

    void send(String message){
        try {
            out.write(message);
            out.flush();
        }catch (Exception e){}
    }

}
