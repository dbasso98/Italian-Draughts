package dssc.exam.draughts.exceptions;

import java.awt.*;

public class IncompleteMoveException extends Exception {
    private final int weight;
    private final Point newSource;


    public IncompleteMoveException(String message, Point newSource, int weight) {
        super(message);
        this.newSource = newSource;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Point getNewSource(){
        return newSource;
    }
}
