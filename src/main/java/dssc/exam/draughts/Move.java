package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.*;

public class Move {
    private Board board;
    private Point source; // non sarebbe piu sensato che path includa una destination anche e uno gli passa direttamente
    private Point destination; // la path?

    public Move (Board board, Point source, Point destination) {
        this.board = board;
        this.source = source;
        this.destination = destination;
    }

    public void moveDecider() throws DraughtsException {
        MoveRules.checkIfPositionsAreValid(board, source, destination); // maybe this should be in the ctor? You only construct a move if its valid.
        var candidatePaths = MoveRules.candidatePathsForSkipMove(board, board.getColorOfPieceAtTile(source));
        var maxWeight = getWeightOfBestPath(candidatePaths);
        var bestTilesToStartTheSkip = new ArrayList<>(candidatePaths.values().stream()
                                                                    .filter(entry -> entry.getWeight() == maxWeight)
                                                                    .map(Path::getSource)
                                                                    .collect(Collectors.toList()));
        var tilesContainingKingsAmongBestTiles = new ArrayList<>(bestTilesToStartTheSkip.stream()
                                                                    .filter(Tile::containsAKing)
                                                                    .collect(Collectors.toList()));
        if (isASimpleMove()) {
            doASimpleMove(candidatePaths, bestTilesToStartTheSkip);
        } else {
            doASkipMove(candidatePaths, bestTilesToStartTheSkip, tilesContainingKingsAmongBestTiles);
        }
    }

    private int getWeightOfBestPath(HashMap<Tile, Path> candidateTiles) {
        if (candidateTiles.isEmpty())
            return 0;
        else
            return Collections.max(candidateTiles.values().stream()
                    .map(path -> path.getWeight())
                    .collect(Collectors.toList()));
    }

    private boolean isASimpleMove(){
        return Math.abs(destination.x - source.x) == 1;
    }

    private void doASimpleMove(HashMap<Tile, Path> candidatePaths, ArrayList<Tile> bestSourceTiles) throws DraughtsException {
        if (candidatePaths.isEmpty())
            diagonalMove();
        else
            throw new MoveException("There are pieces that must capture, try these positions:"
                    + printPositionsOfTiles(bestSourceTiles));
    }

    private void doASkipMove(HashMap<Tile, Path> candidatePaths, ArrayList<Tile> bestSourceTiles,
                             ArrayList<Tile> sourceTilesContainingKings) throws DraughtsException {
        if (bestSourceTiles.contains(board.getTile(source))) {
            doTheBestSkip(sourceTilesContainingKings);
            canContinueToSkip(candidatePaths);
        } else
            throw new MoveException("You can select a better skip! Choose one of the tiles at these positions:"
                    + printPositionsOfTiles(bestSourceTiles));
    }

    private void doTheBestSkip(ArrayList<Tile> sourceTilesContainingKings) throws DraughtsException {
        if (sourceTilesContainingKings.isEmpty() || board.getTile(source).containsAKing())
            skipMove();
        else
            throw new MoveException("You should skip with a King instead of a Man! Choose one of these positions:"
                    + printPositionsOfTiles(sourceTilesContainingKings));
    }

    private void canContinueToSkip(HashMap<Tile, Path> candidatePaths) throws IncompleteMoveException {
        if (candidatePaths.get(board.getTile(source)).getWeight() > 18) // clarify
            throw new IncompleteMoveException("You can continue to skip!", destination, candidatePaths.get(board.getTile(source)));
    }

    public void continueToSkip(ArrayList<Tile> path) throws DraughtsException {
        MoveRules.checkIfPositionsAreValid(board, source, destination);
        if (path.stream().map(Tile::getPosition).collect(Collectors.toList()).contains(destination))
            skipMove();
        else
            throw new MoveException("You HAVE to continue to skip! Look carefully at the board and choose the right position");
    }

    private void skipMove() throws DraughtsException {
        var sourceTile = board.getTile(source);
        var middleTile = board.getTile(board.getMiddlePosition(source, destination));
        if (middleTile.isEmpty())
            throw new TileException("Skip move over an empty tile is not accepted");
        if (middleTile.getPiece().getColor() == sourceTile.getPiece().getColor())
            throw new MoveException("Color of piece to skip cannot be the same as source piece");
        diagonalMove();
        middleTile.popPiece();
    }


    public void diagonalMove() {
        var sourceTile = board.getTile(source);
        var destinationTile = board.getTile(destination);
        movePiece(sourceTile, destinationTile);
        updateToKingWhenLastRowIsReached(destinationTile, destinationTile.getRow());
    }

    private void movePiece(Tile sourceTile, Tile destinationTile) {
        var piece = sourceTile.popPiece();
        destinationTile.setPiece(piece);
    }

    public void movePiece() {
        movePiece(board.getTile(source), board.getTile(destination));
    }

    private void updateToKingWhenLastRowIsReached(Tile destinationTile, int destinationRow) {
        var destinationTilePiece = destinationTile.getPiece();
        if (!destinationTilePiece.isKing()){
            if ((destinationTilePiece.getColor() == Color.WHITE && destinationRow == 7) ||
                (destinationTilePiece.getColor() == Color.BLACK && destinationRow == 0))
                destinationTilePiece.upgradeToKing();
        }
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
}