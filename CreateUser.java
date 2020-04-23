import java.io.File;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.io.Console;
import java.io.PrintWriter;
import java.util.Arrays;

public class CreateUser {



    public static void checkUser(String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";

        if (new File(publicfileName).exists()) {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
        } else {
            GenerateKeys(name);
        }

    }

    public static void GenerateKeys(String name) throws Exception {


        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(2048);

        KeyPair keyPair = keys.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();






    }

    public static void saveUser(String name, String privateKey, String publicKey) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";

        FileWriter writeFile = new FileWriter(publicfileName);

        writeFile.write(publicKey);

        writeFile.close();
        System.out.println("Eshte krijuar celesi privat:     '" + privatefileName + "'.");

        FileWriter writeAnotherFile = new FileWriter(privatefileName);

        writeAnotherFile.write(privateKey);

        writeAnotherFile.close();
        System.out.println("Eshte krijuar celesi publik:     '" + publicfileName + "'.");


    }




    static String getBase64(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }



