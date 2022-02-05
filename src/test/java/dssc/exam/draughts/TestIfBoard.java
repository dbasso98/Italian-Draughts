package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfBoard {
    Board board = new Board();
    @Test
    void has_size_of_64_Tiles(){
        assertEquals(64, board.get_size_of_board());
    }

    @Test
    void has_24_pieces(){
        assertEquals(24, board.get_number_pieces());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, 12", "WHITE, 12"})
    void has_12_black_pieces(Color color, int number){
        assertEquals(number, board.get_pieces(Color.BLACK));
    }


}
