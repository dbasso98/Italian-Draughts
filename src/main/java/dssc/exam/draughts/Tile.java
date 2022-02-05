package dssc.exam.draughts;

import java.awt.*;

public class Tile {
    Piece piece = null;
    Point position = null;
    Color color = null;

    Tile(Piece piece, Point position, Color color) {
        this(position);
        this.piece = piece;
        this.color = color;
    }

    Tile(Piece piece, int x, int y, Color color) {
        this(piece, new Point(x,y), color);
    }

    Tile(Point position) {
        if (check_if_tile_position_is_inside_board(position))
            this.position = position;
        else {
            // to be substituted by an exception
            System.out.println("Position set to default value 0,0");
            this.position = new Point(0,0);
        }
    }

    Tile(int x, int y) {
        this(new Point(x,y));
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

    boolean check_if_tile_position_is_inside_board(Point position) {
        if (position.getX() <= 7 && position.getX() >= 0
            && position.getY() <= 7 && position.getY() >= 0) {
            return true;
        }

        return false;
    }
}
