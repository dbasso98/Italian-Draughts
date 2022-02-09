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
                          int rowExpected, int columnExpected ) {
        String fakeInput = rowInput + " " + columnInput + "\n";
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);

        Player player = new Player();
        Point point = player.readPosition();
        assertEquals(point.x, rowExpected);
        assertEquals(point.y, columnExpected);
    }
}
