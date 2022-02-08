package dssc.exam.draughts;

import java.awt.*;

public class MoveRules {

    public static boolean isPositionOfMoveValid(Board board, Point source, Point destination) throws Exception{
        // secondo me questo e quello sotto si ripetono....
        int sourceRow = source.x;
        int sourceColumn = source.y;
        int destinationRow = destination.x;
        int destinationColumn = destination.y;
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
        try{
            board.isValidPosition(position.x, position.y);
        }
        catch(Exception e){
            throw e;
        }

    }
}