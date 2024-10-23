public class Kviz1 {

    public static void main(String[] args) {
        System.out.printf("%d\n", izracunajPrepogibe(Math.pow(10,6)*149, 0.1d, 0));
    }

    public static long izracunajPrepogibe(double amountToBeat, double previous, long i) {
        if (previous > amountToBeat) return i;
        return izracunajPrepogibe(amountToBeat, previous*2, i + 1);
    }

}
