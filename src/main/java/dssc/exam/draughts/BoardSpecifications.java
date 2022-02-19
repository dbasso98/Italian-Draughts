package dssc.exam.draughts;

public class BoardSpecifications {
    private static final int size = 64;
    private static final int maxRows = 8;
    private static final int maxColumns = maxRows;
    private static final int piecesPerPlayer = 12;

    public static final int boardSize(){
        return size;
    }
    public static final int numberOfRows(){
        return maxRows;
    }
    public static final int numberOfColumns(){
        return maxColumns;
    }
    public static final int numberOfPiecesPerPlayer(){
        return piecesPerPlayer;
    }
    public static final int initialAreaOccupiedByOnePlayer(){
        return numberOfPiecesPerPlayer()*2;
    }
}
