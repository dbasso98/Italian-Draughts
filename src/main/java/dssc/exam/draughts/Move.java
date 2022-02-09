package dssc.exam.draughts;

import java.awt.*;

public class Move {
    public final Point source;
    public final Point destinantion;
    public static void moveDiagonallyToEmptyTile(Board board, Point source, Point destination) throws Exception{
        try{
            // 1. first check if initial position is valid
            // at this point, only things i know are: the positions are inside the board, they are not the same
            // and they are specified correctly (in other words, destination is diagonal to source).
            MoveRules.checkIfPositionsAreValid(board, source, destination);

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

    public Move(Point source, Point destinantion) {
        this.source = source;
        this.destinantion = destinantion;
    }

    public void executeOn(Board board){
        // update the board so that the move is applied
    }
}
