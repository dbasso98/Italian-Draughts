package dssc.exam.draughts;

import java.util.ArrayList;

public class Path {
    private ArrayList<Tile> path;
    private int weight;

    public Path (ArrayList<Tile> path, int weight){
        this.path = path;
        this.weight = weight;
    }

    public ArrayList<Tile> getPath() {
        return path;
    }

    public int getWeight() {
        return weight;
    }
}
