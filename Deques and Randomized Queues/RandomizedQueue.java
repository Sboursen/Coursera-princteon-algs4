/* *****************************************************************************
 *  Name: Soufiane BOURSEN
 *  Date: 26 NOV, 2021
 *  Description: Coursera Princeton-Algorithms Week2 assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int head;
    private int tail;

    private class RandomArrayIterator implements Iterator<Item>
    {
        private int i = head;
        private final int[] array;
        public RandomArrayIterator() {
            array = new int[items.length];
            for (int j = 0; j < items.length; j++) {
                array[j] = j;
            }
            StdRandom.shuffle(array, head, tail);
        }

        public boolean hasNext() {
            return i < tail;
        }

        public Item next()
        {
            if (!hasNext()) throw new java.util.NoSuchElementException("No more elements for you");
            return items[array[i++]];
        }
        public void remove() {
            throw new UnsupportedOperationException("Romove operation is unsupported in this project.");
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        head = 0;
        tail = 0;
        items = (Item[]) new Object[1];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = head; i < tail; i++) {
            copy[i - head] = items[i];
        }
        items = copy;
        tail = tail - head;
        head = 0;
    }
    private void shift() {
        for (int i = 0; i <= tail - head - 1; i++) {
            items[i] = items[i + head];
            items[i + head] = null;
        }
        tail = tail - head;
        head = 0;
    }





    // is the randomized queue empty?
    public boolean isEmpty() {
        return tail == head;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail - head;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        if (tail == items.length) {
            if (head < tail/4.0) {
                resize(2* items.length);
            } else {
                shift();
            }
        }
        items[tail++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new java.util.NoSuchElementException("the Rqueue is empty");
        Item item = items[head];
        items[head++] = null;
        if ((double) (size()) == items.length/4.0) resize(items.length/2);
        if (size() == 0) {
            head = 0;
            tail = 0;
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new java.util.NoSuchElementException("the Rqueue is empty");
        return items[StdRandom.uniform(head, tail)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    // for unit testing
    private String printqueue() {
        String stringqueue = "";
        for (Item item : this) {
            stringqueue = stringqueue.concat("'" + item + "'" + " ");
        }
        return stringqueue;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> intrqueue = new RandomizedQueue<>();
        int item;
        StdOut.println("Size: "+intrqueue.size());
        StdOut.println("Full deque: " + intrqueue.printqueue());
        StdOut.println("--------------------------------------");
        item = 3;
        intrqueue.enqueue(item);
        StdOut.println("added item to first: " + item);
        StdOut.println("Size: "+intrqueue.size());
        StdOut.println("Full deque: " + intrqueue.printqueue());
        item = intrqueue.dequeue();
        StdOut.println("removed item from last: " + item);
        StdOut.println("Size: "+intrqueue.size());
        StdOut.println("Full deque: " + intrqueue.printqueue());
        item = 0;
        intrqueue.enqueue(item);
        StdOut.println("added item to first: " + item);
        StdOut.println("Size: "+intrqueue.size());
        StdOut.println("Full deque: " + intrqueue.printqueue());


        /* RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        StdOut.println("Size: "+rqueue.size());
        StdOut.println("Full deque: " + rqueue.printqueue());
        StdOut.println("------------------------------------------------------------");
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            if (string.contains("_")) {
                String item = rqueue.dequeue();
                StdOut.println("removed item from last: " + item);
                StdOut.println("Size: "+rqueue.size());
                StdOut.println("Full deque: " + rqueue.printqueue());

            } else {
                rqueue.enqueue(string);
                StdOut.println("added item to first: " + string);
                StdOut.println("Size: "+rqueue.size());
                StdOut.println("Full deque: " + rqueue.printqueue());
            }
        } */

    }

}
