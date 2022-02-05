package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    Piece piece = null;
    Point position = null;

    Tile(Piece piece, Point position) {
        this(position);
        this.piece = piece;
    }

    Tile(Piece piece, int x, int y) {
        this(x,y);
        this.piece = piece;
    }

    Tile(Point position) {
        this.position = position;
    }

    Tile(int x, int y) {
        this.position.setLocation(x,y);
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
