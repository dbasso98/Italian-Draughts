package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.*;

public class Move {
    private final Board board;
    private final Point source;
    private final Point destination;
    private final Tile sourceTile;
    private final Tile destinationTile;

    public Move(Board board, Point source, Point destination) {
        this.board = board;
        this.source = source;
        this.destination = destination;
        this.sourceTile = board.getTile(source);
        this.destinationTile = board.getTile(destination);
    }

    public void moveDecider() throws DraughtsException {
        new MoveValidator(board, source, destination).throwExceptionIfPositionsAreInvalid();
        var candidatePaths = CandidateSkipPathBuilder.build(board, board.getColorOfPieceAtTile(source));
        doTheMoveIfPossible(candidatePaths, getMaxWeightTiles(candidatePaths));
    }

    private void doTheMoveIfPossible(HashMap<Tile, Path> candidatePaths, ArrayList<Tile> maxWeightTiles) throws DraughtsException {
        if (isASimpleMove())
            doASimpleMoveIfPossible(candidatePaths, maxWeightTiles);
        else
            doASkipMove(candidatePaths, maxWeightTiles, getMaxWeightTilesWithKing(maxWeightTiles));
    }

    private ArrayList<Tile> getMaxWeightTilesWithKing(ArrayList<Tile> tilesWithMaxWeight) {
        return new ArrayList<>(tilesWithMaxWeight.stream()
                .filter(Tile::containsAKing)
                .collect(Collectors.toList()));
    }

    private ArrayList<Tile> getMaxWeightTiles(HashMap<Tile, Path> candidatePaths) {
        return new ArrayList<>(candidatePaths.values().stream()
                .filter(entry -> entry.getWeight() == getWeightOfBestPath(candidatePaths))
                .map(Path::getSource)
                .collect(Collectors.toList()));
    }

    private int getWeightOfBestPath(HashMap<Tile, Path> candidateTiles) {
        if (candidateTiles.isEmpty())
            return 0;

        return Collections.max(candidateTiles.values().stream()
                .map(Path::getWeight)
                .collect(Collectors.toList()));
    }

    private boolean isASimpleMove() {
        return Math.abs(destination.x - source.x) == 1;
    }

    private void doASimpleMoveIfPossible(HashMap<Tile, Path> candidatePaths, ArrayList<Tile> tilesWithMaxWeight) throws DraughtsException {
        if (!candidatePaths.isEmpty()) {
            throw new MoveException("There are pieces that must capture, try these positions:"
                    + printPositionsOfTiles(tilesWithMaxWeight));
        }
        doASimpleMove();
    }

    void doASimpleMove() {
        movePiece(sourceTile, destinationTile);
        updateToKingWhenLastRowIsReached(destinationTile.getPiece(), destinationTile.getRow());
    }

    private void movePiece(Tile sourceTile, Tile destinationTile) {
        var piece = sourceTile.popPiece();
        destinationTile.setPiece(piece);
    }

    public void movePiece() {
        movePiece(sourceTile, destinationTile);
    }

    private void updateToKingWhenLastRowIsReached(Piece destinationTilePiece, int destinationRow) {
        if (!destinationTilePiece.isKing()) {
            if (destinationTilePiece.getColor().associatedEndOfBoardRow() == destinationRow)
                destinationTilePiece.upgradeToKing();
        }
    }

    private void doASkipMove(HashMap<Tile, Path> candidatePaths, ArrayList<Tile> bestSourceTiles,
                             ArrayList<Tile> MaxWeightTilesWithKing) throws DraughtsException {
        if (!bestSourceTiles.contains(sourceTile)) {
            throw new MoveException("You can select a better skip! Choose one of the tiles at these positions:"
                    + printPositionsOfTiles(bestSourceTiles));
        }
        doTheBestSkip(MaxWeightTilesWithKing);
        throwIncompleteMoveIfContinueToSkip(candidatePaths);
    }

    private void doTheBestSkip(ArrayList<Tile> MaxWeightTilesWithKing) throws DraughtsException {
        if (!MaxWeightTilesWithKing.isEmpty() && !sourceTile.containsAKing())
            throw new MoveException("You should skip with a King instead of a Man! Choose one of these positions:"
                    + printPositionsOfTiles(MaxWeightTilesWithKing));
        skipMove();

    }

    private void throwIncompleteMoveIfContinueToSkip(HashMap<Tile, Path> candidatePaths) throws IncompleteMoveException {
        if (candidatePaths.get(sourceTile).getWeight() > 18) // clarify
            throw new IncompleteMoveException("You can continue to skip!", destination, candidatePaths.get(board.getTile(source)));
    }

    public void continueToSkip(ArrayList<Tile> path) throws DraughtsException {
        new MoveValidator(board, source, destination).throwExceptionIfPositionsAreInvalid();
        if (path.stream().map(Tile::getPosition).collect(Collectors.toList()).contains(destination))
            skipMove();
        else
            throw new MoveException("You HAVE to continue to skip! Look carefully at the board and choose the right position");
    }

    private void skipMove() throws DraughtsException {
        var middleTile = board.getTile(board.getMiddlePosition(source, destination));
        if (middleTile.isEmpty())
            throw new TileException("Skip move over an empty tile is not accepted");
        if (middleTile.getPiece().getColor() == sourceTile.getPiece().getColor())
            throw new MoveException("Color of piece to skip cannot be the same as source piece");
        doASimpleMove();
        middleTile.popPiece();
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