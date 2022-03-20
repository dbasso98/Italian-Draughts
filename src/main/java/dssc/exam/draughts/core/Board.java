package dssc.exam.draughts.core;

import dssc.exam.draughts.exceptions.*;
import dssc.exam.draughts.utilities.BoardSpecifications;
import dssc.exam.draughts.utilities.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Board {
    private final ArrayList<Tile> boardArray = new ArrayList<>(BoardSpecifications.boardSize());
    private final int lastIndexOfBoardArray = BoardSpecifications.boardSize() - 1;

    public Board() {
        initializeEmptyBoard();
        for (int tileIndex = 0; tileIndex < BoardSpecifications.initialAreaOccupiedByOnePlayer(); ++tileIndex) {
            if (getTile(tileIndex).isBlack()) {
                setNewPieceAtIndex(tileIndex, Color.WHITE);
                setNewPieceAtIndex(getSymmetricIndexOf(tileIndex), Color.BLACK);
            }
        }
    }

    private void initializeEmptyBoard() {
        for (int row = 0; row < BoardSpecifications.numberOfRows(); row += 2) {
            createEvenRow(row);
            createOddRow(row + 1);
        }
    }

    private void createOddRow(int row) {
        createRow(row, Color.BLACK, Color.WHITE);
    }

    private void createEvenRow(int row) {
        createRow(row, Color.WHITE, Color.BLACK);
    }

    private void createRow(int row, Color firstColor, Color secondColor) {
        for (int column = 0; column < BoardSpecifications.numberOfColumns(); column += 2) {
            boardArray.add(new Tile(firstColor, new Point(row, column)));
            boardArray.add(new Tile(secondColor, new Point(row, column + 1)));
        }
    }

    private void setNewPieceAtIndex(int tileIndex, Color color) {
        Tile tile = boardArray.get(tileIndex);
        tile.setPiece(new Piece(color));
    }

    private int convertRowColumnToIndex(int row, int column) {
        return BoardSpecifications.numberOfColumns() * row + column;
    }

    public boolean isPositionInsideTheBoard(Point position) {
        return isPositionInsideTheBoard(position.x, position.y);
    }

    private boolean isPositionInsideTheBoard(int row, int column) {
        return isColumnInsideTheBoard(column) && isRowInsideTheBoard(row);
    }

    private boolean isColumnInsideTheBoard(int column) {
        return column >= 0 && column <= BoardSpecifications.numberOfColumns();
    }

    private boolean isRowInsideTheBoard(int row) {
        return row >= 0 && row < BoardSpecifications.numberOfRows();
    }

    public int getMiddlePosition(Point source, Point destination) throws IndexException {
        if (!isPositionInsideTheBoard(source) && !isPositionInsideTheBoard(destination))
            throw new IndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        return getMiddleIndex(convertRowColumnToIndex(source.x, source.y), convertRowColumnToIndex(destination.x, destination.y));
    }

    private int getMiddleIndex(int startIndex, int endIndex) {
        int distance = Math.abs(startIndex - endIndex);
        return Math.min(startIndex, endIndex) + distance / 2;
    }

    public int getNumberOfPiecesOfColor(Color color) {
        return new ArrayList<>(getTilesContainingPieceOfColor(color).stream()
                .map(Tile::getPiece)
                .collect(Collectors.toList())).size();
    }

    public ArrayList<Tile> getTilesContainingPieceOfColor(Color color) {
        return boardArray.stream()
                .filter(tile -> tile.containsPieceOfColor(color)).collect(Collectors.toCollection(ArrayList::new));
    }

    public int getSize() {
        return boardArray.size();
    }

    public Tile getTile(int index) {
        return boardArray.get(index);
    }

    public Tile getTile(int row, int column) {
        return getTile(convertRowColumnToIndex(row, column));
    }

    public Tile getTile(Point position) {
        return getTile(position.x, position.y);
    }

    public Piece getPieceAtTile(int row, int column) {
        return getTile(row, column).getPiece();
    }

    public Color getColorOfPieceAtTile(Point position) {
        return getPieceAtTile(position.x, position.y).getColor();
    }

    public Tile getTileInDiagonalOffset(Tile tile, Point offset) {
        if (tile != null && isPositionInsideTheBoard(tile.getRow() + offset.x, tile.getColumn() + offset.y))
            return getTile(tile.getRow() + offset.x, tile.getColumn() + offset.y);
        return null;
    }

    private int getSymmetricIndexOf(int index) {
        return lastIndexOfBoardArray - index;
    }
}