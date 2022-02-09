package dssc.exam.draughts;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayer {


    @ParameterizedTest
    @CsvSource({"3, 4, 3, 4", "12, 15, 12, 15"})
    void testReadPosition(String rowInput, String columnInput,
                          int rowExpected, int columnExpected) {

        String fakeInput = rowInput + " " + columnInput + "\n";
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);

        Point point = new Player(Color.BLACK).readPosition();
        assertEquals(point.x, rowExpected);
        assertEquals(point.y, columnExpected);
    }

    @ParameterizedTest
    @CsvSource({"3, 4, 5, 6, 3, 4, 5, 6", "12, 15, 14, 36, 12, 15, 14, 36"})
    void testGetMove(String sourceRowIn, String sourceColumnIn,
                     String destinationRowIn, String destinationColumIn,
                     int sourceRow, int sourceColumn,
                     int destinationRow, int destinationColumn) {

        String fakeInput1  = sourceRowIn + " " + sourceColumnIn + "\n ";
        String fakeInput2 = destinationRowIn + " " + destinationColumIn + "\n";
        String fakeInput = fakeInput1 + fakeInput2;
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);

        Move move = new Player(Color.BLACK).getMove();

        Point sourcePoint = new Point(sourceRow, sourceColumn);
        Point destinationPoint = new Point(destinationRow, destinationColumn);

        assertEquals(move.source, sourcePoint);
        assertEquals(move.destinantion, destinationPoint);
    }
}
