package view;

import javax.swing.*;
import java.awt.*;

public class SignIn extends JPanel {
    private int width, height;

    private JLabel  topic,
                    userNameLabel,
                    passWordLabel;

    private JTextField  userNameTxt;
    private JPasswordField passWordTxt;

    private JButton submit,
                    signUp;
    public SignIn(int width, int height) {
        this.width = width;
        this.height = height;
        initPanel();
        initComponents();
    }

    private void initPanel() {
        setBounds(0, 0, width, height);
        setLayout(null);
        setBackground(new Color(...));
    }

    private void initComponents()
    {
        initLabels();
        initTxtFields();
        initButtons();
    }

    private void initLabels()
    {
        topic = new JLabel();
        topic.setBounds(...);
        topic.setFont(...);
        topic.setForeground(...);

        userNameLabel = new JLabel();
        userNameLabel.setBounds(...);
        userNameLabel.setFont(...);
        userNameLabel.setForeground(...);

        passWordLabel = new JLabel();
        passWordLabel.setBounds(...);
        passWordLabel.setFont(...);
        passWordLabel.setForeground(...);
    }

    private void initTxtFields()
    {
        userNameTxt = new JTextField();
        userNameTxt.setBounds(...);
        userNameTxt.setFont(...);
        userNameTxt.setForeground(...);

        passWordTxt = new JPasswordField();
        passWordTxt.setBounds(...);
        passWordTxt.setForeground(...);
    }

    private void initButtons()
    {
        submit = new JButton();
        submit.setBounds(...);
        submit.setFocusPainted(false);
        submit.setBorderPainted(false);
        submit.setBackground(...);

        signUp = new JButton();
        signUp.setBounds(...);
        signUp.setFocusPainted(false);
        signUp.setBorderPainted(false);
        signUp.setBackground(...);
    }
}
