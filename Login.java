import java.io.Console;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class Login {

    public static void dbConnect(String name, String password) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Sqlite\\db\\projekti_siguri.db");
            Statement statement = con.createStatement();
            getUsername(statement, name, password);
        } catch (SQLException e) {
            System.out.println("Ndodhi nje gabim");
            System.exit(-1);
        }
    }

    public static void getUsername(Statement statement, String name, String password) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
            String dbSalt = resultSet.getString("salt");
            String dbPassword = resultSet.getString("hashed_password");
            checkPassword(dbSalt, dbPassword, password);
        } catch (SQLException e) {
            System.out.println("Ndodhi nje gabim");
        }

    }
    public static void checkPassword(String salt, String dbPassword, String password){
        byte[] byteSalt = Base64.getDecoder().decode(salt);
        String hashedPassdword = CreateUser.hashPassword(byteSalt, password);
        if(hashedPassdword.equals(dbPassword)){
            generateToken();
        }
        else {
            System.out.println("Gabim:Shfrytezuesi ose fjalekalimi i gabuar.");
        }
    }
    public static void generateToken(){
        System.out.println("token");
    }
}

