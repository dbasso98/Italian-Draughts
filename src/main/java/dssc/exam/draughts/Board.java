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

    private void createOddRow(int row) {
        Color firstColor = Color.BLACK;
        Color secondColor = Color.WHITE;
        createRow(row, firstColor, secondColor);
    }

    private void createEvenRow(int row) {
        Color firstColor = Color.WHITE;
        Color secondColor = Color.BLACK;
        createRow(row, firstColor, secondColor);
    }

    private void createRow(int row, Color firstColor, Color secondColor) {
        for (int column = 0; column < maxColumns; column += 2) {
            board.add(new Tile(firstColor, new Point(row, column)));
            board.add(new Tile(secondColor, new Point(row, column + 1)));
        }
    }

    private void setNewPieceAtIndex(int tileIndex, Color color) {
        Tile tile = board.get(tileIndex);
        tile.setPiece(new Piece(tileIndex, color));
    }

    Board() {
        for (int row = 0; row < maxRows; row += 2) {
            createEvenRow(row);
            createOddRow(row + 1);
        }
        for (int tileIndex = 0; tileIndex < piecesPerPlayer * 2; ++tileIndex) {
            if (isBlackTile(tileIndex)) {
                setNewPieceAtIndex(tileIndex, Color.WHITE);
                setNewPieceAtIndex(lastIndex - tileIndex, Color.BLACK);
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
        ArrayList<Piece> listOfPiece = new ArrayList<>(piecesPerPlayer);
        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (tileContainsPieceOfColor(tileIndex, color))
                listOfPiece.add(getPieceAtTile(tileIndex));
        }
        return listOfPiece;
    }

    private boolean tileContainsPieceOfColor(int tileIndex, Color color) {
        return getTile(tileIndex).isNotEmpty() && getPieceAtTile(tileIndex).getColor() == color;
    }

    ArrayList<Tile> getTilesContainingPieceOfColor(Color color) {
        ArrayList<Tile> listOfTiles = new ArrayList<>(piecesPerPlayer);

        for (int tileIndex = 0; tileIndex < getSize(); ++tileIndex) {
            if (tileContainsPieceOfColor(tileIndex, color))
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
        return getTile(position.x, position.y);
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

    public boolean isBlackTile(int tileIndex) {
        return !getTile(tileIndex).isWhite();
    }

    private void HandleInvalidIndexInDisplay(InvalidIndexException e) {
        System.out.println("ERROR: Unable to print the board: ");
        System.out.println(e.getMessage());
        System.exit(1);
    }

    private void displayInnerPartOfBoard() throws InvalidIndexException {
        for (int row = maxRows - 1; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < maxRows; col++) {
                System.out.print(getTile(row, col).display());
            }
            System.out.println(" " + (row + 1));
        }
    }

    // Extract Responsibility (return a string representation)
    public void display() {
        String indexLine = "   1  2  3  4  5  6  7  8";
        try {
            System.out.println(indexLine);
            displayInnerPartOfBoard();
            System.out.println(indexLine);
        } catch (InvalidIndexException e) {
            HandleInvalidIndexInDisplay(e);
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
