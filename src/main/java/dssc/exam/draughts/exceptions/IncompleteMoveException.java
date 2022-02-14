package dssc.exam.draughts.exceptions;

public class IncompleteMoveException extends Exception{
    private final int weight;

    public IncompleteMoveException(String message, int weight) {
        super(message);
        this.weight = weight;
    }

    public int getWeight(){
        return this.weight;
    }
}
