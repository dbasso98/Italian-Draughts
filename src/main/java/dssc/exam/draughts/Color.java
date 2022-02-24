package dssc.exam.draughts;

public enum Color {
    BLACK(-1, 0), WHITE(1, BoardSpecifications.numberOfRows()-1);

    private int direction;
    private int endOfBoardIndex;

    private Color(int direction, int endOfBoardIndex){
        this.direction = direction;
        this.endOfBoardIndex = endOfBoardIndex;
    }
    public int associatedDirection(){
        return this.direction;
    }
    public int associatedEndOfBoardRow(){
        return this.endOfBoardIndex;
    }
}
