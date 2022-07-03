package view;

import constants.ScreenSize;
import model.Player1;
import model.Player2;
import model.User;
import service.IngameService;
import constants.Character;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Ingame extends JPanel {
    public Socket socket;
    public BufferedReader reader;
    public BufferedWriter writer;
    public String enemyStatus, selfStatus; // 1: normal, 2: atk, 3: atk_sc, 4: atk_draw
    int x, y, width, height;

    User user;
    public Player1 P1;
    public Player2 P2;

    public JButton atk;
    public JLabel player1;
    public JLabel player2;
    JLabel enemy;
    JLabel yourSelf;

    public Ingame(Socket socket, User user) throws IOException {
        this.socket = socket;
        this.user = user;
        this.x = 0;
        this.y = 0;
        enemyStatus = "1";
        selfStatus = "1";
        this.width = ScreenSize.MAX_WIDTH;
        this.height = ScreenSize.MAX_HEIGHT;
        initThis();
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        addComponent();
    }

    private void addComponent() {
        add(player1);
        add(player2);
        add(enemy);
        add(atk);
    }

    private void initThis() {
        setBounds(x, y, width, height);
        setLayout(null);
    }

    private void initPanel() throws IOException {
        initPlayer1();

        initPlayer2();

        repaint();
    }

    private void initPlayer2() throws IOException {
        player2 = new JLabel();
        ImageIcon playerI = Character.PLAYER2_NORMAL;
        player2.setBounds((int) (width*0.45), (int) (height*0.05), (int) (width*0.45), (int) (height*0.55));
        Image player = playerI.getImage().getScaledInstance(player2.getWidth(), player2.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        player2.setIcon( playerI);
        P2 = new Player2(3, 1, 10);

        enemy = new JLabel(reader.readLine());
        enemy.setBounds((int) (width*0.75), (int) (height*0.05), (int) (width*0.1), (int) (height*0.05));
    }

    private void initPlayer1() {
        ImageIcon playerI = Character.PLAYER1_NORMAL;
        player1 = new JLabel();
        player1.setBounds((int) (width*0.05), (int) (height*0.05), (int) (width*0.45), (int) (height*0.55));
        Image player = playerI.getImage().getScaledInstance(player1.getWidth(), player1.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        player1.setIcon( playerI);
        P1 = new Player1(3, 1, 10);

        yourSelf = new JLabel(user.getUsername());
        yourSelf.setBounds((int) (width*0.25), (int) (height*0.05), (int) (width*0.45), (int) (height*0.55));
    }

    private void initComponents() throws IOException {
        initPanel();
        initButtons();
    }

    private void initButtons() {
        atk = new JButton();
        atk.setBounds((int) (width*0.42), (int) (height*0.65), (int) (width*0.15), (int) (height*0.2));
        atk.setFocusPainted(false);
        atk.addMouseListener(new IngameService(this));
    }
}
