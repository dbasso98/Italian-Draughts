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
            System.out.print("[B]");
        else
            System.out.print("[W]");
    }

    private void manRepresentation(Piece piece) {
        if (piece.getColor() == Color.BLACK)
            System.out.print("[b]");
        else
            System.out.print("[w]");
    }
}
