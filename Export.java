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

            if (type.equals("private")) {
                fileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
                messageForError = ("Gabim: Celesi privat '" + fileName + "' nuk ekziston.");
                messageWhenSaved = "Celesi privat u ruajt ne fajllin '" + path + "'.";
            } else if (type.equals("public")) {
                fileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";
                messageForError = ("Gabim: Celesi publik '" + fileName + "' nuk ekziston.");
                messageWhenSaved = "Celesi publik u ruajt ne fajllin '" + path + "'.";
            }
        }
    }
}
