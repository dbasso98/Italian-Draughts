package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board {
    private final ArrayList<Tile> boardArray = new ArrayList<>(BoardSpecifications.boardSize());
    private final int lastIndexOfBoardArray = BoardSpecifications.boardSize() - 1;


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
        for (int column = 0; column < BoardSpecifications.numberOfColumns(); column += 2) {
            boardArray.add(new Tile(firstColor, new Point(row, column)));
            boardArray.add(new Tile(secondColor, new Point(row, column + 1)));
        }
    }

    private void setNewPieceAtIndex(int tileIndex, Color color) {
        Tile tile = boardArray.get(tileIndex);
        tile.setPiece(new Piece(tileIndex, color));
    }

    public Board() {
        for (int row = 0; row < BoardSpecifications.numberOfRows(); row += 2) {
            createEvenRow(row);
            createOddRow(row + 1);
        }
        for (int tileIndex = 0; tileIndex < BoardSpecifications.initialAreaOccupiedByOnePlayer(); ++tileIndex) {
            if (isBlackTile(tileIndex)) {
                setNewPieceAtIndex(tileIndex, Color.WHITE);
                setNewPieceAtIndex(getSymmetricIndexOf(tileIndex), Color.BLACK);
            }
        }
    }

    private int convertRowAndColumnToIndex(int row, int column) {
        return BoardSpecifications.numberOfColumns() * row + column;
    }

    private boolean startOrEndAreInvalid(int startPosition, int endPosition) {
        return isInvalidIndex(startPosition) || isInvalidIndex(endPosition);
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

    private boolean isInvalidIndex(int index) {
        return index < 0 || index > lastIndexOfBoardArray;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && column >= 0 && row <= 7 && column <= 7;
    }

    public boolean isValidPosition(Point position) {
        return isValidPosition(position.x, position.y);
    }

    public boolean isBlackTile(int tileIndex) {
        return getTile(tileIndex).isBlack();
    }

    // possibly useless since BoardSpecifications.boardSize() = 64 but technically that is
    // the capacity of the array?..
    public int getSize() {
        return boardArray.size();
    }

    public int getNumberOfPiecesOfColor(Color color) {
        return getPiecesOfColor(color).size();
    }

    private ArrayList<Piece> getPiecesOfColor(Color color) {
        return new ArrayList<>(getTilesContainingPieceOfColor(color).stream()
                                                                    .map(tile -> tile.getPiece())
                                                                    .collect(Collectors.toList()));
    }

    public ArrayList<Tile> getTilesContainingPieceOfColor(Color color) {
        ArrayList<Tile> listOfTiles = new ArrayList<>(BoardSpecifications.numberOfPiecesPerPlayer());
        for (Tile tile : boardArray) {
            if (tile.containsPieceOfColor(color))
                listOfTiles.add(tile);
        }
        return listOfTiles;
    }

    public int getNumberOfPiecesOnTheBoard() {
        int piecesLeftOnTheBoard = 0;
        for (Tile tile : boardArray) {
            if (tile.isNotEmpty())
                ++piecesLeftOnTheBoard;
        }
        return piecesLeftOnTheBoard;
    }

    public Tile getTile(int index) {
        return boardArray.get(index);
    }

    public Tile getTile(int row, int column) throws InvalidIndexException {
        if (isValidPosition(row, column))
            return getTile(convertRowAndColumnToIndex(row, column));
        else
            throw new InvalidIndexException("Every position must be in range of 1 to 8 for each axis!");
    }

    public Tile getTile(Point position) throws InvalidIndexException {
        return getTile(position.x, position.y);
    }

    public Piece getPieceAtTile(int index) {
        return getTile(index).getPiece();
    }

    public Piece getPieceAtTile(int row, int column) throws Exception {
        return getTile(row, column).getPiece();
    }

    public Tile getSymmetricTile(int tileIndex) {
        return boardArray.get(getSymmetricIndexOf(tileIndex));
    }

    private int getSymmetricIndexOf(int index) {
        return lastIndexOfBoardArray - index;
    }

    private int getMiddlePosition(int startPosition, int endPosition) throws Exception {
        int distance = Math.abs(startPosition - endPosition);

        HandleInvalidPositions(startPosition, endPosition);
        HandleInvalidDistance(distance);

        return Math.min(startPosition, endPosition) + distance / 2;
    }

    int getMiddlePosition(Point source, Point destination) throws Exception {
        return getMiddlePosition(convertRowAndColumnToIndex(source.x, source.y), convertRowAndColumnToIndex(destination.x,destination.y));
    }

    public Color getColorOfPieceAtTile(int index) {
        return getPieceAtTile(index).getColor();
    }

    public Color getColorOfPieceAtTile(Point position) throws Exception {
        return getPieceAtTile(position.x, position.y).getColor();
    }

    public Tile getTileInDiagonalOffset(Tile tile, int offset1, int offset2) {
        try {
            return getTile(tile.getRow() + offset1, tile.getColumn() + offset2);
        } catch (Exception e) {
            return new Tile(tile.getColor(), new Point(-1, -1));
        }
    }

    // three methods below must be moved to a displayBoard Class of some sort.
    private void handleInvalidIndexInDisplay(InvalidIndexException e) {
        System.out.println("ERROR: Unable to print the board: ");
        System.out.println(e.getMessage());
        System.exit(1);
    }

    private void displayInnerPartOfBoard() throws InvalidIndexException {
        for (int row = BoardSpecifications.numberOfRows() - 1; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < BoardSpecifications.numberOfRows(); col++) {
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
            handleInvalidIndexInDisplay(e);
        }
    }


}

