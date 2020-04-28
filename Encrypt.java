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

        String encryptedRSA = RSA(keyBytes, name);

        Cipher ecipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] utf8 = message.getBytes("UTF8");
        byte[] enc = ecipher.doFinal(utf8);
        String encryptedDES = Base64.getEncoder().encodeToString(enc);
        return encryptedRSA + "." + encryptedDES;
    }
    private static String RSA(byte[] keyBytes, String name) throws Exception {
        String publicK = readPublicKey(name);
        if(publicK.equals("celesi nuk ekziston")){
            System.exit(-1);
        }
        byte[] publicBytes = Base64.getDecoder().decode(publicK);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

         Cipher cipher = Cipher.getInstance("RSA");
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
            return "celesi nuk ekziston";
        }

    }

    public static void saveOrShow(String name, String message, String path) throws Exception {
        String name64 = base64Encode(name);
        String iv = encodeBytes();
        String des = DES(message, name);
        if (path.equals("no path")) {
            System.out.println(name64 + "." + iv + "." + des);
        } else {
            try {
                File file = new File(path);
                FileWriter writeFile = new FileWriter(file);
                writeFile.write(name64 + "." + iv + "." + des);
                writeFile.close();
            } catch (Exception e) {
                System.out.println("Ju lutem shkruani nje path valid.");
            }
        }

    }

}