import java.io.File;
public class DeleteUser {
    public static void deleteUser(String name) throws Exception{
        String publicfileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".xml";
        String privatefileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".pub.xml";

        File publicFile  = new File(publicfileName);
        File privateFile = new File(privatefileName);
        if(publicFile.exists() && privateFile.exists()){
            privateFile.delete();
            System.out.println("Eshte larguar celesi privat:    '" + privatefileName + "'.");

            publicFile.delete();
            System.out.println("Eshte larguar celesi publik:    '" + publicfileName + "'.");
        } else if(publicFile.exists() && !privateFile.exists()){
            publicFile.delete();
            System.out.println("Eshte larguar celesi publik: '" + publicfileName + "'.");
        } else if(!publicFile.exists() && privateFile.exists()){
            privateFile.delete();
            System.out.println("Eshte larguar celesi privat:" + privatefileName + "'.");
        } else {
            System.out.println("Gabim: Celesi '" + name + "' nuk ekziston.");
        }


    }



}