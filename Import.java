import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.Base64;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

public class Import {
    public static String message = "Fajlli nuk eshte RSA celes ne formatin PEM.";
        public static void main(String[] args) throws Exception {
            checkUser(args[0], args[1]);
        }

    public static void importKey(String name, String path) throws Exception {

        try {
            URL urlObj = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            Integer responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader inputReader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer response = new StringBuffer();
                String firstLine = inputReader.readLine();
                String secondLine = inputReader.readLine();
                String thirdLine = inputReader.readLine();
                inputReader.close();

                if (firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {

                    String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";
                    FileWriter writeFile = new FileWriter(privatefileName);
                    writeFile.write(firstLine + "\n");
                    writeFile.write(secondLine + "\n");
                    writeFile.write(thirdLine);
                    writeFile.close();
                    message = "Celesi privat u ruajt ne fajllin  '" + privatefileName + "'";
                    generatePublicKey(secondLine, name);
                } else if (firstLine.equals("-----BEGIN RSA PUBLIC KEY-----")) {

                    String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";
                    message = "Celesi publk u ruajt ne fajllin  '" + publicfileName + "'";
                    FileWriter writeFile = new FileWriter(publicfileName);
                    writeFile.write(firstLine + "\n");
                    writeFile.write(secondLine + "\n");
                    writeFile.write(thirdLine);
                    writeFile.close();
                }

                System.out.println(message);


            }
        } catch (Exception ex) {

            try {
                BufferedReader Buff = new BufferedReader(new FileReader(path));
                String firstLine = Buff.readLine();
                String secondLine = Buff.readLine();
                Buff.close();
                File importedFile = new File(path);
                if (firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {
                    String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";


                    if (importedFile.renameTo(new File(privatefileName))) {
                        importedFile.delete();
                        System.out.println("Celesi privat u ruajt ne fajllin  '" + privatefileName + "'");
                        generatePublicKey(secondLine, name);
                    } else {
                        System.out.println("Gabim: Celesi '" + name + "' ekziston paraprakisht.");
                    }
                } else if (firstLine.equals("-----BEGIN RSA PUBLIC KEY-----")) {
                    String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";

                    if (importedFile.renameTo(new File(publicfileName))) {
                        importedFile.delete();
                        System.out.println("Celesi publik u ruajt ne fajllin '" + publicfileName + "'");

                    } else {
                        System.out.println("Gabim: Celesi '" + name + "' ekziston paraprakisht.");
                    }
                }
                else{
                    System.out.println("Fajlli nuk eshte celes RSA ne formatin PEM.");
                }

            } catch (FileNotFoundException e) {
                System.out.println("Ju lutem shkruani nje path valid.");
            }
        }
    }

    public static void checkUser(String name, String path) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";

        if (new File(publicfileName).exists()) {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
        } else {
            importKey(name, path);
        }


    }
    static String getBase64 ( byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void generatePublicKey(String privateKeyS, String name) throws Exception{
        byte[] decoded = Base64.getDecoder().decode(privateKeyS);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);

        RSAPrivateCrtKey privk = (RSAPrivateCrtKey) privateKey;

        RSAPublicKeySpec publicKeySpec = new java.security.spec.RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey myPublicKey = keyFactory.generatePublic(publicKeySpec);
        String publickKeyS    = getBase64(myPublicKey.getEncoded());
        savePublicKey(name, publickKeyS);
    }
    public static void savePublicKey(String name, String publicKey) throws Exception {
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";
        boolean exists = publicUserExists(publicfileName);
        if (!exists) {
            FileWriter writeFile = new FileWriter(publicfileName);
            writeFile.write("-----BEGIN RSA PUBLIC KEY-----\n");
            writeFile.write(publicKey);
            writeFile.write("\n-----END RSA PUBLIC KEY-----\n");
            writeFile.close();
            System.out.println("Celesi publik u ruajt ne fajllin '" + publicfileName+"'.");
        } else {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
        }
    }

    public static boolean publicUserExists(String publicFileName){
        if (new File(publicFileName).exists()) {
            return true;
        } else {
            return false;
        }

    }  public static void savePublicKey(String name, String publicKey) throws Exception {
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";
        boolean exists = publicUserExists(publicfileName);
        if (!exists) {
            FileWriter writeFile = new FileWriter(publicfileName);
            writeFile.write("-----BEGIN RSA PUBLIC KEY-----\n");
            writeFile.write(publicKey);
            writeFile.write("\n-----END RSA PUBLIC KEY-----\n");
            writeFile.close();
            System.out.println("Celesi publik u ruajt ne fajllin '" + publicfileName+"'.");
        } else {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
        }
    }
    public static boolean publicUserexists(String publicFileName){
        if (new File(publicfileName).exists()) {
            return true;
        }
        else {
            return false;
        }
    }


}