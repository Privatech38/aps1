import java.util.Scanner;

@SuppressWarnings({"SuspiciousNameCombination", "unchecked"})
public class DN2 {

    private static class ArrayList<T> {
        private static final int DEFAULT_CAPACITY = 16;
        private T[] elements = (T[]) new Object[DEFAULT_CAPACITY];
        private int size = 0;

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
        for (String string : data.split(" ")) {
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
        switch (commands[1]) {
            case "insert":
                insertionSort(list, nacinDelovanja, smerUrejanja);
                break;
            case "select":
                selectionSort(list, nacinDelovanja, smerUrejanja);
                break;
        }
    }

    private static void insertionSort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja) {
        insertionSort(elements, nacinDelovanja, smerUrejanja, 0);
    }

    private static void insertionSort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
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
                insertionSort(elements, nacinDelovanja, smerUrejanja, countMode);
            } else if (countMode == 2) {
                insertionSort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
            }
        }
    }

    private static void selectionSort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja) {
        selectionSort(elements, nacinDelovanja, smerUrejanja, 0);
    }

    private static void selectionSort(ArrayList<Integer> elements, NacinDelovanja nacinDelovanja, SmerUrejanja smerUrejanja, int countMode) {
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
                selectionSort(elements, nacinDelovanja, smerUrejanja, countMode);
            } else if (countMode == 2) {
                selectionSort(elements, nacinDelovanja, smerUrejanja == SmerUrejanja.ASC ? SmerUrejanja.DESC : SmerUrejanja.ASC, countMode);
            }
        }
    }

}