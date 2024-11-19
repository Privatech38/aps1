public class Izziv2 {

    public static class CollectionException extends Exception {
        public CollectionException(String msg) {
            super(msg);
        }
    }

    interface Collection {
        static final String ERR_MSG_EMPTY = "Collection is empty.";
        static final String ERR_MSG_FULL = "Collection is full.";

        boolean isEmpty();
        boolean isFull();
        int size();
        String toString();
    }

    interface Stack<T> extends Collection {
        T top() throws CollectionException;
        void push(T x) throws CollectionException;
        T pop() throws CollectionException;
    }

    interface Deque<T> extends Collection {
        T front() throws CollectionException;
        T back() throws CollectionException;
        void enqueue(T x) throws CollectionException;
        void enqueueFront(T x) throws CollectionException;
        T dequeue() throws CollectionException;
        T dequeueBack() throws CollectionException;
    }

    interface Sequence<T> extends Collection {
        static final String ERR_MSG_INDEX = "Wrong index in sequence.";

        T get(int i) throws CollectionException;

        void add(T x) throws CollectionException;
    }

    static class ArrayDeque<T> implements Deque<T>, Stack<T>, Sequence<T> {
        private static final int DEFAULT_CAPACITY = 4;
        private final T[] elements = (T[]) new Object[DEFAULT_CAPACITY];
        private int top = -1;

        @Override
        public T front() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            return elements[0];
        }

        @Override
        public T back() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            return elements[top];
        }

        @Override
        public void enqueue(T x) throws CollectionException {
            if (isFull())
                throw new CollectionException(ERR_MSG_FULL);
            elements[++top] = x;
        }

        @Override
        public void enqueueFront(T x) throws CollectionException {
            if (isFull())
                throw new CollectionException(ERR_MSG_FULL);
            System.arraycopy(elements, 0, elements, 1, top++);
            elements[0] = x;
        }

        @Override
        public T dequeue() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            final T element = elements[0];
            System.arraycopy(elements, 1, elements, 0, top--);
            return element;
        }

        @Override
        public T dequeueBack() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            final T element = elements[top];
            elements[top--] = null;
            return element;
        }

        @Override
        public T get(int i) throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            if (i > top || top < 0)
                throw new CollectionException(ERR_MSG_INDEX);
            return elements[i];
        }

        @Override
        public void add(T x) throws CollectionException {
            if (isFull())
                throw new CollectionException(ERR_MSG_FULL);
            elements[++top] = x;
        }

        @Override
        public T top() throws CollectionException {
            if (isEmpty())
                throw new CollectionException(ERR_MSG_EMPTY);
            return elements[top];
        }

        @Override
        public void push(T x) throws CollectionException {
            if (isFull())
                throw new CollectionException(ERR_MSG_FULL);
            elements[++top] = x;
        }

        @Override
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
                return "[]";
            String output = "[";
            for (int i = 0; i < top; i++) {
                output = output.concat(elements[i] + ", ");
            }
            return output.concat(elements[top] + "]");
        }
    }

    // Tester

    public static void main(String[] args) throws CollectionException {
        // Stack tester
        Stack<String> stack = new ArrayDeque<>();
        stack.push("ABC");
        stack.push("DEF");
        stack.push("GHI");
        stack.push("JKL");
        System.out.println("Stack: " + stack);
        // Push error test
        System.out.println("Full stack push test:");
        try {
            stack.push("I can't enter because the stack is full!");
        } catch (CollectionException e) {
            e.printStackTrace();
        }
        // Pop tests
        System.out.println("Popped element: " + stack.pop());
        System.out.println("Stack: " + stack);
        stack.pop();
        stack.pop();
        stack.pop();
        System.out.println("Stack after popping all elements: " + stack);
        // Pop error test
        System.out.println("Empty stack pop test:");
        try {
            stack.pop();
        } catch (CollectionException e) {
            e.printStackTrace();
        }

        // Deque tester
        Deque<String> deque = new ArrayDeque<>();
        // Add elements to deque
        deque.enqueue("A");
        deque.enqueue("B");
        deque.enqueue("C");
        System.out.println("Deque: " + deque);
        // Enque front
        deque.enqueueFront("D");
        System.out.println("Deque: " + deque);
        // Deque fail test
        try {
            deque.dequeue();
        } catch (CollectionException e) {
            e.printStackTrace();
        }
        try {
            deque.dequeueBack();
        } catch (CollectionException e) {
            e.printStackTrace();
        }
    }

}
