package dssc.exam.draughts.core;

import dssc.exam.draughts.utilities.Color;

import java.awt.*;

public class Tile {
    private Piece piece = null;
    private final dssc.exam.draughts.utilities.Color tileColor;
    private final Point position;

    public Tile(dssc.exam.draughts.utilities.Color color, Point position) {
        this.tileColor = color;
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece popPiece() {
        var piece = this.piece;
        this.piece = null;
        return piece;
    }

    public dssc.exam.draughts.utilities.Color getColor() {
        return tileColor;
    }

    public Point getPosition() {
        return position;
    }

    public int getRow() {
        return position.x;
    }

    public int getColumn() {
        return position.y;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isNotEmpty() {
        return !(isEmpty());
    }

    public boolean isBlack() {
        return tileColor == dssc.exam.draughts.utilities.Color.BLACK;
    }

    public boolean isWhite() {
        return tileColor == dssc.exam.draughts.utilities.Color.WHITE;
    }

    public boolean containsPieceOfColor(Color color) {
        return isNotEmpty() && getPiece().getColor() == color;
    }

    public boolean containsAKing() {
        return getPiece().isKing();
    }

    public void display() {
        if (isEmpty()) {
            System.out.print("[ ]");
        }
        else
            piece.display();
    }
}
