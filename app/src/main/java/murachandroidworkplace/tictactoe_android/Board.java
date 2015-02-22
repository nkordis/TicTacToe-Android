package murachandroidworkplace.tictactoe_android;

/**
 * Created by nikos on 20/2/2015.
 */
public class Board {

    // Named-constants for the dimensions
    public static final int ROWS = 3;
    public static final int COLS = 3;

    // package access
    public Cell[][] cells;  // a board composes of ROWS-by-COLS Cell instances
    int currentRow, currentCol;  // the current seed's row and column

    /** Constructor to initialize the game board */
    public Board() {
        cells = new Cell[ROWS][COLS];  // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col); // allocate element of the array
            }
        }
    }

    /** Initialize (or re-initialize) the contents of the game board */
    public void init() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].clear();  // clear the cell content
            }
        }
    }

    /** Return true if it is a draw (i.e., no more EMPTY cell) */
    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == Seed.EMPTY) {
                    return false; // an empty seed found, not a draw, exit
                }
            }
        }
        return true; // no empty cell, it's a draw
    }

    /** Return true if the player with "theSeed" has won after placing at
     (currentRow, currentCol) */
    public boolean hasWon(Seed theSeed) {
        boolean ret;
        ret =(cells[currentRow][0].content == theSeed         // 3-in-the-row
                && cells[currentRow][1].content == theSeed
                && cells[currentRow][2].content == theSeed
                || cells[0][currentCol].content == theSeed      // 3-in-the-column
                && cells[1][currentCol].content == theSeed
                && cells[2][currentCol].content == theSeed
                || currentRow == currentCol            // 3-in-the-diagonal
                && cells[0][0].content == theSeed
                && cells[1][1].content == theSeed
                && cells[2][2].content == theSeed
                || currentRow + currentCol == 2    // 3-in-the-opposite-diagonal
                && cells[0][2].content == theSeed
                && cells[1][1].content == theSeed
                && cells[2][0].content == theSeed);

        return ret;

    }

    public int[] getWinningButtons(Seed theSeed){
        int [] winButtons = new int[3];

        if(cells[currentRow][0].content == theSeed         // 3-in-the-row
           && cells[currentRow][1].content == theSeed
           && cells[currentRow][2].content == theSeed){
            winButtons[0] = 1; winButtons[1] = 2; winButtons[2] = 3;
            switch (currentRow){
                 case 0: winButtons[0] = 1; winButtons[1] = 2; winButtons[2] = 3;break;
                 case 1: winButtons[0] = 4; winButtons[1] = 5; winButtons[2] = 6;break;
                 case 2: winButtons[0] = 7; winButtons[1] = 8; winButtons[2] = 9;break;
             }
        }else if(cells[0][currentCol].content == theSeed      // 3-in-the-column
                && cells[1][currentCol].content == theSeed
                && cells[2][currentCol].content == theSeed){

            switch (currentCol){
                case 0: winButtons[0] = 1; winButtons[1] = 4; winButtons[2] = 7;break;
                case 1: winButtons[0] = 2; winButtons[1] = 5; winButtons[2] = 8;break;
                case 2: winButtons[0] = 3; winButtons[1] = 6; winButtons[2] = 9;break;
            }
        }else if(cells[0][0].content == theSeed){
              winButtons[0] = 1; winButtons[1] = 5; winButtons[2] = 9;
        }else{
              winButtons[0] = 3; winButtons[1] = 5; winButtons[2] = 7;
        }



        return winButtons;
    }

    /** Paint itself */
    public void paint() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint();   // each cell paints itself
                if (col < COLS - 1) System.out.print("|");
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----------");
            }
        }
    }

    public boolean firstMove() {
        boolean ret = true;


        for (int row = 0; row < ROWS; ++row)
            for (int col = 0; col < COLS; ++col)
                if(cells[row][col].content != Seed.EMPTY){
                    ret = false;
                }


        return ret;
    }
}
