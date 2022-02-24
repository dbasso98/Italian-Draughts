package dssc.exam.draughts;

import java.util.List;

class CustomizableBoard extends Board {

    void setManAtTile(int x, int y, Color color) {
        getTile(x, y).setPiece(new Piece(0, color));
    }

    void setKingAtTile(int x, int y, Color color) {
        setManAtTile(x, y, color);
        getPieceAtTile(x, y).upgradeToKing();
    }

    void popPiecesAt(List<Integer> indexesToPop) {
        for (Integer index : indexesToPop) {
            getTile(index).popPiece();
        }
    }

    void setMultipleManAt(List<Integer> indexesOfPieces, Color color) {
        for (Integer index : indexesOfPieces) {
            getTile(index).setPiece(new Piece(0, color));
        }
    }

    void upgradeToKing(List<Integer> indexesOfPieces) {
        for (Integer index : indexesOfPieces) {
            getTile(index).getPiece().upgradeToKing();
        }
    }

}
