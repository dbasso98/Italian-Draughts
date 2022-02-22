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
            if (getTile(tileIndex).isBlack()) {
                setNewPieceAtIndex(tileIndex, Color.WHITE);
                setNewPieceAtIndex(getSymmetricIndexOf(tileIndex), Color.BLACK);
            }
        }
    }

    private int convertRowColumnToIndex(int row, int column) {
        return BoardSpecifications.numberOfColumns() * row + column;
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && column >= 0 && row <= 7 && column <= 7;
    }

    public boolean isValidPosition(Point position) {
        return isValidPosition(position.x, position.y);
    }

    private int getMiddleIndex(int startIndex, int endIndex) {
        int distance = Math.abs(startIndex - endIndex);
        return Math.min(startIndex, endIndex) + distance / 2;
    }

    int getMiddlePosition(Point source, Point destination) throws InvalidIndexException {
        if(!isValidPosition(source) && !isValidPosition(destination))
            throw new InvalidIndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        return getMiddleIndex(convertRowColumnToIndex(source.x, source.y), convertRowColumnToIndex(destination.x, destination.y));
    }

    // possibly useless since BoardSpecifications.boardSize() = 64 but technically that is
    // the capacity of the array?..
    public int getSize() {
        return boardArray.size();
    }

    public int getNumberOfPiecesOfColor(Color color) { // serve
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

    public int getNumberOfPiecesOnTheBoard() { // non serve -> metti in test
        int piecesLeftOnTheBoard = 0;
        for (Tile tile : boardArray) {
            if (tile.isNotEmpty())
                ++piecesLeftOnTheBoard;
        }
        return piecesLeftOnTheBoard;
    }


    public Tile getTile(int index) { // dovrebbe andar bene
        return boardArray.get(index);
    }

    public Tile getTile(int row, int column) {
        return getTile(convertRowColumnToIndex(row, column));
    }

    public Tile getTile(Point position) {
        return getTile(position.x, position.y);
    }

    public Piece getPieceAtTile(int index) { // eliminate method and put chain inside the test where its called
        return getTile(index).getPiece();
    }

    public Piece getPieceAtTile(int row, int column) {
        return getTile(row, column).getPiece();
    }

    public Tile getSymmetricTile(int tileIndex) {
        return boardArray.get(getSymmetricIndexOf(tileIndex));
    }

    private int getSymmetricIndexOf(int index) {
        return lastIndexOfBoardArray - index;
    }

    public Color getColorOfPieceAtTile(int index) {
        return getPieceAtTile(index).getColor();
    }

    public Color getColorOfPieceAtTile(Point position) {
        return getPieceAtTile(position.x, position.y).getColor();
    }

    public Tile getTileInDiagonalOffset(Tile tile, int offset1, int offset2) {
        if (tile!=null && isValidPosition(tile.getRow() + offset1, tile.getColumn() + offset2))
            return getTile(tile.getRow() + offset1, tile.getColumn() + offset2);
        else
            return null;
    }
}

