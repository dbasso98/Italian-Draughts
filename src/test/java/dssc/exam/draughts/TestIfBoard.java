package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import java.beans.BeanProperty;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfBoard {
    Board board = new Board();
    @Test
    void has_size_of_64_Tiles(){
        assertEquals(board.board.size(), 64);
    }

    @Test
    void has_24_pieces(){
        assertEquals(64, board.get_size_of_board());
    }
    @Test
    void has_12_black_pieces(){
        assertEquals(12, board.get_black_pieces());
    }

    @Test
    void has_12_white_pieces(){
        assertEquals(12, board.get_white_pieces());
    }



}
