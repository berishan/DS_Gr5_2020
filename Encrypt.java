import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;




public class Encrypt {

    public static void main (String[] args) throws Exception{
        String name=base64Encode(args[0]);
        encodeBytes();
        encryptDES();
        System.out.println(name);


    }


    private static String base64Encode(String value) {
        try {
            return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8.toString()));
        } catch(UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void encodeBytes( ){
        byte[] b = new byte[8];
        new Random().nextBytes(b);
        byte[] encoded = Base64.getEncoder().encode(b);
        System.out.println(new String(encoded));

    }



 public static void encryptDES() throws Exception {
     Cipher ecipher;

     Cipher dcipher;
     //byte[] key = new byte[8];
     //new Random().nextBytes(key);
     SecretKey key = KeyGenerator.getInstance("DES").generateKey();
     byte[] data = key.getEncoded();
     System.out.println(data.length);
    // SecretKey key2 = new SecretKeySpec(data, 0, data.length, "DES");

         ecipher = Cipher.getInstance("DES");
         dcipher = Cipher.getInstance("DES");
         ecipher.init(Cipher.ENCRYPT_MODE, key);
         dcipher.init(Cipher.DECRYPT_MODE, key);


         // Encode the string into bytes using utf-8
        // byte[] utf8 = str.getBytes("UTF8");

         // Encrypt
         //byte[] enc = ecipher.doFinal(utf8);
     }
 }

        // Encode byt


//byte[] decoded = Base64.getDecoder().decode(encoded);
//println(new String(decoded))