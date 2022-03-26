package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;
import dssc.exam.draughts.exceptions.SurrenderException;
import dssc.exam.draughts.utilities.Color;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfPlayer {

    private PlayerInterfaceDouble getDoubledInputInterface(List<Integer> inputList) {
        PlayerInterfaceDouble inputDouble = new PlayerInterfaceDouble();
        inputDouble.setIntegers(inputList);
        return inputDouble;
    }

    static Stream<Arguments> generateDataGetMove() {
        return Stream.of(
                Arguments.of(Arrays.asList(3, 4, 5, 6), 2, 3, 4, 5),
                Arguments.of(Arrays.asList(12, 15, 14, 36), 11, 14, 13, 35)
        );
    }

    @ParameterizedTest
    @MethodSource("generateDataGetMove")
    void readsPosition(List<Integer> inputList, int rowExpected, int columnExpected)
            throws SurrenderException {
        var in = getDoubledInputInterface(inputList);
        Point point = in.readSource();
        assertEquals(point.x, columnExpected);
        assertEquals(point.y, rowExpected);
    }

    @ParameterizedTest
    @MethodSource("generateDataGetMove")
    void readsPositionsToMakeAMove(List<Integer> inputList,
                                   int sourceColumn, int sourceRow,
                                   int destinationColumn, int destinationRow)
            throws SurrenderException {
        var in = getDoubledInputInterface(inputList);
        Point actualSource = in.readSource();
        Point actualDestination = in.readDestination();
        assertEquals(new Point(sourceRow, sourceColumn), actualSource);
        assertEquals(new Point(destinationRow, destinationColumn), actualDestination);
    }

    @ParameterizedTest
    @MethodSource("generateDataGetMove")
    void alertsForInvalidExpression(List<Integer> inputList) throws SurrenderException {
        PlayerInterfaceInputMismatchExceptionRaiserDouble input = new
                PlayerInterfaceInputMismatchExceptionRaiserDouble(new InputMismatchException());
        input.setIntegers(inputList);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        input.readSource();
        String expected = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)" +
                System.lineSeparator() + "or press s to surrender" + System.lineSeparator() +
                "Please enter a valid expression" + System.lineSeparator();
        assertEquals(expected, fakeStandardOutput.toString());
    }

    @ParameterizedTest
    @CsvSource({"Michele, Andres, Davide"})
    void testNameGetter(String name) {
        PlayerInterfaceDouble input = new PlayerInterfaceDouble();
        input.setStrings(List.of(name));
        Player player = new Player(Color.BLACK, input);
        input.initializePlayerName(player, 0);
        assertEquals(name, player.name);
    }

    private class PlayerInterfaceInputMismatchExceptionRaiserDouble extends PlayerInterfaceDouble {
        private final InputMismatchException exceptionToThrow;
        private boolean isFirstCall = true;

        PlayerInterfaceInputMismatchExceptionRaiserDouble(InputMismatchException exception) {
            this.exceptionToThrow = exception;
        }

        @Override
        public int getInt() throws InputMismatchException, SurrenderException {
            if (isFirstCall) {
                isFirstCall = false;
                throw exceptionToThrow;
            }
            return super.getInt();
        }

    }

    private class PlayerInterfaceDouble extends ScannerPlayerInput {
        private int stringIndex = 0;
        private int intIndex = 0;
        private List<String> strings;
        private List<Integer> integers;

        void setIntegers(List<Integer> integers) {
            this.integers = integers;
        }

        void setStrings(List<String> strings) {
            this.strings = strings;
        }

        @Override
        public String getString() {
            return strings.get(stringIndex++);
        }

        @Override
        public int getInt() throws SurrenderException, InputMismatchException {
            return integers.get(intIndex++);
        }

        @Override
        public Point readPoint() throws InputMismatchException, SurrenderException {
            int column = getInt();
            int row = getInt();
            return new Point(row - 1, column - 1);
        }

        @Override
        public void skipToNextInput() {
        }
    }
}
