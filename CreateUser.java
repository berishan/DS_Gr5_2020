import java.util.Base64;
import javax.crypto.*;
import java.security.*;
public class CreateUser {
    public static void main(String[] args) throws Exception{


    }


    public static void GenerateKeys() throws Exception {

        Base64.Encoder encoder = Base64.getEncoder();
        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(2048);

        KeyPair keyPair = keys.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        String privateKeyString = encoder.encodeToString(privateKey.getEncoded());
        String publicKeyString = encoder.encodeToString(publicKey.getEncoded());








    }


}