package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
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
        int direction = 0;
        if (color == Color.BLACK) {
            direction = -1;
        }
        else if (color == Color.WHITE)
            direction = 1;
        int skipWeight;
        for (Tile tile : listOfTiles) {
            if (tile.getPieceOfTile().isKing())
                skipWeight = checkAdjacentDiagonalForKing(board, tile, color, direction, -1, new ArrayList<>());
            else
                skipWeight = checkAdjacentDiagonal(board, tile, color, direction, -1);
            if (skipWeight > 0)
                candidateTilesForSkipMap.put(tile, skipWeight);
        }
        return candidateTilesForSkipMap;
    }

    public static int checkAdjacentDiagonalForKing(Board board, Tile tile, Color originalColorOfPiece, int direction, int steps, ArrayList<Tile> path) {
        if (steps == 3) {
            return 0;
        }
        else{
            ++steps;
        }
        var oppositeDirection = -1*direction;
        boolean rightCheck, leftCheck, oppositeDirectionRightCheck, oppositeDirectionLeftCheck;
        var firstRightDiagonalTile = board.getTileInDiagonalOffset(tile, direction, 1);
        var secondRightDiagonalTile = board.getTileInDiagonalOffset(firstRightDiagonalTile, direction, 1);
        rightCheck = canSkip(board, originalColorOfPiece, firstRightDiagonalTile, secondRightDiagonalTile);
        rightCheck = rightCheck && !(path.contains(secondRightDiagonalTile));

        var firstOppositeRightDiagonalTile = board.getTileInDiagonalOffset(tile, oppositeDirection, 1);
        var secondOppositeRightDiagonalTile = board.getTileInDiagonalOffset(firstOppositeRightDiagonalTile, oppositeDirection, 1);
        oppositeDirectionRightCheck = canSkip(board, originalColorOfPiece, firstOppositeRightDiagonalTile, secondOppositeRightDiagonalTile);
        oppositeDirectionRightCheck = oppositeDirectionRightCheck && !(path.contains(secondOppositeRightDiagonalTile));

        var firstLeftDiagonalTile = board.getTileInDiagonalOffset(tile, direction, -1);
        var secondLeftDiagonalTile = board.getTileInDiagonalOffset(firstLeftDiagonalTile, direction, -1);
        leftCheck = canSkip(board, originalColorOfPiece, firstLeftDiagonalTile, secondLeftDiagonalTile);
        leftCheck = leftCheck && !(path.contains(secondLeftDiagonalTile));

        var firstOppositeLeftDiagonalTile = board.getTileInDiagonalOffset(tile, oppositeDirection, -1);
        var secondOppositeLeftDiagonalTile = board.getTileInDiagonalOffset(firstOppositeLeftDiagonalTile, oppositeDirection, -1);
        oppositeDirectionLeftCheck = canSkip(board, originalColorOfPiece, firstOppositeLeftDiagonalTile, secondOppositeLeftDiagonalTile);
        oppositeDirectionLeftCheck = oppositeDirectionLeftCheck && !(path.contains(secondOppositeLeftDiagonalTile));

        if (!(leftCheck || rightCheck || oppositeDirectionRightCheck || oppositeDirectionLeftCheck)) {
            return 0;
        }
        else {
            path.add(tile);
            int leftWeight=0, rightWeight=0, oppositeLeftWeight=0, oppositeRightWeight=0;
            if (secondLeftDiagonalTile.getTileRow()!= -1 && leftCheck)
                leftWeight = checkAdjacentDiagonalForKing(board, secondLeftDiagonalTile, originalColorOfPiece, direction, steps, path);
            if (secondRightDiagonalTile.getTileRow()!= -1 && rightCheck)
                rightWeight = checkAdjacentDiagonalForKing(board, secondRightDiagonalTile, originalColorOfPiece, direction, steps, path);
            if (secondOppositeLeftDiagonalTile.getTileRow()!= -1 && oppositeDirectionLeftCheck)
                oppositeLeftWeight = checkAdjacentDiagonalForKing(board, secondOppositeLeftDiagonalTile, originalColorOfPiece, direction, steps, path);
            if (secondOppositeRightDiagonalTile.getTileRow()!= -1 && oppositeDirectionRightCheck)
                oppositeRightWeight = checkAdjacentDiagonalForKing(board, secondOppositeRightDiagonalTile, originalColorOfPiece, direction, steps, path);
            return Math.max(Math.max(leftWeight, rightWeight), Math.max(oppositeLeftWeight,oppositeRightWeight)) + 1;
        }
    }

    static int checkAdjacentDiagonal(Board board, Tile tile, Color originalColorOfPiece, int direction, int steps) {
        if (steps == 3) {
            return 0;
        }
        else{
            ++steps;
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
            var leftWeight = checkAdjacentDiagonal(board, secondLeftDiagonalTile, originalColorOfPiece, direction, steps);
            var rightWeight = checkAdjacentDiagonal(board, secondRightDiagonalTile, originalColorOfPiece, direction, steps);
            return Math.max(leftWeight, rightWeight) + 1;
        }
    }

    private static boolean canSkip(Board board, Color originalColorOfPiece, Tile firstDiagonalTile, Tile secondDiagonalTile) {
        return board.isValidPosition(firstDiagonalTile.getTilePosition()) &&
                    board.isValidPosition(secondDiagonalTile.getTilePosition()) &&
                    firstDiagonalTile.isTileNotEmpty() &&
                    firstDiagonalTile.getPieceOfTile().getColorOfPiece() != originalColorOfPiece &&
                    secondDiagonalTile.isTileEmpty();
    }
}
