import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int[][] gridOfSites;
    private int top = 0;
    private int bottom;
    private int size;
    private int openSitesCount;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.gridOfSites = new int[n][n];
        size = n;
        openSitesCount = 0;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(n*n + 2);
        bottom = n * n + 1;
    }

    private int getWeightedQuickUnionUFIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private boolean checkIsvalid(int row, int col) {
        if (row >= 1 && row <= size && col >= 1 && col <= size)
            return true;
        else
            return false;
    }

    private void checkAndPerformUnion(int row, int col) {

        int ufIndex = getWeightedQuickUnionUFIndex(row, col);

        if (col > 1 && isOpen(row,col-1)) {
            weightedQuickUnionUF.union(ufIndex, getWeightedQuickUnionUFIndex(row,col-1));
        }

        if(col < size && isOpen(row,col + 1) ) {
            weightedQuickUnionUF.union(ufIndex, getWeightedQuickUnionUFIndex(row,col + 1));
        }

        if (row < size && isOpen(row + 1,col)) {
            weightedQuickUnionUF.union(ufIndex, getWeightedQuickUnionUFIndex(row + 1,col));
        }

        if (row > 1 && isOpen(row - 1,col )) {
            weightedQuickUnionUF.union(ufIndex, getWeightedQuickUnionUFIndex(row-1, col));
        }


    }

    public void open(int row, int col) {

        if(!checkIsvalid(row, col)){
         throw new java.lang.IllegalArgumentException();
        }
        else {
            if( this.gridOfSites[row-1][col-1] == 0 ){
                this.gridOfSites[row-1][col-1] = 1;
                openSitesCount = openSitesCount + 1;
            }

            // if row == 1 then perform union between top(0) and this row.
            if(row == 1) {
                // get index value corresponding to row = 1 and col in getWeightedQuickUnionUFIndex
                this.weightedQuickUnionUF.union(getWeightedQuickUnionUFIndex(row,col),top);
            }

            // if row == size then perform union between bottom and this row
            if(row == size) {
                this.weightedQuickUnionUF.union(getWeightedQuickUnionUFIndex(row,col),bottom);
            }

            // check adjacent sites to find whether they are open or not
            // if they are open then perform a union
            checkAndPerformUnion(row, col);

        }
    }

    public boolean isOpen(int row, int col) {
        if(!checkIsvalid(row, col)){
            throw new java.lang.IllegalArgumentException();
        }

        if( this.gridOfSites[row-1][col-1] == 1 ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFull(int row, int col) {

        if(!checkIsvalid(row, col)){
            throw new java.lang.IllegalArgumentException();
        }

        if (row > 0 && row <= size && col > 0 && col <= size) {
            // for each open site at the top row, find if the row,col can be reached from an open site in the top row
            for(int j = 1; j <= size; j++) {
                if (isOpen(1, j)) {
                    return weightedQuickUnionUF.connected(getWeightedQuickUnionUFIndex(1, j), getWeightedQuickUnionUFIndex(row, col));
                }
            }
        } else {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        return false;
    }

    public int numberOfOpenSites() {
        return this.openSitesCount;
    }


    public boolean percolates(){
        return weightedQuickUnionUF.connected(top, bottom);
    }



}