package service;

import constants.Cipher;
import constants.Role;
import main.Main;
import model.User;
import serverHost.reponsitories.SignInRepo;
import security.Encryption;
import view.Enter;
import view.SignIn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SignInService implements ActionListener {

    SignIn signin;
    SignInRepo SIRepo;
    User user;
    Encryption encryption;
    BufferedWriter writer;
    BufferedReader reader;
    public SignInService(SignIn signin){
        this.signin = signin;

        try {
            reader = new BufferedReader(new InputStreamReader(signin.player.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(signin.player.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        SIRepo = new SignInRepo();
        encryption = new Encryption();
    }

    public void reset(){
        signin.userNameTxt.setText("");
        signin.passWordTxt.setText("");
    }

    private void alert(String s){
        JOptionPane.showMessageDialog(null, s);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signin.submit) {
            try {
                System.out.println("Catch button");
                user = new User(signin.userNameTxt.getText(), encryption.encrypt( String.valueOf( signin.passWordTxt.getPassword())), Role.PLAYER);
                writer.write(convertToCipher(user.getUsername(), user.getPassword()));
                writer.newLine();
                writer.flush();
                boolean stop = false;

                while(!stop){
                    String getCipher = reader.readLine();
                    if(getCipher.equals(Cipher.cipher_True)){
                        alert("Sign In Successful");
                        signin.setVisible(false);
                        stop = true;
                        Main.accept(user);
                    }
                    else if(getCipher.equals(Cipher.cipher_False)){
                        reset();
                        alert("Wrong input, try again");
                        stop = true;
                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == signin.signUp) {
            signin.setVisible(false);
            reset();
            Enter.signup.setVisible(true);
        }
    }

    private String convertToCipher(String un, String pw) {
        return Cipher.cipher_SIGNIN_OFFER + un + Cipher.cipherKey + pw;
    }
}

