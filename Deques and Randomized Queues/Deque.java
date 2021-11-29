/* *****************************************************************************
 *  Name: Soufiane BOURSEN
 *  Date: 26 NOV, 2021
 *  Description: Coursera Princeton-Algorithms Week2 assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int numberOfItems;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException("No more elements for you");
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException("Romove operation is unsupported in this project.");
        }
    }


    // construct an empty deque
    public Deque() {
        numberOfItems = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return numberOfItems;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (isEmpty()) last = first;
        else oldFirst.previous = first;
        numberOfItems++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.previous = oldLast;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        numberOfItems++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException("the deque is empty");
        Item item = first.item;
        first = first.next;

        if (isEmpty()) last = null;
        else first.previous = null;
        numberOfItems--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException("the deque is empty");
        Item item = last.item;
        last = last.previous;

        if (isEmpty()) first = null;
        else last.next = null;
        numberOfItems--;
        return item;

    }

    // for unit testing
    private String printDeque() {
        String stringDeque = "";
        for (Item item : this) {
            stringDeque = stringDeque.concat(item + " ");
        }
        return stringDeque;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Integer> deque = new Deque<>();

        StdOut.println("Size: " + deque.size());
        StdOut.println("Full deque: " + deque.printDeque());
        StdOut.println("");
        deque.addFirst(1);
        StdOut.println("Size: " + deque.size());
        StdOut.println("Full deque: " + deque.printDeque());
        StdOut.println("");
        deque.removeLast();
        StdOut.println("Size: " + deque.size());
        StdOut.println("Full deque: " + deque.printDeque());

    }
}
