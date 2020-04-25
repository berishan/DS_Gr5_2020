/****************************************************************************************
 *  References
 *  For Formatting XML Files
 *  Title: java-to-dotnet-signature
 *  Author: codybartfast
 *  Availability: https://github.com/codybartfast/java-to-dotnet-signature/blob/master/CreateKeysJ/src/CreateKeysJ.java
 */

import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.security.*;


public class CreateUser {


    public static void checkUser(String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";

        if (new File(publicfileName).exists()) {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
        }
        else {
            GenerateKeys(name);
        }
    }

    public static void GenerateKeys(String name) throws Exception {

        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(2048);

        KeyPair keyPair = keys.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        byte[] publicKeyEncoded = publicKey.getEncoded();
        byte[] privateKeyEncoded = privateKey.getEncoded();

        String privateFileContent =  getBase64(privateKeyEncoded);
        String publicFileContent =  getBase64(publicKeyEncoded);

        saveUser(privateFileContent, publicFileContent, name);
    }

    public static void saveUser(String privateFileContent, String publicFileContent, String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";
        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";

        FileWriter writeFile = new FileWriter(publicfileName);
        writeFile.write("-----BEGIN RSA PRIVATE KEY-----\n");
        writeFile.write(privateFileContent);
        writeFile.write("\n-----END RSA PRIVATE KEY-----\n");
        writeFile.close();
        System.out.println("Eshte krijuar celesi privat:     '" + privatefileName + "'.");

        FileWriter writeAnotherFile = new FileWriter(privatefileName);
        writeAnotherFile.write("-----BEGIN RSA PUBLIC KEY-----\n");
        writeAnotherFile.write(publicFileContent);
        writeAnotherFile.write("-----END RSA PUBLIC KEY-----\n");

        writeAnotherFile.close();
        System.out.println("Eshte krijuar celesi publik:     '" + publicfileName + "'.");
    }
    static String getBase64 ( byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }


}



