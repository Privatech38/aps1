import java.util.Scanner;

@SuppressWarnings({"SuspiciousNameCombination", "unchecked"})
public class DN2 {

    private static class ArrayList<T> {
        private static final int DEFAULT_CAPACITY = 16;
        private T[] elements;
        private int size = 0;

        public ArrayList() {
            elements = (T[]) new Object[DEFAULT_CAPACITY];
        }

        public ArrayList(int capacity) {
            elements = (T[]) new Object[capacity];
        }

        public void add(T x) {
            if (isFull()) {
                final T[] newElements = (T[]) new Object[elements.length * 2];
                System.arraycopy(elements, 0, newElements, 0, elements.length);
                elements = newElements;
            }
            elements[size++] = x;
        }

        public void add(int i, T x) {
            if (isFull()) {
                final T[] newElements = (T[]) new Object[elements.length * 2];
                System.arraycopy(elements, 0, newElements, 0, elements.length);
                elements = newElements;
            }
            System.arraycopy(elements, i, elements, i + 1, size++ - i);
            elements[i] = x;
        }

        public T get(int i) {
            return elements[i];
        }

        public void set(int i, T x) {
            elements[i] = x;
        }

        public T pop(int i) {
            final T x = elements[i];
            System.arraycopy(elements, i + 1, elements, i, --size - i);
            return x;
        }

        public void swap(int i, int j) {
            T temp = elements[i];
            elements[i] = elements[j];
            elements[j] = temp;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return elements.length == 0;
        }

        public boolean isFull() {
            return elements.length == size;
        }

        public String toString() {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(elements[i]);
                if (i < size - 1) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }

        public ArrayList<T> subList(int fromIndex, int toIndex) {
            final ArrayList<T> list = new ArrayList<>();
            for (int i = fromIndex; i < toIndex; i++) {
                list.add(elements[i]);
            }
            return list;
        }

    }

    public static void main(String[] args) {
        // User input
        final Scanner sc = new Scanner(System.in);
        final String[] commands = sc.nextLine().split(" ");
        final String data = sc.nextLine();
        final ArrayList<Integer> numbers = new ArrayList<>();
        for (String string : data.split("\\s+")) {
            numbers.add(Integer.parseInt(string));
        }
        process(commands, numbers);
    }

    enum NacinDelovanja {
        TRACE,
        COUNT
    }

    enum SmerUrejanja {
        ASC,
        DESC
    }

    private static void process(String[] commands, ArrayList<Integer> list) {
        // Determine sorting
        final NacinDelovanja nacinDelovanja = NacinDelovanja.valueOf(commands[0].toUpperCase());
        SmerUrejanja smerUrejanja;
        switch (commands[2]) {
            case "up":
                smerUrejanja = SmerUrejanja.ASC;
                break;
            case "down":
                smerUrejanja = SmerUrejanja.DESC;
                break;
            default:
                smerUrejanja = SmerUrejanja.ASC;
        }
        // Sort
        Sorter sorter;
        switch (commands[1]) {
            case "insert":
                sorter = new InsertionSorter();
                break;
            case "select":
                sorter = new SelectionSorter();
                break;
            case "bubble":
                sorter = new BubbleSorter();
                break;
            case "heap":
                sorter = new HeapSorter();
                break;
            case "merge":
                sorter = new MergeSorter();
                break;
            case "quick":
                sorter = new QuickSorter();
                break;
            case "radix":
                sorter = new RadixSorter();
                break;
            default:
                sorter = new InsertionSorter();
        }
        sorter.sort(list, nacinDelovanja, smerUrejanja, 0);
    }

    private static interface Sorter {
        void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode);
    }

    private static class InsertionSorter implements Sorter {
        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            long moveCount = 0;
            long compareCount = 0;
            for (int i = 1; i < elements.size(); i++) {
                int j = i;
                int currentElement = elements.get(i);
                moveCount++;
                while (j > 0) {
                    compareCount++;
                    if (smerUrejanja == SmerUrejanja.ASC ? elements.get(j - 1) > currentElement : elements.get(j - 1) < currentElement) {
                        elements.set(j, elements.get(j - 1));
                        moveCount++;
                        j--;
                    } else {
                        break;
                    }
                }
                elements.set(j, currentElement);
                moveCount++;
                if (nacinDelovanja == NacinDelovanja.TRACE) {
                    System.out.printf("%s | %s\n", elements.subList(0, i + 1), elements.subList(i + 1, elements.size()));
                }
            }
            if (nacinDelovanja == NacinDelovanja.COUNT) {
                System.out.printf("%s%d %d", countMode++ == 0 ? "" : " | ", moveCount, compareCount);
                if (countMode == 1) {
                    this.sort(elements, nacinDelovanja, smerUrejanja, countMode);
                } else if (countMode == 2) {
                    this.sort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
                }
            }
        }
    }

    private static class SelectionSorter implements Sorter {
        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            long moveCount = 0;
            long compareCount = 0;
            for (int i = 0; i < elements.size(); i++) {
                int minIndex = i;
                for (int j = i + 1; j < elements.size(); j++) {
                    compareCount++;
                    if (smerUrejanja == SmerUrejanja.ASC ? elements.get(j) < elements.get(minIndex) : elements.get(j) > elements.get(minIndex)) {
                        minIndex = j;
                    }
                }
                if (i == elements.size() - 1) {
                    continue;
                }
                elements.swap(i, minIndex);
                moveCount += 3;
                if (nacinDelovanja == NacinDelovanja.TRACE) {
                    System.out.printf("%s | %s\n", elements.subList(0, i + 1), elements.subList(i + 1, elements.size()));
                }
            }
            if (nacinDelovanja == NacinDelovanja.COUNT) {
                System.out.printf("%s%d %d", countMode++ == 0 ? "" : " | ", moveCount, compareCount);
                if (countMode == 1) {
                    this.sort(elements, nacinDelovanja, smerUrejanja, countMode);
                } else if (countMode == 2) {
                    this.sort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
                }
            }
        }
    }

    private static class BubbleSorter implements Sorter {
        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            long moveCount = 0;
            long compareCount = 0;
            int i = 0; // Also counts as last swapped index
            while (i < elements.size() - 1) {
                boolean swapped = false;
                int lastSwappedIndex = elements.size() - 1;
                for (int j = elements.size() - 1; j > i; j--) {
                    compareCount++;
                    if (smerUrejanja == SmerUrejanja.ASC ? elements.get(j - 1) > elements.get(j) : elements.get(j - 1) < elements.get(j)) {
                        elements.swap(j - 1, j);
                        moveCount += 3;
                        swapped = true;
                        lastSwappedIndex = j;
                    }
                }
                i = lastSwappedIndex;
                if (nacinDelovanja == NacinDelovanja.TRACE)
                    System.out.printf("%s | %s\n", elements.subList(0, i), elements.subList(i, elements.size()));
                if (!swapped) {
                    break;
                }

            }
            if (nacinDelovanja == NacinDelovanja.COUNT) {
                System.out.printf("%s%d %d", countMode++ == 0 ? "" : " | ", moveCount, compareCount);
                if (countMode == 1) {
                    this.sort(elements, nacinDelovanja, smerUrejanja, countMode);
                } else if (countMode == 2) {
                    this.sort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
                }
            }
        }
    }

    private static class HeapSorter implements Sorter {

        private long moveCount;
        private long compareCount;

        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            moveCount = -3;
            compareCount = 0;
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            for (int i = elements.size() / 2 - 1; i >= 0; i--) {
                heapify(elements, elements.size(), i, smerUrejanja);
            }
            for (int i = elements.size() - 1; i >= 0; i--) {
                if (nacinDelovanja == NacinDelovanja.TRACE)
                    System.out.printf("%s | %s\n", elements.subList(0, i + 1), elements.subList(i + 1, elements.size()));
                elements.swap(0, i);
                heapify(elements, i, 0, smerUrejanja);
                moveCount += 3;
            }
            if (nacinDelovanja == NacinDelovanja.COUNT) {
                System.out.printf("%s%d %d", countMode++ == 0 ? "" : " | ", moveCount, compareCount);
                if (countMode == 1) {
                    this.sort(elements, nacinDelovanja, smerUrejanja, countMode);
                } else if (countMode == 2) {
                    this.sort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
                }
            }
        }

        private void heapify(ArrayList<Integer> elements, int n, int i, SmerUrejanja smerUrejanja) {
            int largest = i;
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            if (l < n) {
                compareCount++;
                if (smerUrejanja == SmerUrejanja.ASC ? elements.get(l) > elements.get(largest) : elements.get(l) < elements.get(largest)) {
                    largest = l;
                }
            }
            if (r < n) {
                compareCount++;
                if (smerUrejanja == SmerUrejanja.ASC ? elements.get(r) > elements.get(largest) : elements.get(r) < elements.get(largest)) {
                    largest = r;
                }
            }
            if (largest != i) {
                elements.swap(i, largest);
                moveCount += 3;
                heapify(elements, n, largest, smerUrejanja);
            }
        }
    }

    private static class MergeSorter implements Sorter {
        private long moveCount = 0;
        private long compareCount = 0;

        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            moveCount = 0;
            compareCount = 0;
            split(elements, 0, elements.size(), smerUrejanja, nacinDelovanja, countMode);
            if (nacinDelovanja == NacinDelovanja.COUNT) {
                System.out.printf("%s%d %d", countMode++ == 0 ? "" : " | ", moveCount, compareCount);
                if (countMode == 1) {
                    this.sort(elements, nacinDelovanja, smerUrejanja, countMode);
                } else if (countMode == 2) {
                    this.sort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
                }
            }
        }

        private void split(ArrayList<Integer> elements, int l, int r, SmerUrejanja smerUrejanja, NacinDelovanja nacinDelovanja, int countMode) {
            if (r - l < 2) {
                return;
            }
            int diff = (int) Math.ceil((r - l) / 2.0f);
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.printf("%s | %s\n", elements.subList(l, l + diff), elements.subList(l + diff, r));
            split(elements, l, l + diff, smerUrejanja, nacinDelovanja, countMode);
            split(elements, l + diff, r, smerUrejanja, nacinDelovanja, countMode);
            merge(elements, l, l + diff, r, smerUrejanja, nacinDelovanja, countMode);
        }

        // Right is exclusive
        private void merge(ArrayList<Integer> elements, int left, int middle, int right, SmerUrejanja smerUrejanja, NacinDelovanja nacinDelovanja, int countMode) {
            int i = 0;
            int leftIndex = 0;
            int rightIndex = 0;
            ArrayList<Integer> leftList = elements.subList(left, middle);
            ArrayList<Integer> rightList = elements.subList(middle, right);
            while (leftIndex < leftList.size() && rightIndex < rightList.size()) {
                compareCount++;
                if (smerUrejanja == SmerUrejanja.ASC ? leftList.get(leftIndex) <= rightList.get(rightIndex) : leftList.get(leftIndex) >= rightList.get(rightIndex)) {
                    elements.set(left + i, leftList.get(leftIndex++));
                } else {
                    elements.set(left + i, rightList.get(rightIndex++));
                }
                i++;
                moveCount += 2;
            }
            while (leftIndex < leftList.size()) {
                elements.set(left + i++, leftList.get(leftIndex++));
                moveCount += 2;
            }
            while (rightIndex < rightList.size()) {
                elements.set(left + i++, rightList.get(rightIndex++));
                moveCount += 2;
            }
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements.subList(left, right));
        }
    }

    private static class QuickSorter implements Sorter {

        private long moveCount;
        private long compareCount;

        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            moveCount = 0;
            compareCount = 0;
            quickSort(elements, 0, elements.size() - 1, smerUrejanja, nacinDelovanja);
            if (nacinDelovanja == NacinDelovanja.TRACE)
                System.out.println(elements);
            if (nacinDelovanja == NacinDelovanja.COUNT) {
                System.out.printf("%s%d %d", countMode++ == 0 ? "" : " | ", moveCount, compareCount);
                if (countMode == 1) {
                    this.sort(elements, nacinDelovanja, smerUrejanja, countMode);
                } else if (countMode == 2) {
                    this.sort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
                }
            }
        }

        private void quickSort(ArrayList<Integer> elements, int left, int right, SmerUrejanja smerUrejanja, NacinDelovanja nacinDelovanja) {
            if (left < right) {
                int pivot = partition(elements, left, right, smerUrejanja, nacinDelovanja);
                quickSort(elements, left, pivot - 1, smerUrejanja, nacinDelovanja);
                quickSort(elements, pivot + 1, right, smerUrejanja, nacinDelovanja);
            }
        }

        private int partition(ArrayList<Integer> elements, int left, int right, SmerUrejanja smerUrejanja, NacinDelovanja nacinDelovanja) {
            int pivot = elements.get(left);
            moveCount++;
            int l = left;
            int r = right + 1;

            while (true) {
                do {
                    l++;
                    compareCount++;
                } while (l < right && (smerUrejanja == SmerUrejanja.ASC ? elements.get(l) < pivot : elements.get(l) > pivot));
                do {
                    r--;
                    compareCount++;
                } while (r > left && (smerUrejanja == SmerUrejanja.ASC ? elements.get(r) > pivot : elements.get(r) < pivot));
                if (l < r) {
                    elements.swap(l, r);
                    moveCount += 3;
                } else {
                    break;
                }
            }
            elements.swap(left, r);
            moveCount += 3;
            if (nacinDelovanja == NacinDelovanja.TRACE) {
                if (left < r)
                    System.out.print(elements.subList(left, r) + " ");
                System.out.printf("| %d |", elements.get(r));
                if (r + 1 < right + 1)
                    System.out.print(" " + elements.subList(r + 1, right + 1));
                System.out.println();
            }
            return r;
        }

    }

    private static class RadixSorter implements Sorter {

        private static int getDigit(Integer number, int digitPosition) {
            final String numberStr = number.toString();
            if (digitPosition >= numberStr.length())
                return 0;
            return Character.getNumericValue(numberStr.charAt(digitPosition));
        }

        @Override
        public void sort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
            long moveCount = 0;
            long compareCount = 0;
            // Get the longest number
            int longest = 0;
            for (int i = 0; i < elements.size(); i++) {
                int length = elements.get(i).toString().length();
                if (length > longest)
                    longest = length;
            }
            // Sort
            ArrayList<Integer> tmp = new ArrayList<>(elements.size());
            for (int i = 0; i < longest; i++) {
                int[] c = new int[10];
                // Doloci c
                for (int j = 0; j < elements.size(); j++) {
                    c[getDigit(elements.get(j), i)]++;
                    compareCount++;
                    moveCount++;
                }
                // Akumuliraj c
                for (int j = 1; j < 10; j++) {
                    c[j] += c[j - 1];
                }
                // Sortiraj
                for (int j = elements.size() - 1; j >= 0; j--) {
                    tmp.set(--c[getDigit(elements.get(j), i)], elements.get(j));
                }
                // Prestavi nazaj
                for (int j = 0; j < tmp.size(); j++) {
                    elements.set(j, tmp.get(j));
                }
                // Print
                if (nacinDelovanja == NacinDelovanja.TRACE)
                    System.out.println(elements);
            }
        }
    }

}