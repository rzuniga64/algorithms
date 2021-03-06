package stacks;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  Stack: array implementation is faster than other implementations.
 *  Stacks are used to complete a task and are soon after discarded.
 *
 *  Stack
 *  1.  Stack is a data structure that holds a collection of elements of
 *      the same type.
 *  2.  Allows only a single item to be added or removed at a time
 *  3.  Allows access to the last item inserted, first out (LIFO)
 *  4.  Problem: No random access to other elements
 *  5.  Stack overflow: Trying to push an item onto a full stack
 *  6.  Stack underflow: Trying to pop an item from an empty stack
 *
 *  Stack Considerations:
 *  - Underflow: throw exception if pop from an empty stack.
 *  - Overflow: use resizing array for array implementations. Use "repeated
 *    doubling".
 *  - Null items: We allow null items to be inserted.
 *  - Loitering: Holding a reference to an object when it is no longer needed
 *    public String pop() {
 *       return array[--N];  // loitering
 *    }
 *
 *  DEFECT: You have to declare the size of the array ahead of time so the
 *  stack has a certain capacity.  If there are more items on the stack than
 *  the capacity we have to deal with that problem.
 *
 *  AMORTIZED RUNNING TIME ANALYSIS
 *  Average running time per operation over a worst-case sequence of operations.
 *  Proposition: Starting from an empty stack, any sequence of M push and pop
 *  operations takes time proportional to M.
 *
 *  Order of growth of running time for resizing stack with N items:
 *  best    worst   amortized
 *  construct   1       1       1
 *  push        1       N       1
 *  pop         1       N       1
 *  size        1       1       1
 *
 *  MEMORY USAGE: stack resizing-array implementation:
 *  Proposition: Uses ~8 N - ~32 N bytes to represent a stack with N items.
 *  - ~8 N when full
 *  - ~32 N when one-quarter full
 *
 *  public class StackArray          8 bytes (reference to array)
 *  {                               24 bytes ( array overhead)
 *  private T[] stackArray;          8 bytes x array size
 *  private int N = 0;               4 bytes int
 *                                   4 bytes padding
 *  }
 *  Remark: Analysis includes memory for the stack (but not the items
 *  themselves, which the client owns).
 *
 *  STACK OPERATIONS
 *  Operations: These operations should take constant AMORTIZED time,
 *
 *  isEmpty: true if the stack currently contains no elements
 *  isFull: true if the stack is currently full
 *  push: add a item onto the top of the stack. Make sure it is not full first.
 *  pop: remove (and return) the item from the top of the stack. Make sure it is
 *  not empty first.
 *  peek: view the item on the top of the stack.
 *  makeEmpty: set top of the stack to 0.
 *
 *  This operation should take linear time O(n)
 *  popAll: removes all the elements
 *
 *  Stack Applications
 *  - Parsing in a compiler
 *  - Java Virtual Machine
 *  - Undo in a word processor
 *  - Back button in a web browser
 *  - PostScript language for printers
 *  - Implementing function calls in a compiler
 *
 *  @param <T> generic item
 */
public final class StackArray<T> implements Iterable {

    /** An array to implement stack of generic items. */
    private T[] stackArray;
    /** The top of the stack. */
    private int top;
    /** The type. */
    private Class<T[]> type;

    /**
     * Constructor.
     * @param type the type
     */
    private StackArray(final Class<T[]> type) {

        stackArray = type.cast(Array.newInstance(type.getComponentType(), 1));
        top = 0;   // stack is empty
        this.type = type;
    }

    /**
     * Returns true or false if stack is empty.
     * @return true if stack is empty.
     */
    private boolean isEmpty() {

        return (top == 0);
    }

    /**
     * Returns true or false if stack is full.
     * @return true if stack is full.
     */
    private boolean isFull() {

        return top == stackArray.length;
    }

    /** Set the top of stack to zero. */
    private void makeEmpty() {

        top = 0;
    }

    /**
     * Resize the array.
     * @param capacity the capacity of the array
     */
    private void resize(final int capacity) {

        T[] copy = type.cast(Array.newInstance(type.getComponentType(),
                capacity));
        for (int i = 0; i < top; i++) {
            copy[i] = stackArray[i];
        }
        stackArray = copy;
    }

    /**
     * Push an item onto the stack.
     * Inserting first N items takes time proportional to N (not N^2)
     * @param item the item
     */
    private void push(final T item) {

        if (isFull()) {
            resize(2 * stackArray.length);
        }
        stackArray[top++] = item;
    }

    /**
     * Pop an item off the stack.
     * Average time complexity: O(1)
     * This version avoids "loitering". Garbage collector can reclaim memory
     * only if no outstanding references
     *
     * @return The item popped of the stack
     */
    private T pop() {

        if (top - 1 < 0) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        }
        T item = stackArray[--top];
        // avoid loitering so garbage collector can reclaim memory.
        stackArray[top] = null;

        if (top > 0 && top == stackArray.length / 4) {
            resize(stackArray.length / 2);
        }
        return item;
    }

    /**
     * Looks at the object at the top of this stack without removing it
     * from the stack.
     *
     * @return the object at the top of this stack
     */
    private T peek() {

        return stackArray[top - 1];
    }

    /**
     * Removes all items off the stack.
     * Average time complexity: O(N)
     */
    private void popAll() {

        while (!isEmpty()) {
            pop();
        }
    }

    /**
     * Display the stack
     * Average time complexity for traversing list: O(n).
     */
    private void displayTheStack() {

        for (int n = 0; n < stackArray.length - 1; n++) {
            System.out.println(stackArray[n]);
        }
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO
     * order.
     * @return an iterator that iterates over the items in this queue in FIFO
     * order
     */
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    /**
     * an iterator, doesn't implement remove() since it's optional.
     */
    private class ArrayIterator implements Iterator<T> {
        /** i. */
        private int i = 0;

        /**
         * Is there another item?
         * @return true if there is another item; false otherwise */
        public boolean hasNext() {
            return i < stackArray.length - 1;
        }

        /** Not supported. */
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Get the next item in the array.
         * @return the next item in the array.
         */
        public T next() {

            if (hasNext()) {
                throw new NoSuchElementException();
            }
            return stackArray[i++];
        }
    }

    /**
     * Unit tests the StackArray data type.
     *
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        StackArray<String> stack = new StackArray<>(String[].class);

        stack.push("Don Quixote");
        stack.push("A Tale of Two Cities");
        stack.push("The Lord of the Rings");
        stack.push("Harry Potter and the Sorcerer's Stone");

        stack.displayTheStack();

        // Look at the top item on the stack
        System.out.println("Peek at top of the stack: " + stack.peek());

        // Remove value from the stack (LIFO)
        System.out.println("Pop the top of the stack: " + stack.pop());

        // Remove all from the stack
        stack.popAll();
        stack.makeEmpty();
    }
}
