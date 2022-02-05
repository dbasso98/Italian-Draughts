package dssc.exam.draughts;

public class Tile {
    public Piece piece = null;
    public Color tileColor = null;

    Tile(Piece piece, Color tileColor) {
        this(tileColor);
        this.piece = piece;
    }
    Tile(Color color){
        this.tileColor = color;
    }

    Tile() {
    }

    boolean isTileEmpty() {
        return piece == null;
    }

    void resetTileToEmpty() {
        this.piece = null;
    }

    void setPieceContainedInTile(Piece piece) {
        this.piece = piece;
    }

    public boolean isTileNotEmpty() {
        return !(isTileEmpty());
    }
}
