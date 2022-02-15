package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;

public class Board {
    private final int size = 64;
    private final int lastIndex = size - 1;
    final int maxRows = 8;
    final int maxColumns = 8;
    final int piecesPerPlayer = 12;
    private final ArrayList<Tile> board = new ArrayList<>(size);

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

    private int getIndex(int row, int column) {
        return 8 * row + column;
    }

    private int getIndex(Point position) {
        return getIndex(position.x, position.y);
    }

    int getSize() {
        return board.size();
    }

    int getNumberOfPiecesOfColor(Color color) {
        return getPiecesOfColor(color).size();
    }

    private ArrayList<Piece> getPiecesOfColor(Color color) {
        ArrayList<Piece> listOfPiece = new ArrayList<>(12);
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (getTile(tileIndex).isNotEmpty() && getPieceAtTile(tileIndex).getColor() == color)
                listOfPiece.add(getPieceAtTile(tileIndex));
        }
        return listOfPiece;
    }

    ArrayList<Tile> getTilesContainingPieceOfColor(Color color) {
        ArrayList<Tile> listOfTiles = new ArrayList<>(12);
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (getTile(tileIndex).isNotEmpty() && getPieceAtTile(tileIndex).getColor() == color)
                listOfTiles.add(getTile(tileIndex));
        }
        return listOfTiles;
    }

    int getNumberOfPiecesOnTheBoard() {
        int sum = 0;
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (board.get(tileIndex).isNotEmpty())
                sum += 1;
        }
        return sum;
    }

    Tile getTile(int index) {
        return board.get(index);
    }

    Tile getTile(int row, int column) throws InvalidIndexException {
        if (isValidPosition(row, column))
            return getTile(getIndex(row, column));
        else
            throw new InvalidIndexException("Every position must be in range of 1 to 8 for each axis!");
    }

    Tile getTile(Point position) throws InvalidIndexException {
        try {
            return getTile(position.x, position.y);
        } catch (Exception e) {
            throw e;
        }
    }

    Piece getPieceAtTile(int index) {
        return getTile(index).getPiece();
    }

    Piece getPieceAtTile(int row, int column) throws Exception {
        return getTile(row, column).getPiece();
    }

    Piece getPieceAtTile(Point position) throws Exception {
        return getTile(position).getPiece();
    }

    Tile getSymmetricTile(int index) {
        return board.get(lastIndex - index);
    }

    Tile getSymmetricTile(int row, int column) {
        return getSymmetricTile(getIndex(row, column));
    }

    Tile getSymmetricTile(Point position) {
        return getSymmetricTile(position.x, position.y);
    }

    private boolean startOrEndAreInvalid(int startPosition, int endPosition) {
        return isInValidPosition(startPosition) || isInValidPosition(endPosition);
    }

    private void HandleInvalidPositions(int startPosition, int endPosition) throws InvalidIndexException {
        if (startOrEndAreInvalid(startPosition, endPosition)) {
            throw new InvalidIndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        }
    }

    private void HandleInvalidDistance(int distance) throws InvalidMoveException {
        if (distance != 14 && distance != 18) {
            throw new InvalidMoveException("Checker can move only by one or two tiles!");
        }
    }

    private int getMiddlePosition(int startPosition, int endPosition) throws Exception {
        int distance = Math.abs(startPosition - endPosition);

        HandleInvalidPositions(startPosition, endPosition);
        HandleInvalidDistance(distance);

        return Math.min(startPosition, endPosition) + distance / 2;
    }

    int getMiddlePosition(Point source, Point destination) throws Exception {
        return getMiddlePosition(getIndex(source), getIndex(destination));
    }

    private boolean isInValidPosition(int position) {
        return position < 0 || position > lastIndex;
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
