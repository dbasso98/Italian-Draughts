package dssc.exam.draughts;

import java.awt.*;

public class MoveRules {

    public static boolean isValidMove(Board board, Point source, Point destination) throws Exception{
        int sourceRow = source.x;
        int sourceColumn = source.y;
        int destinationRow = destination.x;
        int destinationColumn = destination.y;
        // check if both are valid positions
        try {
            isValidPosition(board, source);
            isValidPosition(board, destination);
        }
        catch (Exception e) {
            throw e;
        }

        return true;
    }

    private static void isValidPosition(Board board, Point position) throws Exception {
        if (!board.isValidPosition(position.x, position.y)){
            throw new Exception("Every position must be in range of 0 to 7 for each axis!");
        }
    }
}