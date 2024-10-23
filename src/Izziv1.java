import java.util.Random;

public class Izziv1 {

    /*



     */

    public static void main(String[] args) {
        System.out.println("    n    | lin. iskanje | bin. iskanje | rek. bin. iskanje");
        for (int i = 20000; i <= 1000000; i += 20000) {
            int[] tabela = generateTable(i);
            long linearTime = timeLinear(tabela);
            long binaryTime = timeBinary(tabela);
            long binaryRecursiveTime = timeBinaryRecursive(tabela);
            System.out.printf(" %7d |    %6d    |     %4d     |     %4d\n", i, linearTime, binaryTime, binaryRecursiveTime);
        }
    }

    private static int[] generateTable(int n) {
        int[] table = new int[n];
        for (int i = 0; i < n; i++) {
            table[i] = i + 1;
        }
        return table;
    }

    private static int findLinear(int[] table, int searchedValue) {
        for (int i = 0; i < table.length; i++)
            if (table[i] == searchedValue)
                return i;
        return -1;
    }

    private static int findBinary(int[] table, int lowerBound, int upperBound, int searchedValue) {
        while (lowerBound <= upperBound) {
            int mid = (lowerBound + upperBound) / 2;
            if (table[mid] == searchedValue)
                return mid;
            if (table[mid] < searchedValue)
                lowerBound = mid + 1;
            if (table[mid] > searchedValue)
                upperBound = mid - 1;
        }
        return -1;
    }

    private static int findBinaryRecursive(int [] table, int lowerBound, int upperBound, int searchedValue) {
        if (lowerBound > upperBound)
            return -1;
        int mid = lowerBound + (upperBound - lowerBound) / 2;
        if (searchedValue < table[mid])
            return findBinaryRecursive(table, lowerBound, mid - 1, searchedValue);
        if (searchedValue > table[mid])
            return findBinaryRecursive(table, mid + 1, upperBound, searchedValue);
        return mid;
    }

    private static long timeLinear(int[] tabela) {
        long startNanos = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            findLinear(tabela, new Random().nextInt(tabela.length));
        return (System.nanoTime() - startNanos)/1000;
    }

    private static long timeBinary(int[] tabela) {
        long startNanos = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            findBinary(tabela, 0, tabela.length - 1, new Random().nextInt(tabela.length));
        return (System.nanoTime() - startNanos)/1000;
    }

    private static long timeBinaryRecursive(int[] tabela) {
        long startNanos = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            findBinaryRecursive(tabela, 0, tabela.length - 1, new Random().nextInt(tabela.length));
        return (System.nanoTime() - startNanos)/1000;
    }


}
