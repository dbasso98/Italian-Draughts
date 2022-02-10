package dssc.exam.draughts;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private final Color color;
    private static final String readSourceMessage = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
    private static final String readDestinationMessage = "What are the coordinates (x, y) of the Tile you intend to move the piece to? (e.g. 3 4)";
    private final Scanner input = new Scanner(System.in);

    Player(Color color) {
        this.color = color;
    }
    Point readPosition() {
        return readPosition(readSourceMessage);
    }

    Point readPosition(String message) {
        System.out.println(message);
        int row = 0;
        int column = 0;

        boolean IsInputNotNumerical = true;
        while (IsInputNotNumerical) {
            try {
                column = input.nextInt();
                row = input.nextInt();
                IsInputNotNumerical = false;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid expression");
                input.next();
            }
        }
        return new Point(row - 1, column - 1);
    }

    Move getMove() {
        Point source = readPosition(readSourceMessage);
        Point destination = readPosition(readDestinationMessage);
        return new Move(source, destination);
    }

    Color getColor() {
        return color;
    }

}
