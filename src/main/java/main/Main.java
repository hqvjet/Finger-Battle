package main;

import model.User;
import org.xml.sax.SAXException;
import view.Enter;
import view.FingerBattle;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {
    static Enter signin;

    static Socket player;

    public Main() throws IOException, ParserConfigurationException, SAXException {
        player = new Socket("localhost", 2);

        signin = new Enter(player);
    }

    public static void main(String[] args) throws IOException {
        try {
            new Main();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static void accept(User user) {
        signin.dispose();
        new FingerBattle(user, player);
    }
}
