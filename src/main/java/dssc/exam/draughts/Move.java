package dssc.exam.draughts;

import java.awt.*;

public class Move {
    public void moveDiagonally(Board board, Point from, Point to) throws Exception{
        try{
            // 1. first check if initial position is valid
            MoveRules.checkIfPositionIsValid(board, from, to);
            var piece = board.getTile(from).returnPieceAndResetTileToEmpty();
            board.getTile(to).setPieceContainedInTile(piece);
        }
        catch (Exception e){
            throw e;
        }

    }

}
