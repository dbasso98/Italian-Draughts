package dssc.exam.draughts;

import dssc.exam.draughts.IOInterfaces.StringRepresentationOfPiece;

public class Piece {
    private final Color pieceColor;
    private boolean isKing;
    private StringRepresentationOfPiece representation;

    public Piece(Color pieceColor) {
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


    public String display() {
        return representation.getPieceRepresentation();
    }
}
