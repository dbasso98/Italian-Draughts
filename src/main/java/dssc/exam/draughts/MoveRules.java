package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.EmptyTileException;
import dssc.exam.draughts.exceptions.NonEmptyTileException;
import dssc.exam.draughts.exceptions.NotDiagonalMoveException;
import dssc.exam.draughts.exceptions.SamePositionException;

import java.awt.*;
import java.util.BitSet;
import java.util.Map;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception {
        try {
            board.isValidPosition(source);
            board.isValidPosition(destination);
            board.isBlackTile(board.getTile(source));
            board.isBlackTile(board.getTile(destination));
            isNotSamePosition(source, destination);
        } catch (Exception e) {
            throw e;
        }

        return true;
    }

    static void isDiagonal(Point source, Point destination) throws NotDiagonalMoveException {
        if (Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y)) {
            throw new NotDiagonalMoveException("Checker can only move diagonally!");
        }
    }

    static void isNotSamePosition(Point source, Point destination) throws SamePositionException {
        if (source.x == destination.x && source.y == destination.y) {
            throw new SamePositionException("Source and destination position cannot be the same!");
        }
    }

    static void checkTileNonEmptiness(Point destination, Tile destinationTile) throws NonEmptyTileException {
        if (destinationTile.isTileNotEmpty()) {
            throw new NonEmptyTileException("Cannot move since tile at (" + (destination.y + 1) + "," + (destination.x + 1) + ") is not empty");
        }
    }

    static void checkTileEmptiness(Point source, Tile sourceTile) throws EmptyTileException {
        if (sourceTile.isTileEmpty()) {
            throw new EmptyTileException("Cannot move since tile at (" + (source.y + 1) + "," + (source.x + 1) + ") is empty");
        }
    }

    static BitSet candidateTilesForSkipMove(Board board, Color color) {
        var listOfTiles = board.getTilesContainingPieceOfColor(color);
        var bitSetOfCandidatesTiles = new BitSet(64);
        for (Tile tile : listOfTiles) {
            // check che la tile contiene un king o no:
            // non king
            // w = checkAdjacent(board, tile, direction)
            // se sei king
            // check(su giu) w =
            int weight = 0;
            weight += checkAdjacentDiagonal(board, tile, tile.getPieceOfTile().getColorOfPiece(), 1, weight);
                //bitSetOfCandidatesTiles.set(board.getIndex(tile.getTileRow(), tile.getTileColumn()));
        }
        return bitSetOfCandidatesTiles;
    }

    static int checkAdjacentDiagonal(Board board, Tile tile, Color originalColorOfPiece, int direction, int weight) {
        var tileRow = tile.getTileRow();
        var tileColumn = tile.getTileColumn();
        Map<String, Integer> side = Map.ofEntries(Map.entry("LEFT", -1), Map.entry("RIGHT", 1));
        for (var diagonalLR : side.values()) {
            try {
                board.isValidPosition(tileRow + direction, tileColumn + diagonalLR);
                board.isValidPosition(tileRow + 2 * direction, tileColumn + 2 * diagonalLR);
                var firstDiagonalTile = board.getTileInDiagonalOffset(tile, direction, diagonalLR);
                var secondDiagonalTile = board.getTileInDiagonalOffset(firstDiagonalTile, direction, diagonalLR);
                var check = (firstDiagonalTile.isTileNotEmpty() &&
                        firstDiagonalTile.getPieceOfTile().getColorOfPiece() == originalColorOfPiece &&
                        secondDiagonalTile.isTileEmpty());
                if (!check) {
                    return ++weight;
                } else {
                    weight += checkAdjacentDiagonal(board, secondDiagonalTile, originalColorOfPiece, direction, weight);
                    return weight;
                }


            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }




}
