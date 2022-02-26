package dssc.exam.draughts;

import java.awt.*;

public class SkipMoveRules {
    private final Tile sourceTile;
    private final Point offset;
    private Tile firstDiagonalTile;
    private Tile secondDiagonalTile;
    private boolean skipCheck;

    SkipMoveRules(Tile source, Point offset, Board board) {
        sourceTile = source;
        this.offset = offset;
        evaluateFirstAndSecondDiagonalTile(board);
    }

    void evaluateIfCanSkip(Path path) {
        if (path.startsFromKing()) {
            evaluateIfKingCanSkip(path);
            return;
        }
        evaluateIfManCanSkip(path);
    }

    private void evaluateIfManCanSkip(Path path) {
        skipCheck = isSkipValid(path.getSourceColor()) && checkThatIsSkippingAMan();
    }

    private void evaluateFirstAndSecondDiagonalTile(Board board) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, offset);
        secondDiagonalTile = board.getTileInDiagonalOffset(firstDiagonalTile, offset);
    }

    private void evaluateIfKingCanSkip(Path path) {
        skipCheck = tileWasNotVisitedYet(path) && isSkipValid(path.getSourceColor());
    }

    private boolean isSkipValid(Color movingPieceColor) {
        return isTileInsideTheBoard(secondDiagonalTile) &&
                firstDiagonalTile.isNotEmpty() &&
                firstDiagonalTile.getPiece().getColor() != movingPieceColor &&
                secondDiagonalTile.isEmpty();
    }

    private boolean checkThatIsSkippingAMan() {
        if (firstDiagonalTile.isNotEmpty())
            return !firstDiagonalTile.containsAKing();
        return false;
    }

    private boolean tileWasNotVisitedYet(Path path) {
        return !(path.containsTile(secondDiagonalTile));
    }

    private boolean isTileInsideTheBoard(Tile tile) {
        return tile != null;
    }

    Tile getFirstTile() {
        return firstDiagonalTile;
    }

    Tile getSecondTile() {
        return secondDiagonalTile;
    }

    boolean getSkipCheck() {
        return skipCheck;
    }
}