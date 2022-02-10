package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.EmptyTileException;
import dssc.exam.draughts.exceptions.NonEmptyTileException;

import java.awt.*;

public class Move {
    public final Point source;
    public final Point destination;

    public static void moveDecider(Board board, Point source, Point destination) throws Exception{
        try{
            MoveRules.checkIfPositionsAreValid(board, source, destination);

            if( MoveRules.isASimpleMove(source, destination) ){
                simpleDiagonalMove(board, source, destination);
            }
            else{
                simpleSkipMove(board, source, destination);
            }

            //2. se è skipmove


        }
        catch(Exception e){
            throw e;
        }
    }

    private static void simpleSkipMove(Board board, Point source, Point destination) throws Exception{
        try {
            var sourceTile = board.getTile(source);
            var middleTile = board.getTile(board.getMiddlePosition(source,destination));
            if(middleTile.isTileEmpty())
                throw new EmptyTileException("Skip move over two empty tiles is not accepted");
            //if( middleTile.getTilePiece().getColorOfPiece() )
        }
        catch(Exception e){
            throw e;
        }


    }

    public static void simpleDiagonalMove(Board board, Point source, Point destination) throws Exception {
        try {
            var sourceTile = board.getTile(source);
            if(sourceTile.isTileEmpty()) {
                throw new EmptyTileException("Cannot move since tile at (" + (source.y+1) + "," + (source.x+1) + ") is empty");
            }
            else if(board.getTile(destination).isTileNotEmpty()) {
                throw new NonEmptyTileException("Cannot move since tile at (" + (destination.y+1) + "," + (destination.x+1) + ") is not empty");
            }
            var piece = sourceTile.popPieceContainedInTile();
            board.getTile(destination).setPieceContainedInTile(piece);
        } catch (Exception e) {
            throw e;
        }

    }

    public Move(Point source, Point destination) {
        this.source = source;
        this.destination = destination;
    }

    public void executeOn(Board board) throws Exception {
        // update the board so that the move is applied
        // At the moment does just diagonal moves
        simpleDiagonalMove(board, this.source, this.destination);
    }
}
