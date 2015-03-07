package murachandroidworkplace.tictactoe_android;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import murachandroidworkplace.tictactoe_android.AI.AIPlayer;
import murachandroidworkplace.tictactoe_android.AI.AIPlayerMinimax;


public class MainActivity extends ActionBarActivity {

    static private final String TAG = "TicTacToe";

    // final constants for the sound files
    private final int WIN = 1;
    private final int DRAW = 2;
    private final int MOVE = 3;
    private final int NEW_GAME = 4;

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
    private  Board board;
    private GameState currentState;
    private Seed currentPlayer;
    private AIPlayer computer;

    public static final int ROWS = 3, COLS = 3; // number of rows and columns

    final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
    MediaPlayer player;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentState = GameState.WAIT;
        board = new Board();// allocate game-board
        board.init();  // clear the board contents
        gameOption = GameOptions.COMPUTERvsHUMAN;

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
                //  button1.startAnimation(animation);
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

                initGame();

                if(gameOption == GameOptions.HUMANvsHUMAN || gameOption == GameOptions.HUMANvsCOMPUTER)
                    playerMove(currentPlayer); // update the content, currentRow and currentCol
                if(gameOption == GameOptions.COMPUTERvsHUMAN || gameOption == GameOptions.COMPUTERvsCOMPUTER){
                    computerMove();
                }
                if(gameOption == GameOptions.COMPUTERvsCOMPUTER){

                    while(currentState == GameState.PLAYING){

                        computerMove();
                        updateGame(currentPlayer);
                    }
                }

            }
        });




    }



    private void clearGuiButtons() {
        button1.clearAnimation(); button1.setText("");
        button2.clearAnimation(); button2.setText("");
        button3.clearAnimation(); button3.setText("");
        button4.clearAnimation(); button4.setText("");
        button5.clearAnimation(); button5.setText("");
        button6.clearAnimation(); button6.setText("");
        button7.clearAnimation(); button7.setText("");
        button8.clearAnimation(); button8.setText("");
        button9.clearAnimation(); button9.setText("");
    }

    private void updateGame(Seed currentPlayer) {
        if (board.hasWon(currentPlayer)) {  // check for win

            currentState = (currentPlayer == Seed.CROSS)? GameState.CROSS_WON:GameState.NOUGHT_WON;
            String winText = (currentPlayer == Seed.CROSS)? "'X' won!":"'O' won!";
            message.setTextColor(getResources().getColor(R.color.goldenGreen));
            message.setText(winText);

            animateWinningButtons();

            playSound(WIN);

        } else if (board.isDraw()) {  // check for draw

            currentState = GameState.DRAW;
            message.setTextColor(getResources().getColor(R.color.goldenGreen));
            message.setText("It's Draw!");
            playSound(DRAW);

         }else {

            playSound(MOVE);
            this.currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

            if (gameOption == GameOptions.HUMANvsHUMAN) {
                playerMove(this.currentPlayer);
            } else if ((gameOption == GameOptions.HUMANvsCOMPUTER && this.currentPlayer == Seed.CROSS)
                    || (gameOption == GameOptions.COMPUTERvsHUMAN && this.currentPlayer == Seed.NOUGHT)) {
                playerMove(this.currentPlayer);
            } else {
                computerMove();
            }
        }
    }

    private void playSound(int soundFile) {
        if(player!=null){player.release();}
        switch (soundFile){
            case 1: player=MediaPlayer.create(MainActivity.this,R.raw.win);break;
            case 2: player=MediaPlayer.create(MainActivity.this,R.raw.draw);break;
            case 3: player=MediaPlayer.create(MainActivity.this,R.raw.move);break;
            case 4: player=MediaPlayer.create(MainActivity.this,R.raw.new_game);break;
        }
        player.start();
    }

    private void animateWinningButtons() {

        animation.setDuration(500); // duration - half a second
        animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        //animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
        animation.setRepeatCount(5); // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in

        int [] winButtons;
         winButtons = board.getWinningButtons(currentPlayer);
        for(int i = 0; i < 3; i++) {
            switch (winButtons[i]) {
                case 1:
                    button1.startAnimation(animation);
                    break;
                case 2:
                    button2.startAnimation(animation);
                    break;
                case 3:
                    button3.startAnimation(animation);
                    break;
                case 4:
                    button4.startAnimation(animation);
                    break;
                case 5:
                    button5.startAnimation(animation);
                    break;
                case 6:
                    button6.startAnimation(animation);
                    break;
                case 7:
                    button7.startAnimation(animation);
                    break;
                case 8:
                    button8.startAnimation(animation);
                    break;
                case 9:
                    button9.startAnimation(animation);
                    break;
            }

        }
    }

    private void computerMove() {
        int row;
        int col;

        if(board.firstMove()){ // if this the first move play it randomly.

            Random r = new Random();
            do {

                row = r.nextInt(ROWS);
                col = r.nextInt(COLS);


            }while(board.cells[row][col].content!= Seed.EMPTY);
        }else{
            computer.setSeed(currentPlayer);
            int [] receive = computer.move();
            row = receive[0];
            col = receive[1];
        }
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

        updateGame(currentPlayer);
    }

    private void playerMove(Seed currentPlayer) {
        if (currentPlayer == Seed.CROSS && currentState == GameState.PLAYING) {
          message.setText("Player 'X', enter your move ");
        } else if(currentPlayer == Seed.NOUGHT && currentState == GameState.PLAYING){
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

        clearGuiButtons();
        board = new Board();// allocate game-board
        board.init();  // clear the board contents
        message.setTextColor(Color.WHITE);
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
       // computer = new AIPlayerRandom(board);
       // computer = new AIPlayerTableLookup(board);
         computer = new AIPlayerMinimax(board);

        playSound(NEW_GAME);

        try {  // sleep for 1 sec in order to play the new_game sound
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

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
