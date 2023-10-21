package net.htlgkr.tictactoe_with_minimax;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TicTacToe ticTacToe;
    HashMap<Integer, Integer> idToFieldNumber;
    HashMap<Integer, Integer> fieldNumberToId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ticTacToe = new TicTacToe();
        initializeMap();
        ((TextView) findViewById(R.id.tvPlayer)).setText("X");
        ((TextView) findViewById(R.id.tvMoves)).setText("9");
    }

    public void initializeMap() {
        idToFieldNumber = new HashMap<>();
        idToFieldNumber.put(R.id.r1p1, 1);
        idToFieldNumber.put(R.id.r1p2, 2);
        idToFieldNumber.put(R.id.r1p3, 3);
        idToFieldNumber.put(R.id.r2p1, 4);
        idToFieldNumber.put(R.id.r2p2, 5);
        idToFieldNumber.put(R.id.r2p3, 6);
        idToFieldNumber.put(R.id.r3p1, 7);
        idToFieldNumber.put(R.id.r3p2, 8);
        idToFieldNumber.put(R.id.r3p3, 9);

        fieldNumberToId = new HashMap<>();
        fieldNumberToId.put(1, R.id.r1p1);
        fieldNumberToId.put(2, R.id.r1p2);
        fieldNumberToId.put(3, R.id.r1p3);
        fieldNumberToId.put(4, R.id.r2p1);
        fieldNumberToId.put(5, R.id.r2p2);
        fieldNumberToId.put(6, R.id.r2p3);
        fieldNumberToId.put(7, R.id.r3p1);
        fieldNumberToId.put(8, R.id.r3p2);
        fieldNumberToId.put(9, R.id.r3p3);
    }


    @Override
    public void onClick(View view) {
        if (idToFieldNumber.containsKey(view.getId())) {
            if (ticTacToe.tryToSetField(idToFieldNumber.get(view.getId()))) {
                ((Button) view).setText(((TextView) findViewById(R.id.tvPlayer)).getText());

                int movesRemaining = Integer.parseInt(((TextView) findViewById(R.id.tvMoves)).getText().toString());

                ((TextView) findViewById(R.id.tvMoves)).setText(String.valueOf(movesRemaining - 1));

                if (ticTacToe.getActualPlayer() == 1) {
                    ((TextView) findViewById(R.id.tvPlayer)).setText("X");
                } else {
                    ((TextView) findViewById(R.id.tvPlayer)).setText("O");
                }
                findBestMove(ticTacToe);

                int gameStatus = ticTacToe.checkGameStatus();

                if (gameStatus == 1) {
                    Toast.makeText(this, "Spieler 1 (X) hat gewonnen", Toast.LENGTH_LONG).show();
                    resetGame();
                } else if (gameStatus == 2) {
                    Toast.makeText(this, "Spieler 2 (O) hat gewonnen", Toast.LENGTH_LONG).show();
                    resetGame();
                } else if (gameStatus == 3) {
                    Toast.makeText(this, "Unentschieden", Toast.LENGTH_LONG).show();
                    resetGame();
                }
            } else {
                Toast.makeText(this, "Feld ist schon besetzt", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void resetGame() {
        ticTacToe.resetGame();
        ((TextView) findViewById(R.id.tvPlayer)).setText("X");
        ((TextView) findViewById(R.id.tvMoves)).setText("9");
        ((Button) findViewById(R.id.r1p1)).setText("");
        ((Button) findViewById(R.id.r1p2)).setText("");
        ((Button) findViewById(R.id.r1p3)).setText("");
        ((Button) findViewById(R.id.r2p1)).setText("");
        ((Button) findViewById(R.id.r2p2)).setText("");
        ((Button) findViewById(R.id.r2p3)).setText("");
        ((Button) findViewById(R.id.r3p1)).setText("");
        ((Button) findViewById(R.id.r3p2)).setText("");
        ((Button) findViewById(R.id.r3p3)).setText("");
    }

    public int minmax(TicTacToe ticTacToe, int depth, boolean isMaximizingPlayer) {
        int winner = ticTacToe.checkGameStatus();

        switch (winner) {
            case 1:
                return -1;
            case 2:
                return 1;
            case 3:
                return 0;
        }

        int bestVal;
        if (isMaximizingPlayer) {
            bestVal = Integer.MIN_VALUE;
            for (int i = 1; i <= 9; i++) {
                if (ticTacToe.fields.get(i).getSymbol().equals(" ")) {
                    ticTacToe.fields.get(i).setSymbol("O");
                    ticTacToe.fields.get(i).setOccupied(true);
                    int value = minmax(ticTacToe, depth + 1, false);
                    bestVal = Math.max(bestVal, value);
                    ticTacToe.fields.get(i).setSymbol(" ");
                    ticTacToe.fields.get(i).setOccupied(false);
                }
            }
        } else {
            bestVal = Integer.MAX_VALUE;
            for (int i = 1; i <= 9; i++) {
                if (ticTacToe.fields.get(i).getSymbol().equals(" ")) {
                    ticTacToe.fields.get(i).setSymbol("O");
                    ticTacToe.fields.get(i).setOccupied(true);
                    int value = minmax(ticTacToe, depth + 1, true);
                    bestVal = Math.min(bestVal, value);
                    ticTacToe.fields.get(i).setSymbol(" ");
                    ticTacToe.fields.get(i).setOccupied(false);
                }
            }
        }
        return bestVal;
    }

    public void findBestMove(TicTacToe ticTacToe) {
        int bestMove = Integer.MIN_VALUE;
        int bestMovePosition = -1;
        for (int i = 1; i <= 9; i++) {
            if (ticTacToe.fields.get(i).getSymbol().equals(" ")) {
                ticTacToe.fields.get(i).setSymbol("O");
                ticTacToe.fields.get(i).setOccupied(true);
                int moveValue = minmax(ticTacToe, 0, false);
                Log.d("minimaxOutput", moveValue + " " + i);
                ticTacToe.fields.get(i).setSymbol(" ");
                ticTacToe.fields.get(i).setOccupied(false);

                if (moveValue > bestMove) {
                    bestMove = moveValue;
                    bestMovePosition = i;
                }
            }
        }
        ticTacToe.fields.get(bestMovePosition).setOccupied(true);
        ticTacToe.fields.get(bestMovePosition).setSymbol("O");
        ((Button) findViewById(fieldNumberToId.get(bestMovePosition))).setText("O");
        int movesRemaining = Integer.parseInt(((TextView) findViewById(R.id.tvMoves)).getText().toString());
        ((TextView) findViewById(R.id.tvMoves)).setText(String.valueOf(movesRemaining - 1));
        ticTacToe.setActualPlayer(1);
        ((TextView) findViewById(R.id.tvPlayer)).setText("X");
    }

}