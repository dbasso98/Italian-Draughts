package dssc.exam.draughts;

import java.awt.*;

public class MoveRules {

    public static boolean checkIfPositionsAreValid(Board board, Point source, Point destination) throws Exception{
        try {
            board.isValidPosition(source.x, source.y);
            board.isValidPosition(destination.x, destination.y);
            isNotSamePosition(source, destination);
            isDiagonal(source, destination);
        }
        catch (Exception e) {
            throw e;
        }

        return true;
    }

    private static void isDiagonal(Point source, Point destination) throws Exception {
        if(Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y)) {
            throw new Exception("Checker can only move diagonally!");
        }

    }

    private static void isNotSamePosition(Point source, Point destination) throws Exception {
        if (source.x == destination.x && source.y == destination.y) {
            throw new Exception("Source and destination position cannot be the same!");
        }
    }

    private static boolean isASimpleMove(Point source, Point destination) {
        if(Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y) ||
           Math.abs(destination.x - source.x) != 1) {
            return false;
        }
        return true;
    }

    /*
    public static void isACorrectSimpleMove(Point source, Point destination) {
        if (isASimpleMove(source, destination)){

        }
    }
    */

    public static boolean isASkipMove(Point source, Point destination) {
        if(Math.abs(destination.x - source.x) != Math.abs(destination.y - source.y) ||
           Math.abs(destination.x - source.x) != 2) {
            return false;
        }
        return true;
    }

    /*
    public static void isACorrectSkipMove(Point source, Point destination) {
        if (isASimpleMove(source, destination)){

        }
    }
    */
}