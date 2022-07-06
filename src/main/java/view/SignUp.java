package view;

import constants.Colors;
import service.SignUpService;
import tools.MyButtonUI;
import tools.Radius;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * a class extends JPanel containing these elements that are using for taking values from user
 * @author hqvjet
 * @version 1.0.0
 * @since 2022/06/20 2:50 PM
 */
public class SignUp extends JPanel {
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private JLabel  topic,
            userNameLabel,
            passWordLabel,
            rePassWordLabel;

    public JTextField  userNameTxt;
    public JPasswordField  passWordTxt;
    public JPasswordField rePassWordTxt;

    public JButton submit;
    public JButton signIn;
    private JLabel bg;
    public Socket player;
    /**
     * A constructor class of class SignUp
     * @author hqvjet
     * @since 2022/06/20 2:50 PM
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public SignUp(Socket socket, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.player = socket;
        initPanel();
        initComponents();
        addElements();
    }

    public ImageIcon resizeImg(ImageIcon component){
        ImageIcon playerI = component;
        Image player = playerI.getImage().getScaledInstance(this.getWidth(), this.getHeight()+70, Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        return playerI;
    }

    private void addElements() {
        add(topic);
        add(userNameLabel);
        add(passWordLabel);
        add(rePassWordLabel);
        add(userNameTxt);
        add(passWordTxt);
        add(rePassWordTxt);
        add(submit);
        add(signIn);
        add(bg);
    }

    /**
     * A private function for initializing class SignUp as JPanel
     * @author hqvjet
     * @since 2022/06/20 2:50 PM
     */
    private void initPanel() {
        setBounds(x, y, width, height);
        setLayout(null);
    }

    private void initComponents()
    {
        initLabels();
        initTxtFields();
        initButtons();
    }

    /**
     * A private function for initializing  JLabel of class SignUp
     * @author hqvjet
     * @since 2022/06/20 2:49 PM
     */
    private void initLabels()
    {
        int w = getWidth(), h = getHeight();

        bg = new JLabel();
        bg.setBounds(0,0, w, h);
        bg.setIcon((resizeImg(new ImageIcon("icons/SigninBg.jpg"))));

        topic = new JLabel("<HTML><U>SIGN UP</U></HTML>");
        topic.setBounds((int) (w*0.05), (int) (h*0.05),(int) (w*0.95),(int) (h*0.1));
        topic.setFont(new Font("Times New Roman", Font.BOLD, 25));
        topic.setForeground(Colors.signin_label);

        userNameLabel = new JLabel("User name: ");
        userNameLabel.setBounds((int) (w*0.15), (int) (h*0.2), (int) (w*0.2), (int) (h*0.1));
        userNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        userNameLabel.setForeground(Colors.signin_label);

        passWordLabel = new JLabel("Password: ");
        passWordLabel.setBounds((int) (w*0.15), (int) (h*0.4), (int) (w*0.2), (int) (h*0.1));
        passWordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        passWordLabel.setForeground(Colors.signin_label);

        rePassWordLabel = new JLabel("Confirm your password: ");
        rePassWordLabel.setBounds((int) (w*0.15), (int) (h*0.6), (int) (w*0.4), (int) (h*0.1));
        rePassWordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        rePassWordLabel.setForeground(Colors.signin_label);
    }

    /**
     * A private function for initializing  JTextField of class SignUp
     * @author hqvjet
     * @since 2022/06/20 2:49 PM
     */
    private void initTxtFields()
    {
        int w = getWidth(), h = getHeight();

        userNameTxt = Radius.radiusTextField();
        userNameTxt.setBounds((int) (w*0.55), (int) (h*0.2), (int) (w*0.4), (int) (h*0.1));
        userNameTxt.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //userNameTxt.setForeground(new Color());

        passWordTxt = Radius.radiusPasswordField();
        passWordTxt.setBounds((int) (w*0.55), (int) (h*0.4), (int) (w*0.4), (int) (h*0.1));
        //passWordTxt.setForeground(new Color());

        rePassWordTxt = Radius.radiusPasswordField();
        rePassWordTxt.setBounds((int) (w*0.55), (int) (h*0.6), (int) (w*0.4), (int) (h*0.1));
        //rePassWordTxt.setForeground(new Color());
    }

    /**
     * A private function for initializing  JButton of class SignUp
     * @author hqvjet
     * @since 2022/06/20 2:48 PM
     */
    private void initButtons()
    {
        int w = getWidth(), h = getHeight();

        submit = new JButton("Ok");
        submit.setBounds((int) (w*0.75), (int) (h*0.8), (int) (w*0.15), (int) (h*0.1));
        submit.setFocusPainted(false);
        submit.setUI(new MyButtonUI());
        //submit.setBackground(new Color());
        submit.addActionListener(new SignUpService(this));

        signIn = new JButton("Sign in now");
        signIn.setBounds((int) (w*0.25), (int) (h*0.8), (int) (w*0.2), (int) (h*0.1));
        signIn.setFocusPainted(false);
        signIn.setUI(new MyButtonUI());
        //signIn.setBackground(new Color());
        signIn.addActionListener(new SignUpService(this));
    }
}


