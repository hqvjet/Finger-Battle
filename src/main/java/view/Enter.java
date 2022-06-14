package view;

import javax.swing.*;

/**
 * Create the Jframe for signin
 */

public class Enter extends JFrame {
    SignIn signin;
    SignUp signup;

    public Enter(){
        initFrame();
        initPanel();
    }

    private void initFrame()
    {
        setLayout(null);
        setUndecorated(true);
        setSize(500,300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initPanel()
    {
        signin = new SignIn(500, 300);
    }

    public static void main(String[] args)
    {
        new Enter();
    }
}
