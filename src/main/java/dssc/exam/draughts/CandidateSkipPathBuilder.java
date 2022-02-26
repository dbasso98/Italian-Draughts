package dssc.exam.draughts;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CandidateSkipPathBuilder {

    static HashMap<Tile, Path> build(Board board, Color movingPieceColor) {
        ArrayList<Tile> tilesContainingPieceOfSameColor = board.getTilesContainingPieceOfColor(movingPieceColor);
        HashMap<Tile, Path> tilesToStartSkippingFrom = new HashMap<>();
        for (Tile tile : tilesContainingPieceOfSameColor) {
            var skipPath = new Path(tile);
            buildPath(board, tile, skipPath);
            if (skipPath.getWeight() > 0)
                tilesToStartSkippingFrom.put(tile, skipPath);
        }
        return tilesToStartSkippingFrom;
    }

    private static void buildPath(Board board, Tile currentTile, Path path) {
        path.addTile(currentTile);
        if (path.getNumberOfSkips() < 3) {
            var movingDirection = path.getSourceColor().associatedDirection();

            ArrayList<SkipMoveRules> candidateSkipMoves = getListOfSameDirectionSkipMove(currentTile, movingDirection, board);
            if (path.startsFromKing()) {
                candidateSkipMoves.addAll(getListOfSameDirectionSkipMove(currentTile, -movingDirection, board));
            }
            candidateSkipMoves.forEach(move -> move.evaluateIfCanSkip(path));
            extendPathIfPossible(board, path, candidateSkipMoves);
        }
    }

    private static ArrayList<SkipMoveRules> getListOfSameDirectionSkipMove(Tile currentTile, int Direction, Board board) {
        var rightMove = new SkipMoveRules(currentTile, new Point(Direction, 1), board);
        var leftMove = new SkipMoveRules(currentTile, new Point(Direction, -1), board);
        return new ArrayList<>(Arrays.asList(rightMove, leftMove));
    }

    private static void extendPathIfPossible(Board board, Path path,
                                             List<SkipMoveRules> candidateSkipMoves) {
        var candidatesPaths = new ArrayList<Path>();
        boolean atLeastOneMoveIsPossible = false;
        for (SkipMoveRules move : candidateSkipMoves) {
            if (move.canSkip()) {
                continueToBuildPath(board, path, move, candidatesPaths);
                atLeastOneMoveIsPossible = true;
            }
        }
        if (atLeastOneMoveIsPossible) {
            updateBestPath(path, candidatesPaths);
        }
    }

    private static void continueToBuildPath(Board board, Path path, SkipMoveRules diagonalMove, ArrayList<Path> candidatesPaths) {
        var nextPath = Path.copy(path);
        int newWeight;
        if (path.startsFromKing()) {
            newWeight = getCurrentWeight(path.getNumberOfSkips(), diagonalMove.getFirstTile().containsAKing());
        } else {
            newWeight = 10 * (path.getNumberOfSkips() + 1);
        }
        nextPath.setWeight(newWeight);
        buildPath(board, diagonalMove.getSecondTile(), nextPath);
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
        int currentWeight = 10 * (skips + 1);
        if (skippedAKing)
            currentWeight += 5 + (3 - skips);
        return currentWeight;
    }
}