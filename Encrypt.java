import java.io.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


import static java.nio.charset.StandardCharsets.UTF_8;


public class Encrypt {

    public static void main(String[] args) throws Exception {
        try {
            saveOrShow(args[0], args[1], args[2]);

        } catch (IndexOutOfBoundsException e) {
            saveOrShow(args[0], args[1], "no path");
        }
    }

    private static String encodeBytes() {
        byte[] b = new byte[8];
        new Random().nextBytes(b);
        return Base64.getEncoder().encodeToString(b);
    }


    private static String base64Encode(String value) {
        try {
            return Base64.getEncoder().encodeToString(value.getBytes(UTF_8.toString()));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String DES(String message, String name) throws Exception {
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        byte[] keyBytes = key.getEncoded();

        String test = RSA(keyBytes, name);

        Cipher ecipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] utf8 = message.getBytes("UTF8");
        byte[] enc = ecipher.doFinal(utf8);
        String nora = Base64.getEncoder().encodeToString(enc);
        return test + "." + nora;
    }
}