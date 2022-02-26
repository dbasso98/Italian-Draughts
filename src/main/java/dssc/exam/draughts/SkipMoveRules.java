package dssc.exam.draughts;

public class SkipMoveRules {
    private final Tile sourceTile;
    private final int rowOffset;
    private final int columnOffset;
    private Tile firstDiagonalTile;
    private Tile secondDiagonalTile;
    private boolean skipCheck;

    SkipMoveRules(Tile source, int rowOffset, int columnOffset) {
        sourceTile = source;
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    void evaluateIfCanSkip(Board board, Path path) {
        if (path.startsFromKing()) {
            evaluateIfKingCanSkip(board, path);
            return;
        }
        evaluateIfManCanSkip(board, path);
    }

    private void evaluateIfManCanSkip(Board board, Path path) {
        evaluateFirstAndSecondDiagonalTile(board);
        skipCheck = isSkipValid(path.getSourceColor()) && checkThatIsSkippingAMan();
    }

    private void evaluateFirstAndSecondDiagonalTile(Board board) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, rowOffset, columnOffset);
        secondDiagonalTile = board.getTileInDiagonalOffset(firstDiagonalTile, rowOffset, columnOffset);
    }

    private void evaluateIfKingCanSkip(Board board, Path path) {
        evaluateFirstAndSecondDiagonalTile(board);
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