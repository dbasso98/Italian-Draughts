package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.EmptyTileException;
import dssc.exam.draughts.exceptions.NonEmptyTileException;
import dssc.exam.draughts.exceptions.NotDiagonalMoveException;
import dssc.exam.draughts.exceptions.SamePositionException;

import java.awt.*;
import java.util.BitSet;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception{
        try {
            board.isValidPosition(source);
            board.isValidPosition(destination);
            board.isBlackTile(board.getTile(source));
            board.isBlackTile(board.getTile(destination));
            isNotSamePosition(source, destination);
        }
        catch (Exception e) {
            throw e;
        }

        return true;
    }

    static void isDiagonal(Point source, Point destination) throws NotDiagonalMoveException {
        if(Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y)) {
            throw new NotDiagonalMoveException("Checker can only move diagonally!");
        }
    }

    static void isNotSamePosition(Point source, Point destination) throws SamePositionException {
        if (source.x == destination.x && source.y == destination.y) {
            throw new SamePositionException("Source and destination position cannot be the same!");
        }
    }

    static void checkTileNonEmptiness(Point destination, Tile destinationTile) throws NonEmptyTileException {
        if (destinationTile.isTileNotEmpty()) {
            throw new NonEmptyTileException("Cannot move since tile at (" + (destination.y + 1) + "," + (destination.x + 1) + ") is not empty");
        }
    }

    static void checkTileEmptiness(Point source, Tile sourceTile) throws EmptyTileException {
        if (sourceTile.isTileEmpty()) {
            throw new EmptyTileException("Cannot move since tile at (" + (source.y + 1) + "," + (source.x + 1) + ") is empty");
        }
    }

    static BitSet candidateTilesForSkipMove(Board board, Color color) {
        var listOfTiles = board.getTilesContainingPieceOfColor(color);
        var bitSetOfCandidatesTiles = new BitSet(64);
        for (Tile tile : listOfTiles) {
            if (checkAdjacentDiagonal(board, tile))
                bitSetOfCandidatesTiles.set(board.getIndex(tile.getTileRow(), tile.getTileColumn()));
        }
        return bitSetOfCandidatesTiles;
    }

    static boolean checkAdjacentDiagonal(Board board, Tile tile) {
        var pieceColor = tile.getPieceOfTile().getColorOfPiece();

        if (pieceColor == Color.WHITE) {
            var firstUpperLeftDiagonalTile = board.getTileInDiagonalOffset(tile,1, -1);
            var secondUpperLeftDiagonalTile = board.getTileInDiagonalOffset(firstUpperLeftDiagonalTile, 1, -1);

            var firstUpperRightDiagonalTile = board.getTileInDiagonalOffset(tile,1, 1);
            var secondUpperRightDiagonalTile = board.getTileInDiagonalOffset(firstUpperLeftDiagonalTile, 1, 1);

            if((firstUpperLeftDiagonalTile.isTileNotEmpty() &&
                    firstUpperLeftDiagonalTile.getPieceOfTile().getColorOfPiece() == Color.BLACK &&
                        secondUpperLeftDiagonalTile.isTileEmpty())
                    ||
                    (firstUpperRightDiagonalTile.isTileNotEmpty() &&
                            firstUpperRightDiagonalTile.getPieceOfTile().getColorOfPiece() == Color.BLACK &&
                                secondUpperRightDiagonalTile.isTileEmpty()))
                return true;
        }
        else if (pieceColor == Color.BLACK) {
            var firstLowerLeftDiagonalTile = board.getTileInDiagonalOffset(tile,-1, -1);
            var secondLowerLeftDiagonalTile = board.getTileInDiagonalOffset(firstLowerLeftDiagonalTile, -1, -1);

            var firstLowerRightDiagonalTile = board.getTileInDiagonalOffset(tile,-1, 1);
            var secondLowerRightDiagonalTile = board.getTileInDiagonalOffset(firstLowerRightDiagonalTile, -1, 1);

            if((firstLowerLeftDiagonalTile.isTileNotEmpty() &&
                    firstLowerLeftDiagonalTile.getPieceOfTile().getColorOfPiece() == Color.WHITE &&
                        secondLowerLeftDiagonalTile.isTileEmpty())
                    ||
                    firstLowerRightDiagonalTile.isTileNotEmpty() &&
                            firstLowerRightDiagonalTile.getPieceOfTile().getColorOfPiece() == Color.WHITE &&
                                secondLowerRightDiagonalTile.isTileEmpty())
                return true;
        }

        return false;
    }

}