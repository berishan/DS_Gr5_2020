import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Encrypt {


    private static PrivateKey PrivateKey;
    private static String signature = "";
    private static String encSender = "";

    private static String encodeBytes() {
        byte[] b = new byte[8];
        new Random().nextBytes(b);
        return Base64.getEncoder().encodeToString(b);
    }


    public static String base64Encode(String value) {
        try {
            return Base64.getEncoder().encodeToString(value.getBytes(UTF_8.toString()));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String DES(String message, String name, String sender, String token) throws Exception {
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        byte[] keyBytes = key.getEncoded();

        String encryptedRSA = RSA(keyBytes, name);

        Cipher ecipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] utf8 = message.getBytes("UTF8");
        byte[] enc = ecipher.doFinal(utf8);
        String encryptedDES = Base64.getEncoder().encodeToString(enc);
        if(!sender.equals("nosender")) {
            if (tokenIsValid(sender, token)) {
                encSender = base64Encode(sender);
                PrivateKey = (PrivateKey) Login.readPrivateKey("keys/" + sender + ".key");
                Signature sig = Signature.getInstance("SHA1WithRSA");
                sig.initSign(PrivateKey);
                sig.update(enc);
                byte[] signatureBytes = sig.sign();
                signature = Base64.getEncoder().encodeToString(signatureBytes);
                System.out.println("bra");
            }
        }
        else {
            System.exit(-1);
        }

        return encryptedRSA + "." + encryptedDES;
       
    }
    private static String RSA(byte[] keyBytes, String name) throws Exception {
        String publicK = readPublicKey(name);
        if(publicK.equals("celesi nuk ekziston")){
            System.exit(-1);
        }
        byte[] publicBytes = Base64.getDecoder().decode(publicK);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

         Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherText = cipher.doFinal(keyBytes);
        String cipherTextString = Base64.getEncoder().encodeToString(cipherText);
        return cipherTextString;
    }



    public static String readPublicKey(String name) throws Exception {
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";
        try {
            BufferedReader Buff = new BufferedReader(new FileReader(publicfileName));
            String firstLine = Buff.readLine();
            if (firstLine.equals("-----BEGIN RSA PUBLIC KEY-----")) {
                String secondLine = Buff.readLine();
                Buff.close();
                return secondLine;

            } else {
                System.out.println("File nuk permban celes publik RSA.");
                Buff.close();
                return "gabim";
            }}
        catch (FileNotFoundException e) {
            System.out.println("Celesi publik '" + name + "' nuk ekziston.");
            return "celesi nuk ekziston";
        }

    }

    public static void saveOrShow(String name, String message, String path,String sender, String token) throws Exception {
        String name64 = base64Encode(name);
        String iv = encodeBytes();
        String des = DES(message, name, sender, token);
        if (path.equals("no path")) {
            System.out.println(name64 + "." + iv + "." + des);
        } else {
            try {
                File file = new File(path);
                FileWriter writeFile = new FileWriter(file);
                writeFile.write(name64 + "." + iv + "." + des);
                writeFile.close();
            } catch (Exception e) {
                System.out.println("Ju lutem shkruani nje path valid.");
            }
        }

    }

    public static boolean tokenIsValid(String sender,String token) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        String path = Status.dbConnect(sender);
        RSAPublicKey publicKey = Status.readPublicKey(path);
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("nora")
                .build();
        try {
        DecodedJWT jwt = verifier.verify(token);
        return true;
    } catch (JWTVerificationException exception) {
            System.out.println("Tokeni nuk eshte valid.");
            return false;
        }
    }

}