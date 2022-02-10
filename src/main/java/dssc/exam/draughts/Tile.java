package dssc.exam.draughts;

class Tile {
    private Piece piece = null;
    private final Color tileColor;

    Tile(Piece piece, Color tileColor) {
        this(tileColor);
        this.piece = piece;
    }

    Tile(Color color) {
        this.tileColor = color;
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
