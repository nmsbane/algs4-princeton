import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] fractionOfOpenedSites;
    private Percolation percolation;
    private final int numberOfTrials;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        numberOfTrials = trials;
        fractionOfOpenedSites = new double[trials];

        for(int exptNumber = 0; exptNumber < trials; exptNumber++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row,col);
                }
            }
            double fraction = (double) percolation.numberOfOpenSites() / (n*n);
            fractionOfOpenedSites[exptNumber] = fraction;
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(fractionOfOpenedSites);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(fractionOfOpenedSites);
    }

    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - ((1.96 * stddev()) / Math.sqrt(numberOfTrials));
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + ((1.96 * stddev()) / Math.sqrt(numberOfTrials));
    }

    public static void main(String[] args) {
        // test client (described below)
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);
        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
