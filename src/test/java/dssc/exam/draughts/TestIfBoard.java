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
        assertEquals(24, board.getTotalNumberOfPieces());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, 12", "WHITE, 12"})
    void has12PiecesOfColor(Color color, int number){
        assertEquals(number, board.getPiecesOfColor(color));
    }

    @ParameterizedTest
    @CsvSource({"BLACK, 16", "BLACK, 18", "BLACK, 20", "BLACK, 22",
                "BLACK, 9", "BLACK, 11", "BLACK, 13", "BLACK, 15",
                "BLACK, 0", "BLACK, 2", "BLACK, 4", "BLACK, 6",})
    void has12BlackPiecesInFirstThreeRows(Color color, int position){
        assertEquals(board.getTile(position).getPiece().getColor(), color);
    }


}
