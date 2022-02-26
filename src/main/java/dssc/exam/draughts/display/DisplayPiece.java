package dssc.exam.draughts.display;

import dssc.exam.draughts.Color;
import dssc.exam.draughts.Piece;

public class DisplayPiece implements Display<Piece> {

    @Override
    public void display(Piece piece) {
        if(piece.isKing())
            kingRepresentation(piece);
        else
            manRepresentation(piece);
    }

    private void kingRepresentation(Piece piece) {
        if (piece.getColor() == Color.BLACK)
            System.out.println("[B]");
        else
            System.out.println("[W]");
    }

    private void manRepresentation(Piece piece) {
        if (piece.getColor() == Color.BLACK)
            System.out.println("[b]");
        else
            System.out.println("[w]");
    }
}
