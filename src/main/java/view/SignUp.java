package view;

import service.SignUpService;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

import static view.Enter.signup;

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
    }

    /**
     * A private function for initializing class SignUp as JPanel
     * @author hqvjet
     * @since 2022/06/20 2:50 PM
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
     * A private function for initializing  JLabel of class SignUp
     * @author hqvjet
     * @since 2022/06/20 2:49 PM
     */
    private void initLabels()
    {
        int w = getWidth(), h = getHeight();

        topic = new JLabel("Sign up");
        topic.setBounds((int) (w*0.05), (int) (h*0.05),(int) (w*0.95),(int) (h*0.1));
        topic.setFont(new Font("Times New Roman", Font.BOLD, 25));
        //topic.setForeground(new Color());

        userNameLabel = new JLabel("User name: ");
        userNameLabel.setBounds((int) (w*0.15), (int) (h*0.2), (int) (w*0.2), (int) (h*0.1));
        userNameLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //userNameLabel.setForeground(new Color());

        passWordLabel = new JLabel("Password: ");
        passWordLabel.setBounds((int) (w*0.15), (int) (h*0.4), (int) (w*0.2), (int) (h*0.1));
        passWordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //passWordLabel.setForeground(new Color());

        rePassWordLabel = new JLabel("Confirm your password: ");
        rePassWordLabel.setBounds((int) (w*0.15), (int) (h*0.6), (int) (w*0.4), (int) (h*0.1));
        rePassWordLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //rePassWordLabel.setForeground(new Color());
    }

    /**
     * A private function for initializing  JTextField of class SignUp
     * @author hqvjet
     * @since 2022/06/20 2:49 PM
     */
    private void initTxtFields()
    {
        int w = getWidth(), h = getHeight();

        userNameTxt = new JTextField();
        userNameTxt.setBounds((int) (w*0.55), (int) (h*0.2), (int) (w*0.4), (int) (h*0.1));
        userNameTxt.setFont(new Font("Times New Roman", Font.BOLD, 15));
        //userNameTxt.setForeground(new Color());

        passWordTxt = new JPasswordField();
        passWordTxt.setBounds((int) (w*0.55), (int) (h*0.4), (int) (w*0.4), (int) (h*0.1));
        //passWordTxt.setForeground(new Color());

        rePassWordTxt = new JPasswordField();
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
        submit.setBorderPainted(false);
        //submit.setBackground(new Color());
        submit.addActionListener(new SignUpService(this));

        signIn = new JButton("Sign in now");
        signIn.setBounds((int) (w*0.25), (int) (h*0.8), (int) (w*0.2), (int) (h*0.1));
        signIn.setFocusPainted(false);
        signIn.setBorderPainted(false);
        //signIn.setBackground(new Color());
        signIn.addActionListener(new SignUpService(this));
    }
}
