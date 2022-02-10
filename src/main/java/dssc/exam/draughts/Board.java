package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
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
            if (row % 2 != 0) {
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
                board.get(tileIndex).setPieceContainedInTile(new Piece(tileIndex, Color.WHITE));
                board.get(lastIndex - tileIndex).setPieceContainedInTile(new Piece(lastIndex - tileIndex, Color.BLACK));
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
        for (int tileIndex = 0; tileIndex < getSizeOfBoard(); ++tileIndex) {
            if (board.get(tileIndex).isTileNotEmpty() && board.get(tileIndex).getTilePiece().getColorOfPiece() == color)
                sum += 1;
        }
        return sum;
    }

    public int getTotalNumberOfPieces() {
        int sum = 0;
        for (int tileIndex = 0; tileIndex < getSizeOfBoard(); ++tileIndex) {
            if (board.get(tileIndex).isTileNotEmpty())
                sum += 1;
        }
        return sum;
    }

    public Tile getTile(int index) {
        return board.get(index);
    }

    public Tile getTile(int row, int column) {
        return getTile(getIndex(row, column));
    }

    public Tile getTile(Point position) {
        return getTile(position.x, position.y);
    }

    public Piece getPieceAtTile(int index) {
        return getTile(index).getTilePiece();
    }

    public Piece getPieceAtTile(int row, int column) {
        return getTile(row, column).getTilePiece();
    }

    public Piece getPieceAtTile(Point position) {
        return getTile(position).getTilePiece();
    }

    public Tile getSymmetricTile(int index) {
        return board.get(lastIndex - index);
    }

    public Tile getSymmetricTile(int row, int column) {
        return getSymmetricTile(getIndex(row,column));
    }

    public Tile getSymmetricTile(Point position) {
        return getSymmetricTile(position.x, position.y);
    }

    int getMiddlePosition(int startPosition, int endPosition) {
        int distance = Math.abs(startPosition - endPosition);
        if (!isValidPosition(startPosition) || !isValidPosition(endPosition)) {
            return -1;
        }
        //if (getTile(startPosition).getTileColor() == Color.WHITE ||
        //      getTile(endPosition).getTileColor() == Color.WHITE)
        // return -1;
        if (distance != 14 && distance != 18) {
            return -1;
        }
        return Math.min(startPosition, endPosition) + distance / 2;
    }

    public int getMiddlePosition(Point source, Point destination) {
        return getMiddlePosition(getIndex(source.x, source.y), getIndex(destination.x, destination.y));
    }

    private boolean isValidPosition(int position) {
        return position >= 0 && position <= lastIndex;
    }

    public boolean isValidPosition(int row, int column) throws InvalidIndexException {
        if(row <0 || column < 0 || row > 7 || column > 7)
            throw new InvalidIndexException("Every position must be in range of 1 to 8 for each axis!");
        return true;
    }

    public boolean isValidPosition(Point position) throws InvalidIndexException{
        return isValidPosition(position.x, position.y);
    }

    public boolean isBlackTile(Tile tile) throws WhiteTileException{
        if (tile.getTileColor() == Color.WHITE)
            throw new WhiteTileException("Cannot play on white tiles, only black ones, please change position!");
        return true;
    }

    public void display() {
        String indexLine = "   1  2  3  4  5  6  7  8";
        System.out.println(indexLine);
        for (int row = maxRows - 1; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < maxRows; col++) {
                System.out.print(getTile(row, col).display());
            }
            System.out.println(" " + (row + 1));
        }
        System.out.println(indexLine);
    }
}
