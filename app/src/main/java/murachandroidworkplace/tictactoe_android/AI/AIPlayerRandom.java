package murachandroidworkplace.tictactoe_android.AI;

import java.util.Random;

import murachandroidworkplace.tictactoe_android.Board;
import murachandroidworkplace.tictactoe_android.Seed;

/**
 * Created by nikos on 21/2/2015.
 */
public class AIPlayerRandom extends AIPlayer {
    public AIPlayerRandom(Board board) {
        super(board);
    }

    public int[] move() {

        int [] ret = new int[2];
        int row;
        int col;
        Random r = new Random();
        do {

            row = r.nextInt(ROWS);
            col = r.nextInt(COLS);


        }while(cells[row][col].content!= Seed.EMPTY);
        ret[0] = row;
        ret[1] = col;
        return ret;
    }
}
