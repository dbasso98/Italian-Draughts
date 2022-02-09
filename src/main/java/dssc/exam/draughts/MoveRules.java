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
        }
        catch (Exception e) {
            throw e;
        }

        return true;
    }
}