/* *****************************************************************************
 *  Name:              Soufiane Boursen
 *  Coursera User ID:  enaimann
 *  Last modified:     Nov 19, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double PARA = 1.96;
    private final double[] trialresults;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("parameters must be non-zero positive integers");
        this.trialresults = new double[trials];


        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int row;
            int col;
            while (!percolation.percolates()) {

                do {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;

                } while (percolation.isOpen(row, col));

                percolation.open(row, col);
            }
            this.trialresults[i] = (percolation.numberOfOpenSites()/((double) (n * n)));
            // StdOut.println("Trial " + i + " : percolation atchieved with p* " + this.trialresults[i]);

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.trialresults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.trialresults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - PARA *this.stddev()/(Math.sqrt(this.trialresults.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + PARA *this.stddev()/(Math.sqrt(this.trialresults.length));
    }

    // test client (see below)
   public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);


        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + "[" + percolationStats.confidenceLo() + ", " +percolationStats.confidenceLo()+ "]");

    }

}
