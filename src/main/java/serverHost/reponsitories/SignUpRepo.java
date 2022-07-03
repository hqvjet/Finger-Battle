package serverHost.reponsitories;

import model.User;
import security.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static serverHost.reponsitories.DBConnection.getConnection;

public class SignUpRepo {
    static Connection cnt = null;
    ResultSet rs;
    static PreparedStatement preparedstatement;
    static Encryption encryption;
    public SignUpRepo(){
        try{
            cnt = getConnection();
        }
        catch(Exception e){

        }
        encryption = new Encryption();
    }

    public SignUpRepo(User ADMIN) throws ClassNotFoundException, SQLException {
        cnt = getConnection();
        encryption = new Encryption();
        pushDataInToDatabase(ADMIN);
    }

    public boolean isCorrect(User user) throws SQLException {
        if(user.getUsername().equals("") || user.getPassword().equals("")){
            System.out.println("1");
            return false;
        }

        else if(isExist(user.getUsername())){
            System.out.println("2");
            return false;
        }
        return true;
    }

    private boolean isExist(String un) throws SQLException {
        String cmd = "select username from user where username = ?";
        preparedstatement = cnt.prepareStatement(cmd);
        preparedstatement.setString(1, un);
        rs = preparedstatement.executeQuery();
        return rs.next();
    }

    public void pushDataInToDatabase(User user) throws SQLException {
        String cmd = "insert into user (username, password) values (?, ?)";
        preparedstatement =cnt.prepareStatement(cmd);
        preparedstatement.setString(1, user.getUsername());
        preparedstatement.setString(2, encryption.encrypt(user.getPassword()));
        preparedstatement.executeUpdate();
    }
}
