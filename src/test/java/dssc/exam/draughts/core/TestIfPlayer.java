package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
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

import static org.junit.jupiter.api.Assertions.*;

public class TestIfPlayer {

    private Player getPlayerWithDoubledInput(List<Integer> inputList) {
        PlayerInterfaceDouble inputDouble = new PlayerInterfaceDouble();
        inputDouble.setIntegers(inputList);
        return new Player(Color.BLACK, inputDouble);
    }

    static Stream<Arguments> generateDataGetMove() {
        return Stream.of(
                Arguments.of(Arrays.asList(3, 4, 5, 6), 2, 3, 4, 5),
                Arguments.of(Arrays.asList(12, 15, 14, 36), 11, 14, 13, 35)
        );
    }

    @ParameterizedTest
    @MethodSource("generateDataGetMove")
    void readsPosition(List<Integer> inputList, int rowExpected, int columnExpected) {
        Point point = getPlayerWithDoubledInput(inputList).readSource();
        assertEquals(point.x, columnExpected);
        assertEquals(point.y, rowExpected);
    }

    @ParameterizedTest
    @MethodSource("generateDataGetMove")
    void readsPositionsToMakeAMove(List<Integer> inputList,
                                   int sourceColumn, int sourceRow,
                                   int destinationColumn, int destinationRow) {
        Player player = getPlayerWithDoubledInput(inputList);
        Point actualSource = player.readSource();
        Point actualDestination = player.readDestination();
        assertEquals(new Point(sourceRow, sourceColumn), actualSource);
        assertEquals(new Point(destinationRow, destinationColumn), actualDestination);
    }

    @ParameterizedTest
    @MethodSource("generateDataGetMove")
    void alertsForInvalidExpression(List<Integer> inputList) {
        PlayerInterfaceExceptionRaiserDouble input = new
                PlayerInterfaceExceptionRaiserDouble(new InputMismatchException());
        input.setIntegers(inputList);
        Player player = new Player(Color.WHITE, input);
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        player.readSource();
        String expected = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)" +
                           System.lineSeparator() +
                          "Please enter a valid expression" + System.lineSeparator();
        assertEquals(expected, fakeStandardOutput.toString());
    }

    @ParameterizedTest
    @CsvSource({"Michele, Andres, Davide"})
    void testNameGetter(String name) {
        PlayerInterfaceDouble input = new PlayerInterfaceDouble();
        input.setStrings(List.of(name));
        Player player = new Player(Color.BLACK, input);
        player.initializePlayerName(0);
        assertEquals(name, player.name);
    }

    private class PlayerInterfaceExceptionRaiserDouble extends PlayerInterfaceDouble {
        private final InputMismatchException exceptionToThrow;
        private boolean isFirstCall = true;

        PlayerInterfaceExceptionRaiserDouble(InputMismatchException exception) {
            this.exceptionToThrow = exception;
        }

        @Override
        public int getInt() throws InputMismatchException {
            if (isFirstCall) {
                isFirstCall = false;
                throw exceptionToThrow;
            }
            return super.getInt();
        }
    }

    private class PlayerInterfaceDouble implements PlayerInputInterface {
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
        public int getInt() {
            return integers.get(intIndex++);
        }

        @Override
        public Point readPoint() throws InputMismatchException {
            int column = getInt();
            int row = getInt();
            return new Point(row - 1, column - 1);
        }

        @Override
        public void skipToNextInput() {}

        @Override
        public void askName(Player player, int playerNum){}

        @Override
        public Point readSource(){ return null;};

        @Override
        public Point readDestination(){return null;};

        @Override
        public Point readPosition(String message){return null;};
    }
}
