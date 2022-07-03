package service;

import constants.Cipher;
import model.Player1;
import model.Player2;
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
import static constants.Cipher.*;

public class IngameService implements MouseListener, Runnable {
    Ingame ig;
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    Player1 P1;
    Player2 P2;
    JLabel player1, player2;
    public IngameService(Ingame ingame) {
        this.ig = ingame;
        this.socket = ig.socket;
        this.reader = ig.reader;
        this.writer = ig.writer;
        this.P1 = ig.P1;
        this.P2 = ig.P2;
        this.player1 = ig.player1;
        this.player2 = ig.player2;

        Thread t = new Thread(this);
        t.start();
    }

    public ImageIcon resizeImg(ImageIcon component){
        ImageIcon playerI = component;
        Image player = playerI.getImage().getScaledInstance(ig.player1.getWidth(), ig.player1.getHeight(), Image.SCALE_DEFAULT);
        playerI = new ImageIcon(player);
        return playerI;
    }

    public void sendActionToEnemy() {
        try {
                ig.writer.write(ig.selfStatus);
                ig.writer.newLine();
                ig.writer.flush();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForEnemyAction(){
        new Thread(() -> {
            String enemyAction;
            while(ig.socket.isConnected()){
                try {
                    enemyAction = ig.reader.readLine();
                    if(enemyAction.equals(ig.enemyStatus)){
                        ig.player2.setIcon( getIconByValueForP1(enemyAction));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    //catch enemy act
    @Override
    public void run() {
        boolean stop = false;
        String getActionOfEnemy;
        while(!stop){
            try {
                getActionOfEnemy = reader.readLine();
                if(getActionOfEnemy.equals(cipher_ATTACK_SUCCESS))
                    player2.setIcon(getIconByValueForP2("3"));
                else if(getActionOfEnemy.equals(cipher_ATTACK_FAIL))
                    player2.setIcon(getIconByValueForP2("2"));
                else if(getActionOfEnemy.equals(cipher_ATTACK_DRAW))
                    player2.setIcon(getIconByValueForP2("4"));
                else if(getActionOfEnemy.equals(cipher_NORMAL))
                    player2.setIcon(getIconByValueForP2("1"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == ig.atk){
            try {
                writer.write(Cipher.cipher_ATTACK_PERMISSIONS);
                writer.newLine();
                writer.flush();

                String getTurn = reader.readLine();
                if(getTurn.equals(cipher_ATTACK_SUCCESS)){
                    player1.setIcon(getIconByValueForP1("3"));
                    P2.setHp(P2.getHp() - P1.getAtk());
                    writer.write(cipher_ATTACK_SUCCESS);
                    writer.newLine();
                    writer.flush();
                }

                else if(getTurn.equals(Cipher.cipher_ATTACK_FAIL)){
                    player1.setIcon(getIconByValueForP1("2"));
                    writer.write(Cipher.cipher_ATTACK_FAIL);
                    writer.newLine();
                    writer.flush();
                }

                else if(getTurn.equals(Cipher.cipher_ATTACK_DRAW)){
                    player1.setIcon(getIconByValueForP1("4"));
                    writer.write(Cipher.cipher_ATTACK_DRAW);
                    writer.newLine();
                    writer.flush();
                }


            } catch (IOException ex) {
                ex.printStackTrace();
            }

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
