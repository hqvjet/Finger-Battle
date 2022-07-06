package view;

import constants.Colors;
import constants.ScreenSize;
import org.checkerframework.checker.units.qual.C;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;


/**
 * Enter class is created as sign in UI, extends JFrame, it will be disappeared when login function successful, it is also a container
 * @author hqvjet
 * @since 2022-06-19 1:08 AM
 * @version 1.0.0
 */
public class Enter extends JFrame {

    //Declare variables
    Socket player;
    public static SignIn signin;
    public static SignUp signup;
    //construction function
    public Enter(Socket socket) throws ParserConfigurationException, IOException, SAXException {
        this.player = socket;
        initFrame();
        initPanel();
        addElements();

    }

    /**
     * Initialize Enter class as JFrame with Width: 500, Height: 300
     * @author hqvjet
     * @since 2022-06-19 1:05 AM
     */
    private void initFrame()
    {
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
    }

    /**
     * Initialize both SignIn and SignUp class as JPanel with Width: 500, Height: 300
     * @author hqvjet
     * @since 2022-06-19 1:06 AM
     */
    private void initPanel() throws ParserConfigurationException, IOException, SAXException {
        int w = getWidth(), h = getHeight();

        signin = new SignIn(player, (int) (w*0.25), (int) (h*0.3), (int) (w*0.5), (int) (h*0.4));

        signup = new SignUp(player, (int) (w*0.25), (int) (h*0.22), (int) (w*0.5), (int) (h*0.55));
    }

    private void addElements(){
        add(signin);
        add(signup);
        signup.setVisible(false);
    }
}
