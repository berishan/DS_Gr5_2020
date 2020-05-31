import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Date;


public class Login {
    private static final long TOKEN_EXPIRE = System.currentTimeMillis() + 20 * 60 * 1000;

    public static void dbConnect(String name, String password) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Sqlite\\db\\projekti_siguri.db");
            Statement statement = con.createStatement();
            getData(statement, name, password);
        } catch (SQLException e) {
            System.out.println("Ndodhi nje gabim1");
            System.exit(-1);
        }
    }

    public static void getData(Statement statement, String name, String password) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
            String dbSalt = resultSet.getString("salt");
            String dbPassword = resultSet.getString("hashed_password");

            if(correctPassword(dbSalt, dbPassword, password)){
                generateToken(name,resultSet.getString("priv_key_path"));
            } else{
                System.out.println("Gabim:Shfrytezuesi ose fjalekalimi i gabuar.");
            }
        } catch (SQLException | InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            System.out.println("Ndodhi nje gabim2");
        }

    }
    public static boolean correctPassword(String salt, String dbPassword, String password) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        byte[] byteSalt = Base64.getDecoder().decode(salt);
        String hashedPassword = CreateUser.hashPassword(byteSalt, password);
        if(hashedPassword.equals(dbPassword)) {
            return true;
        }
        else {
            return false;
        }
    }
    public static void generateToken(String name,String privateKeyPath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("token");
        RSAPrivateKey privateKey = readPrivateKey(privateKeyPath);
        Algorithm algorithmRS = Algorithm.RSA256(null, privateKey);
        System.out.println(algorithmRS);
        try {
            String token = JWT.create()
                    .withIssuer("nora")
                    .withExpiresAt(new Date(TOKEN_EXPIRE))
                    .withSubject(name)
                    .sign(algorithmRS);
            System.out.println(token);
    } catch (
    JWTCreationException exception){
            System.out.println("gabim72");

    }


    }

    public static RSAPrivateKey readPrivateKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        BufferedReader Buff = new BufferedReader(new FileReader(path));
        String firstLine = Buff.readLine();
        if(firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {
            String secondLine = Buff.readLine();
            Buff.close();
            byte[] decoded = Base64.getDecoder().decode(secondLine);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf.generatePrivate(keySpec);
            System.out.println((RSAPrivateKey)privateKey);
            return (RSAPrivateKey)privateKey;
        }
        else {
            System.out.println("Celes gabim");
            return null;
        }
    }
}

