package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception {
        if (!(board.isValidPosition(source) || board.isValidPosition(destination)))
            throw new InvalidIndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        isSamePosition(source, destination);
        isWhiteTile(board, source);
        isWhiteTile(board, destination);
        isCorrectDirection(board, source, destination);
        isDiagonal(source, destination);
        isValidDistance(source, destination);
        return true;
    }

    private static void isCorrectDirection(Board board, Point source, Point destination) throws InvalidMoveException{
        var colorOfSourceTile = board.getColorOfPieceAtTile(source);
        var isSourceTileAKing = board.getPieceAtTile(source.x, source.y).isKing();
        var direction = destination.x - source.x;
        if (!isSourceTileAKing &&
            ((colorOfSourceTile == Color.WHITE && direction < 0) || (colorOfSourceTile == Color.BLACK && direction > 0)))
            throw new InvalidMoveException("You are moving in the opposite direction!");
    }

    private static void isWhiteTile(Board board, Point position) throws WhiteTileException {
        if (board.getTile(position).isWhite())
            throw new WhiteTileException("Cannot play on white tiles, only black ones, please change position!");
    }

    private static void isValidDistance(Point source, Point destination) throws InvalidMoveException {
        var distance = Math.abs(destination.x - source.x);
        if (distance != 1 && distance != 2)
            throw new InvalidMoveException(("Checker can move only by one or two tiles!"));
    }

    static void isDiagonal(Point source, Point destination) throws NotDiagonalMoveException {
        if (Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y))
            throw new NotDiagonalMoveException("Checker can only move diagonally!");
    }

    static void isSamePosition(Point source, Point destination) throws SamePositionException {
        if (source.equals(destination))
            throw new SamePositionException("Source and destination position cannot be the same!");
    }

    static void checkTileNonEmptiness(Tile destinationTile) throws NonEmptyTileException {
        if (destinationTile.isNotEmpty())
            throw new NonEmptyTileException("Cannot move since tile at (" + (destinationTile.getColumn() + 1)
                                            + "," + (destinationTile.getRow() + 1) + ") is not empty");
    }

    static void checkTileEmptiness(Tile sourceTile) throws EmptyTileException {
        if (sourceTile.isEmpty())
            throw new EmptyTileException("Cannot move since tile at (" + (sourceTile.getColumn() + 1)
                                         + "," + (sourceTile.getRow() + 1) + ") is empty");
    }

    static HashMap<Tile, Path> candidateTilesForSkipMove(Board board, Color color) {
        var listOfTiles = board.getTilesContainingPieceOfColor(color);
        var tilesThatCanPerformASkip = new HashMap<Tile, Path>();
        int direction = getMovingDirection(color);
        int skipWeight;
        for (Tile tile : listOfTiles) {
            var skipPath = new ArrayList<Tile>();
            if (tile.getPiece().isKing())
                skipWeight = getWeightForKingSkipPath(board, tile, color, direction, -1, skipPath);
            else
                skipWeight = getWeightForManSkipPath(board, tile, color, direction, -1, skipPath);
            if (skipWeight > 0)
                tilesThatCanPerformASkip.put(tile, new Path(skipPath, skipWeight));
        }
        return tilesThatCanPerformASkip;
    }

    private static int getMovingDirection(Color color) {
        if (color == Color.BLACK)
            return -1;
        else
            return 1;
    }

    public static int getWeightForKingSkipPath(Board board, Tile tile, Color originalColorOfPiece, int direction, int steps, ArrayList<Tile> path) {
        ++steps;
        if (steps == 3)
            return 0;
        path.add(tile);
        var oppositeDirection = -1 * direction;

        var firstRightDiagonalTile = board.getTileInDiagonalOffset(tile, direction, 1);
        var secondRightDiagonalTile = board.getTileInDiagonalOffset(firstRightDiagonalTile, direction, 1);
        boolean rightCheck = tileWasNotVisitedYet(path, secondRightDiagonalTile) &&
                            canSkip(originalColorOfPiece, firstRightDiagonalTile, secondRightDiagonalTile);

        var firstOppositeRightDiagonalTile = board.getTileInDiagonalOffset(tile, oppositeDirection, 1);
        var secondOppositeRightDiagonalTile = board.getTileInDiagonalOffset(firstOppositeRightDiagonalTile, oppositeDirection, 1);
        boolean oppositeDirectionRightCheck = tileWasNotVisitedYet(path, secondOppositeRightDiagonalTile) &&
                            canSkip(originalColorOfPiece, firstOppositeRightDiagonalTile, secondOppositeRightDiagonalTile);

        var firstLeftDiagonalTile = board.getTileInDiagonalOffset(tile, direction, -1);
        var secondLeftDiagonalTile = board.getTileInDiagonalOffset(firstLeftDiagonalTile, direction, -1);
        boolean leftCheck = tileWasNotVisitedYet(path, secondLeftDiagonalTile) &&
                            canSkip(originalColorOfPiece, firstLeftDiagonalTile, secondLeftDiagonalTile);

        var firstOppositeLeftDiagonalTile = board.getTileInDiagonalOffset(tile, oppositeDirection, -1);
        var secondOppositeLeftDiagonalTile = board.getTileInDiagonalOffset(firstOppositeLeftDiagonalTile, oppositeDirection, -1);
        boolean oppositeDirectionLeftCheck = tileWasNotVisitedYet(path, secondOppositeLeftDiagonalTile) &&
                            canSkip(originalColorOfPiece, firstOppositeLeftDiagonalTile, secondOppositeLeftDiagonalTile);

        if (!(leftCheck || rightCheck || oppositeDirectionRightCheck || oppositeDirectionLeftCheck)) {
            return 0;
        }
        else {
            int leftWeight=0, rightWeight=0, oppositeLeftWeight=0, oppositeRightWeight=0;
            int possibleKingsSkipped = 0;
            if (leftCheck) {
                leftWeight = getWeightForKingSkipPath(board, secondLeftDiagonalTile, originalColorOfPiece, direction, steps, path);
                possibleKingsSkipped = updateNumberOfKingsSkipped(firstLeftDiagonalTile, possibleKingsSkipped);
            }
            if (rightCheck) {
                rightWeight = getWeightForKingSkipPath(board, secondRightDiagonalTile, originalColorOfPiece, direction, steps, path);
                possibleKingsSkipped = updateNumberOfKingsSkipped(firstRightDiagonalTile, possibleKingsSkipped);
            }
            if (oppositeDirectionLeftCheck) {
                oppositeLeftWeight = getWeightForKingSkipPath(board, secondOppositeLeftDiagonalTile, originalColorOfPiece, direction, steps, path);
                possibleKingsSkipped = updateNumberOfKingsSkipped(firstOppositeLeftDiagonalTile, possibleKingsSkipped);
            }
            if (oppositeDirectionRightCheck) {
                oppositeRightWeight = getWeightForKingSkipPath(board, secondOppositeRightDiagonalTile, originalColorOfPiece, direction, steps, path);
                possibleKingsSkipped = updateNumberOfKingsSkipped(firstOppositeRightDiagonalTile, possibleKingsSkipped);
            }
            var currentWeight = getCurrentWeight(steps, possibleKingsSkipped);
            return Math.max(Math.max(leftWeight, rightWeight), Math.max(oppositeLeftWeight,oppositeRightWeight)) + currentWeight;
        }
    }

    private static int updateNumberOfKingsSkipped(Tile firstLeftDiagonalTile, int numberOfKingsSkipped) {
        if (firstLeftDiagonalTile.getPiece().isKing())
            ++numberOfKingsSkipped;
        return numberOfKingsSkipped;
    }

    private static boolean tileWasNotVisitedYet(ArrayList<Tile> path, Tile secondRightDiagonalTile) {
        return !(path.contains(secondRightDiagonalTile));
    }

    private static int getCurrentWeight(int steps, int skippedKings) {
        int currentWeight =  10 * (steps + 1);
        if (skippedKings > 0)
             currentWeight += 5 + (3 - steps);
        return currentWeight;
    }

    static int getWeightForManSkipPath(Board board, Tile tile, Color originalColorOfPiece, int direction, int steps, ArrayList<Tile> path) {
        ++steps;
        if (steps == 3)
            return 0;
        path.add(tile);

        var firstRightDiagonalTile = board.getTileInDiagonalOffset(tile, direction, 1);
        var secondRightDiagonalTile = board.getTileInDiagonalOffset(firstRightDiagonalTile, direction, 1);
        boolean rightCheck = canSkip(originalColorOfPiece, firstRightDiagonalTile, secondRightDiagonalTile) &&
                            checkThatIsSkippingAMan(firstRightDiagonalTile);

        var firstLeftDiagonalTile = board.getTileInDiagonalOffset(tile, direction, -1);
        var secondLeftDiagonalTile = board.getTileInDiagonalOffset(firstLeftDiagonalTile, direction, -1);
        boolean leftCheck = canSkip(originalColorOfPiece, firstLeftDiagonalTile, secondLeftDiagonalTile) &&
                            checkThatIsSkippingAMan(firstLeftDiagonalTile);
        if (!(leftCheck || rightCheck)) {
            return 0;
        }
        else {
            int leftWeight=0, rightWeight=0;
            if (leftCheck)
                leftWeight = getWeightForManSkipPath(board, secondLeftDiagonalTile, originalColorOfPiece, direction, steps, path);
            if (rightCheck)
                rightWeight = getWeightForManSkipPath(board, secondRightDiagonalTile, originalColorOfPiece, direction, steps, path);
            var currentWeight = 10 * (steps + 1);
            return Math.max(leftWeight, rightWeight) + currentWeight;
        }
    }

    private static boolean checkThatIsSkippingAMan(Tile tile) {
        if (tile.isNotEmpty())
            return !tile.getPiece().isKing();
        return false;
    }

    private static boolean isTileInsideTheBoard(Tile tile) {
        return tile != null;
    }

    private static boolean canSkip(Color originalColorOfPiece, Tile firstDiagonalTile, Tile secondDiagonalTile) {
        return isTileInsideTheBoard(firstDiagonalTile) &&
               isTileInsideTheBoard(secondDiagonalTile) &&
               firstDiagonalTile.isNotEmpty() &&
               firstDiagonalTile.getPiece().getColor() != originalColorOfPiece &&
               secondDiagonalTile.isEmpty();
    }
}