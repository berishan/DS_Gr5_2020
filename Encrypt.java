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
    private static String RSA(byte[] keyBytes, String name) throws Exception {
        String publicK = readPublicKey(name);
        if(publicK.equals("p")){
            System.exit(-1);
        }
        byte[] publicBytes = Base64.getDecoder().decode(publicK);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        final Cipher cipher = Cipher.getInstance("RSA");
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
            return "p";
        }

    }
}