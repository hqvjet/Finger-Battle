package view;

import javax.swing.*;
import java.awt.*;

public class SignUp extends JPanel {
    private int width, height;

    private JLabel  topic,
            userNameLabel,
            passWordLabel,
            rePassWordLabel;

    private JTextField  userNameTxt;
    private JPasswordField  passWordTxt,
                            rePassWordTxt;

    private JButton submit,
            signUp;
    public SignUp(int width, int height) {
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

        rePassWordLabel = new JLabel();
        rePassWordLabel.setBounds(...);
        rePassWordLabel.setFont(...);
        rePassWordLabel.setForeground(...);
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

        rePassWordTxt = new JPasswordField();
        rePassWordTxt.setBounds(...);
        rePassWordTxt.setForeground(...);
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
