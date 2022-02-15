package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.*;

public class Move {

    public static void moveDecider(Board board, Point source, Point destination) throws Exception {
        MoveRules.checkIfPositionsAreValid(board, source, destination);
        var candidateTiles = MoveRules.candidateTilesForSkipMove(board, board.getColorOfPieceAtTile(source));
        int maxWeight;
        if (!candidateTiles.isEmpty())
            maxWeight = Collections.max(candidateTiles.values());
        else
            maxWeight = 0;
        var bestTilesToStartTheSkip = new ArrayList<>(candidateTiles.entrySet().stream()
                .filter(entry -> entry.getValue() == maxWeight)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList()));

        if (isASimpleMove(source, destination)) {
            if (candidateTiles.isEmpty())
                diagonalMove(board, source, destination);
            else
                throw new InvalidMoveException("There are pieces that must capture, try these positions: "
                        + printPositionsOfTiles(bestTilesToStartTheSkip));
        } else {
            if (bestTilesToStartTheSkip.contains(board.getTile(source))) {
                skipMove(board, source, destination);
                if (candidateTiles.get(board.getTile(source)) > 1)
                    throw new IncompleteMoveException("You can continue to skip!", candidateTiles.get(board.getTile(source)));
            } else
                throw new InvalidMoveException("You can select a better skip! Choose one of the tiles at these positions: "
                        + printPositionsOfTiles(bestTilesToStartTheSkip));
        }
    }

    public static void continueToSkip(Board board, Point source, Point destination) throws Exception {
        MoveRules.checkIfPositionsAreValid(board, source, destination);
        if (!isASimpleMove(source, destination))
            skipMove(board, source, destination);
        else
            throw new InvalidMoveException("You HAVE to continue to skip! Look carefully at the board and choose the right position");
    }

    private static String printPositionsOfTiles(ArrayList<Tile> tiles) {
        StringBuilder result = new StringBuilder();
        for (var tile : tiles) {
            result.append("(").append(tile.getPosition().y + 1).append(",").append(tile.getPosition().x + 1).append(") ");
        }
        return result.toString();
    }

    static void skipMove(Board board, Point source, Point destination) throws Exception {
        var sourceTile = board.getTile(source);
        var middleTile = board.getTile(board.getMiddlePosition(source, destination));
        if (middleTile.isEmpty())
            throw new EmptyTileException("Skip move over two empty tiles is not accepted");
        if (middleTile.getPiece().getColor() == sourceTile.getPiece().getColor())
            throw new SameColorException("Color of piece to skip cannot be the same as source piece");
        diagonalMove(board, source, destination);
        middleTile.popPiece();
    }

    public static void diagonalMove(Board board, Point source, Point destination) throws Exception {
        var sourceTile = board.getTile(source);
        var destinationTile = board.getTile(destination);
        MoveRules.checkTileEmptiness(source, sourceTile);
        MoveRules.checkTileNonEmptiness(destination, destinationTile);
        movePiece(sourceTile, destinationTile);
    }

    public static boolean isASimpleMove(Point source, Point destination) throws Exception {
        MoveRules.isDiagonal(source, destination);
        return Math.abs(destination.x - source.x) == 1;
    }

    public static void movePiece(Tile sourceTile, Tile destinationTile) {
        var piece = sourceTile.popPiece();
        destinationTile.setPiece(piece);
    }

    public static void movePiece(Board board, Point source, Point destination) throws Exception {
        movePiece(board.getTile(source), board.getTile(destination));

    }
}
