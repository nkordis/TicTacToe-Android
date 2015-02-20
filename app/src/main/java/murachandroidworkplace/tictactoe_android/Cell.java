package murachandroidworkplace.tictactoe_android;

/**
 * Created by nikos on 20/2/2015.
 */
public class Cell {
    // package access
    public Seed content; // content of this cell of type Seed.
    // take a value of Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT
    int row, col; // row and column of this cell, not used in this program

    /** Constructor to initialize this cell */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();  // clear content
    }

    /** Clear the cell content to EMPTY */
    public void clear() {
        content = Seed.EMPTY;
    }

    /** Paint itself */
    public String paint() {
        String ret = " ";
        switch (content) {
            case CROSS:
                ret = " X "; break;
            case NOUGHT:
                ret = " O "; break;
            case EMPTY:
                ret = "  "; break;
        }
        return ret;
    }

}
