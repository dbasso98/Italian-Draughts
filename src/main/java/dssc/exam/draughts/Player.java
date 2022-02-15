package dssc.exam.draughts;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    public String name = "";
    private final Color color;
    private final Scanner input = new Scanner(System.in);

    private static final String readSourceMessage = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
    private static final String readDestinationMessage = "What are the coordinates (x, y) of the Tile you intend to move the piece to? (e.g. 3 4)";

    Player(Color color) {
        this.color = color;
    }

    Point readPosition() {
        return readPosition(readSourceMessage);
    }

    String askName(int playerNum) {
        System.out.println("Player" + playerNum + "[" + color + "]: Please, insert your name:");
        return input.next();
    }

    void setName(String name) {
        this.name = name;
    }

    void askAndSetName(int playerNum) {
        setName(askName(playerNum));
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
//        Point source = readPosition(readSourceMessage);
        Point source = getSource();
//        Point destination = readPosition(readDestinationMessage);
        Point destination = getDestination();
//        return new Move(source, destination);
        return new Move(source, destination);
    }

    Point getSource(){
        return readPosition(readSourceMessage);
    }

    Point getDestination(){
        return readPosition(readDestinationMessage);
    }

    Color getColor() {
        return color;
    }

    public void displayHolder() {
        System.out.println("Player " + name + "[" + color + "]:");
    }
}
