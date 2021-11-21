/* *****************************************************************************
 *  Name:              Soufiane Boursen
 *  Coursera User ID:  enaimann
 *  Last modified:     Nov 19, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final byte OPEN = 0b0001;
    private static final byte TOP = 0b0011;
    private static final byte BOTTOM = 0b0101;
    private static final byte TOPBOTTOM = 0b0111;
    private final int num;
    private final WeightedQuickUnionUF uf;
    private byte[] flatgrid;
    private int opensitesnum;
    private boolean pertcolate;





    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("grid dimensions should be non-zero positif integer");
        }
        this.pertcolate = false;
        this.opensitesnum = 0;
        this.num = n;
        // total number of sites
        int numberofsites = this.num * this.num;

        // each site has 3bit content: {isOpen , isFull, isConnectedToBottom}
        this.flatgrid = new byte[numberofsites];
        this.uf = new WeightedQuickUnionUF(numberofsites);
        // All Sites are {0 , 0 , 1}
        for (int i = 0; i < numberofsites; i++) {
            this.flatgrid[i] = 0b0000;
        }
    }


    private int getIndexfromcoordinates(int row, int col) {
        return (row - 1)*this.num + col - 1;
    }

    private int[] getOpenneighboursids(int row, int col) {
        int[] neighbours = {-1, -1, -1, -1};
        int index = 0;
        // left
        if (col > 1 && this.isOpen(row, col - 1)) {
            neighbours[index] = getIndexfromcoordinates(row, col - 1);
            index++;
        }
        // right
        if (col < this.num && this.isOpen(row, col + 1)) {
            neighbours[index] = getIndexfromcoordinates(row, col + 1);
            index++;
        }
        // top
        if (row > 1 && this.isOpen(row - 1, col)) {
            neighbours[index] = getIndexfromcoordinates(row - 1, col);
            index++;
        } else if (row == 1) {
            this.flatgrid[this.uf.find(getIndexfromcoordinates(row, col))] |= TOP;

        }
        // bottom
        if (row < this.num && this.isOpen(row + 1, col)) {
            neighbours[index] = getIndexfromcoordinates(row + 1, col);
        } else if (row == this.num) {
            this.flatgrid[this.uf.find(getIndexfromcoordinates(row, col))] |= BOTTOM;
        }
        return neighbours;

    }



    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > this.num || col < 1 || col > this.num) {
            throw new IllegalArgumentException("row and col should be in the range 1 to n inclusif");
        }
        if (this.flatgrid[getIndexfromcoordinates(row, col)] == 0b0000) {
            this.flatgrid[getIndexfromcoordinates(row, col)] = OPEN;
            int[] neighbours = getOpenneighboursids(row, col);

            int site = getIndexfromcoordinates(row, col);
            int siteroot = this.uf.find(site);

            int i = 0;
            byte sr;
            byte nr;
            while (i < neighbours.length && neighbours[i] != -1) {
                int neighbouroot = this.uf.find(neighbours[i]);
                sr = this.flatgrid[siteroot];
                nr = this.flatgrid[neighbouroot];
                this.uf.union(siteroot, neighbouroot);
                byte result = (byte) (sr | nr);
                siteroot = this.uf.find(siteroot);
                this.flatgrid[siteroot] = result;
                i++;
            }
            this.opensitesnum++;

            if (flatgrid[(this.uf.find(siteroot))] == TOPBOTTOM) {
                this.pertcolate = true;
            }

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > this.num || col < 1 || col > this.num) {
            throw new IllegalArgumentException("row and col should be in the range 1 to n inclusif");
        }
        return (this.flatgrid[getIndexfromcoordinates(row, col)] & OPEN) == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > this.num || col < 1 || col > this.num) {
            throw new IllegalArgumentException("row and col should be in the range 1 to n inclusif");
        }
        return (this.flatgrid[this.uf.find(getIndexfromcoordinates(row, col))] & TOP) == TOP;

    }
    // ------------------------------------------------------------------------------------------------------
    private boolean isbFull(int row, int col) {
        if (row < 1 || row > this.num || col < 1 || col > this.num) {
            throw new IllegalArgumentException("row and col should be in the range 1 to n inclusif");
        }
        return (this.flatgrid[this.uf.find(getIndexfromcoordinates(row, col))] & BOTTOM) == BOTTOM;

    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.opensitesnum;

    }

    // does the system percolate?
    public boolean percolates() {
        return this.pertcolate;
    }

    // test client (see below)

    public static void main(String[] args) {
        int n;
        if (!StdIn.isEmpty()) {
            n = StdIn.readInt();

            Percolation percolation = new Percolation(n);
            int row;
            int col;

            while (!StdIn.isEmpty()) {
                row = StdIn.readInt();
                col = StdIn.readInt();

                percolation.open(row, col);
                StdOut.println("-------------");
                StdOut.println("site: " + "(" + row+","+" "+ col+")");
                StdOut.println("isopen: " + percolation.isOpen(row, col));
                StdOut.println("isfull: " + percolation.isFull(row, col));
                StdOut.println("isbottomfull: " + percolation.isbFull(row, col));
                StdOut.println("percolates?: " + percolation.percolates() + "\n");
                StdOut.println("-------------------------------------------- \n");
            }
        }




    }

}