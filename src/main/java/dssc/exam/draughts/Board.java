package dssc.exam.draughts;

import java.util.ArrayList;

public class Board {
    public ArrayList<Tile> board = new ArrayList<Tile>(64);

    Board(String leave_empty){
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column ++) {
                var tile = new Tile(row, column);
                board.add(tile);
            }
        }
    }

    Board(){
        // initialize an empty board first
        this("");
        // fill first three rows with black checkers

    }


}
