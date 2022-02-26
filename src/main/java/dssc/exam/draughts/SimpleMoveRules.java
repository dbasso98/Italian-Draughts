package dssc.exam.draughts;

import java.awt.*;

public class SimpleMoveRules extends MoveRules{

    private boolean canSimplyMove;

    SimpleMoveRules(Tile source, Point offset, Board board) {
        super(source, offset, board);
    }

    void evaluateIfCanSimplyMove() {
        canSimplyMove = firstDiagonalTile != null && firstDiagonalTile.isEmpty();
    }

    boolean canSimplyMove() {
        return canSimplyMove;
    }
}
