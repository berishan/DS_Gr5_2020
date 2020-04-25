import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Export {
    static public String messageForError = "";
    static public String fileName = "";
    static public String messageWhenSaved = "";
    static String data = "";


    public static void exportKey(String name, String type, String path) throws Exception {

        boolean hasPath;
        if (path.equals("no path")) {
            hasPath = false;
        } else {
            hasPath = true;
        }
            if (type.equals("private")) {
                fileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
                messageForError = ("Gabim: Celesi privat '" + fileName + "' nuk ekziston.");
                messageWhenSaved = "Celesi privat u ruajt ne fajllin '" + path + "'.";
            } else if (type.equals("public")) {
                fileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";
                messageForError = ("Gabim: Celesi publik '" + fileName + "' nuk ekziston.");
                messageWhenSaved = "Celesi publik u ruajt ne fajllin '" + path + "'.";
            }

            try {
                File myFile = new File(fileName);
                Scanner myReader = new Scanner(myFile).useDelimiter("(\\b|\\B)");
                while (myReader.hasNextLine()) {
                    data = data + myReader.next();
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println(messageForError);
            }

            if (hasPath) {
                path = "keys/" + path;
                FileWriter writeFile = new FileWriter(path);
                writeFile.write(data);
                writeFile.close();
                System.out.println(messageWhenSaved);
            } else if (!hasPath) {
                System.out.println(data);

            }


        }
    }


