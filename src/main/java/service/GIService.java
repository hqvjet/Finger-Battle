package service;

import constants.Cipher;
import view.FingerBattle;
import view.GI;
import view.Ingame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class GIService implements ActionListener {
    GI gameInterface;
    FingerBattle FF;
    Socket socket;
    BufferedWriter writer;
    BufferedReader reader;
    boolean[] room = new boolean[1000];

    public GIService(GI gameInterface){

        this.gameInterface = gameInterface;
        this.FF = gameInterface.FF;
        this.socket = gameInterface.socket;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gameInterface.match){
            try {
                writer.write(Cipher.cipher_INQUEUE);
                writer.newLine();
                writer.flush();
                if(reader.readLine().equals(Cipher.cipher_ALLOW_TO_BATTLE)){

                    FF.loadInterface.remove(gameInterface);
                    System.out.println("go");
                    FF.loadInterface.add(new Ingame(socket, gameInterface.user));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        else if(e.getSource() == gameInterface.avatar){

        }

        else if(e.getSource() == gameInterface.setting){

        }

        else if(e.getSource() == gameInterface.quit){

        }
        FF.loadInterface.repaint();
    }

    private int generatePortID() {
        for(int i = 2; i <= 1000; ++i)
            if(!room[i]){
                room[i] = true;
                return i;
            }

        return -1;
    }
}
