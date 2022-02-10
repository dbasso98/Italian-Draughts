package dssc.exam.draughts;

import java.awt.*;

class Tile {
    private Piece piece = null;
    private final Color tileColor;
    private Point position = null;

    Tile(Piece piece, Color tileColor, Point position) {
        this(tileColor, position);
        this.piece = piece;
    }

    Tile(Color color, Point position) {
        this.tileColor = color;
        this.position = position;
    }

    boolean isTileEmpty() {
        return piece == null;
    }

    Piece popPieceContainedInTile() {
        var piece = this.piece;
        this.piece = null;
        return piece;
    }

    void setPieceContainedInTile(Piece piece) {
        this.piece = piece;
    }

    boolean isTileNotEmpty() {
        return !(isTileEmpty());
    }

    Piece getTilePiece() {
        return piece;
    }

    Color getTileColor() {
        return tileColor;
    }

    public Point getTilePosition(){
        return position;
    }

    private boolean isBlack() {
        return tileColor == Color.BLACK;
    }

    public int getTileRow() {
        return position.x;
    }

    public int getTileColumn() {
        return position.y;
    }



    private String displayEmptyTile() {
        return "[ ]";
    }

    public String display() {
        if (isTileEmpty()) {
            return displayEmptyTile();
        }
        return piece.display();
    }
}
