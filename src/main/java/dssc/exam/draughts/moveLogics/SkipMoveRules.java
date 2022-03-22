package dssc.exam.draughts.moveLogics;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.utilities.Color;
import dssc.exam.draughts.core.Path;
import dssc.exam.draughts.core.Tile;

import java.awt.*;

class SkipMoveRules {
    final Tile sourceTile;
    final Point offset;
    Tile firstDiagonalTile;
    private Tile secondDiagonalTile;
    private boolean canSkip;

    SkipMoveRules(Tile source, Point offset, Board board) {
        this.sourceTile = source;
        this.offset = offset;
        getFirstDiagonalTile(board);
        getSecondDiagonalTile(board);
    }

    void getSecondDiagonalTile(Board board) {
        secondDiagonalTile = board.getTileInDiagonalOffset(this.firstDiagonalTile, offset);
    }

    void evaluateIfCanSkip(Path path) {
        if (path.startsFromKing()) {
            evaluateIfKingCanSkip(path);
            return;
        }
        evaluateIfManCanSkip(path);
    }

    private void evaluateIfManCanSkip(Path path) {
        canSkip = isSkipValid(path.getSourceColor()) && checkThatIsSkippingAMan();
    }

    private void evaluateIfKingCanSkip(Path path) {
        canSkip = tileWasNotVisitedYet(path) && isSkipValid(path.getSourceColor());
    }

    private boolean isSkipValid(Color movingPieceColor) {
        return isTileInsideTheBoard(secondDiagonalTile) &&
                firstDiagonalTile.isNotEmpty() &&
                firstDiagonalTile.getPiece().getColor() != movingPieceColor &&
                secondDiagonalTile.isEmpty();
    }

    private boolean isTileInsideTheBoard(Tile tile) {
        return tile != null;
    }

    private boolean checkThatIsSkippingAMan() {
        if (firstDiagonalTile.isNotEmpty())
            return !firstDiagonalTile.containsAKing();
        return false;
    }

    private boolean tileWasNotVisitedYet(Path path) {
        return !(path.containsTile(secondDiagonalTile));
    }

    void getFirstDiagonalTile(Board board) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, offset);
    }

    Tile getFirstTile() {
        return firstDiagonalTile;
    }

    Tile getSecondTile() {
        return secondDiagonalTile;
    }

    boolean canSkip() {
        return canSkip;
    }
}