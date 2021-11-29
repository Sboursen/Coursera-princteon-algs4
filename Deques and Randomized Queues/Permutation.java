/* *****************************************************************************
 *  Name: Soufiane BOURSEN
 *  Date: 26 NOV, 2021
 *  Description: Coursera Princeton-Algorithms Week2 assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        int counter = 0;
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            rqueue.enqueue(string);
            }

        Iterator<String> permutator = rqueue.iterator();
        while (permutator.hasNext() && counter < k) {
            StdOut.println(permutator.next());
            counter++;
        }

        }
    }
