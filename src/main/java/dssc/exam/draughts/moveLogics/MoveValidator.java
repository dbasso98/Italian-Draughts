package dssc.exam.draughts.moveLogics;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.core.Tile;
import dssc.exam.draughts.exceptions.DraughtsException;
import dssc.exam.draughts.exceptions.IndexException;
import dssc.exam.draughts.exceptions.MoveException;
import dssc.exam.draughts.exceptions.TileException;

import java.awt.*;

class MoveValidator {
    private final Board board;
    private final Point source;
    private final Point destination;

    MoveValidator(Board board, Point source, Point destination) {
        this.board = board;
        this.source = source;
        this.destination = destination;
    }

    void throwExceptionIfPositionsAreInvalid() throws DraughtsException {
        if (!(board.isPositionInsideTheBoard(source) || board.isPositionInsideTheBoard(destination)))
            throw new IndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        throwExceptionIfIsWhiteTile(source);
        throwExceptionIfIsWhiteTile(destination);
        throwExceptionIfIsNotADiagonalMove();
        throwExceptionIfIsNotMovingByOneOrTwoTiles();
        throwExceptionIfSourceTileIsEmpty();
        throwExceptionIfDestinationTileIsNonEmpty();
        throwExceptionIfWrongDirection();
    }

     void signalIncorrectMove() throws DraughtsException {
        var middleTile = board.getTile(board.getMiddlePosition(source, destination));
        var sourceTile = board.getTile(source);
        if (middleTile.isEmpty())
            throw new MoveException("Skip move over an empty tile is not accepted!");
        if (middleTile.getPiece().getColor() == sourceTile.getPiece().getColor())
            throw new MoveException("Color of piece to skip cannot be the same as source piece!");
        if(middleTile.containsAKing() && !sourceTile.containsAKing())
            throw new MoveException("A Man cannot skip a King!");
    }

    private void throwExceptionIfIsWhiteTile(Point position) throws TileException {
        if (board.getTile(position).isWhite())
            throw new TileException("Cannot play on white tiles, only black ones, please change position!");
    }

    private void throwExceptionIfWrongDirection() throws MoveException {
        if (board.getPieceAtTile(source.x, source.y).isKing())
            return;

        var movingPieceColor = board.getColorOfPieceAtTile(source);
        var direction = destination.x - source.x;
        if (movingPieceColor.associatedDirection() * direction < 0)
            throw new MoveException("You are moving in the opposite rowOffset!");
    }

    private void throwExceptionIfIsNotADiagonalMove() throws MoveException {
        if (Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y))
            throw new MoveException("Checker can only move diagonally!");
    }

    private void throwExceptionIfIsNotMovingByOneOrTwoTiles() throws MoveException {
        var distance = Math.abs(destination.x - source.x);
        if (distance != 1 && distance != 2)
            throw new MoveException(("Checker can move only by one or two tiles!"));
    }

    private void throwExceptionIfDestinationTileIsNonEmpty() throws TileException {
        Tile destinationTile = board.getTile(destination);
        if (destinationTile.isNotEmpty())
            throw new TileException("Cannot move since tile at (" + (destinationTile.getColumn() + 1)
                    + "," + (destinationTile.getRow() + 1) + ") is not empty");
    }

    private void throwExceptionIfSourceTileIsEmpty() throws TileException {
        Tile sourceTile = board.getTile(source);
        if (sourceTile.isEmpty())
            throw new TileException("Cannot move since tile at (" + (sourceTile.getColumn() + 1)
                    + "," + (sourceTile.getRow() + 1) + ") is empty");
    }

}
