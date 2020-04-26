
import java.net.URL;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


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
                Buff.close();
                File importedFile = new File(path);
                if (firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {
                    String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";

                    if (importedFile.renameTo(new File(privatefileName))) {
                        importedFile.delete();
                        System.out.println("Celesi privat u ruajt ne fajllin  '" + privatefileName + "'");
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


}