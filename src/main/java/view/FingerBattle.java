package view;

import model.User;

import javax.swing.*;
import java.net.Socket;

public class FingerBattle extends JFrame {
    //declare variables
    GI gameInterface;
    public JPanel loadInterface;
    Socket socket;
    User user;
    public FingerBattle(User user, Socket player){
        this.user = user;
        this.socket = player;
        initFrame();
        initComponents(user);
        addComponent();
    }

    private void addComponent() {
        add(loadInterface);
    }

    private void initComponents(User user) {
        gameInterface = new GI(this);

        initPanel();
    }

    private void initPanel() {
        loadInterface = new JPanel();
        loadInterface.setBounds(0,0, getWidth(), getHeight());
        loadInterface.setLayout(null);
        loadInterface.add(gameInterface);
    }

    private void initFrame() {
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }
}
