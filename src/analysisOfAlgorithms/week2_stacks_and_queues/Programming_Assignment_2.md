Programming Assignment 2: Deques and Randomized Queues
------------------------------------------------------

Write a generic data type for a deque and a randomized queue. The goal of this assignment is to implement elementary 
data structures using arrays and linked lists, and to introduce you to generics and iterators.

###Dequeue. 
A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue that supports adding and 
removing items from either the front or the back of the data structure. 

Create a generic data type analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2.Deque that implements the following API:

public class analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2.Deque<Item> implements Iterable<Item> {

   public analysisOfAlgorithms.week2_stacks_and_queues.DequeAlgorithms.week2.wee2.Deque()                           // construct an empty deque
   
   public boolean isEmpty()                 // is the deque empty?
   
   public int size()                        // return the number of items on the deque
   
   public void addFirst(Item item)          // add the item to the front
   
   public void addLast(Item item)           // add the item to the end
   
   public Item removeFirst()                // remove and return the item from the front
   
   public Item removeLast()                 // remove and return the item from the end
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   
   public static void main(String[] args)   // unit testing
   
}

####Corner cases. 

* Throw a java.lang.NullPointerException if the client attempts to add a null item; 
* throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque; 
* throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator; 
* throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more 
  items to return.

####Performance requirements.   

* Your deque implementation must support each deque operation in constant worst-case time. 
* A deque containing n items must use at most 48n + 192 bytes of memory. 
* and use space proportional to the number of items currently in the deque. 
* Additionally, your iterator implementation must support each operation (including construction) in constant worst-case
  time.

#Randomized queue. 

A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from items 
in the data structure. Create a generic data type analysisOfAlgorithms.week2_stacks_and_queues.RandomizedQueue.week2.wee2.RandomizedQueue that implements the following API:

public class analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2.RandomizedQueue<Item> implements Iterable<Item> {

   public analysisOfAlgorithms.week2_stacks_and_queues.RandomizedQueue.week2.wee2.RandomizedQueue()                 // construct an empty randomized queue

   public boolean isEmpty()                 // is the queue empty?

   public int size()                        // return the number of items on the queue

   public void enqueue(Item item)           // add the item

   public Item dequeue()                    // remove and return a random item

   public Item sample()                     // return (but do not remove) a random item

   public Iterator<Item> iterator()         // return an independent iterator over items in random order

   public static void main(String[] args)   // unit testing

}

####Performance requirements. 


* Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) in 
  constant amortized time. 
* That is, any sequence of m randomized queue operations (starting from an empty queue) should take at most cm steps in 
  the worst case, for some constant c. 
* A randomized queue containing n items must use at most 48n + 192 bytes of memory. 
* Additionally, your iterator implementation must support operations next() and hasNext() in constant worst-case time; 
* Construction in linear time; you may (and will need to) use a linear amount of extra memory per iterator.

#analysisOfAlgorithms.week2_stacks_and_queues.Subsetlgorithms.week2.wee2.Subset client. 

Write a client program analysisOfAlgorithms.week2_stacks_and_queues.Subsetlgorithms.week2.wee2.Subset.java that takes a command-line integer k; reads in a sequence of N strings from standard 
input using StdIn.readString(); and prints out exactly k of them, uniformly at random. Each item from the sequence can 
be printed out at most once. You may assume that 0 ≤ k ≤ n, where n is the number of string on standard input.

% echo A B C D E F G H I | java analysisOfAlgorithms.week2_stacks_and_queues.Subsetlgorithms.week2.wee2.Subset 3       % echo AA BB BB BB BB BB CC CC | java analysisOfAlgorithms.week2_stacks_and_queues.Subsetlgorithms.week2.wee2.Subset 8

C                                              BB

G                                              AA

A                                              BB
                                               CC
% echo A B C D E F G H I | java analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2.Subset 3       BB

E                                              BB

F                                              CC

G                                              BB

###Performance Requirements
* The running time of analysisOfAlgorithms.week2_stacks_and_queues.Subsetlgorithms.week2.wee2.Subset must be linear in the size of the input. 
* You may use only a constant amount of memory plus either one analysisOfAlgorithms.week2_stacks_and_queues.DequeAlgorithms.week2.wee2.Deque or analysisOfAlgorithms.week2_stacks_and_queues.RandomizedQueue.week2.wee2.RandomizedQueue object of maximum size at most 
  n, where n is the number of strings on standard input. 
* (For an extra challenge, use only one analysisOfAlgorithms.week2_stacks_and_queues.DequeAlgorithms.week2.wee2.Deque or analysisOfAlgorithms.week2_stacks_and_queues.RandomizedQueue.week2.wee2.RandomizedQueue object of maximum size at most k.) 

It should have the following API.

public class analysisOfAlgorithms.week2_stacks_and_queues.Subsetlgorithms.week2.wee2.Subset {

   public static void main(String[] args)

}

### Deliverables. 

Submit only analysisOfAlgorithms.week2_stacks_and_queues.DequeAlgorithms.week2.wee2.Deque.java, analysisOfAlgorithms.week2_stacks_and_queues.RandomizedQueue.week2.wee2.RandomizedQueue.java, and analysisOfAlgorithms.week2_stacks_and_queues.wee2ysisOfAlgorithms.week2.Subset.java. 

We will supply algs4.jar. 

Your submission not call library functions except those in StdIn, StdOut, StdRandom, java.lang, java.util.Iterator, and 
java.util.NoSuchElementException. 

In particular, you may not use either java.util.LinkedList or java.util.ArrayList.