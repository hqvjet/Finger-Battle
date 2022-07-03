package serverHost.reponsitories;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String DB_HOST = "localhost";
        int DB_PORT = 3306;
        String DB_NAME = "FF";
        String uri = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        Connection conn = null;

        try {
            String DB_USERNAME = "root";
            String DB_PASSWORD = "";
            conn = DriverManager.getConnection(uri, DB_USERNAME, DB_PASSWORD);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connect failed!");
        }

        return conn;
    }

}