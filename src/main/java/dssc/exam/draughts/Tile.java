package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    private Piece piece = null;
    private final Color tileColor;
    private Point position;

    Tile(Piece piece, Color tileColor, Point position) {
        this(tileColor, position);
        this.piece = piece;
    }

    Tile(Color color, Point position) {
        this.tileColor = color;
        this.position = position;
    }

    boolean isEmpty() {
        return piece == null;
    }

    Piece popPiece() {
        var piece = this.piece;
        this.piece = null;
        return piece;
    }

    void setPiece(Piece piece) {
        this.piece = piece;
    }

    boolean isNotEmpty() {
        return !(isEmpty());
    }

    Piece getPiece() {
        return piece;
    }

    Color getColor() {
        return tileColor;
    }

    public Point getPosition() {
        return position;
    }

    private boolean isBlack() {
        return tileColor == Color.BLACK;
    }

    public int getRow() {
        return position.x;
    }

    public int getColumn() {
        return position.y;
    }

    private String displayEmptyTile() {
        return "[ ]";
    }

    public String display() {
        if (isEmpty()) {
            return displayEmptyTile();
        }
        return piece.display();
    }

    boolean isWhite() {
        return tileColor == Color.WHITE;
    }
}
