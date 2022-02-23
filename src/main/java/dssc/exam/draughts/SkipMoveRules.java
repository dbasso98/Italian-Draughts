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

    public void manDiagonalCheck(Board board, Color color) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, rowOffset, columnOffset);
        secondDiagonalTile = board.getTileInDiagonalOffset(firstDiagonalTile, rowOffset, columnOffset);
        skipCheck = canSkip(color) && checkThatIsSkippingAMan();
    }

    public void kingDiagonalCheck(Board board, Color color, Path path) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, rowOffset, columnOffset);
        secondDiagonalTile = board.getTileInDiagonalOffset(firstDiagonalTile, rowOffset, columnOffset);
        skipCheck = tileWasNotVisitedYet(path) && canSkip(color);
    }

    private boolean canSkip(Color color) {
        return isTileInsideTheBoard(firstDiagonalTile) &&
                isTileInsideTheBoard(secondDiagonalTile) &&
                firstDiagonalTile.isNotEmpty() &&
                firstDiagonalTile.getPiece().getColor() != color &&
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

    public Tile getFirstTile() {
        return firstDiagonalTile;
    }

    public Tile getSecondTile() {
        return secondDiagonalTile;
    }

    public boolean getSkipCheck() {
        return skipCheck;
    }
}