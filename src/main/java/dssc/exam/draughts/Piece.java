package dssc.exam.draughts;

public class Piece {
    private final int id;
    private final Color pieceColor;
    private boolean isKing;
    private StringRepresentationOfPiece representation;

    public Piece(int id, Color pieceColor) {
        this.id = id;
        this.pieceColor = pieceColor;
        this.isKing = false;
        this.representation = StringRepresentationOfPiece.representation(this);
    }

    public void upgradeToKing() {
        this.isKing = true;
        updateRepresentation();
    }

    private void updateRepresentation() {
        this.representation = StringRepresentationOfPiece.representation(this);

    }

    public boolean isKing() {
        return this.isKing;
    }

    public Color getColor() {
        return this.pieceColor;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", color=" + pieceColor +
                ", isKing=" + isKing +
                '}';
    }

    public String display() {
        return representation.getPieceRepresentation();
    }
}
