public class ds {
    public static void main (String[] args) {

        if (args.length <= 2) {

            System.out.println("udhezime:");
            System.exit(1);
        }

        switch (args[0]) {
            case "beale": {
                System.out.println("hehe");
            }

            break;
            case "caesar": {

                System.out.println("hehe");
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
