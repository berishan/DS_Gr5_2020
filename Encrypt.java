import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;




public class Encrypt {

    public static void main (String[] args) throws Exception{
        String name=base64Encode(args[0]);
        encodeBytes();
        encryptDES(args[0]);
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



    public static void encryptDES(String name) throws Exception {
        Cipher ecipher;

        Cipher dcipher;
        //byte[] key = new byte[8];
        //new Random().nextBytes(key);
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        byte[] data = key.getEncoded();
        encryptKey(name, data);
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
    public static void encryptKey(String name, byte[] key) throws Exception {
        String RSAkeyString = readPublicKey(name);
        byte[] decoded = Base64.getDecoder().decode(RSAkeyString);
        if(key.equals("gabim")){

        }
        else{

        }

    }
    public static String readPublicKey (String name) throws Exception {
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";
        BufferedReader Buff = new BufferedReader(new FileReader(publicfileName));
        String firstLine = Buff.readLine();
        if (firstLine.equals("-----BEGIN RSA PUBLIC KEY-----")){
            String secondLine = Buff.readLine();
            Buff.close();
            return secondLine;

        }
        else{
            System.out.println("File nuk permban celes publik RSA.");
            Buff.close();
            return "gabim";
        }

    }
}

// Encode byt


//byte[] decoded = Base64.getDecoder().decode(encoded);
//println(new String(decoded))