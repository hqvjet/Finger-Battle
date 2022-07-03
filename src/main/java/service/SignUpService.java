package service;

import constants.Cipher;
import constants.Role;
import model.User;
import serverHost.reponsitories.SignUpRepo;
import view.Enter;
import view.SignUp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;

public class SignUpService implements ActionListener {

    SignUp signup;
    SignUpRepo SURepo;
    User user;
    BufferedReader reader;
    BufferedWriter writer;

    public SignUpService(SignUp signup) {
        this.signup = signup;
        SURepo = new SignUpRepo();
        try {
            reader = new BufferedReader(new InputStreamReader(signup.player.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(signup.player.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alert(String s){
        JOptionPane.showMessageDialog(null, s);
    }

    public void reset(){
        signup.userNameTxt.setText("");
        signup.passWordTxt.setText("");
        signup.rePassWordTxt.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signup.submit) {
            try {
            user = new User(signup.userNameTxt.getText(), String.valueOf( signup.passWordTxt.getPassword()), Role.PLAYER);
            writer.write(convertToCipher(user.getUsername(), user.getPassword()));
            writer.newLine();
            writer.flush();
            boolean stop = false;
            String getCipher;
            while(!stop){
                getCipher = reader.readLine();
                if(getCipher.equals(Cipher.cipher_True)){
                    //alert register successful
                    signup.setVisible(false);
                    Enter.signin.setVisible(true);
                    alert("Sign up successful, please sign in");
                    stop = true;
                }
                else if(getCipher.equals(Cipher.cipher_False)){
                    //alert register error
                    reset();
                    alert("Wrong input, try again");
                    stop = true;
                }
            }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == signup.signIn) {
            signup.setVisible(false);
            reset();
            Enter.signin.setVisible(true);
        }
    }

    private String convertToCipher(String un, String pw) {
        return Cipher.cipher_SIGNUP_OFFER + un + Cipher.cipherKey + pw;
    }
}
