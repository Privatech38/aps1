import java.util.Arrays;

public class Izziv5 {
    protected static class Oseba implements Comparable<Oseba> {

        public static int attr = 0;

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
                    return "";
            }
        }
    }

    enum SmerUrejanja {
        ASC,
        DESC
    }
    
    public void bubbleSort(Oseba[] elements, SmerUrejanja smerUrejanja) {
        System.out.println(Arrays.toString(elements));
        int i = 0; // Also counts as last swapped index
        while (i < elements.length - 1) {
            boolean swapped = false;
            int lastSwappedIndex = elements.length - 1;
            for (int j = elements.length - 1; j > i; j--) {
                if (smerUrejanja == SmerUrejanja.ASC && elements[j - 1].compareTo(elements[j]) < 0) {
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
            if (!swapped) {
                break;
            }
        }
    }
}
