import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.regex.Pattern;


public class CreateUser {


    public static boolean checkUser(String name) {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";

        if (new File(publicfileName).exists()) {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
            return false;
        } else
            return true;
    }
//        else {
//
//            GenerateKeys(name);
//        }


    public static void GenerateKeys(String name) throws Exception {

        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(512);

        KeyPair keyPair = keys.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] publicKeyEncoded = publicKey.getEncoded();
        byte[] privateKeyEncoded = privateKey.getEncoded();

        String privateFileContent = getBase64(privateKeyEncoded);
        String publicFileContent = getBase64(publicKeyEncoded);

        saveUser(privateFileContent, publicFileContent, name);
    }

    public static void saveUser(String privateFileContent, String publicFileContent, String name) throws Exception {

        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";

        FileWriter writeFile = new FileWriter(privatefileName);
        writeFile.write("-----BEGIN RSA PRIVATE KEY-----\n");
        writeFile.write(privateFileContent);
        writeFile.write("\n-----END RSA PRIVATE KEY-----\n");
        writeFile.close();
        System.out.println("Eshte krijuar celesi privat:     '" + privatefileName + "'.");

        FileWriter writeAnotherFile = new FileWriter(publicfileName);
        writeAnotherFile.write("-----BEGIN RSA PUBLIC KEY-----\n");
        writeAnotherFile.write(publicFileContent);
        writeAnotherFile.write("\n-----END RSA PUBLIC KEY-----\n");

        writeAnotherFile.close();
        System.out.println("Eshte krijuar celesi publik:     '" + publicfileName + "'.");
    }

    static String getBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }


    public static boolean correctPassword(String password) {
        String regex = "^(?=.*[@#$%^&+=])|(?=.*[0-9])(?=\\S+$).{6,}$";
        return Pattern.matches(regex, password);
    }

    public static void updateDatabase(String name,String password) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Sqlite\\db\\projekti_siguri.db");
            Statement statement = con.createStatement();
            byte[] salt = generateSalt();
            String dbSalt = Base64.getEncoder().encodeToString(generateSalt());
            String hashedPassword = hashPassword(salt,password);
            if(!hashedPassword.equals("gabim")) {
                String query = "INSERT INTO users (name, salt, hashed_password) VALUES ('" + name + "', '" + dbSalt + "', '" + hashedPassword + "')";
                statement.execute(query);
                statement.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Ndodhi nje gabim");
            System.out.println(e);
            System.exit(-1);
        }
    }
    public static String hashPassword (byte[] salt,String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ndodhi nje gabim");
            System.exit(-1);
            return "gabim";
        }
    }
    public static byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}






