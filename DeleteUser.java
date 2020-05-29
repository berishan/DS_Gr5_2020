import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUser {
    public static void deleteUser(String name) throws Exception {

        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";

        File publicFile = new File(publicfileName);
        File privateFile = new File(privatefileName);
        if (publicFile.exists() && privateFile.exists()) {

            privateFile.delete();
            System.out.println("Eshte larguar celesi privat:    '" + privatefileName + "'.");
            publicFile.delete();
            System.out.println("Eshte larguar celesi publik:    '" + publicfileName + "'.");
            deleteUserFromDB(name);
        }
        else if (publicFile.exists() && !(privateFile.exists())) {

            publicFile.delete();
            System.out.println("Eshte larguar celesi publik:    '" + publicfileName + "'.");
            deleteUserFromDB(name);
        }
        else if (!(publicFile.exists()) && privateFile.exists()) {
            privateFile.delete();
            System.out.println("Eshte larguar celesi privat:    '" + privatefileName + "'.");
            deleteUserFromDB(name);
        }
        else {

            System.out.println("Gabim: Celesi '" + name + "' nuk ekziston.");
        }


    }
    public static void deleteUserFromDB(String name) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Sqlite\\db\\projekti_siguri.db");
            Statement statement = con.createStatement();
            String query = "DELETE FROM users WHERE name = '" + name+"'";
            statement.execute(query);
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Ndodhi nje gabim");
            System.exit(-1);

        }
    }
}