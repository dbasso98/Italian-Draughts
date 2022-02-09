package dssc.exam.draughts;

import java.awt.*;
import java.util.Scanner;

public class Player {
    public final Color color;
    private static final String readSourceMessage = "What are the coordinates (row, column) of the piece you intend to move? \n(e.g. 3 4)";
    private static final String readDestinationMessage = "What are the coordinates (row, column) of the Tile you intend to move the piece to? \n(e.g. 3 4)";
    private final Scanner input = new Scanner(System.in);

    Player(Color color) {
        this.color = color;
    }

    Point readPosition() {
        return readPosition(readSourceMessage);
    }

    Point readPosition(String message) {
        System.out.println(message);
        int row = input.nextInt();
        int column = input.nextInt();
        return new Point(row, column);
    }

    Move getMove() {
        Point source = readPosition(readSourceMessage);
        Point destination = readPosition(readDestinationMessage);
        return new Move(source, destination);
    }

}
