package dssc.exam.draughts;

public class Tile {
    Piece piece = null;
    Color tileColor = null;

    Tile(Piece piece, Color tileColor) {
        this(tileColor);
        this.piece = piece;
    }

    Tile(Color color) {
        this.tileColor = color;
    }

    Tile() {
    }

    // can simplify to return piece since if null then 0 and 1 otherwise?
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
