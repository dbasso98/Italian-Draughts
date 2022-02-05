package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfBoard {
    Board board = new Board();
    @Test
    void hasSizeOf64Tiles(){
        assertEquals(64, board.getSizeOfBoard());
    }

    @Test
    void has24Pieces(){
        assertEquals(24, board.getNumberPieces());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, 12", "WHITE, 12"})
    void has_12_black_pieces(Color color, int number){
        assertEquals(number, board.getPieces(color));
    }


}
