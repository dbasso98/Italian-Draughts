package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.Color;
import dssc.exam.draughts.Piece;

public class StringRepresentationOfPiece implements RepresentationOfPiece {

    private final String pieceRepresentation;

    StringRepresentationOfPiece(String representation) {
        this.pieceRepresentation = representation;
    }

    public String getPieceRepresentation() {
        return pieceRepresentation;
    }

    private static StringRepresentationOfPiece kingRepresentation(Piece piece) {
        if (piece.getColor() == Color.BLACK)
            return new StringRepresentationOfPiece("[B]");
        else
            return new StringRepresentationOfPiece("[W]");
    }

    private static StringRepresentationOfPiece manRepresentation(Piece piece) {
        if (piece.getColor() == Color.BLACK)
            return new StringRepresentationOfPiece("[b]");
        else
            return new StringRepresentationOfPiece("[w]");
    }

    public static StringRepresentationOfPiece representation(Piece piece) {
        if (piece.isKing()) {
            return kingRepresentation(piece);
        } else
            return manRepresentation(piece);
    }
}
