package dssc.exam.draughts;

public class Tile {
    Piece piece = null;

    Tile(Piece piece) {
        this.piece = piece;
    }

    Tile() {
    }

    boolean empty(){
        return piece == null;
    }
}
