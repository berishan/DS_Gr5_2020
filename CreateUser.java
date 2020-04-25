

import java.io.File;
import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;


public class CreateUser {


    public static void checkUser(String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";

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

        String privateFileContent = FormatXML.getPrivateKeyAsXml(privateKey);
        String publicFileContent = FormatXML.getPublicKeyAsXml(publicKey);

        saveUser(privateFileContent, publicFileContent, name);
    }

    public static void saveUser(String privateFileContent, String publicFileContent, String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";

        FileWriter writeFile = new FileWriter(publicfileName);

        writeFile.write(privateFileContent);
        writeFile.close();
        System.out.println("Eshte krijuar celesi privat:     '" + privatefileName + "'.");

        FileWriter writeAnotherFile = new FileWriter(privatefileName);
        writeAnotherFile.write(publicFileContent);
        writeAnotherFile.close();
        System.out.println("Eshte krijuar celesi publik:     '" + publicfileName + "'.");
    }


}



