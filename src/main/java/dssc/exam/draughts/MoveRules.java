package dssc.exam.draughts;

import java.awt.*;

public class MoveRules {

    public static boolean checkIfPositionIsValid(Board board, Point source, Point destination) throws Exception{
        int sourceRow = source.x;
        int sourceColumn = source.y;
        int destinationRow = destination.x;
        int destinationColumn = destination.y;
        try {
            board.isValidPosition(source.x, source.y);
            board.isValidPosition(destination.x, destination.y);
            isSamePosition(source, destination);
        }
        catch (Exception e) {
            throw e;
        }

        return true;
    }

    public static void isSamePosition(Point source, Point destination) throws Exception {
        if (source.x == destination.x && source.y == destination.y) {
            throw new Exception("Source and destination position cannot be the same!");
        }
    }
}