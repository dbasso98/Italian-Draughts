package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    Piece piece = null;
    Point position = null;
    Color tile_color = null;

    Tile(Piece piece, Point position, Color tile_color) {
        this(position, tile_color);
        this.piece = piece;
    }
    Tile(Piece piece, int x, int y, Color tile_color) {
        this(piece, new Point(x,y), tile_color);
    }

    Tile(Point position, Color tile_color) {
        if (check_if_tile_position_is_inside_board(position))
            this.position = position;
        else {
            // to be substituted by an exception
            System.out.println("Position set to default value 0,0");
            this.position = new Point(0,0);
        }
        this.tile_color = tile_color;
    }

    Tile(int x, int y, Color tile_color) {
        this(new Point(x,y), tile_color);
    }

    Tile() {
    }

    // can simplify to return piece since if null then 0 and 1 otherwise?
    boolean is_tile_empty() {
        return piece == null;
    }

    void reset_tile_to_empty() {
        this.piece = null;
    }

    void set_piece_contained_in_tile(Piece piece) {
        this.piece = piece;
    }

    boolean check_if_tile_position_is_inside_board(Point position) {
        return position.getX() <= 7 && position.getX() >= 0
                && position.getY() <= 7 && position.getY() >= 0;
    }

    public boolean is_not_empty() {
        return !(is_tile_empty());
    }
}
