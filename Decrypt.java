import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Decrypt {
    public static void main(String[] args) throws Exception {
        readMessage(args[0]);
    }

    public static void readMessage(String path) throws Exception {
        try {
            StringBuffer response = new StringBuffer();
            String inputLine;
            BufferedReader Buff = new BufferedReader(new FileReader(path));
            while ((inputLine = Buff.readLine()) != null) {
                response.append(inputLine);
            }
            String message = response.toString();
            String message1 = message.replace(".", "NORA");
            String[] test = message1.split("NORA");
            String name = readKeyName(test[0]);
            System.out.println("Marresi: " + name);
            readDESKey(name, test[2], test[3]);
        } catch (FileNotFoundException e) {
            System.out.println("Ju lutem shkruani nje path valid.");
        }
    }
}
