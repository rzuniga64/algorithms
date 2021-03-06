/******************************************************************************
 *  Compilation:  javac BinarySearchST.java
 *  Execution:    java BinarySearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with binary search in an ordered array.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java BinarySearchST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

package symboltable;

import edu.princeton.cs.algs4.*;

import java.util.NoSuchElementException;

/**
 *  The BST class represents an ordered symbol table of generic key-value pairs.
 *  It supports the usual put, get, contains, delete, size, and is-empty
 *  methods. It also provides ordered methods for finding the minimum,
 *  maximum, floor, select, and ceiling.It also provides a keys method for
 *  iterating over all of the keys.
 *
 *  A symbol table implements the associative array abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be null—setting the value associated with a key to null is
 *  equivalent to deleting the key from the symbol table.
 *
 *  This implementation uses a sorted array. It requires that the key type
 *  implements the Comparable interface and calls the compareTo() and method
 *  to compare two keys. It does not call either equals() or hashCode().
 *  The put and remove operations each take linear time in the worst case;
 *  the contains, ceiling, floor, and rank operations take logarithmic time;
 *  the size, is-empty, minimum, maximum, and select operations take constant
 *  time. Construction takes constant time.
 *
 *  operation           running time
 *  search              lg N
 *  insert/delete       N (To insert, need to shift all greater keys over)
 *  min/max             1
 *  floor/ceiling       lg N
 *  rank                lg N
 *  select              1
 *  ordered iteration   N
 *
 *  For additional documentation, see
 *  \<a href="http://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BST},
 *  {@link SequentialSearchST}, {@link RedBlackBST},
 *  {@link SeparateChainingHashST}, and {@link LinearProbingHashST},
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * Class Binary Search.
 * @param <Key> the key
 * @param <Value> the value
 */
public final class BinarySearchST<Key extends Comparable<Key>, Value> {

    /** Initial capacity. */
    private static final int INIT_CAPACITY = 2;
    /** Array of keys. */
    private Key[] keys;
    /** Array of values. */
    private Value[] vals;
    /** Number of items. */
    private int n = 0;

    /**Initializes an empty symbol table.*/
    private BinarySearchST() {
        this(INIT_CAPACITY);
    }
    /**
     * Initializes an empty symbol table with the specified initial capacity.
     * @param capacity the maximum capacity
     */
    private BinarySearchST(final int capacity) {

        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    /**
     * Resize the underlying arrays.
     * @param capacity the capacity
     */
    private void resize(final int capacity) {

        assert capacity >= n;
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Does this symbol table contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains()is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the
     *         symbol table and null if the key is not in the symbol
     *         table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(final Key key) {

        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) {
            return vals[i];
        }
        return null;
    }

    /**
     * Returns the number of keys in this symbol table strictly less than key.
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private int rank(final Key key) {

        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if      (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void put(final Key key, final Value val)  {

        if (key == null) {
            throw new IllegalArgumentException("first argument to "
                    + "put() is null");
        }

        if (val == null) {
            delete(key);
            return;
        }

        int i = rank(key);

        // key is already in table
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        // insert new key-value pair
        if (n == keys.length) {
            resize(2 * keys.length);
        }

        for (int j = n; j > i; j--)  {
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        n++;

        assert check();
    }

    /**
     * Removes the specified key and associated value from this symbol table
     * (if the key is in the symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void delete(final Key key) {

        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (isEmpty()) {
            return;
        }

        // compute rank
        int i = rank(key);

        // key not in table
        if (i == n || keys[i].compareTo(key) != 0) {
            return;
        }

        for (int j = i; j < n - 1; j++)  {
            keys[j] = keys[j + 1];
            vals[j] = vals[j + 1];
        }

        n--;
        keys[n] = null;  // to avoid loitering
        vals[n] = null;

        // resize if 1/4 full
        if (n > 0 && n == keys.length / 4) {
            resize(keys.length / 2);
        }
        assert check();
    }

    /**
     * Removes the smallest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {

        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        delete(min());
    }

    /**
     * Removes the largest key and associated value from this symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {

        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        delete(max());
    }

    //**************************************************************************
    // Ordered symbol table methods.
    //*************************************************************************/
   /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    private Key min() {

        if (isEmpty()) {
            return null;
        }
        return keys[0];
    }

    /**
     * Returns the largest key in this symbol table.
     * @return the largest key in this symbol table
     * @throws NoSuchElementException if this symbol table is empty
     */
    public Key max() {

        if (isEmpty()) {
            return null;
        }
        return keys[n - 1];
    }

    /**
     * Return the kth smallest key in this symbol table.
     * @param  k the order statistic
     * @return the kth smallest key in this symbol table
     * @throws IllegalArgumentException unless {@code k} is between 0 and
     *        <em>n</em> &minus; 1
     */
    private Key select(final int k) {

        if (k < 0 || k >= n) {
            return null;
        }
        return keys[k];
    }

    /**
     * Returns the largest key in this symbol table less than or equal to key}.
     * @param  key the key
     * @return the largest key in this symbol table less than or equal to key
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Key floor(final Key key) {

        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        int i = rank(key);
        if (i < n && key.compareTo(keys[i]) == 0) {
            return keys[i];
        }
        if (i == 0) {
            return null;
        } else {
            return keys[i - 1];
        }
    }

    /**
     * Returns the smallest key in this symbol table greater than or equal to
     * key.
     * @param  key the key
     * @return  the smallest key in this symbol table greater than or equal to
     *          key
     * @throws  NoSuchElementException if there is no such key
     * @throws  IllegalArgumentException if {@code key} is {@code null}
     */
    public Key ceiling(final Key key) {

        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        int i = rank(key);
        if (i == n) {
            return null;
        } else {
            return keys[i];
        }
    }

    /**
     * Returns the number of keys in this symbol table in the specified range.
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return the number of keys in this symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public int size(final Key lo, final Key hi) {

        if (lo == null) {
            throw new IllegalArgumentException("first argument to size() is "
                    + "null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to size() is "
                    + "null");
        }

        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }

    /**
     * Returns all keys in this symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in this symbol table
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Returns all keys in this symbol table in the given range,
     * as an Iterable.
     * @param lo minimum endpoint
     * @param hi maximum endpoint
     * @return all keys in this symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    private Iterable<Key> keys(final Key lo, final Key hi) {

        if (lo == null) {
            throw new IllegalArgumentException("first argument to keys() is "
                    + "null");
        }
        if (hi == null) {
            throw new IllegalArgumentException("second argument to keys() is "
                    + "null");
        }

        Queue<Key> queue = new Queue<Key>();
        if (lo.compareTo(hi) > 0) {
            return queue;
        }
        for (int i = rank(lo); i < rank(hi); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(hi)) {
            queue.enqueue(keys[rank(hi)]);
        }
        return queue;
    }
    //**************************************************************************
    //Check internal invariants.
    // ************************************************************************/

    /**
     * Check().
     * @return true if sorted; false otherwise.
     */
    private boolean check() {
        return isSorted() && rankCheck();
    }

    /**
     * Are the items in the array in ascending order?
     * @return true if array in ascending order; false otherwise.
     */
    private boolean isSorted() {

        for (int i = 1; i < size(); i++) {
            if (keys[i].compareTo(keys[i - 1]) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check that rank(select(i) = 1.
     * @return true if rank(select(i) = 1; false otherwise
     */
    private boolean rankCheck() {

        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (int i = 0; i < size(); i++) {
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Unit tests the {@code BinarySearchST} data type.
     * @param args the command-line arguments
     */
    public static void main(final String[] args) {

        // Try keys: S E A R C H E X A M P L E
        BinarySearchST<String, Integer> st = new BinarySearchST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}

