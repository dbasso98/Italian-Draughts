package dssc.exam.draughts.core;

import dssc.exam.draughts.utilities.Color;

import java.util.ArrayList;

public class Path {
    private ArrayList<Tile> path = new ArrayList<>();
    private final Tile source;
    private int weight;

    public Path(Tile source) {
        this.source = source;
    }

    public static Path copy(Path other) {
        var newPath = new Path(other.source);
        newPath.path = new ArrayList<>(other.path);
        newPath.weight = other.weight;
        return newPath;
    }

    public Tile getSource() {
        return source;
    }

    private Piece getPieceContainedInSource() {
        return getSource().getPiece();
    }

    public ArrayList<Tile> getPath() {
        return path;
    }

    public void setPath(ArrayList<Tile> newPath) {
        this.path = newPath;
    }

    public void addTile(Tile tile) {
        getPath().add(tile);
    }

    public boolean containsTile(Tile tile) {
        return getPath().contains(tile);
    }

    public int getNumberOfSkips() {
        return getPath().size() - 1;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean startsFromKing() {
        return getPieceContainedInSource().isKing();
    }

    public Color getSourceColor() {
        return getPieceContainedInSource().getColor();
    }
}
