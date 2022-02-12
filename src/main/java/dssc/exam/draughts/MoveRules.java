package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.BitSet;
import java.util.HashMap;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception {
        try {
            board.isValidPosition(source);
            board.isValidPosition(destination);
            board.isBlackTile(board.getTile(source));
            board.isBlackTile(board.getTile(destination));
            isNotSamePosition(source, destination);
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

    static void isDiagonal(Point source, Point destination) throws NotDiagonalMoveException {
        if (Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y)) {
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

    static HashMap<Tile, Integer> candidateTilesForSkipMove(Board board, Color color) {
        var listOfTiles = board.getTilesContainingPieceOfColor(color);
        var candidateTilesForSkipMap = new HashMap<Tile, Integer>();
        var direction = 1;
        Color oppositeColor = Color.BLACK;
        if (color == Color.BLACK) {
            direction = -1;
            oppositeColor = Color.WHITE;
        }
        for (Tile tile : listOfTiles) {
            int skipWeight = checkAdjacentDiagonal(board, tile, oppositeColor, direction, 0);
            if (skipWeight > 0)
                candidateTilesForSkipMap.put(tile, skipWeight);
        }
        return candidateTilesForSkipMap;
    }

    static int checkAdjacentDiagonal(Board board, Tile tile, Color originalColorOfPiece, int direction, int steps) {
        if (steps == 3) {
            return 0;
        }
        boolean rightCheck, leftCheck;
        var firstRightDiagonalTile = board.getTileInDiagonalOffset(tile, direction, 1);
        var secondRightDiagonalTile = board.getTileInDiagonalOffset(firstRightDiagonalTile, direction, 1);
        rightCheck = canSkip(board, originalColorOfPiece, firstRightDiagonalTile, secondRightDiagonalTile);
        var firstLeftDiagonalTile = board.getTileInDiagonalOffset(tile, direction, -1);
        var secondLeftDiagonalTile = board.getTileInDiagonalOffset(firstLeftDiagonalTile, direction, -1);
        leftCheck = canSkip(board, originalColorOfPiece, firstLeftDiagonalTile, secondLeftDiagonalTile);
        if (!(leftCheck || rightCheck)) {
            return 0;
        }
        else {
            var leftWeight = checkAdjacentDiagonal(board, secondLeftDiagonalTile, originalColorOfPiece, direction, ++steps);
            var rightWeight = checkAdjacentDiagonal(board, secondRightDiagonalTile, originalColorOfPiece, direction, ++steps);
            if(leftWeight > rightWeight)
                return leftWeight+1;
            else
                return rightWeight+1;
        }
    }

    private static boolean canSkip(Board board, Color originalColorOfPiece, Tile firstDiagonalTile, Tile secondDiagonalTile) {
        try {
            return board.isValidPosition(firstDiagonalTile.getTilePosition()) &&
                    board.isValidPosition(secondDiagonalTile.getTilePosition()) &&
                    firstDiagonalTile.isTileNotEmpty() &&
                    firstDiagonalTile.getPieceOfTile().getColorOfPiece() == originalColorOfPiece &&
                    secondDiagonalTile.isTileEmpty();
        }
        catch(Exception e){
            return false;
        }
    }
}
