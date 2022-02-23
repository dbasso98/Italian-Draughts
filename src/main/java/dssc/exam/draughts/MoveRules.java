package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception {
        if (!(board.isPositionInsideTheBoard(source) || board.isPositionInsideTheBoard(destination)))
            throw new InvalidIndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        isWhiteTile(board, source);
        isWhiteTile(board, destination);
        isSamePosition(source, destination);
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
            throw new InvalidMoveException("You are moving in the opposite rowOffset!");
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

    static HashMap<Tile, Path> candidatePathsForSkipMove(Board board, Color color) {
        var listOfTiles = board.getTilesContainingPieceOfColor(color);
        var tilesToStartSkipping = new HashMap<Tile, Path>();
        for (Tile tile : listOfTiles) {
            var skipPath = new Path(tile);
            if (tile.containsAKing())
                buildPathStartingFromKing(board, tile, skipPath);
            else
                buildPathStartingFromMan(board, tile, skipPath);
            if (skipPath.getWeight() > 0)
                tilesToStartSkipping.put(tile, skipPath);
        }
        return tilesToStartSkipping;
    }

    private static int getMovingDirection(Color color) {
        if (color == Color.BLACK)
            return -1;
        else
            return 1;
    }

    public static void buildPathStartingFromKing(Board board, Tile currentTile, Path path) {
        path.addTile(currentTile);
        var currentSkipsMade = path.getNumberOfSkips();
        if (currentSkipsMade == 3)
            return;
        var originalColorOfPiece = path.getPieceContainedInSource().getColor();
        var direction = getMovingDirection(originalColorOfPiece);

        var rightDiagonalMove = new SkipMoveRules(currentTile, direction, 1);
        rightDiagonalMove.kingDiagonalCheck(board, originalColorOfPiece, path);
        var oppositeRightDiagonalMove = new SkipMoveRules(currentTile, -1*direction, 1);
        oppositeRightDiagonalMove.kingDiagonalCheck(board, originalColorOfPiece, path);
        var leftDiagonalMove = new SkipMoveRules(currentTile, direction, -1);
        leftDiagonalMove.kingDiagonalCheck(board, originalColorOfPiece, path);
        var oppositeLeftDiagonalMove = new SkipMoveRules(currentTile, -1*direction, -1);
        oppositeLeftDiagonalMove.kingDiagonalCheck(board, originalColorOfPiece, path);

        if (!(rightDiagonalMove.getCheck() || leftDiagonalMove.getCheck() ||
              oppositeRightDiagonalMove.getCheck() || oppositeLeftDiagonalMove.getCheck())) {
            return;
        }
        else {
            var candidatesPaths = new ArrayList<Path>();
            if (leftDiagonalMove.getCheck())
                continueToBuildPath(board, path, leftDiagonalMove, candidatesPaths);
            if (rightDiagonalMove.getCheck())
                continueToBuildPath(board, path, rightDiagonalMove, candidatesPaths);
            if (oppositeLeftDiagonalMove.getCheck())
                continueToBuildPath(board, path, oppositeLeftDiagonalMove, candidatesPaths);
            if (oppositeRightDiagonalMove.getCheck())
                continueToBuildPath(board, path, oppositeRightDiagonalMove, candidatesPaths);
            updateBestPath(path, candidatesPaths);
        }
    }

    private static void updateBestPath(Path path, ArrayList<Path> candidatesPaths) {
        Path bestPath = path;
        for (var candidate : candidatesPaths) {
            if (candidate.getWeight() > bestPath.getWeight())
                bestPath = candidate;
        }
        path.setPath(bestPath.getPath());
        path.setWeight(path.getWeight() + bestPath.getWeight());
    }

    private static int getCurrentWeight(int skips, boolean skippedAKing) {
        int currentWeight =  10 * (skips + 1);
        if (skippedAKing)
             currentWeight += 5 + (3 - skips);
        return currentWeight;
    }

    static void buildPathStartingFromMan(Board board, Tile currentTile, Path path) {
        path.addTile(currentTile);
        var currentSkipsMade = path.getNumberOfSkips();
        if (currentSkipsMade == 3)
            return;
        var originalColorOfPiece = path.getPieceContainedInSource().getColor();
        var direction = getMovingDirection(originalColorOfPiece);

        var rightDiagonalMove = new SkipMoveRules(currentTile, direction, 1);
        rightDiagonalMove.manDiagonalCheck(board, originalColorOfPiece);
        var leftDiagonalMove = new SkipMoveRules(currentTile, direction, -1);
        leftDiagonalMove.manDiagonalCheck(board, originalColorOfPiece);
        if (!(rightDiagonalMove.getCheck() || leftDiagonalMove.getCheck())) {
            return;
        }
        else {
            var candidatesPaths = new ArrayList<Path>();
            if (leftDiagonalMove.getCheck())
                continueToBuildPath(board, path, leftDiagonalMove, candidatesPaths);
            if (rightDiagonalMove.getCheck())
                continueToBuildPath(board, path, rightDiagonalMove, candidatesPaths);
            updateBestPath(path, candidatesPaths);
        }
    }

    private static void continueToBuildPath(Board board, Path path, SkipMoveRules diagonalMove, ArrayList<Path> candidatesPaths) {
        var nextPath = Path.copy(path);
        if(path.getSource().containsAKing()) {
            nextPath.setWeight(getCurrentWeight(path.getNumberOfSkips(), diagonalMove.getFirstTile().containsAKing()));
            buildPathStartingFromKing(board, diagonalMove.getSecondTile(), nextPath);
        }
        else {
            nextPath.setWeight(10 * (path.getNumberOfSkips() + 1));
            buildPathStartingFromMan(board, diagonalMove.getSecondTile(), nextPath);
        }
        candidatesPaths.add(nextPath);
    }
}