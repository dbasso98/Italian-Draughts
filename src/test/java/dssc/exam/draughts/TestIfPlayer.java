package dssc.exam.draughts;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfPlayer {


    @ParameterizedTest
    @CsvSource({"3, 4, 3, 4", "12, 15, 12, 15"})
    void testReadPosition(String xInput, String yInput,
                          int rowExpected, int columnExpected) {

        String fakeInput = xInput + " " + yInput + "\n";
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);

        Point point = new Player(Color.BLACK).readPosition();
        assertEquals(point.x, columnExpected);
        assertEquals(point.y, rowExpected);
    }

    @ParameterizedTest
    @CsvSource({"3, 4, 5, 6, 3, 4, 5, 6", "12, 15, 14, 36, 12, 15, 14, 36"})
    void testGetMove(String sourceXIn, String sourceYIn,
                     String destinationXIn, String destinationYIn,
                     int sourceColumn, int sourceRow,
                     int destinationColumn, int destinationRow) {

        String fakeInput1 = sourceXIn + " " + sourceYIn + "\n ";
        String fakeInput2 = destinationXIn + " " + destinationYIn + "\n";
        String fakeInput = fakeInput1 + fakeInput2;
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);

        Move move = new Player(Color.BLACK).getMove();

        Point sourcePoint = new Point(sourceRow, sourceColumn);
        Point destinationPoint = new Point(destinationRow, destinationColumn);

        assertEquals(move.source, sourcePoint);
        assertEquals(move.destination, destinationPoint);
    }
}
