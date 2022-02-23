package dssc.exam.draughts;

public enum Color {
    BLACK(-1), WHITE(1);

    private int direction;

    private Color(int direction){
        this.direction = direction;
    }
    public int associatedDirection(){
        return this.direction;
    }
}
