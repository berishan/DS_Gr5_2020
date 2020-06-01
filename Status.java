import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.Base64;

public class Status {
    private static RSAPublicKey RSAPublicKey;

    public static RSAPublicKey readPublicKey(String path) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            BufferedReader Buff = new BufferedReader(new FileReader(path));
            String firstLine = Buff.readLine();
            if (firstLine.equals("-----BEGIN RSA PUBLIC KEY-----")) {
                String secondLine = Buff.readLine();
                Buff.close();
                byte[] decodedKey = Base64.getDecoder().decode(secondLine);
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(keySpec);
                return (RSAPublicKey) publicKey;
            } else {

                return null;
            }
        } catch (FileNotFoundException e) {
            return null;
        }

    }

    public static void token(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println("User: " + jwt.getSubject());
            String path = dbConnect(jwt.getSubject());
            RSAPublicKey = readPublicKey(path);
            if(RSAPublicKey.equals(null)){
                System.out.println("Valid: jo");
            }
            else {
                Algorithm algorithm = Algorithm.RSA256(RSAPublicKey, null);
                JWTVerifier verifier = JWT.require(algorithm)
                        .withIssuer("nora")
                        .build();
                DecodedJWT jwts = verifier.verify(token);
                System.out.println("Valid: po");
                System.out.println("Skadimi: " + jwt.getExpiresAt());
            }

        } catch (JWTDecodeException e) {
            System.out.println("Tokeni ka pesuar ndryshime.");
        } catch (JWTVerificationException exception) {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println("Valid: jo");
            System.out.println("Skadimi: " + jwt.getExpiresAt());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Ndodhi nje gabim.");
        } catch (IllegalArgumentException e) {
            DecodedJWT jwt = JWT.decode(token);
            System.out.println("Valid: jo");
            System.out.println("Skadimi: " + jwt.getExpiresAt());
        } catch (IOException e) {
            System.out.println("gabim");
        } catch (InvalidKeySpecException e) {
            System.out.println("gabim");
        }
    }

    public static String dbConnect(String name) {
        try {
            Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Sqlite\\db\\projekti_siguri.db");
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
            String path = resultSet.getString("pub_key_path");
            return path;
        } catch (
                SQLException e) {
            return "no_user";
        }

    }
}

