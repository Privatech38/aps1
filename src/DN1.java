import java.util.Scanner;

public class DN1 {

    private static class CollectionException extends Exception {
        public CollectionException(String msg) {
            super(msg);
        }
    }

    interface Collection {
        String ERR_MSG_EMPTY = "Collection is empty.";
        String ERR_MSG_FULL = "Collection is full.";

        boolean isEmpty();
        boolean isFull();
        int size();
        String toString();
    }

    @SuppressWarnings("unchecked")
    private static class Stack<T> implements Collection {
        private static final int DEFAULT_CAPACITY = 64;
        private final T[] elements = (T[]) new Object[DEFAULT_CAPACITY];
        private int top = -1;
        
        public T top() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            return elements[top];
        }
        
        public void push(T x) throws CollectionException {
            if (isFull())
                throw new CollectionException(ERR_MSG_FULL);
            elements[++top] = x;
        }
        
        public T pop() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            final T element = elements[top];
            elements[top--] = null;
            return element;
        }

        @Override
        public boolean isEmpty() {
            return top == -1;
        }

        @Override
        public boolean isFull() {
            return top == DEFAULT_CAPACITY - 1;
        }

        @Override
        public int size() {
            return top + 1;
        }

        @Override
        public String toString() {
            if (isEmpty())
                return "";
            String output = "";
            for (int i = 0; i < top; i++) {
                output = output.concat(elements[i] + " ");
            }
            return output;
        }
    }

    @SuppressWarnings("unchecked")
    private static class Sequence<T> implements Collection {
        private static final int DEFAULT_CAPACITY = 64;
        private final T[] elements = (T[]) new Object[DEFAULT_CAPACITY];
        private int top = -1;
        static final String ERR_MSG_INDEX = "Wrong index in sequence.";

        public T get(int i) throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            if (i > top || i < 0)
                throw new CollectionException(ERR_MSG_INDEX);
            return elements[i];
        }

        public void add(T x) throws CollectionException {
            if (isFull())
                throw new CollectionException(ERR_MSG_FULL);
            elements[++top] = x;
        }

        @Override
        public int size() {
            return top + 1;
        }

        @Override
        public boolean isEmpty() {
            return top == -1;
        }

        @Override
        public boolean isFull() {
            return top == DEFAULT_CAPACITY - 1;
        }
    }

    private static final Sequence<Stack<String>> stacks = new Sequence<>();

    public static void main(String[] args) throws CollectionException {
        // Create stacks
        for (int i = 0; i < 42; i++)
            stacks.add(new Stack<>());
        // User input
        final Scanner sc = new Scanner(System.in).useDelimiter(System.lineSeparator() + "|\\s");
//        System.out.print("> ");
        while (sc.hasNext()) {
            String s = sc.next();
            System.out.println(s);
            stacks.get(0).push(s);
        }

    }

    private static void process(String input) {

    }

}
