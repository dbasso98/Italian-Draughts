package dssc.exam.draughts.moveLogics;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.utilities.Color;
import dssc.exam.draughts.core.Path;
import dssc.exam.draughts.core.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CandidateSkipPathBuilder {

    public static HashMap<Tile, Path> build(Board board, Color movingPieceColor) {
        ArrayList<Tile> tilesContainingPieceOfSameColor = board.getTilesContainingPieceOfColor(movingPieceColor);
        HashMap<Tile, Path> tilesToStartSkippingFrom = new HashMap<>();
        collectCandidateSkipPaths(board, tilesContainingPieceOfSameColor, tilesToStartSkippingFrom);
        return tilesToStartSkippingFrom;
    }

    private static void collectCandidateSkipPaths(Board board, ArrayList<Tile> tilesContainingPieceOfSameColor, HashMap<Tile, Path> tilesToStartSkippingFrom) {
        for (Tile tile : tilesContainingPieceOfSameColor) {
            var skipPath = new Path(tile);
            buildPath(board, tile, skipPath);
            if (skipPath.getWeight() > 0)
                tilesToStartSkippingFrom.put(tile, skipPath);
        }
    }

    private static void buildPath(Board board, Tile currentTile, Path path) {
        path.addTile(currentTile);
        if (path.getNumberOfSkips() < 3) {
            ArrayList<SkipMoveRules> candidateSkipMoves = getCandidateSkipMoves(board, currentTile, path);
            candidateSkipMoves.forEach(move -> move.evaluateIfCanSkip(path));
            extendPathIfPossible(board, path, candidateSkipMoves);
        }
    }

    private static ArrayList<SkipMoveRules> getCandidateSkipMoves(Board board, Tile currentTile, Path path) {
        var movingDirection = path.getSourceColor().associatedDirection();
        ArrayList<SkipMoveRules> candidateSkipMoves = getListOfSameDirectionSkipMove(currentTile, movingDirection, board);
        if (path.startsFromKing()) {
            candidateSkipMoves.addAll(getListOfSameDirectionSkipMove(currentTile, -movingDirection, board));
        }
        return candidateSkipMoves;
    }

    private static ArrayList<SkipMoveRules> getListOfSameDirectionSkipMove(Tile currentTile, int direction, Board board) {
        var rightMove = new SkipMoveRules(currentTile, new Point(direction, 1), board);
        var leftMove = new SkipMoveRules(currentTile, new Point(direction, -1), board);
        return new ArrayList<>(Arrays.asList(rightMove, leftMove));
    }

    private static void extendPathIfPossible(Board board, Path path,
                                             List<SkipMoveRules> candidateSkipMoves) {
        var candidatesPaths = new ArrayList<Path>();
        boolean atLeastOneSkipIsPossible = false;
        for (SkipMoveRules move : candidateSkipMoves) {
            if (move.canSkip()) {
                candidatesPaths.add(getNextPath(board, path, move));
                atLeastOneSkipIsPossible = true;
            }
        }
        if (atLeastOneSkipIsPossible) {
            updateBestPath(path, candidatesPaths);
        }
    }

    private static Path getNextPath(Board board, Path path, SkipMoveRules diagonalMove) {
        var nextPath = Path.copy(path);
        int newWeight;
        if (path.startsFromKing()) {
            newWeight = getCurrentWeight(path.getNumberOfSkips(), diagonalMove.getFirstTile().containsAKing());
        } else {
            newWeight = 10 * (path.getNumberOfSkips() + 1);
        }
        nextPath.setWeight(newWeight);
        buildPath(board, diagonalMove.getSecondTile(), nextPath);
        return nextPath;
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
        int currentWeight = 10 * (skips + 1);
        if (skippedAKing)
            return currentWeight + 5 + (3 - skips);
        return currentWeight;
    }
}