

public class Beale
{
    public static String decode(String file,String desc) throws IOException
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"\\" +file))); //e ndrrojme tani nese duhet se idk qysh duhet sig
        char[] list = new char[1000];
        String line = br.readLine();
        char[] words;
        while(line != null)
        {
            int index = 0;
            words = line.toCharArray();
            for(char w : words)
            {
                list[index++] = w;
                System.out.println(w + " " + index);
            }
            line = br.readLine();
        }
        String[] numbers = desc.split(" ");
        String decodedMessage = "";
        for(String number : numbers)
        {
            int n = Integer.parseInt(number);
            System.out.println(n + " " + list[n]);
            decodedMessage += list[n - 1];
        }
        return "Decoded message " + decodedMessage ;
    }



    public static String encode(String file,String desc) throws IOException
    {
        System.out.println("Encode called from encode");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"\\"+file)));
        char[] list = new char[1000];
        String line = br.readLine();
        char[] words;
        while(line != null)
        {
            int index = 0;
            words = line.toCharArray();
            for(char w : words)
            {
                list[index++] = w;
            }
            line = br.readLine();
        }
        int indexForLetters =0;
        String encodedMessage = "";
        while(indexForLetters < desc.length())
        {
            for(int i =0;i < list.length;i++)
            {

                if(desc.length() <= indexForLetters)
                    break ;
                if(list[i] == desc.charAt(indexForLetters))
                {
                    indexForLetters++;
                    encodedMessage += (i + 1) +" ";
                    break;
                }
            }

        }
        return "Encoded message " + encodedMessage;
    }
}