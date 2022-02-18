package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    private Piece piece = null;
    private final Color tileColor;
    private final Point position;

    public Tile(Piece piece, Color tileColor, Point position) {
        this(tileColor, position);
        this.piece = piece;
    }

    public Tile(Color color, Point position) {
        this.tileColor = color;
        this.position = position;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece popPiece() {
        var piece = this.piece;
        this.piece = null;
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isNotEmpty() {
        return !(isEmpty());
    }

    public Piece getPiece() {
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

    public boolean isBlack() {
        return tileColor == Color.BLACK;
    }

    public boolean isWhite() {
        return tileColor == Color.WHITE;
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
}
