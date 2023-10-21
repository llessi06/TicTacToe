package net.htlgkr.tictactoe_with_minimax;

import java.io.Serializable;

public class Field implements Serializable {
    public boolean occupied;
    public String symbol;

    public Field(boolean occupied, String symbol) {
        this.occupied = occupied;
        this.symbol = symbol;
    }

    public Field() {
        this.occupied = false;
        this.symbol = " ";
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        if (occupied) {
            return symbol;
        } else {
            return " ";
        }
    }
}
