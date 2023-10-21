package net.htlgkr.tictactoe_with_minimax;

import java.util.HashMap;
import java.util.Objects;

public class TicTacToe {
    HashMap<Integer, Field> fields;
    final String PLAYER_1_SYMBOL = "X";
    final String PLAYER_2_SYMBOL = "O";
    int actualPlayer;

    public TicTacToe() {
        this.fields = new HashMap<>();
        initializeMap();
        actualPlayer = 1;
    }

    public void setActualPlayer(int actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    private void initializeMap() {
        for (int i = 1; i <= 9; i++) {
            fields.put(i, new Field());
        }
    }

    public int getActualPlayer() {
        return actualPlayer;
    }

    public boolean tryToSetField(int fieldNumber) {
        if (!fields.get(fieldNumber).isOccupied()) {
            fields.get(fieldNumber).setOccupied(true);
            if (actualPlayer == 1) {
                fields.get(fieldNumber).setSymbol(PLAYER_1_SYMBOL);
                actualPlayer = 2;
            } else {
                fields.get(fieldNumber).setSymbol(PLAYER_2_SYMBOL);
                actualPlayer = 1;
            }
            return true;
        } else {
            return false;
        }
    }

    public int checkGameStatus() {
        // Überprüfe Reihen
        for (int i = 1; i <= 7; i += 3) {
            if (Objects.equals(fields.get(i).getSymbol(), fields.get(i + 1).getSymbol()) &&
                    Objects.equals(fields.get(i).getSymbol(), fields.get(i + 2).getSymbol()) &&
                    fields.get(i).isOccupied()) {
                if (Objects.equals(fields.get(i).getSymbol(), PLAYER_1_SYMBOL)) {
                    return 1; // Spieler X gewinnt
                } else {
                    return 2; // Spieler O gewinnt
                }
            }
        }

        // Überprüfe Spalten
        for (int i = 1; i <= 3; i++) {
            if (Objects.equals(fields.get(i).getSymbol(), fields.get(i + 3).getSymbol()) &&
                    Objects.equals(fields.get(i).getSymbol(), fields.get(i + 6).getSymbol()) &&
                    fields.get(i).isOccupied()) {
                if (Objects.equals(fields.get(i).getSymbol(), PLAYER_1_SYMBOL)) {
                    return 1; // Spieler X gewinnt
                } else {
                    return 2; // Spieler O gewinnt
                }
            }
        }

        // Überprüfe Diagonalen
        if ((Objects.equals(fields.get(1).getSymbol(), fields.get(5).getSymbol()) &&
                Objects.equals(fields.get(1).getSymbol(), fields.get(9).getSymbol()) &&
                fields.get(1).isOccupied()) ||
                (Objects.equals(fields.get(3).getSymbol(), fields.get(5).getSymbol()) &&
                        Objects.equals(fields.get(3).getSymbol(), fields.get(7).getSymbol()) &&
                        fields.get(3).isOccupied())) {
            if (Objects.equals(fields.get(5).getSymbol(), PLAYER_1_SYMBOL)) {
                return 1; // Spieler X gewinnt
            } else {
                return 2; // Spieler O gewinnt
            }
        }

        // Überprüfe auf Unentschieden
        boolean isDraw = true;
        for (int i = 1; i <= 9; i++) {
            if (!fields.get(i).isOccupied()) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            return 3; // Unentschieden
        }

        return 0; // Spiel läuft noch
    }


    public void resetGame() {
        initializeMap();
        actualPlayer = 1;
    }

    public boolean tryMoveForTesting(int fieldNumber, int player) {
        if (!fields.get(fieldNumber).isOccupied()) {
            fields.get(fieldNumber).setOccupied(true);
            if (player == 1) {
                fields.get(fieldNumber).setSymbol(PLAYER_1_SYMBOL);
            } else {
                fields.get(fieldNumber).setSymbol(PLAYER_2_SYMBOL);
            }
            return true;
        } else {
            return false;
        }
    }

}
