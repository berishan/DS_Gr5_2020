import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.security.*;
import javax.crypto.*;
public class CreateUser {
    public static void main(String[] args) throws Exception{


    }


    public static void GenerateKeys(String name) throws Exception {

        Base64.Encoder encoder = Base64.getEncoder();
        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(2048);

        KeyPair keyPair = keys.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        String privateKeyString = encoder.encodeToString(privateKey.getEncoded());
        String publicKeyString = encoder.encodeToString(publicKey.getEncoded());

        saveUser(name, privateKeyString, publicKeyString);


    }
    public static void saveUser(String name, String privateKey, String publicKey) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";

        FileWriter writeFile = new FileWriter(publicfileName);
        writeFile.write("-----BEGIN RSA PUBLIC KEY-----\n");
        writeFile.write(publicKey);
        writeFile.write("\n-----END RSA PUBLIC KEY-----\n");
        writeFile.close();

        FileWriter writeAnotherFile = new FileWriter(privatefileName);
        writeAnotherFile.write("-----BEGIN RSA PRIVATE KEY-----\n");
        writeAnotherFile.write(privateKey);
        writeAnotherFile.write("\n-----END RSA PRIVATE KEY-----\n");
        writeAnotherFile.close();

    }


}