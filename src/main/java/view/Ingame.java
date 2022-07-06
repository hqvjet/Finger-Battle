package view;

import constants.Colors;
import constants.ScreenSize;
import model.Player1;
import model.Player2;
import model.User;
import service.IngameService;
import constants.Character;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
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
    public JLabel bg_remote;
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
        add(bg_remote);
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
        P2Name.setForeground(Color.WHITE);
    }

    private void initP1Name() {
        P1Name = new JLabel("P1: " + user.getUsername());
        P1Name.setBounds((int) (width*0.2), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P1Name.setFont(new Font("Serif", Font.ITALIC, 16));
        P1Name.setForeground(Color.WHITE);
    }

    private void initP2HP() {
        P2HP = new JLabel("HP: " + P2.getHp());
        P2HP.setBounds((int) (width*0.8), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P2HP.setFont(new Font("Serif", Font.ITALIC, 16));
        P2HP.setForeground(Color.WHITE);
    }

    private void initP1HP() {
        P1HP = new JLabel("HP: " + P1.getHp());
        P1HP.setBounds((int) (width*0.25), (int) (height*0.6), (int) (width*0.1), (int) (height*0.05));
        P1HP.setFont(new Font("Serif", Font.ITALIC, 16));
        P1HP.setForeground(Color.WHITE);
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

    private ImageIcon resizeImg(int x, int y, ImageIcon component){
        ImageIcon playerI = component;
        Image player = playerI.getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        return playerI;
    }

    private void initLabels() {
        bg = new JLabel();
        bg.setBounds(0,0, this.getWidth(), (int) (this.getHeight()*0.6));
        ImageIcon bgI = resizeImg(bg.getWidth(), bg.getHeight(), new ImageIcon("icons/BattlePiece.jpg"));
        bg.setIcon(bgI);

        bg_remote = new JLabel();
        bg_remote.setBounds(0, (int) (this.getHeight()*0.6), this.getWidth(), (int) (this.getHeight()*0.4));
        ImageIcon bgRemoteI = resizeImg(bg_remote.getWidth(), bg_remote.getHeight(), new ImageIcon("icons/ig_bg_panel.png"));
        bg_remote.setIcon(bgRemoteI);
    }

    private void initButtons() {
        atk = new RoundedCornerButton("ATTACK!!!");
        atk.setBounds((int) (width*0.42), (int) (height*0.65), (int) (width*0.15), (int) (height*0.2));
        atk.setFocusPainted(false);
        atk.setBackground(Colors.gi_atk);
        atk.addMouseListener(new IngameService(this));
    }
}

class RoundedCornerButton extends JButton {
    private static final float ARC_WIDTH = 16f;
    private static final float ARC_HEIGHT = 16f;
    protected static final int FOCUS_STROKE = 2;
    protected final Color fc = new Color(100, 150, 255, 200);
    protected final Color ac = new Color(230, 230, 230);
    protected final Color rc = Color.ORANGE;
    protected Shape shape;
    protected Shape border;
    protected Shape base;
    public RoundedCornerButton() {
        super();
    }

    public RoundedCornerButton(Icon icon) {
        super(icon);
    }

    public RoundedCornerButton(String text) {
        super(text);
    }

    public RoundedCornerButton(Action a) {
        super(a);
        // setAction(a);
    }

    public RoundedCornerButton(String text, Icon icon) {
        super(text, icon);
        // setModel(new DefaultButtonModel());
        // init(text, icon);
        // setContentAreaFilled(false);
        // setBackground(new Color(250, 250, 250));
        // initShape();
    }

    @Override public void updateUI() {
        super.updateUI();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBackground(new Color(250, 250, 250));
        initShape();
    }

    protected void initShape() {
        if (!getBounds().equals(base)) {
            base = getBounds();
            shape = new RoundRectangle2D.Float(
                    0, 0, getWidth() - 1, getHeight() - 1, ARC_WIDTH, ARC_HEIGHT);
            border = new RoundRectangle2D.Float(
                    FOCUS_STROKE, FOCUS_STROKE, getWidth() - 1 - FOCUS_STROKE * 2,
                    getHeight() - 1 - FOCUS_STROKE * 2, ARC_WIDTH, ARC_HEIGHT);
        }
    }

    private void paintFocusAndRollover(Graphics2D g2, Color color) {
        g2.setPaint(new GradientPaint(
                0, 0, color, getWidth() - 1, getHeight() - 1,
                color.brighter(), true));
        g2.fill(shape);
        g2.setColor(getBackground());
        g2.fill(border);
    }

    @Override protected void paintComponent(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()) {
            g2.setColor(ac);
            g2.fill(shape);
        } else if (isRolloverEnabled() && getModel().isRollover()) {
            paintFocusAndRollover(g2, rc);
        } else if (hasFocus()) {
            paintFocusAndRollover(g2, fc);
        } else {
            g2.setColor(getBackground());
            g2.fill(shape);
        }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2.setColor(getBackground());
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override protected void paintBorder(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.draw(shape);
        g2.dispose();
    }

    @Override public boolean contains(int x, int y) {
        initShape();
        return shape == null ? false : shape.contains(x, y);
    }
}

