package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    Piece piece = null;
    Point coords = null;

    Tile(Piece piece, Point coords) {
        this(coords);
        this.piece = piece;
    }

    Tile(Piece piece, int x, int y) {
        this(x,y);
        this.piece = piece;
    }

    Tile(Point coords) {
        this.coords = coords;
    }

    Tile(int x, int y) {
        this.coords.setLocation(x,y);
    }

    Tile() {
    }

    boolean is_tile_empty() {
        return piece == null;
    }

    void reset_tile() {
        this.piece = null;
    }

    void set_piece_contained_in_tile(Piece piece) {
        this.piece = piece;
    }
}
