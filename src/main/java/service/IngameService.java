package service;

import constants.Cipher;
import view.Ingame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

import static constants.Character.*;

public class IngameService implements MouseListener, Runnable {
    Ingame ig;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    String getAction = "";
    Thread t;

    public IngameService(Ingame ingame) {
        this.ig = ingame;
        this.socket = ig.socket;
        this.reader = ig.reader;
        this.writer = ig.writer;

        t = new Thread(this);
        t.start();

    }

    public ImageIcon resizeImg(ImageIcon component){
        ImageIcon playerI = component;
        Image player = playerI.getImage().getScaledInstance(ig.player1.getWidth(), ig.player1.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        return playerI;
    }

    private ImageIcon getIconByValueForP1(String value) {
        if(value.equals("1"))
            return resizeImg(PLAYER1_NORMAL);
        else if(value.equals("2"))
            return resizeImg(PLAYER1_ATTACK);
        else if(value.equals("3"))
            return resizeImg(PLAYER1_ATTACK_SUCCESS);
        else
            return resizeImg(PLAYER1_ATTACK_DRAW);
    }

    private ImageIcon getIconByValueForP2(String value) {
        if(value.equals("1"))
            return resizeImg(PLAYER2_NORMAL);
        else if(value.equals("2"))
            return resizeImg(PLAYER2_ATTACK);
        else if(value.equals("3"))
            return resizeImg(PLAYER2_ATTACK_SUCCESS);
        else
            return resizeImg(PLAYER2_ATTACK_DRAW);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    boolean stop = false;
    //catch enemy act
    @Override
    public void run() {

        while(!stop){
            try {
                getAction = reader.readLine();
                System.out.println("get "+getAction);
                if(getAction.equals(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_ATTACK_SUCCESS)){
                    ig.player2.setIcon(getIconByValueForP2("3"));
                    bug = true;
                    ig.P1.setHp(ig.P1.getHp() - ig.P2.getAtk());
                    ig.P1HP.setText("HP: " + ig.P1.getHp());
                }

                else if(getAction.equals(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_ATTACK_FAIL)) {
                    ig.player2.setIcon(getIconByValueForP2("2"));
                    bug = true;
                }
                else if(getAction.equals(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_ATTACK_DRAW)) {
                    ig.player2.setIcon(getIconByValueForP2("4"));
                    bug = true;
                }
                else if(getAction.equals(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_NORMAL)) {
                    ig.player2.setIcon(getIconByValueForP2("1"));
                    bug = true;
                }

                if(ig.P1.getHp() == 0){
                    alert("LOSE!");
                    ig.atk.setText("LEAVE");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void alert(String s){
        JOptionPane.showMessageDialog(null, s);
    }
    boolean bug = true;
    @Override
    public void mousePressed(MouseEvent e) {
        if(ig.atk.getText().equals("ATTACK!!!")){
            try {
                writer.write(Cipher.cipher_ATTACK_PERMISSIONS);
                writer.newLine();
                writer.flush();
                System.out.println("give "+Cipher.cipher_ATTACK_PERMISSIONS);

                if(bug){
                    ig.atk.doClick();
                    bug  = false;
                }
                if(getAction.equals(Cipher.cipher_SELF_ACTION + Cipher.cipher_ATTACK_SUCCESS)){
                    ig.player1.setIcon(getIconByValueForP1("3"));
                    ig.P2.setHp(ig.P2.getHp() - ig.P1.getAtk());
                    ig.P2HP.setText("HP: " + ig.P2.getHp());
                    if(ig.P2.getHp() == 0) {
                        alert("WIN!");
                        ig.atk.setText("LEAVE");
                    }
                }

                else if(getAction.equals(Cipher.cipher_SELF_ACTION + Cipher.cipher_ATTACK_FAIL)){
                    ig.player1.setIcon(getIconByValueForP1("2"));

                }

                else if(getAction.equals(Cipher.cipher_SELF_ACTION + Cipher.cipher_ATTACK_DRAW)){
                    ig.player1.setIcon(getIconByValueForP1("4"));
                }


            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        else{
            try {
                writer.write(Cipher.cipher_ENDGAME);
                writer.newLine();
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            stop = true;
            ig.parent.remove(ig);
            ig.parent.add(ig.gi);
            ig.parent.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getSource() == ig.atk){
            ig.player1.setIcon( getIconByValueForP1("1"));
            try {
                writer.write(Cipher.cipher_NORMAL);
                writer.newLine();
                writer.flush();
                System.out.println("give "+Cipher.cipher_NORMAL);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
