package serverHost;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ServerSocket serverSocket = null;
    public ArrayList<ClientHandler> players;
    public Server(){
        System.out.println("On administrator permission");
        try {
            serverSocket = new ServerSocket(2);
            System.out.println(serverSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
        players = new ArrayList<>();
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws IOException {
        while(!serverSocket.isClosed()){
            Socket newPlayerJoined = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(this, newPlayerJoined);
        }
    }

    public static void main(String[] args){
        Server sv = new Server();
    }
}
