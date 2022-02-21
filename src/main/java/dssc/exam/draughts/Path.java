package dssc.exam.draughts;

import java.util.ArrayList;

public class Path {
    private ArrayList<Tile> path = new ArrayList<>();
    private Tile source;
    private int weight;

    public Path (Tile source) {
        this.source = source;
    }

    public Tile getSource() {
        return source;
    }

    public Piece getPieceContainedInSource() {
        return getSource().getPiece();
    }

    public ArrayList<Tile> getPath() {
        return path;
    }

    public Tile getTileAt(int index) {
        return getPath().get(index);
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
}
