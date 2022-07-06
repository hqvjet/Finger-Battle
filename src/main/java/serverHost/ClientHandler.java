package serverHost;

import constants.Cipher;
import model.User;
import serverHost.reponsitories.SignInRepo;
import serverHost.reponsitories.SignUpRepo;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler implements Runnable{
    Socket socket;
    BufferedReader reader;
    BufferedWriter writer;
    User user;
    SignInRepo SIRepo;
    SignUpRepo SURepo;
    Server server;
    boolean inQueue = false, inBattle = false, attacking = false;
    String inputFromPlayer;

    public ClientHandler(Server server, Socket newPlayerJoined) {
        try {
            this.socket = newPlayerJoined;
            SIRepo = new SignInRepo();
            SURepo = new SignUpRepo();
            this.server = server;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


            boolean stop = false;
            while(!stop){
                String inf = reader.readLine();
                user = new User();
                handleInformationFromClients(inf);
                if(inf.charAt(0) == Cipher.cipher_SIGNIN_OFFER && handleSigninRequest()){
                    stop = true;
                    Thread thread = new Thread(this);
                    thread.start();
                    server.players.add(this);
                }
                else {
                    if (inf.charAt(0) == Cipher.cipher_SIGNUP_OFFER) {
                        handleSignupRequest();
                    }
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleSignupRequest() throws IOException, SQLException {
        if(SURepo.isCorrect(user)){
            SURepo.pushDataInToDatabase(user);
            writer.write(Cipher.cipher_True);
        }
        else
            writer.write(Cipher.cipher_False);

        writer.newLine();
        writer.flush();
    }

    private boolean handleSigninRequest() throws IOException, SQLException {
        if(SIRepo.isCorrect(user)){
            writer.write(Cipher.cipher_True);
            writer.newLine();
            writer.flush();
            System.out.println("true");
            return true;
        }
        else{
            writer.write(Cipher.cipher_False);
            writer.newLine();
            writer.flush();
            System.out.println("false");
            return false;
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {

                if(!inQueue) {
                    inputFromPlayer = reader.readLine();
                    if (inputFromPlayer.equals(Cipher.cipher_INQUEUE)){
                        inQueue = true;
                        System.out.println(user.getUsername() + " queueing...");
                    }

                }
                else {
                    if(!inBattle){
                        for(int i = 0 ; i < server.players.size(); ++i){
                            if(server.players.get(i).inQueue && !server.players.get(i).inBattle && !server.players.get(i).user.getUsername().equals( user.getUsername())){
                                writer.write(Cipher.cipher_ALLOW_TO_BATTLE);
                                writer.newLine();
                                writer.flush();

                                inBattle = true;
                                System.out.println(user.getUsername() + " coop successful");
                                writer.write(server.players.get(i).user.getUsername());
                                writer.newLine();
                                writer.flush();
                            }
                        }

                    }
                    else{// IN BATTLE
                        String getAtkPermission = reader.readLine();
                        if(getAtkPermission.equals(Cipher.cipher_ATTACK_PERMISSIONS)){
                            attacking = true;
                            for(int i = 0 ; i < server.players.size(); ++i){

                                if(!server.players.get(i).user.getUsername().equals( this.user.getUsername())){
                                    if(!server.players.get(i).attacking) {
                                        server.players.get(i).writer.write(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_ATTACK_FAIL);
                                        server.players.get(i).writer.newLine();
                                        server.players.get(i).writer.flush();

                                        writer.write(Cipher.cipher_SELF_ACTION + Cipher.cipher_ATTACK_FAIL);
                                        writer.newLine();
                                        writer.flush();

                                    }
                                    else {
                                        server.players.get(i).writer.write(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_ATTACK_SUCCESS);
                                        server.players.get(i).writer.newLine();
                                        server.players.get(i).writer.flush();

                                        writer.write(Cipher.cipher_SELF_ACTION + Cipher.cipher_ATTACK_SUCCESS);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                            }
                        }
                        else if(getAtkPermission.equals(Cipher.cipher_NORMAL)) {
                            for (int i = 0; i < server.players.size(); ++i) {
                                if(!server.players.get(i).user.getUsername().equals( this.user.getUsername())){
                                    attacking = false;
                                    server.players.get(i).writer.write(Cipher.cipher_ENEMY_ACTION + Cipher.cipher_NORMAL);
                                    server.players.get(i).writer.newLine();
                                    server.players.get(i).writer.flush();
                                }
                            }
                        }
                        else if(getAtkPermission.equals(Cipher.cipher_ENDGAME)){
                            attacking = false;
                            inQueue = false;
                            inBattle = false;
                            System.out.println(user.getUsername() + " out game");
                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void handleInformationFromClients(String inf) {
        int from = hashInf(inf);
        StringBuilder unSB = new StringBuilder();
        for(int i = 1; i < from; ++i)
            unSB.append(inf.charAt(i));
        StringBuilder pwSB = new StringBuilder();
        for(int i = from + Cipher.cipherKey.length(); i < inf.length(); ++i){
            pwSB.append(inf.charAt(i));
        }
        System.out.println(unSB + "\n" + pwSB);
        user.setUsername(unSB.toString());
        user.setPassword(pwSB.toString());
    }

    private int hashInf(String inf) {
        String cipher = Cipher.cipherKey;
        int size = 0, from = 0;
        for(int i = 1; i < inf.length(); i++) {
            if(inf.charAt(i) == cipher.charAt(size)){
                from = i-size;
                ++size;
                if(size == cipher.length()){
                    return from;
                }
            }
            else{
                size = 0;
            }
        }
        return from;
    }
}
