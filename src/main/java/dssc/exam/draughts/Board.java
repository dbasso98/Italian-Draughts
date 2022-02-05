package dssc.exam.draughts;

import java.util.ArrayList;

public class Board {
    public final int size = 64;
    public final int maxRows = 8;
    public final int maxColumns = 8;
    public final int piecesPerPlayer = 12;
    public ArrayList<Tile> board = new ArrayList<>(size);

    Board() {
        for (int row = 0; row < maxRows; row += 2) {
            for (int column = 0; column < maxColumns; column += 2) {
                board.set(getIndex(row, column), new Tile(Color.BLACK));
                board.set(getIndex(row, column + 1), new Tile(Color.WHITE));
                board.set(getIndex(row + 1, column), new Tile(Color.WHITE));
                board.set(getIndex(row + 1, column + 1), new Tile(Color.BLACK));
            }
        }
        for (int i = 0; i < piecesPerPlayer * 2; ++i) {
            if (board.get(i).tileColor == Color.BLACK) {
                board.get(i).setPieceContainedInTile(new Piece(i, Color.BLACK));
                //relies on test.
                board.get(size - 1 - i).setPieceContainedInTile(new Piece(size - 1 - i, Color.WHITE));
            }
        }
    }

    private int getIndex(int row, int column) {
        return 8 * row + column;
    }

    int getSizeOfBoard() {
        return size;
    }

    public int getPieces(Color color) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).tileColor == color && board.get(i).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }

    public int getNumberPieces() {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }
}
