package dssc.exam.draughts;

import java.util.ArrayList;

public class Board {
    public final int size = 64;
    public final int max_rows = 8;
    public final int max_columns = 8;
    public final int pieces_per_player = 12;
    public ArrayList<Tile> board = new ArrayList<>(size);

    Board() {
        for (int row = 0; row < max_rows; row += 2) {
            for (int column = 0; column < max_columns; column += 2) {
                board.set(getIndex(row, column), new Tile(Color.BLACK));
                board.set(getIndex(row, column + 1), new Tile(Color.WHITE));
                board.set(getIndex(row + 1, column), new Tile(Color.WHITE));
                board.set(getIndex(row + 1, column + 1), new Tile(Color.BLACK));
            }
        }
        for (int i = 0; i < pieces_per_player * 2; ++i) {
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

    int get_size_of_board() {
        return size;
    }

    public int get_pieces(Color color) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).tileColor == color && board.get(i).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }

    public int get_number_pieces() {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }
}
