package view;

import service.SignInService;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * a class extends JPanel containing these elements that are using for taking values from user
 * @author hqvjet
 * @version 1.0.0
 * @since 2022/06/19 1:23PM
 */
public class SignIn extends JPanel {
    private final int width;
    private final int height;
    private final int x;
    private final int y;

    private JLabel  topic,
                    userNameLabel,
                    passWordLabel;

    public JTextField  userNameTxt;
    public JPasswordField passWordTxt;

    public JButton  submit;
    public JButton signUp;

    SignUp SU;

    public Socket player;

    /**
     * A constructor class of class SignIn
     * @author hqvjet
     * @since 2022/06/20 1:14 PM
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public SignIn(Socket socket, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.player = socket;
        initPanel();
        initComponents();
        addElements();
    }

    private void addElements() {
        add(topic);
        add(userNameLabel);
        add(passWordLabel);
        add(userNameTxt);
        add(passWordTxt);
        add(submit);
        add(signUp);
    }

    /**
     * A private function for initializing class SignIn as JPanel
     * @author hqvjet
     * @since 2022/06/20 12:59PM
     */
    private void initPanel() {
        setBounds(x, y, width, height);
        setLayout(null);
        setBackground(new Color(205, 0, 0));
    }

    private void initComponents()
    {
        initLabels();
        initTxtFields();
        initButtons();

    }

    /**
     * A private function for initializing  JLabel of class SignIn
     * @author hqvjet
     * @since 2022/06/20 12:59PM
     */
    private void initLabels()
    {
        int w = getWidth(), h = getHeight();

        topic = new JLabel("Sign in");
        topic.setBounds((int) (w*0.05), (int) (h*0.05),(int) (w*0.95),(int) (h*0.1));
        topic.setFont(new Font("Times New Roman", Font.BOLD, 25));
        //topic.setForeground(new Color());

        userNameLabel = new JLabel("User name: ");
        userNameLabel.setBounds((int) (w*0.15), (int) (h*0.25), (int) (w*0.2), (int) (h*0.15));
        userNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //userNameLabel.setForeground(new Color());

        passWordLabel = new JLabel("Password: ");
        passWordLabel.setBounds((int) (w*0.15), (int) (h*0.45), (int) (w*0.2), (int) (h*0.15));
        passWordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //passWordLabel.setForeground(new Color());
    }

    /**
     * A private function for initializing  JTextField of class SignIn
     * @author hqvjet
     * @since 2022/06/20 1:06 PM
     */
    private void initTxtFields()
    {
        int w = getWidth(), h = getHeight();

        userNameTxt = new JTextField();
        userNameTxt.setBounds((int) (w*0.55), (int) (h*0.25), (int) (w*0.4), (int) (h*0.15));
        userNameTxt.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //userNameTxt.setForeground(new Color());

        passWordTxt = new JPasswordField();
        passWordTxt.setBounds((int) (w*0.55), (int) (h*0.45), (int) (w*0.4), (int) (h*0.15));
        //passWordTxt.setForeground(new Color());
    }

    /**
     * A private function for initializing  JButton of class SignIn
     * @author hqvjet
     * @since 2022/06/20 1:12 PM
     */
    private void initButtons()
    {
        int w = getWidth(), h = getHeight();

        submit = new JButton("Ok");
        submit.setBounds((int) (w*0.75), (int) (h*0.75), (int) (w*0.2), (int) (h*0.1));
        submit.setFocusPainted(false);
        submit.setBorderPainted(false);
        //submit.setBackground(new Color());
        submit.addActionListener(new SignInService(this));

        signUp = new JButton("Sign up now");
        signUp.setBounds((int) (w*0.25), (int) (h*0.75), (int) (w*0.2), (int) (h*0.1));
        signUp.setFocusPainted(false);
        signUp.setBorderPainted(false);
        //signUp.setBackground(new Color());

        signUp.addActionListener(new SignInService(this));
    }
}
