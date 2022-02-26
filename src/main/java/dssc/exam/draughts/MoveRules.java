package dssc.exam.draughts;

import java.awt.*;

public class MoveRules {
    final Tile sourceTile;
    final Point offset;
    Tile firstDiagonalTile;

    MoveRules(Tile source, Point offset, Board board) {
        sourceTile = source;
        this.offset = offset;
        getFirstDiagonalTile(board);
    }

    void getFirstDiagonalTile(Board board) {
        firstDiagonalTile = board.getTileInDiagonalOffset(sourceTile, offset);
    }

    Tile getFirstTile() {
        return firstDiagonalTile;
    }
}