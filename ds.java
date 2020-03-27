public class ds {
    public static void main (String[] args) {


        if (args.length <= 2) {

            System.out.println("Numri i argumenteve eshte me i vogel sesa qe duhet." +
                    "Ju lutem specifikoni komanden, nenkomanden, celesin dhe tekstin! ");
            System.exit(1);
        }

        switch (args[0]) {
            case "beale": {
                System.out.println("hehe");
            }

            break;


            case "caesar": {

                if (args[1].equals("encrypt")){
                    System.out.println(Caesar.encrypt(args[3], Integer.parseInt(args[2])));
                }
                else if (args[1].equals("decrypt")) {

                    System.out.println(Caesar.decrypt(args[3], Integer.parseInt(args[2])));

                }
                else if (args[1].equals("bruteForce")) {

                    System.out.print(Caesar.bruteForce(args[2]));
                }
                else {

                    System.out.println("Ju keni shtypur komanden e gabuar!"+
                            "Komandat per caesar jane encrypt, decrypt ose bruteForce");

                    System.exit(1);

                }

            }
            break;




            case "playfair": {

                boolean shouldPrintTable = false;
                try {
                    if (args[4].equalsIgnoreCase("--table")) {
                        shouldPrintTable = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println();
                }

                Pf pf = new Pf();
                pf.startPF(args[2], args[3], args[1], shouldPrintTable);


            }
            break;
            default: {
                System.out.println("Ju keni shtypur komande te gabuar!");
                System.exit(1);
            }
        }


    }
}
