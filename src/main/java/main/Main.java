package main;

import model.User;
import view.Enter;
import view.FingerBattle;

import java.io.IOException;
import java.net.Socket;

public class Main {
    static Enter signin;

    static Socket player;

    public Main() throws IOException {
        player = new Socket("localhost", 2);

        signin = new Enter(player);
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    public static void accept(User user) {
        signin.dispose();
        new FingerBattle(user, player);
    }
}
