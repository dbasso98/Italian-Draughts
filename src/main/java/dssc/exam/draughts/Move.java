package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.*;

public class Move {
    private Board board;
    private Point source;
    private Point destination;

    public Move (Board board, Point source, Point destination) {
        this.board = board;
        this.source = source;
        this.destination = destination;
    }

    public void moveDecider() throws Exception {
        MoveRules.checkIfPositionsAreValid(board, source, destination);
        var candidateTiles = MoveRules.candidatePathsForSkipMove(board, board.getColorOfPieceAtTile(source));
        int maxWeight;
        if (!candidateTiles.isEmpty())
            maxWeight = Collections.max(candidateTiles.values().stream()
                                                       .map(path -> path.getWeight())
                                                       .collect(Collectors.toList()));
        else
            maxWeight = 0;
        var bestTilesToStartTheSkip = new ArrayList<>(candidateTiles.values().stream()
                                                                    .filter(entry -> entry.getWeight() == maxWeight)
                                                                    .map(path -> path.getSource())
                                                                    .collect(Collectors.toList()));
        var tilesContainingKingsAmongBestTiles = new ArrayList<>(bestTilesToStartTheSkip.stream()
                .filter(entry -> entry.getPiece().isKing())
                .collect(Collectors.toList()));

        if (isASimpleMove()) {
            if (candidateTiles.isEmpty())
                diagonalMove();
            else
                throw new InvalidMoveException("There are pieces that must capture, try these positions:"
                        + printPositionsOfTiles(bestTilesToStartTheSkip));
        } else {
            if (bestTilesToStartTheSkip.contains(board.getTile(source))) {
                if (tilesContainingKingsAmongBestTiles.isEmpty() || board.getTile(source).getPiece().isKing())
                    skipMove();
                else
                    throw new InvalidMoveException("You should skip with a King instead of a Man! Choose one of these positions:"
                        + printPositionsOfTiles(tilesContainingKingsAmongBestTiles));
                if (candidateTiles.get(board.getTile(source)).getWeight() > 18)
                    throw new IncompleteMoveException("You can continue to skip!", destination, candidateTiles.get(board.getTile(source)));
            } else
                throw new InvalidMoveException("You can select a better skip! Choose one of the tiles at these positions:"
                        + printPositionsOfTiles(bestTilesToStartTheSkip));
        }
    }

    public void continueToSkip(ArrayList<Tile> path) throws Exception {
        MoveRules.checkIfPositionsAreValid(board, source, destination);
        if (path.stream().map(tile -> tile.getPosition()).collect(Collectors.toList()).contains(destination))
            skipMove();
        else
            throw new InvalidMoveException("You HAVE to continue to skip! Look carefully at the board and choose the right position");
    }

    private String printPositionsOfTiles(ArrayList<Tile> tiles) {
        StringBuilder result = new StringBuilder();
        for (var tile : tiles) {
            result.append(" (")
                    .append(tile.getColumn() + 1)
                    .append(",")
                    .append(tile.getRow() + 1)
                    .append(")");
        }
        return result.toString();
    }

    void skipMove() throws Exception {
        var sourceTile = board.getTile(source);
        var middleTile = board.getTile(board.getMiddlePosition(source, destination));

        if (middleTile.isEmpty())
            throw new EmptyTileException("Skip move over two empty tiles is not accepted");
        if (middleTile.getPiece().getColor() == sourceTile.getPiece().getColor())
            throw new SameColorException("Color of piece to skip cannot be the same as source piece");

        diagonalMove();
        middleTile.popPiece();
    }

    public  void diagonalMove() throws Exception {
        var sourceTile = board.getTile(source);
        var destinationTile = board.getTile(destination);

        MoveRules.checkTileEmptiness(sourceTile);
        MoveRules.checkTileNonEmptiness(destinationTile);

        movePiece(sourceTile, destinationTile);
        updateToKingWhenLastRowIsReached(destinationTile, destinationTile.getRow());
    }

    public boolean isASimpleMove(){
        return Math.abs(destination.x - source.x) == 1;
    }

    public void movePiece(Tile sourceTile, Tile destinationTile) {
        var piece = sourceTile.popPiece();
        destinationTile.setPiece(piece);
    }

    public void movePiece() {
        movePiece(board.getTile(source), board.getTile(destination));
    }

    private void updateToKingWhenLastRowIsReached(Tile destinationTile, int destinationRow){
        var destinationTilePiece = destinationTile.getPiece();
        if (!destinationTilePiece.isKing()){
            if ((destinationTilePiece.getColor() == Color.WHITE && destinationRow == 7) ||
                (destinationTilePiece.getColor() == Color.BLACK && destinationRow == 0))
                destinationTilePiece.upgradeToKing();
        }
    }
}
