public class ds {

    public static void main(String[] args) {


        if (args.length < 4) {
            if (args[0].equals("caesar") && args[1].equals("bruteForce") && !(args[2].equals(""))) {
                Caesar.bruteForce(args[2]);

            } else {

                System.out.println("Numri i argumenteve eshte me i vogel sesa qe duhet." +
                        "Ju lutem specifikoni komanden, nenkomanden, celesin dhe tekstin! ");
                System.exit(1);
            }
        }

        switch (args[0]) {
            case "beale": {


                if (args[1].contains("encrypt")) {
                    try {

                        System.out.println(Beale.encode(args[2], args[3]));
                    } catch (Exception e) {
                        System.out.println("Ju lutem shkruani nje emer valid te file");
                        System.exit(1);
                    }

                } else if (args[1].contains("decrypt")) {
                    try {
                        System.out.println(Beale.decode(args[2], args[3]));
                    } catch (Exception e) {
                        System.out.println("Ju lutem shkruani nje emer valid te file");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Keni shtypur nenkomanden e gabuar!" + " Provoni encrypt ose decrypt");
                    System.exit(1);

                }
            }

            break;


            case "caesar": {
                try {

                    if (args[1].equals("encrypt")) {
                        System.out.println(Caesar.encrypt(args[3], Integer.parseInt(args[2])));
                    } else if (args[1].equals("decrypt")) {

                        System.out.println(Caesar.decrypt(args[3], Integer.parseInt(args[2])));

                    } else if (args[1].equals("bruteForce")) {

                        break;
                    } else {

                        System.out.println("Ju keni shtypur nenkomanden e gabuar!" +
                                "Nenkomandat per caesar jane encrypt, decrypt ose bruteForce");

                        System.exit(1);

                    }
                } catch (NumberFormatException e) {
                    System.out.println("Celesi duhet te jete numer!");
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
                System.out.println("Ju keni shtypur komanden e gabuar!" +
                        " Komandat qe duhet te perdoren jane: beale, caesar ose playfair");
                System.exit(1);
            }
        }


    }
}
