package dssc.exam.draughts;

import java.awt.*;

public class Move {
    public static void moveDiagonallyToEmptyTile(Board board, Point source, Point destination) throws Exception{
        try{
            // 1. first check if initial position is valid
            MoveRules.checkIfPositionAreValid(board, source, destination);

            var sourceTile = board.getTile(source);
            if(sourceTile.isTileNotEmpty()) {
                var piece = board.getTile(source).returnPieceAndResetTileToEmpty();
                board.getTile(destination).setPieceContainedInTile(piece);
            }
            else
                throw new Exception("Cannot move since tile at (" + source.x + "," + source.y + ") is empty");
        }
        catch (Exception e){
            throw e;
        }

    }

}
