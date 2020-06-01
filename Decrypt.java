import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Decrypt {
    private static PublicKey PublicKey;

    public static void readMessage(String message) throws Exception {
        String[] messageArray = message.split("\\.");
            String name = readKeyName(messageArray[0]);
            System.out.println("Marresi: " + name);
            readDESKey(name, messageArray[2], messageArray[3]);
            if(messageArray.length == 6){
                readSender(messageArray[4], messageArray[5], messageArray[3]);
            }

    }

    public static void readFile(String path) throws Exception {
        try {
            StringBuffer response = new StringBuffer();
            String inputLine;
            BufferedReader Buff = new BufferedReader(new FileReader(path));
            while ((inputLine = Buff.readLine()) != null) {
                response.append(inputLine);
            }
            String message = response.toString();
            String[] messageArray = message.split("\\.");
            String name = readKeyName(messageArray[0]);
            System.out.println("Marresi: " + name);
            readDESKey(name, messageArray[2], messageArray[3]);
            if(messageArray.length == 6){
                readSender(messageArray[4], messageArray[5], messageArray[3]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ju lutem shkruani nje path valid.");
        }
    }
    public static String readKeyName(String encryptedName) throws UnsupportedEncodingException {
        byte[] bytes = Base64.getDecoder().decode(encryptedName);
        return new String(bytes, "UTF-8");
    }

    public static void readDESKey(String name, String message, String desMessage) throws Exception  {
        String privateKeyString = readPrivateKey(name);
        if(privateKeyString.equals("nuk ekziston celesi")){
            System.exit(-1);
        }
        byte[] decoded = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        Cipher dcipher = Cipher.getInstance("RSA");
        dcipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] dec = dcipher.doFinal(Base64.getDecoder().decode(message));
        SecretKey DESKey = new SecretKeySpec(dec,0, dec.length, "DES");
        String decryptedMessage = decryptMessage(DESKey, desMessage);

    }

    public static String readPrivateKey(String name) throws IOException {
        String privateFileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";

        BufferedReader Buff = new BufferedReader(new FileReader(privateFileName));
        String firstLine = Buff.readLine();
        if(firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {
            String secondLine = Buff.readLine();
            Buff.close();
            return secondLine;
        }
        else {
            System.out.println("Celesi privat '" + name + "' nuk eksizton.");
            Buff.close();
            return "nuk ekziston celesi";
        }

    }
    public static String decryptMessage(SecretKey key, String message) throws Exception{
        Cipher dcipher = Cipher.getInstance("DES");
        dcipher.init(Cipher.DECRYPT_MODE, key);
        byte[] dec = Base64.getDecoder().decode(message);
        byte[] utf8 = dcipher.doFinal(dec);
        System.out.println("Mesazhi: " + new String(utf8, "UTF8"));
        return  new String(utf8, "UTF8");
    }
    public static void readSender(String encSender, String encSignature, String message) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        byte[] bytes = Base64.getDecoder().decode(encSender);
        String sender = new String(bytes, "UTF-8");
        System.out.println("Derguesi: " + sender);
        validateSignature( sender, encSignature, message);
    }
    public static void validateSignature(String name,String encSignature, String messsage) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA1WithRSA");
            try {
                PublicKey = (PublicKey) Status.readPublicKey("keys/" + name + ".pub.key");
            } catch (IOException e) {
                System.out.println("Nenshkrimi: Mungon celesi publik" + name);
                System.exit(-1);
            } catch (InvalidKeySpecException e) {
                System.out.println("Nenshkrimi: Mungon celesi publik" + name);
                System.exit(-1);
            }

        sig.initVerify(PublicKey);
            byte[] messageBytes = Base64.getDecoder().decode(messsage);
            sig.update(messageBytes);
            byte[] signatureBytes = Base64.getDecoder().decode(encSignature);
            if(sig.verify(signatureBytes)){
                System.out.println("Nenshkrimi: valid");
            }
            else {
                System.out.println("Nenshkrimi: jovalid");
            }
    }

}
