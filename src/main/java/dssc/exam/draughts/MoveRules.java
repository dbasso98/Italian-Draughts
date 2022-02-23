package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception {
        if (!(board.isPositionInsideTheBoard(source) || board.isPositionInsideTheBoard(destination)))
            throw new IndexException("Position is not valid! Index must be between 1 and 8 for each axis!");
        isBlackTile(board, source);
        isBlackTile(board, destination);
        isNotSamePosition(source, destination); // Forse questo dovrebbe essere resp. di Game?
        isCorrectDirection(board, source, destination);
        isMovingInDiagonal(source, destination);
        isItMovingByOneOrTwoTiles(source, destination);
        return true;
    }


    private static void isBlackTile(Board board, Point position) throws TileException {
        if (board.getTile(position).isWhite())
            throw new TileException("Cannot play on white tiles, only black ones, please change position!");
    }

    static void isNotSamePosition(Point source, Point destination) throws MoveException {
        if (source.equals(destination))
            throw new MoveException("Source and destination position cannot be the same!");
    }

    private static void isCorrectDirection(Board board, Point source, Point destination) throws MoveException {
        var colorOfSourceTile = board.getColorOfPieceAtTile(source);
        var isSourceTileAKing = board.getPieceAtTile(source.x, source.y).isKing();
        var direction = destination.x - source.x;
        if (!isSourceTileAKing &&
                ((colorOfSourceTile == Color.WHITE && direction < 0) || (colorOfSourceTile == Color.BLACK && direction > 0)))
            throw new MoveException("You are moving in the opposite rowOffset!");
    }

    private static void isMovingInDiagonal(Point source, Point destination) throws MoveException {
        if (Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y))
            throw new MoveException("Checker can only move diagonally!");
    }

    private static void isItMovingByOneOrTwoTiles(Point source, Point destination) throws MoveException {
        var distance = Math.abs(destination.x - source.x);
        if (distance != 1 && distance != 2)
            throw new MoveException(("Checker can move only by one or two tiles!"));
    }

    static void checkTileNonEmptiness(Tile destinationTile) throws TileException {
        if (destinationTile.isNotEmpty())
            throw new TileException("Cannot move since tile at (" + (destinationTile.getColumn() + 1)
                    + "," + (destinationTile.getRow() + 1) + ") is not empty");
    }

    static void checkTileEmptiness(Tile sourceTile) throws TileException {
        if (sourceTile.isEmpty())
            throw new TileException("Cannot move since tile at (" + (sourceTile.getColumn() + 1)
                    + "," + (sourceTile.getRow() + 1) + ") is empty");
    }

    static HashMap<Tile, Path> candidatePathsForSkipMove(Board board, Color color) {
        var tilesContainingPieceOfColor = board.getTilesContainingPieceOfColor(color);
        var tilesToStartSkippingFrom = new HashMap<Tile, Path>();
        for (Tile tile : tilesContainingPieceOfColor) {
            var skipPath = new Path(tile);
            if (tile.containsAKing())
                buildPathStartingFromKing(board, tile, skipPath);
            else
                buildPathStartingFromMan(board, tile, skipPath);
            if (skipPath.getWeight() > 0)
                tilesToStartSkippingFrom.put(tile, skipPath);
        }
        return tilesToStartSkippingFrom;
    }

    private static int getMovingDirection(Color color) {
        if (color == Color.BLACK)
            return -1;
        else
            return 1;
    }

    static void buildPathStartingFromKing(Board board, Tile currentTile, Path path) {
        path.addTile(currentTile);
        if (path.getNumberOfSkips() < 3) {
            var colorOfSourcePiece = path.getPieceContainedInSource().getColor();
            var movingDirection = getMovingDirection(colorOfSourcePiece);
            var rightDiagonalMove = new SkipMoveRules(currentTile, movingDirection, 1);
            rightDiagonalMove.kingDiagonalCheck(board, colorOfSourcePiece, path);
            var oppositeRightDiagonalMove = new SkipMoveRules(currentTile, -1 * movingDirection, 1);
            oppositeRightDiagonalMove.kingDiagonalCheck(board, colorOfSourcePiece, path);
            var leftDiagonalMove = new SkipMoveRules(currentTile, movingDirection, -1);
            leftDiagonalMove.kingDiagonalCheck(board, colorOfSourcePiece, path);
            var oppositeLeftDiagonalMove = new SkipMoveRules(currentTile, -1 * movingDirection, -1);
            oppositeLeftDiagonalMove.kingDiagonalCheck(board, colorOfSourcePiece, path);
            if (rightDiagonalMove.getSkipCheck() || leftDiagonalMove.getSkipCheck() ||
                oppositeRightDiagonalMove.getSkipCheck() || oppositeLeftDiagonalMove.getSkipCheck()) {
                var candidatesPaths = new ArrayList<Path>();
                if (leftDiagonalMove.getSkipCheck())
                    continueToBuildPath(board, path, leftDiagonalMove, candidatesPaths);
                if (rightDiagonalMove.getSkipCheck())
                    continueToBuildPath(board, path, rightDiagonalMove, candidatesPaths);
                if (oppositeLeftDiagonalMove.getSkipCheck())
                    continueToBuildPath(board, path, oppositeLeftDiagonalMove, candidatesPaths);
                if (oppositeRightDiagonalMove.getSkipCheck())
                    continueToBuildPath(board, path, oppositeRightDiagonalMove, candidatesPaths);
                updateBestPath(path, candidatesPaths);
            }
        }
    }

    static void buildPathStartingFromMan(Board board, Tile currentTile, Path path) {
        path.addTile(currentTile);
        if (path.getNumberOfSkips() < 3) {
            var colorOfSourcePiece = path.getPieceContainedInSource().getColor();
            var movingDirection = getMovingDirection(colorOfSourcePiece);
            var rightDiagonalMove = new SkipMoveRules(currentTile, movingDirection, 1);
            rightDiagonalMove.manDiagonalCheck(board, colorOfSourcePiece);
            var leftDiagonalMove = new SkipMoveRules(currentTile, movingDirection, -1);
            leftDiagonalMove.manDiagonalCheck(board, colorOfSourcePiece);
            if (rightDiagonalMove.getSkipCheck() || leftDiagonalMove.getSkipCheck()) {
                var candidatesPaths = new ArrayList<Path>();
                if (leftDiagonalMove.getSkipCheck())
                    continueToBuildPath(board, path, leftDiagonalMove, candidatesPaths);
                if (rightDiagonalMove.getSkipCheck())
                    continueToBuildPath(board, path, rightDiagonalMove, candidatesPaths);
                updateBestPath(path, candidatesPaths);
            }
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

    private static void updateBestPath(Path currentPath, ArrayList<Path> candidatesPaths) {
        Path bestPath = currentPath;
        for (var candidatePath : candidatesPaths) {
            if (candidatePath.getWeight() > bestPath.getWeight())
                bestPath = candidatePath;
        }
        currentPath.setPath(bestPath.getPath());
        currentPath.setWeight(currentPath.getWeight() + bestPath.getWeight());
    }

    private static int getCurrentWeight(int skips, boolean skippedAKing) {
        int currentWeight =  10 * (skips + 1);
        if (skippedAKing)
            currentWeight += 5 + (3 - skips);
        return currentWeight;
    }
}