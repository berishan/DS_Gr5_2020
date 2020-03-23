/******************************************************************************************************************
 References
 For Playfair Cipher
 Title: Playfair source code
 *       Author: Sean McKenna
 *       Date: Oct 10, 2016
 *       Availability: https://github.com/mckennapsean/code-examples/blob/master/Java/Playfair.java
 */

import java.awt.*;
public class Pf {


    // length of digraph array
    private int length = 0;

    // table for Playfair cipher
    private String[][] table;
    //  added for args
    private String key = "";
    private String plaintext = "";


    // main run of the program
    public void startPF(String key, String text, String function, Boolean shouldPrintTable) {

        String keyword = parseString(key);
        String input = parseString(text);

        table = this.cipherTable(keyword);
        if (shouldPrintTable) {
            this.printTable(table);
        }

        //   encodes or decodes the message
        if (function.equalsIgnoreCase("encrypt")) {

            printResultsE(cipher(input));

        } else if (function.equalsIgnoreCase("decrypt")) {

            printResultsD(decode(input));
        } else {
            System.out.println("udhezime:");
        }
    }


    private String parseString(String s) {
        String parse = s;
        parse = parse.toUpperCase();
        parse = parse.replaceAll("[^A-Z]", "");
        parse = parse.replace("J", "I");
        return parse;
    }


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


    private String cipher(String in) {
        length = in.length() / 2 + in.length() % 2;


        for (int i = 0; i < (length - 1); i++) {
            if (in.charAt(2 * i) == in.charAt(2 * i + 1)) {
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = in.length() / 2 + in.length() % 2;
            }
        }

        String[] digraph = new String[length];
        for (int j = 0; j < length; j++) {
            if (j == (length - 1) && in.length() / 2 == (length - 1))
                in = in + "X";
            digraph[j] = in.charAt(2 * j) + "" + in.charAt(2 * j + 1);
        }


        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for (int k = 0; k < length; k++)
            out = out + encDigraphs[k];
        return out;
    }


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


    private Point getPoint(char c) {
        Point pt = new Point(0, 0);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (c == table[i][j].charAt(0))
                    pt = new Point(i, j);
        return pt;
    }


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


    private void printResultsE(String enc) {
        String chunkedString = "";
        for (int i = 0; i < enc.length(); i = i + 2) {
            chunkedString = chunkedString + enc.charAt(i) + enc.charAt(i + 1) + " ";

        }
        System.out.println("This is the encoded message:");
        System.out.println(chunkedString.toLowerCase());
        System.out.println();
    }

    private void printResultsD(String dec) {
        System.out.println("This is the decoded message:");
        System.out.println(dec.toLowerCase());
    }


}


