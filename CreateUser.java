import java.util.Base64;

public class CreateUser {


    public static void CreateUser() throws Exception {
        Base64.Encoder encoder = Base64.getEncoder();
        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(2048);

        KeyPair keyPair = keys.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();


        System.out.println("-----BEGIN RSA PRIVATE KEY-----\n");
        System.out.println(encoder.encodeToString(privateKey.getEncoded()));
        System.out.println("\n-----END RSA PRIVATE KEY-----\n");
        System.out.println();
        System.out.println("-----BEGIN RSA PUBLIC KEY-----\n");
        System.out.println(encoder.encodeToString(publicKey.getEncoded()));
        System.out.println("\n-----END RSA PUBLIC KEY-----\n");


    }
}

}