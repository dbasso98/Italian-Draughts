package dssc.exam.draughts;
import java.util.ArrayList;

public class Board {
    public final int size = 64;
    public final int maxRows = 8;
    public final int maxColumns = 8;
    public final int piecesPerPlayer = 12;
    public ArrayList<Tile> board = new ArrayList<>(size);

    Board() {
        Color firstColor;
        Color secondColor;
        for (int row = 0; row < maxRows; row ++) {
            if(row%2 == 0){
                firstColor = Color.BLACK;
                secondColor = Color.WHITE;
            }
            else{
                firstColor = Color.WHITE;
                secondColor = Color.BLACK;
            }
            for (int column = 0; column < maxColumns; column += 2) {
                board.add(new Tile(firstColor));
                board.add(new Tile(secondColor));

            }
        }
        for (int i = 0; i < piecesPerPlayer * 2; ++i) {
            if (board.get(i).tileColor == Color.BLACK) {
                board.get(i).setPieceContainedInTile(new Piece(i, Color.BLACK));
                board.get(size - 1 - i).setPieceContainedInTile(new Piece(size - 1 - i, Color.WHITE));
            }
        }
    }

    private int getIndex(int row, int column) {
        return 8 * row + column;
    }

    public int getSizeOfBoard() {
        return board.size();
    }

    public int getPiecesOfColor(Color color) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).isTileNotEmpty() && board.get(i).getPiece().pieceColor == color)
                sum += 1;
        }
        return sum;
    }

    public int getTotalNumberOfPieces() {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if(board.get(i).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }

    public Tile getTile(int position) {
        return board.get(position);
    }
}
