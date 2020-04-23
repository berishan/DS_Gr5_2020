import java.io.File;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.*;
import java.io.Console;
import java.io.PrintWriter;
import java.util.Arrays;

public class CreateUser {
    static final String NL = System.getProperty("line.separator");

    public static void checkUser(String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";

        if (new File(publicfileName).exists()) {
            System.out.println("Celesi '" + name + "' ekziston paraprakisht.");
        }
        else {
            GenerateKeys(name);
        }
    }

    public static void GenerateKeys(String name) throws Exception {

        KeyPairGenerator keys = KeyPairGenerator.getInstance("RSA");
        keys.initialize(2048);

        KeyPair keyPair = keys.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        String privateFileContent = getPrivateKeyAsXml(privateKey);
        String publicFileContent = getPublicKeyAsXml(publicKey);

        saveUser(privateFileContent, publicFileContent, name);
    }

    public static void saveUser(String privateFileContent, String publicFileContent, String name) throws Exception {

        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";

        FileWriter writeFile = new FileWriter(publicfileName);

        writeFile.write(privateFileContent);
        writeFile.close();
        System.out.println("Eshte krijuar celesi privat:     '" + privatefileName + "'.");

        FileWriter writeAnotherFile = new FileWriter(privatefileName);
        writeAnotherFile.write(publicFileContent);
        writeAnotherFile.close();
        System.out.println("Eshte krijuar celesi publik:     '" + publicfileName + "'.");
    }

    static String getPrivateKeyAsXml(PrivateKey privateKey) throws Exception{

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateCrtKeySpec spec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
        StringBuilder sb = new StringBuilder();

        sb.append("<RSAKeyValue>" + NL);
        sb.append(getElement("Modulus", spec.getModulus()));
        sb.append(getElement("Exponent", spec.getPublicExponent()));
        sb.append(getElement("P", spec.getPrimeP()));
        sb.append(getElement("Q", spec.getPrimeQ()));
        sb.append(getElement("DP", spec.getPrimeExponentP()));
        sb.append(getElement("DQ", spec.getPrimeExponentQ()));
        sb.append(getElement("InverseQ", spec.getCrtCoefficient()));
        sb.append(getElement("D", spec.getPrivateExponent()));
        sb.append("</RSAKeyValue>");

        return sb.toString();
    }




    static String getPublicKeyAsXml(PublicKey publicKey) throws Exception{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec spec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        StringBuilder sb = new StringBuilder();

        sb.append("<RSAKeyValue>" + NL);
        sb.append(getElement("Modulus", spec.getModulus()));
        sb.append(getElement("Exponent", spec.getPublicExponent()));
        sb.append("</RSAKeyValue>");

        return sb.toString();
    }


    static String getElement(String name, BigInteger bigInt) throws Exception {
        byte[] bytesFromBigInt = getBytesFromBigInt(bigInt);
        String elementContent = getBase64(bytesFromBigInt);
        return String.format("  <%s>%s</%s>%s", name, elementContent, name, NL);
    }


    static String getBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }


    static byte[] getBytesFromBigInt(BigInteger bigInt){

        byte[] bytes = bigInt.toByteArray();
        int length = bytes.length;
        if(length % 2 != 0 && bytes[0] == 0) {
            bytes = Arrays.copyOfRange(bytes, 1, length);
        }

        return bytes;
    }
}



