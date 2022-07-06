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
import java.util.Objects;

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
    public JLabel P1HP;
    public JLabel P2HP;
    public JLabel P1Name;
    public JLabel P2Name;
    public JPanel parent;
    public JLabel bg;
    public GI gi;

    public Ingame(GI gi, JPanel parent, Socket socket, User user) throws IOException {
        this.gi = gi;
        this.parent = parent;
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
        add(P1Name);
        add(P2Name);
        add(P1HP);
        add(P2HP);
        add(player1);
        add(player2);
        add(atk);
        add(bg);
    }

    private void initThis() {
        setBounds(x, y, width, height);
        setLayout(null);
    }

    private void initPanel() throws IOException {
        initPlayer1();
        initPlayer2();
        initP1Name();
        initP2Name();
        initP1HP();
        initP2HP();
        repaint();
    }

    private void initP2Name() throws IOException {
        P2Name = new JLabel("P2: " + reader.readLine());
        P2Name.setBounds((int) (width*0.75), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P2Name.setFont(new Font("Serif", Font.ITALIC, 16));
    }

    private void initP1Name() {
        P1Name = new JLabel("P1: " + user.getUsername());
        P1Name.setBounds((int) (width*0.2), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P1Name.setFont(new Font("Serif", Font.ITALIC, 16));
    }

    private void initP2HP() {
        P2HP = new JLabel("HP: " + P2.getHp());
        P2HP.setBounds((int) (width*0.8), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P2HP.setFont(new Font("Serif", Font.ITALIC, 16));
    }

    private void initP1HP() {
        P1HP = new JLabel("HP: " + P1.getHp());
        P1HP.setBounds((int) (width*0.25), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P1HP.setFont(new Font("Serif", Font.ITALIC, 16));
    }

    private void initPlayer2() {
        player2 = new JLabel();
        ImageIcon playerI = Character.PLAYER2_NORMAL;
        player2.setBounds((int) (width*0.45), (int) (height*0.05), (int) (width*0.45), (int) (height*0.55));
        Image player = playerI.getImage().getScaledInstance(player2.getWidth(), player2.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        player2.setIcon( playerI);
        P2 = new Player2(3, 1, 10);
    }

    private void initPlayer1() {
        ImageIcon playerI = Character.PLAYER1_NORMAL;
        player1 = new JLabel();
        player1.setBounds((int) (width*0.05), (int) (height*0.05), (int) (width*0.45), (int) (height*0.55));
        Image player = playerI.getImage().getScaledInstance(player1.getWidth(), player1.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        player1.setIcon( playerI);
        P1 = new Player1(3, 1, 10);
    }

    private void initComponents() throws IOException {
        initPanel();
        initButtons();
        initLabels();
    }

    private ImageIcon resizeImg(ImageIcon component){
        ImageIcon playerI = component;
        Image player = playerI.getImage().getScaledInstance(bg.getWidth(), bg.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        return playerI;
    }

    private void initLabels() {
        bg = new JLabel();
        bg.setBounds(0,0, this.getWidth(), (int) (this.getHeight()*0.6));
        ImageIcon bgI = resizeImg(new ImageIcon("icons/BattlePiece.jpg"));
        bg.setIcon(bgI);
    }

    private void initButtons() {
        atk = new JButton("ATTACK!!!");
        atk.setBounds((int) (width*0.42), (int) (height*0.65), (int) (width*0.15), (int) (height*0.2));
        atk.setFocusPainted(false);
        atk.addMouseListener(new IngameService(this));
    }
}
