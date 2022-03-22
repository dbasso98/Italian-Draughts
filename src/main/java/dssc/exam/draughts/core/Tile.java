package dssc.exam.draughts.core;

import dssc.exam.draughts.utilities.Color;

import java.awt.*;

public class Tile {
    private Piece piece = null;
    private final Color tileColor;
    private final Point position;

    public Tile(Color color, Point position) {
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

    public Color getColor() {
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
        return tileColor == Color.BLACK;
    }

    public boolean isWhite() {
        return tileColor == Color.WHITE;
    }

    public boolean containsPieceOfColor(Color color) {
        return isNotEmpty() && getPiece().getColor() == color;
    }

    public boolean containsAKing() {
        return getPiece().isKing();
    }
}
