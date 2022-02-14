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
                board.add(new Tile(firstColor, new Point(row, column)));
                board.add(new Tile(secondColor, new Point(row, column + 1)));
            }
        }
        for (int tileIndex = 0; tileIndex < piecesPerPlayer * 2; ++tileIndex) {
            if (board.get(tileIndex).getColor() == Color.BLACK) {
                board.get(tileIndex).setPiece(new Piece(tileIndex, Color.WHITE));
                board.get(lastIndex - tileIndex).setPiece(new Piece(lastIndex - tileIndex, Color.BLACK));
            }
        }
    }

    public int getIndex(int row, int column) {
        return 8 * row + column;
    }

    public int getSize() {
        return board.size();
    }

    public int getNumberOfPiecesOfColor(Color color) {
        return getPiecesOfColor(color).size();
    }

    public ArrayList<Piece> getPiecesOfColor(Color color) {
        ArrayList<Piece> listOfPiece = new ArrayList<>(12);
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (getTile(tileIndex).isNotEmpty() && getPieceAtTile(tileIndex).getColor() == color)
                listOfPiece.add(getPieceAtTile(tileIndex));
        }
        return listOfPiece;
    }

    public ArrayList<Tile> getTilesContainingPieceOfColor(Color color) {
        ArrayList<Tile> listOfTiles = new ArrayList<>(12);
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (getTile(tileIndex).isNotEmpty() && getPieceAtTile(tileIndex).getColor() == color)
                listOfTiles.add(getTile(tileIndex));
        }
        return listOfTiles;
    }

    public int getNumberOfPiecesOnTheBoard() {
        int sum = 0;
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (board.get(tileIndex).isNotEmpty())
                sum += 1;
        }
        return sum;
    }

    public Tile getTile(int index) {
        return board.get(index);
    }

    public Tile getTile(int row, int column) throws InvalidIndexException {
        if (isValidPosition(row, column))
            return getTile(getIndex(row, column));
        else
            throw new InvalidIndexException("Every position must be in range of 1 to 8 for each axis!");
    }

    public Tile getTile(Point position) throws InvalidIndexException {
        try {
            return getTile(position.x, position.y);
        } catch (Exception e) {
            throw e;
        }
    }

    public Piece getPieceAtTile(int index) {
        return getTile(index).getPiece();
    }

    public Piece getPieceAtTile(int row, int column) throws Exception {
        return getTile(row, column).getPiece();
    }

    public Piece getPieceAtTile(Point position) throws Exception {
        return getTile(position).getPiece();
    }

    public Tile getSymmetricTile(int index) {
        return board.get(lastIndex - index);
    }

    public Tile getSymmetricTile(int row, int column) {
        return getSymmetricTile(getIndex(row, column));
    }

    public Tile getSymmetricTile(Point position) {
        return getSymmetricTile(position.x, position.y);
    }

    int getMiddlePosition(int startPosition, int endPosition) {
        int distance = Math.abs(startPosition - endPosition);
        if (!isValidPosition(startPosition) || !isValidPosition(endPosition)) {
            return -1;
        }
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

    public boolean isValidPosition(int row, int column) {
        return row >= 0 && column >= 0 && row <= 7 && column <= 7;
    }

    public boolean isValidPosition(Point position) {
        return isValidPosition(position.x, position.y);
    }

    public boolean isBlackTile(Tile tile) throws WhiteTileException {
        if (tile.getColor() == Color.WHITE)
            throw new WhiteTileException("Cannot play on white tiles, only black ones, please change position!");
        return true;
    }

    public void display() {
        String indexLine = "   1  2  3  4  5  6  7  8";
        System.out.println(indexLine);
        try {
            for (int row = maxRows - 1; row >= 0; row--) {
                System.out.print((row + 1) + " ");
                for (int col = 0; col < maxRows; col++) {
                    System.out.print(getTile(row, col).display());
                }
                System.out.println(" " + (row + 1));
            }
            System.out.println(indexLine);
        } catch (InvalidIndexException e) {
            System.out.println("ERROR: Unable to print the board: ");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public Color getColorOfPieceAtTile(int index) {
        return getPieceAtTile(index).getColor();
    }

    public Color getColorOfPieceAtTile(int row, int column) throws Exception {
        return getPieceAtTile(row, column).getColor();
    }

    public Color getColorOfPieceAtTile(Point position) throws Exception {
        return getPieceAtTile(position).getColor();
    }

    public Tile getTileInDiagonalOffset(Tile tile, int offset1, int offset2) {
        try {
            return getTile(tile.getRow() + offset1, tile.getColumn() + offset2);
        } catch (Exception e) {
            return new Tile(tile.getColor(), new Point(-1, -1));
        }
    }
}
