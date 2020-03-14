/*******************************************************************************************************************
 * References
 *    For Playfair Cipher
 **        Title: Playfair source code
 * *       Author: Sean McKenna
 * *       Date: Oct 10, 2016
 * *       Availability: https://github.com/mckennapsean/code-examples/blob/master/Java/Playfair.java
 *
 *
 ***************************************************************************************************************/
// encodes text input using the Playfair cipher
// ues letter 'X' for insertion, I replaces J

import java.awt.*;
import java.util.HashMap;

public class Ciphers {
    // length of digraph array
    private int length = 0;

    // table for Playfair cipher
    private String[][] table;
    //  added for args
    private String key = "";
    private String plaintext = "";


    // main program

    public static void main(String[] args) {
        if (args[1].equalsIgnoreCase("playfair")) {
            Ciphers pf = new Ciphers();
            pf.startPF(args[3], args[4], args[2]);
        }

    }

    // main run of the program
    private void startPF(String key, String text, String function) {

        // prepare text for encoding/decoding
        String keyword = parseString(key);
        table = this.cipherTable(keyword);
        String input = parseString(text);


        // output the results to user
        this.printTable(table);


        HashMap<Integer, Character> notLetters = collect(text);


        //   encodes or decodes the message
        if (function.equalsIgnoreCase("encrypt")) {
            printResultsE(format(cipher(input), notLetters));


        } else if (function.equalsIgnoreCase("decrypt")) {
            printResultsD(format(decode(input), notLetters));
        }

    }


    private String parseString(String s) {
        String parse = s;
        parse = parse.toUpperCase();
        parse = parse.replaceAll("[^A-Z]", "");
        parse = parse.replace("J", "I");
        return parse;
    }

    // creates the cipher table based on some input string (already parsed)
    private String[][] cipherTable(String keyy) {
        String[][] playfairTable = new String[5][5];
        String keyString = keyy + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        // fill string array with empty string
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                playfairTable[i][j] = "";

        for (int k = 0; k < keyString.length(); k++) {
            boolean repeat = false;
            boolean used = false;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairTable[i][j].equals("" + keyString.charAt(k))) {
                        repeat = true;
                    } else if (playfairTable[i][j].equals("") && !repeat && !used) {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    // cipher: takes input (all upper-case), encodes it, and returns output
    private String cipher(String in) {
        length =  in.length() / 2 + in.length() % 2;

        // insert x between double-letter digraphs & redefines "length"
        for (int i = 0; i < (length - 1); i++) {
            if (in.charAt(2 * i) == in.charAt(2 * i + 1)) {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length =  in.length() / 2 + in.length() % 2;
            }
        }

        // adds an x to the last digraph, if necessary
        String[] digraph = new String[length];
        for (int j = 0; j < length; j++) {
            if (j == (length - 1) && in.length() / 2 == (length - 1))
                in = in + "X";
            digraph[j] = in.charAt(2 * j) + "" + in.charAt(2 * j + 1);
        }

        // encodes the digraphs and returns the output
        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for (int k = 0; k < length; k++)
            out = out + encDigraphs[k];
        return out;
    }

    // encodes the digraph input with the cipher's specifications
    private String[] encodeDigraph(String di[]) {
        String[] enc = new String[length];
        for (int i = 0; i < length; i++) {
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            // case 1: letters in digraph are of same row, shift columns to right
            if (r1 == r2) {
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;

                // case 2: letters in digraph are of same column, shift rows down
            } else if (c1 == c2) {
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;

                // case 3: letters in digraph form rectangle, swap first column # with second column #
            } else {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            //performs the table look-up and puts those values into the encoded array
            enc[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return enc;
    }

    // decodes the output given from the cipher and decode methods (opp. of encoding process)
    private String decode(String out) {
        String decoded = "";
        for (int i = 0; i < out.length() / 2; i++) {
            char a = out.charAt(2 * i);
            char b = out.charAt(2 * i + 1);
            int r1 = (int) getPoint(a).getX();

            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if (r1 == r2) {
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            } else if (c1 == c2) {
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            } else {
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
        return decoded;
    }

    // returns a point containing the row and column of the letter
    private Point getPoint(char c) {
        Point pt = new Point(0, 0);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (c == table[i][j].charAt(0))
                    pt = new Point(i, j);
        return pt;
    }

    //added HashMap for numbers and symbols
    private HashMap<Integer, Character> collect(String text) {

        HashMap<Integer, Character> notLetters = new HashMap<Integer, Character>();
        for (int i = 0; i < text.length(); i++) {

            if (!Character.isLetter(text.charAt(i))) {
                notLetters.put(i, text.charAt(i));
            }
        }
        return notLetters;
    }


    // StringBuilder to format the result
    private String format(String a, HashMap<Integer, Character> B) {

        StringBuilder sb = new StringBuilder(a);
        for (int i : B.keySet()) {
            sb.insert(Integer.parseInt(String.valueOf(i)), B.get(i));
        }
        return sb.toString();
    }


    // prints the cipher table out for the user
    private void printTable(String[][] printedTable) {
        System.out.println("This is the cipher table from the given keyword.");
        System.out.println();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(printedTable[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // prints results (encoded and decoded)
    private void printResultsE(String enc) {
        System.out.println("This is the encoded message:");
        System.out.println(enc);
        System.out.println();
    }

    private void printResultsD(String dec) {
        System.out.println("This is the decoded message:");
        System.out.println(dec);
    }

}




