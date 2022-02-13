package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.*;

public class Move {
    public final Point source;
    public final Point destination;

    public static void moveDecider(Board board, Point source, Point destination) throws Exception{
        try{
            MoveRules.checkIfPositionsAreValid(board, source, destination);
            var candidateTiles = MoveRules.candidateTilesForSkipMove(board, board.getColorOfPieceAtTile(source));
            var maxWeight = Collections.max(candidateTiles.values());
            var bestTilesToStartTheSkip = new ArrayList<> (candidateTiles.entrySet().stream()
                    .filter(entry -> entry.getValue() == maxWeight)
                    .map(entry -> entry.getKey())
                    .collect(Collectors.toList()));
            if(bestTilesToStartTheSkip.contains(board.getTile(source))) {
                skipMove(board, source, destination);
            }
            else {
                throw new Exception("You can select a better skip! Choose tile at position "
                        + printPositionsOfTiles(bestTilesToStartTheSkip));
            }
            if(candidateTiles.isEmpty()){
                if(isASimpleMove(source, destination)){
                    diagonalMove(board, source, destination);
                }
                else{
                    throw new InvalidMoveException("This piece cannot move there. Pieces can only move diagonally!");
                }
            }
            throw new InvalidMoveException("There are pieces that must capture, try these positions: "
                    + printPositionsOfTiles(bestTilesToStartTheSkip));
        }
        catch(Exception e){
            throw e;
        }
    }

    private static String printPositionsOfTiles(ArrayList<Tile> tiles) {
        StringBuilder result = new StringBuilder();
        for(var tile : tiles){
            result.append("(").append(tile.getTilePosition().y + 1).append(",").append(tile.getTilePosition().x + 1).append(") ");
        }
        return result.toString();
    }

    static void skipMove(Board board, Point source, Point destination) throws Exception{
        try {
            var sourceTile = board.getTile(source);
            var middleTile = board.getTile(board.getMiddlePosition(source,destination));
            if(middleTile.isTileEmpty())
                throw new EmptyTileException("Skip move over two empty tiles is not accepted");
            if(middleTile.getPieceOfTile().getColorOfPiece() == sourceTile.getPieceOfTile().getColorOfPiece())
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
            movePiece(sourceTile, destinationTile);
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

    public static void movePiece(Tile sourceTile, Tile destinationTile) {
        var piece = sourceTile.popPieceContainedInTile();
        destinationTile.setPieceContainedInTile(piece);
    }

    public static void movePiece(Board board, Point source, Point destination) throws Exception{
        try {
            movePiece(board.getTile(source), board.getTile(destination));
        }
        catch (Exception e) {
            throw e;
        }
    }

    public Move(Point source, Point destination) {
        this.source = source;
        this.destination = destination;
    }

    public void executeOn(Board board) throws Exception{
        // update the board so that the move is applied
        // At the moment does just diagonal moves
        try {
            movePiece(board, source, destination);
        } catch (Exception e) {
            throw e;
        }
    }
}
