package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;
import java.awt.*;
import java.util.stream.*;

public class Move {
    public final Point source;
    public final Point destination;

    public static void moveDecider(Board board, Point source, Point destination) throws Exception{
        try{
            MoveRules.checkIfPositionsAreValid(board, source, destination);

            var candidateTiles = MoveRules.candidateTilesForSkipMove(board, board.getPieceAtTile(source).getColorOfPiece());

            if(candidateTiles.get(board.getIndex(source.x, source.y))){
                skipMove(board, source, destination);
            }
            else{
                //candidateTiles.stream().filter(i -> i == 1).mapToObj(i -> board.getTile(i))
                if(candidateTiles.isEmpty()){
                    if( isASimpleMove(source, destination) ){
                        diagonalMove(board, source, destination);
                    }
                }
                else
                    throw new InvalidMoveException("There are pieces that must capture." + IntStream.range(0, candidateTiles.size()).filter(candidateTiles::get).mapToObj(board::getTile).collect(Collectors.toList()));
            }
        }
        catch(Exception e){
            throw e;
        }
    }

    static void skipMove(Board board, Point source, Point destination) throws Exception{
        try {
            var sourceTile = board.getTile(source);
            var middleTile = board.getTile(board.getMiddlePosition(source,destination));
            if(middleTile.isTileEmpty())
                throw new EmptyTileException("Skip move over two empty tiles is not accepted");
            if(middleTile.getTilePiece().getColorOfPiece() == sourceTile.getTilePiece().getColorOfPiece())
                throw new SameColorException("Color of piece to skip cannot be the same as source piece");
            diagonalMove(board, source, destination);

            middleTile.popPieceContainedInTile();
        }
        catch(Exception e){
            throw e;
        }
    }

    public static void diagonalMove(Board board, Point source, Point destination) throws Exception {
        try {
            var sourceTile = board.getTile(source);
            var destinationTile = board.getTile(destination);
            MoveRules.checkTileEmptiness(source, sourceTile);
            MoveRules.checkTileNonEmptiness(destination, destinationTile);
            movePiece(board, destination, sourceTile);
        } catch (Exception e) {
            throw e;
        }

    }

    public static boolean isASimpleMove(Point source, Point destination) throws Exception {
        try {
            // if we generalize to start - end position which indicates final absolute position after eating many times
            // checking if diagonal is not needed. but is needed in every substep.
            MoveRules.isDiagonal(source, destination);
            return Math.abs(destination.x - source.x) == 1;
        } catch (Exception e) {
            throw e;
        }
    }

    public static void movePiece(Board board, Point destination, Tile sourceTile) {
        var piece = sourceTile.popPieceContainedInTile();
        board.getTile(destination).setPieceContainedInTile(piece);
    }

    public Move(Point source, Point destination) {
        this.source = source;
        this.destination = destination;
    }

    public void executeOn(Board board) throws Exception {
        // update the board so that the move is applied
        // At the moment does just diagonal moves
        diagonalMove(board, this.source, this.destination);
    }
}
