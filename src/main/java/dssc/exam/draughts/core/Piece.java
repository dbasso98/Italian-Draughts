package dssc.exam.draughts.core;

import dssc.exam.draughts.display.DisplayPiece;
import dssc.exam.draughts.utilities.Color;

public class Piece {
    private final Color pieceColor;
    private boolean isKing;

    public Piece(Color pieceColor) {
        this.pieceColor = pieceColor;
        this.isKing = false;
    }

    public void upgradeToKing() {
        this.isKing = true;
    }

    public boolean isKing() {
        return this.isKing;
    }

    public Color getColor() {
        return this.pieceColor;
    }


    public void display() {
        new DisplayPiece().display(this);
    }
}
