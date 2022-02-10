package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    private Piece piece = null;
    private Color tileColor = null;
    private Point position = null;

    Tile(Piece piece, Color tileColor, Point position) {
        this(tileColor, position);
        this.piece = piece;
    }

    Tile(Color color, Point position) {
        this.tileColor = color;
        this.position = position;
    }

    Tile (){
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

    public boolean isTileNotEmpty() {
        return !(isTileEmpty());
    }

    public Piece getTilePiece() {
        return piece;
    }

    public Color getTileColor() {
        return tileColor;
    }

    public Point getTilePosition(){
        return position;
    }

    private boolean isBlack() {
        return tileColor == Color.BLACK;
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
