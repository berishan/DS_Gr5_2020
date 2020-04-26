import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import static java.nio.file.StandardCopyOption.*;


public class Import {
    public static void main(String[] args) throws Exception{
        importKey(args[0], args[1]);

    }
    public static void importKey(String name, String path) throws Exception{

        try{
        BufferedReader Buff = new BufferedReader(new FileReader(path));
        String firstLine = Buff.readLine();
        Buff.close();
            File importedFile = new File(path);
            System.out.println(firstLine);
        if(firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {

            String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";


            if (importedFile.renameTo(new File(privatefileName))) {
                importedFile.delete();
                System.out.println("Celesi privat u ruaj ne fajllin  '" + privatefileName + "'");
            } else {
                System.out.println("Gabim: Celesi '" + name + "' ekziston paraprakisht.");
            }
        }
        else if (firstLine.equals("-----BEGIN RSA PUBLIC KEY-----")){
            String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.key";

            if (importedFile.renameTo(new File(publicfileName))) {
                importedFile.delete();
                System.out.println("Celesi publik u ruaj ne fajllin '" + publicfileName + "'");

            } else {
                System.out.println("Gabim: Celesi '" + name + "' ekziston paraprakisht.");
            }
        }

        }
        catch (FileNotFoundException e){
                System.out.println("Ju luttem shkruani nje path valid");
            }









    }


}