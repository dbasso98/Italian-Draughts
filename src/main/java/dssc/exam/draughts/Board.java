package dssc.exam.draughts;

import java.util.ArrayList;

public class Board {
    public final int size = 64;
    public final int lastIndex = size - 1;
    public final int maxRows = 8;
    public final int maxColumns = 8;
    public final int piecesPerPlayer = 12;
    public ArrayList<Tile> board = new ArrayList<>(size);

    Board() {
        Color firstColor;
        Color secondColor;
        for (int row = 0; row < maxRows; row++) {
            if (row % 2 == 0) {
                firstColor = Color.BLACK;
                secondColor = Color.WHITE;
            } else {
                firstColor = Color.WHITE;
                secondColor = Color.BLACK;
            }
            for (int column = 0; column < maxColumns; column += 2) {
                board.add(new Tile(firstColor));
                board.add(new Tile(secondColor));
            }
        }
        for (int tileIndex = 0; tileIndex < piecesPerPlayer * 2; ++tileIndex) {
            if (board.get(tileIndex).getTileColor() == Color.BLACK) {
                board.get(tileIndex).setPieceContainedInTile(new Piece(tileIndex, Color.BLACK));
                board.get(lastIndex - tileIndex).setPieceContainedInTile(new Piece(lastIndex - tileIndex, Color.WHITE));
            }
        }
    }

    public int getIndex(int row, int column) {
        return 8 * row + column;
    }

    public int getSizeOfBoard() {
        return board.size();
    }

    public int getPiecesOfColor(Color color) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).isTileNotEmpty() && board.get(i).getTilePiece().getColorOfPiece() == color)
                sum += 1;
        }
        return sum;
    }

    public int getTotalNumberOfPieces() {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            if (board.get(i).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }

    public Tile getTile(int position) {
        return board.get(position);
    }

    public Tile getSymmetricTile(int position) {
        return board.get(lastIndex - position);
    }

    public int getMiddlePosition(int startPosition, int endPosition) {
        int distance = Math.abs(startPosition-endPosition);
        if (!isValidPosition(startPosition) || !isValidPosition(endPosition)) {
            return -1;
        }
        if (getTile(startPosition).getTileColor() == Color.WHITE ||
            getTile(endPosition).getTileColor() == Color.WHITE)
            return -1;
        if (distance != 14 && distance != 18) {
            return -1;
        }
        return Math.min(startPosition, endPosition) + distance/2;
    }

    public boolean isValidPosition(int position) {
        return position >= 0 && position <= 63;
    }

    public void display() {
        String indexLine = "   1  2  3  4  5  6  7  8";
        System.out.println(indexLine);
        for (int row = 0; row < maxRows; row++) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < maxRows; col++) {
                System.out.print(getTile(getIndex(row, col)).display());
            }
            System.out.print(" " + (row + 1) + "\n");
        }
        System.out.println(indexLine);
    }
}
