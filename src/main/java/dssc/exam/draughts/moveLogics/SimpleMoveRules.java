package dssc.exam.draughts.moveLogics;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.utilities.Color;
import dssc.exam.draughts.core.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SimpleMoveRules {
    final Tile sourceTile;
    final Point offset;
    Tile firstDiagonalTile;
    private boolean canSimplyMove;

    SimpleMoveRules(Tile source, Point offset, Board board) {
        this.sourceTile = source;
        this.offset = offset;
        getFirstDiagonalTile(board);
    }

    void evaluateIfCanSimplyMove() {
        canSimplyMove = firstDiagonalTile != null && firstDiagonalTile.isEmpty();
    }

    boolean canSimplyMove() {
        return canSimplyMove;
    }

    public static boolean CanNotMakeASimpleMove(Board board, Color colorOfMovingPiece) {
        ArrayList<Tile> tilesContainingPieceOfSameColor = board.getTilesContainingPieceOfColor(colorOfMovingPiece);
        var assertCanDoASimpleMove = new ArrayList<Boolean>();
        for (var tile : tilesContainingPieceOfSameColor) {
            canDoAtLeastASimpleMove(assertCanDoASimpleMove, tile, colorOfMovingPiece.associatedDirection(), board);
        }
        return assertCanDoASimpleMove.stream().allMatch(check -> check.equals(false));
    }

    private static void canDoAtLeastASimpleMove(ArrayList<Boolean> assertCanDoASimpleMove, Tile tile,
                                                int movingDirection, Board board) {
        ArrayList<SimpleMoveRules> candidateSimpleMoves = getListOfSameDirectionSimpleMove(tile, movingDirection, board);
        if (tile.containsAKing())
            candidateSimpleMoves.addAll(getListOfSameDirectionSimpleMove(tile, -movingDirection, board));
        candidateSimpleMoves.forEach(SimpleMoveRules::evaluateIfCanSimplyMove);
        assertCanDoASimpleMove.add(candidateSimpleMoves.stream()
                .map(SimpleMoveRules::canSimplyMove)
                .reduce(false, (firstMove, secondMove) -> firstMove || secondMove));
    }

    private static ArrayList<SimpleMoveRules> getListOfSameDirectionSimpleMove(Tile currentTile, int direction, Board board) {
        var rightMove = new SimpleMoveRules(currentTile, new Point(direction, 1), board);
        var leftMove = new SimpleMoveRules(currentTile, new Point(direction, -1), board);
        return new ArrayList<>(Arrays.asList(rightMove, leftMove));
    }

    private void getFirstDiagonalTile(Board board) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, offset);
    }

}
