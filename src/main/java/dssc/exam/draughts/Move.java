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

            // check for source & destination tile correctness (non-emptiness, emptiness)
            var sourceTile = board.getTile(source);
            if(sourceTile.isTileEmpty()) {
                throw new Exception("Cannot move since tile at (" + (source.x+1) + "," + (source.y+1) + ") is empty");
            }
            else if(board.getTile(destination).isTileNotEmpty()) {
                throw new Exception("Cannot move since tile at (" + (destination.x+1) + "," + (destination.y+1) + ") is not empty");
            }
            var piece = sourceTile.popPieceContainedInTile();
            board.getTile(destination).setPieceContainedInTile(piece);
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
