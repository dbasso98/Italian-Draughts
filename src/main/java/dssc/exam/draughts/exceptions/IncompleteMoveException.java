package dssc.exam.draughts.exceptions;

import dssc.exam.draughts.Path;
import dssc.exam.draughts.Tile;

import java.awt.*;
import java.util.ArrayList;

public class IncompleteMoveException extends MoveException {
    private final Path skipPath;
    private final Point newSource;


    public IncompleteMoveException(String message, Point newSource, Path skipPath) {
        super(message);
        this.newSource = newSource;
        this.skipPath = skipPath;
    }

    public int getWeight() {
        return skipPath.getWeight();
    }

    public ArrayList<Tile> getSkipPath() {
        return skipPath.getPath();
    }

    public int getNumberOfSkips() {
        return skipPath.getNumberOfSkips();
    }

    public Point getNewSource(){
        return newSource;
    }
}
