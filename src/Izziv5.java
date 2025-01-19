import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Izziv5 {

    private static final String[] priimki = new String[]{"Novak", "Horvat", "Kovačič", "Krajnc", "Zupančič", "Kovač", "Potočnik", "Mlakar", "Vidmar", "Kos"};
    private static final String[] imena = new String[]{"Franc", "Janez", "Marko", "Andrej", "Luka", "Ana", "Maja", "Mojca", "Nina", "Eva"};

    public static void main(String[] args) {
        // Randomly create
        Scanner scanner = new Scanner(System.in);
        System.out.println("Vnesi število oseb:");
        final int amount = scanner.nextInt();
        Oseba[] osebe = new Oseba[amount];
        for (int i = 0; i < amount; i++) {
            osebe[i] = new Oseba(priimki[new Random().nextInt(priimki.length)], imena[new Random().nextInt(imena.length)],
                    new Random().nextInt(80));
        }
        do {
            Oseba[] osebeCopy = Arrays.copyOf(osebe, osebe.length);
            Oseba.attr = -1;
            System.out.println("Seznam:");
            for (int i = 0; i < amount; i++) {
                System.out.println(osebeCopy[i].toString());
            }
            System.out.println("Vnesi atribut po katerem atributu sortirati (0-2):");
            Oseba.attr = scanner.nextInt();
            System.out.println("Vnesi smer urejanja (ASC ali DESC):");
            SmerUrejanja smerUrejanja = SmerUrejanja.valueOf(scanner.next());
            bubbleSort(osebeCopy, smerUrejanja);
            System.out.println("Želiš ponoviti? (true ali false)");
        } while (scanner.nextBoolean());
    }

    public static class Oseba implements Comparable<Oseba> {

        public static int attr;

        private String priimek;
        private String ime;
        private int letoR;

        public Oseba(String priimek, String ime, int letoR) {
            this.priimek = priimek;
            this.ime = ime;
            this.letoR = letoR;
        }

        public String getPriimek() {
            return priimek;
        }

        public void setPriimek(String priimek) {
            this.priimek = priimek;
        }

        public String getIme() {
            return ime;
        }

        public void setIme(String ime) {
            this.ime = ime;
        }

        public int getLetoR() {
            return letoR;
        }

        public void setLetoR(int letoR) {
            this.letoR = letoR;
        }

        @Override
        public int compareTo(Oseba o) {
            switch (attr) {
                case 0:
                    return priimek.compareTo(o.getPriimek());
                case 1:
                    return ime.compareTo(o.getIme());
                case 2:
                    return letoR - o.getLetoR();
                default:
                    return 0;
            }
        }

        public String toString() {
            switch (attr) {
                case 0:
                    return priimek;
                case 1:
                    return ime;
                case 2:
                    return "" + letoR;
                default:
                    return "%s %s (%d let)".formatted(priimek, ime, letoR);
            }
        }
    }

    public static enum SmerUrejanja {
        ASC,
        DESC
    }
    
    public static void bubbleSort(Oseba[] elements, SmerUrejanja smerUrejanja) {
        int i = 0; // Also counts as last swapped index
        while (i < elements.length - 1) {
            boolean swapped = false;
            int lastSwappedIndex = elements.length - 1;
            for (int j = elements.length - 1; j > i; j--) {
                if (smerUrejanja == SmerUrejanja.ASC && elements[j - 1].compareTo(elements[j]) > 0) {
                    final Oseba temp = elements[j - 1];
                    elements[j - 1] = elements[j];
                    elements[j] = temp;
                    swapped = true;
                    lastSwappedIndex = j;
                } else if (smerUrejanja == SmerUrejanja.DESC && elements[j - 1].compareTo(elements[j]) < 0) {
                    final Oseba temp = elements[j - 1];
                    elements[j - 1] = elements[j];
                    elements[j] = temp;
                    swapped = true;
                    lastSwappedIndex = j;
                }
            }
            i = lastSwappedIndex;
            for (int j = 0; j < i; j++) {
                System.out.print(elements[j].toString() + " ");
            }
            System.out.print("|");
            for (int j = i; j < elements.length; j++) {
                System.out.print(" " + elements[j].toString());
            }
            System.out.println();
            if (!swapped) {
                break;
            }
        }
    }
}
