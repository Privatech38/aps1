import java.util.Scanner;

@SuppressWarnings({"SuspiciousNameCombination", "unchecked"})
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
            for (int i = 0; i <= top; i++) {
                output = output.concat(elements[i] + " ");
            }
            return output;
        }
    }

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

    private static final Sequence<Stack<Object>> stacks = new Sequence<>();

    public static void main(String[] args) throws CollectionException {
        // Create stacks
        for (int i = 0; i < 42; i++)
            stacks.add(new Stack<>());
        // User input
        final Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            // Clear stacks
            for (int i = 0; i < stacks.size(); i++) {
                while (!stacks.get(i).isEmpty())
                    stacks.get(i).pop();
            }
            process(sc.nextLine(), 0);
        }
    }

    private static void process(String input, int givenSlot) throws CollectionException {
        int selectedStack = givenSlot;
        boolean compareResult = false;
        Stack<Object> objectStack = stacks.get(selectedStack);
        // fun count
        int funStack = 0;
        int funCount = -1;
        for (String string : input.split("\\s+")) {
            if (funCount > 0) {
                stacks.get(funStack).push(string);
                funCount--;
                continue;
            } else if (funCount == 0) {
                objectStack = stacks.get(0);
                funCount--;
            }
            if (string.matches("\\?\\S+")) {
                if (!compareResult) {
                    continue;
                } else {
                    string = string.substring(1);
                }
            }
            if (string.matches("-?\\d+")) {
                objectStack.push(Integer.parseInt(string));
                continue;
            }
            if (string.matches("\\??(print|clear|run|loop|fun|move|reverse)")) {
                selectedStack = (int) stacks.get(0).pop();
                objectStack = stacks.get(selectedStack);
                switch (string) {
                    // Peti del
                    case "print":
                        System.out.println(objectStack.toString());
                        break;
                    case "clear":
                        while (!objectStack.isEmpty())
                            objectStack.pop();
                        break;
                    case "run":
                        process(objectStack.toString(), 0);
                        break;
                    case "loop":
                        int loopAmount = (int) stacks.get(0).pop();
                        for (int i = 0; i < loopAmount; i++) {
                            process(objectStack.toString(), 0);
                        }
                        break;
                    case "fun":
                        funStack = selectedStack;
                        funCount = (int) stacks.get(0).pop();
                        break;
                    case "move":
                        int amount = (int) stacks.get(0).pop();
                        for (int i = 0; i < amount; i++)
                            objectStack.push(stacks.get(0).pop());
                        break;
                    case "reverse":
                        final Sequence<Object> temporarySequence = new Sequence<>();
                        while (!objectStack.isEmpty())
                            temporarySequence.add(objectStack.pop());
                        for (int i = 0; i < temporarySequence.size(); i++)
                            objectStack.push(temporarySequence.get(i));
                        break;
                }
                selectedStack = givenSlot;
                objectStack = stacks.get(selectedStack);
                continue;
            }
            switch (string) {
                // Prvi del
                case "echo":
                    System.out.println(objectStack.top());
                    break;
                case "pop":
                    objectStack.pop();
                    break;
                case "dup":
                    objectStack.push(objectStack.top());
                    break;
                case "dup2":
                    final Object topElement = objectStack.pop();
                    final Object secondTopElement = objectStack.top();
                    objectStack.push(topElement);
                    objectStack.push(secondTopElement);
                    objectStack.push(topElement);
                    break;
                case "swap":
                    final Object topElementSwap = objectStack.pop();
                    final Object secondTopElementSwap = objectStack.pop();
                    objectStack.push(topElementSwap);
                    objectStack.push(secondTopElementSwap);
                    break;
                // Drugi del
                case "char":
                    objectStack.push((char) (int) objectStack.pop());
                    break;
                case "even":
                    Object num = objectStack.pop();
                    objectStack.push(((num instanceof Integer ? (int) num : (long) num) % 2 == 0 ? 1 : 0));
                    break;
                case "odd":
                    Object num2 = objectStack.pop();
                    objectStack.push(((num2 instanceof Integer ? (int) num2 : (long) num2) % 2 == 0 ? 0 : 1));
                    break;
                case "!":
                    objectStack.push(factorial((int) objectStack.pop()));
                    break;
                case "len":
                    objectStack.push(String.valueOf(objectStack.pop()).length());
                    break;
                // Tretji del
                case "<>":
                    objectStack.push(((int) objectStack.pop() == (int) objectStack.pop() ? 0 : 1));
                    break;
                case "<":
                    objectStack.push(((int) objectStack.pop() <= (int) objectStack.pop() ? 0 : 1));
                    break;
                case "<=":
                    objectStack.push(((int) objectStack.pop() < (int) objectStack.pop() ? 0 : 1));
                    break;
                case "==":
                    objectStack.push((String.valueOf(objectStack.pop()).equals(String.valueOf(objectStack.pop())) ? 1 : 0));
                    break;
                case ">":
                    objectStack.push(((int) objectStack.pop() >= (int) objectStack.pop() ? 0 : 1));
                    break;
                case ">=":
                    objectStack.push(((int) objectStack.pop() > (int) objectStack.pop() ? 0 : 1));
                    break;
                case "+":
                    objectStack.push((int) objectStack.pop() + (int) objectStack.pop());
                    break;
                case "-":
                    int minus = (int) objectStack.pop();
                    objectStack.push((int) objectStack.pop() - minus);
                    break;
                case "*":
                    objectStack.push((int) objectStack.pop() * (int) objectStack.pop());
                    break;
                case "/":
                    int div = (int) objectStack.pop();
                    objectStack.push((int) objectStack.pop() / div);
                    break;
                case "%":
                    int z = (int) objectStack.pop();
                    objectStack.push((int) objectStack.pop() % z);
                    break;
                case ".":
                    Object concat = objectStack.pop();
                    objectStack.push(objectStack.pop() + "" + concat);
                    break;
                case "rnd":
                    int y = (int) objectStack.pop();
                    int x = (int) objectStack.pop();
                    objectStack.push((int) (x + Math.random() * (y - x)));
                    break;
                // Četrti del
                case "then":
                    compareResult = (int) objectStack.pop() != 0;
                    break;
                case "else":
                    compareResult = !compareResult;
                    break;
                default:
                    objectStack.push(string);
            }
        }
    }

    private static long factorial(int num) {
        long result = 1;
        for (int i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }
}
