package view;

import constants.ScreenSize;
import model.User;
import service.GIService;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class GI extends JPanel {
    //declare variables
    int x, y, width, height;
    public FingerBattle FF;

    public User user;
    public Socket socket;
    JLabel title;


    public JButton match;
    public JButton avatar;
    public JButton setting;
    public JButton quit;

    public GI(FingerBattle FF) {
        this.FF = FF;
        this.user = FF.user;
        this.x = 0;
        this.y = 0;
        this.width = ScreenSize.MAX_WIDTH;
        this.height = ScreenSize.MAX_HEIGHT;
        this.socket = FF.socket;
        initPanel();
        initComponents();
        addComponent();
    }

    private void addComponent() {
        add(match);
        add(avatar);
        add(setting);
        add(quit);
        add(title);
    }

    private void initComponents() {
        initLabels();
        initButtons();
    }

    private void initButtons() {
        match = new JButton("Find a match");
        match.setBounds((int) (width*0.35), (int) (height*0.25), (int) (width*0.3), (int) (height*0.1));
        match.setFont(new Font("Serif", Font.ITALIC, 24));
        match.setFocusPainted(false);
        match.addActionListener(new GIService(this));

        avatar = new JButton("Custom character");
        avatar.setBounds((int) (width*0.35), (int) (height*0.4), (int) (width*0.3), (int) (height*0.1));
        avatar.setFont(new Font("Serif", Font.ITALIC, 24));
        avatar.setFocusPainted(false);
        avatar.addActionListener(new GIService(this));

        setting = new JButton("Setting");
        setting.setBounds((int) (width*0.35), (int) (height*0.55), (int) (width*0.3), (int) (height*0.1));
        setting.setFont(new Font("Serif", Font.ITALIC, 24));
        setting.setFocusPainted(false);
        setting.addActionListener(new GIService(this));

        quit = new JButton("Quit game");
        quit.setBounds((int) (width*0.35), (int) (height*0.7), (int) (width*0.3), (int) (height*0.1));
        quit.setFont(new Font("Serif", Font.ITALIC, 24));
        quit.setFocusPainted(false);
        quit.addActionListener(new GIService(this));
    }

    private void initLabels() {
        title = new JLabel("FINGER BATTLE");
        title.setBounds( 0, (int) (height*0.01), width, (int) (height*0.15));
        title.setFont(new Font("Serif", Font.ITALIC, 60));
        title.setHorizontalAlignment(JLabel.CENTER);
    }

    private void initPanel() {
        setBounds(x, y, width, height);
        //setBackground(Color.BLACK);
        setLayout(null);
    }
}
