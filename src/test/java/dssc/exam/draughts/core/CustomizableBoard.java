package dssc.exam.draughts.core;

import dssc.exam.draughts.utilities.Color;

import java.util.List;

public class CustomizableBoard extends Board {

    public void setManAtTile(int x, int y, Color color) {
        getTile(x, y).setPiece(new Piece(color));
    }

    public void setKingAtTile(int x, int y, Color color) {
        setManAtTile(x, y, color);
        getPieceAtTile(x, y).upgradeToKing();
    }

    public CustomizableBoard popPiecesAt(List<Integer> indexesToPop) {
        for (Integer index : indexesToPop) {
            getTile(index).popPiece();
        }
        return this;
    }

    public CustomizableBoard setMultipleManAt(List<Integer> indexesOfPieces, Color color) {
        for (Integer index : indexesOfPieces) {
            getTile(index).setPiece(new Piece(color));
        }
        return this;
    }

    public CustomizableBoard upgradeToKing(List<Integer> indexesOfPieces) {
        for (Integer index : indexesOfPieces) {
            getTile(index).getPiece().upgradeToKing();
        }
        return this;
    }
}
