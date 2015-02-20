package murachandroidworkplace.tictactoe_android;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    static private final String TAG = "TicTacToe";

    // define variables for the widgets
    private TextView message;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonNewGame;

    // define variables for the game
    private GameOptions gameOption;
    private  Board board;            // the game board
    private GameState currentState; // the current state of the game (of enum GameState)
    private Seed currentPlayer;     // the current player (of enum Seed)

    public static final int ROWS = 3, COLS = 3; // number of rows and columns

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the widgets
        message = (TextView)findViewById(R.id.textView);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        buttonNewGame = (Button)findViewById(R.id.buttonNewGame);

        // set the listeners
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 1 pressed");
              if(checkMove(0,0)) {
                  button1.setText(board.cells[0][0].paint());
                  updateGame(currentPlayer);
              }
            }
        });

        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 2 pressed");
                if(checkMove(0,1)) {
                    button2.setText(board.cells[0][1].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 3 pressed");
                if(checkMove(0,2)) {
                    button3.setText(board.cells[0][2].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 4 pressed");
                if(checkMove(1,0)) {
                    button4.setText(board.cells[1][0].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 5 pressed");
                if(checkMove(1,1)) {
                    button5.setText(board.cells[1][1].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 6 pressed");
                if(checkMove(1,2)) {
                    button6.setText(board.cells[1][2].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 7 pressed");
                if(checkMove(2,0)) {
                    button7.setText(board.cells[2][0].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 8 pressed");
                if(checkMove(2,1)) {
                    button8.setText(board.cells[2][1].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        button9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button 9 pressed");
                if(checkMove(2,2)) {
                    button9.setText(board.cells[2][2].paint());
                    updateGame(currentPlayer);
                }
            }
        });

        buttonNewGame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Button New Game pressed");
                clearGuiButtons();
                message.setTextColor(Color.WHITE);
                initGame();

                if(gameOption == GameOptions.HUMANvsHUMAN || gameOption == GameOptions.HUMANvsCOMPUTER)
                    playerMove(currentPlayer); // update the content, currentRow and currentCol
                if(gameOption == GameOptions.COMPUTERvsHUMAN /*|| gameOption == GameOptions.HUMANvsCOMPUTER */ || gameOption == GameOptions.COMPUTERvsCOMPUTER)
                    computerMove();
                if(gameOption == GameOptions.COMPUTERvsCOMPUTER){

                    while(currentState == GameState.PLAYING){
                        computerMove();
                        updateGame(currentPlayer);
                    }
                }
            }
        });

        gameOption = GameOptions.HUMANvsHUMAN;
        board = new Board();// allocate game-board

        // Initialize the game-board and current status
      //  initGame();
    }

    private void clearGuiButtons() {
        button1.setText("");
        button2.setText("");
        button3.setText("");
        button4.setText("");
        button5.setText("");
        button6.setText("");
        button7.setText("");
        button8.setText("");
        button9.setText("");
    }

    private void updateGame(Seed currentPlayer) {
        if (board.hasWon(currentPlayer)) {  // check for win
            currentState = (currentPlayer == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (board.isDraw()) {  // check for draw
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).

        // Switch player
        else
        {this.currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;}

        if (currentState == GameState.CROSS_WON) {

            message.setTextColor(Color.RED);
            message.setText("'X' won!");
        } else if (currentState == GameState.NOUGHT_WON) {
            message.setTextColor(Color.RED);
            message.setText("'O' won!");
        } else if (currentState == GameState.DRAW) {
            message.setTextColor(Color.RED);
            message.setText("It's Draw!");

        }else{
           /* if(gameOption == GameOptions.HUMANvsHUMAN || gameOption == GameOptions.HUMANvsCOMPUTER)
                playerMove(this.currentPlayer);
            if(gameOption == GameOptions.COMPUTERvsHUMAN || gameOption == GameOptions.HUMANvsCOMPUTER )
                computerMove();*/
            if(gameOption == GameOptions.HUMANvsHUMAN){playerMove(this.currentPlayer);}
            else if((gameOption == GameOptions.HUMANvsCOMPUTER && this.currentPlayer == Seed.CROSS) || (gameOption == GameOptions.COMPUTERvsHUMAN && this.currentPlayer == Seed.NOUGHT))
            {playerMove(this.currentPlayer);}else {computerMove();}


        }

    }

    private void computerMove() {
        if(currentState == GameState.PLAYING){
            int row;
            int col;
            Random r = new Random();
            do {

                row = r.nextInt(ROWS);
                col = r.nextInt(COLS);


            }while(board.cells[row][col].content!= Seed.EMPTY);

            // board[row][col] = currentPlayer;

            board.currentRow = row;
            board.currentCol = col;
            board.cells[row][col].content = currentPlayer;
            switch(row){
                case 0: switch(col){
                    case 0: button1.setText(board.cells[row][col].paint()); break;
                    case 1: button2.setText(board.cells[row][col].paint());  break;
                    case 2: button3.setText(board.cells[row][col].paint());}  break;
                case 1:switch(col){
                    case 0: button4.setText(board.cells[row][col].paint());   break;
                    case 1: button5.setText(board.cells[row][col].paint());    break;
                    case 2: button6.setText(board.cells[row][col].paint());}    break;
                case 2:switch(col){
                    case 0: button7.setText(board.cells[row][col].paint());  break;
                    case 1: button8.setText(board.cells[row][col].paint());  break;
                    case 2: button9.setText(board.cells[row][col].paint());}  break;

            }

         /*   if (board.hasWon(currentPlayer)) {  // check for win
                currentState = (currentPlayer == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
            } else if (board.isDraw()) {  // check for draw
                currentState = GameState.DRAW;
            }
            // Otherwise, no change to current state (still GameState.PLAYING).

            // Switch player
            else
            {this.currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;}

            if (currentState == GameState.CROSS_WON) {
                message.setText("'X' won! Press 'New Game' to play again");
            } else if (currentState == GameState.NOUGHT_WON) {
                message.setText("'O' won!");
            } else if (currentState == GameState.DRAW) {
                message.setText("It's Draw! Bye!");

            }*/ updateGame(currentPlayer);



        }



    }

    private void playerMove(Seed currentPlayer) {
        if (currentPlayer == Seed.CROSS && currentState == GameState.PLAYING) {
          //  System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
            message.setText("Player 'X', enter your move ");
        } else if(currentPlayer == Seed.NOUGHT && currentState == GameState.PLAYING){
         //   System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
            message.setText("Player 'O', enter your move ");
        }
    }

    //check whether the move is valid
    private boolean checkMove(int row, int col) {
        boolean ret = false;
        if(board.cells[row][col].content == Seed.EMPTY && currentState == GameState.PLAYING){
            board.currentRow = row;
            board.currentCol = col;
            board.cells[row][col].content = currentPlayer;
            ret = true;
        }

        return ret;
    }


    private void initGame() {
        board.init();  // clear the board contents
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
