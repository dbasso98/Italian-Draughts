package dssc.exam.draughts;

public class Tile {
    private Piece piece = null;
    private Color tileColor = null;

    Tile(Piece piece, Color tileColor) {
        this(tileColor);
        this.piece = piece;
    }

    Tile(Color color) {
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

    public Piece getTilePiece() {
        return piece;
    }

    public Color getTileColor() {
        return tileColor;
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
