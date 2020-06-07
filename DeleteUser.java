import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteUser {
    public static void deleteUser(String name) {
        String privateFileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";
        String publicFileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";

        File publicFile = new File(publicFileName);
        File privateFile = new File(privateFileName);
        if (publicFile.exists() && privateFile.exists()) {

            privateFile.delete();
            System.out.println("Eshte larguar celesi privat:    '" + privateFileName + "'.");
            publicFile.delete();
            System.out.println("Eshte larguar celesi publik:    '" + publicFileName + "'.");
            deleteUserFromDB(name);
        } else if (publicFile.exists() && !(privateFile.exists())) {

            publicFile.delete();
            System.out.println("Eshte larguar celesi publik:    '" + publicFileName + "'.");
            deleteUserFromDB(name);
        } else if (!(publicFile.exists()) && privateFile.exists()) {
            privateFile.delete();
            System.out.println("Eshte larguar celesi privat:    '" + privateFileName + "'.");
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
            String query = "DELETE FROM users WHERE name = '%s'";
            query = String.format(query, name);
            statement.execute(query);
            statement.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Nuk mund te lidhemi me databaze.");
            System.exit(-1);

        }
    }
}