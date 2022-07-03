package serverHost.reponsitories;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static serverHost.reponsitories.DBConnection.getConnection;

public class SignInRepo {
    Connection cnt = null;
    ResultSet rs;
    PreparedStatement preparedstatement;
    User user;
    public SignInRepo(){
        user = new User();
        try{
            cnt = getConnection();
        }
        catch(Exception ignored){

        }
    }
    public boolean isCorrect(User user) throws SQLException {
        if(user.getUsername().equals("") || user.getPassword().equals(""))
            return false;

        else if(!isExist(user.getUsername()))
            return false;

        else if(!isMatch(user.getUsername(), user.getPassword()))
            return false;

        return true;
    }

    public boolean isExist(String un) throws SQLException {
        String cmd = "select username from user where username = ?";
        preparedstatement = cnt.prepareStatement(cmd);
        preparedstatement.setString(1, un);
        rs = preparedstatement.executeQuery();
        return rs.next();
    }

    public boolean isMatch(String un, String pw) throws SQLException {
        String cmd = "select password from user where username = ?";
        String pass = "";
        preparedstatement = cnt.prepareStatement(cmd);
        preparedstatement.setString(1, un);
        rs = preparedstatement.executeQuery();
        while(rs.next())
            pass = rs.getString("password");
        return pass.equals(pw);
    }
}
