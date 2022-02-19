package dssc.exam.draughts;

import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    public String name = "";
    private final Color color;
    private final PlayerInputInterface inputInterface;

    private static final String readSourceMessage = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
    private static final String readDestinationMessage = "What are the coordinates (x, y) of the Tile you intend to move the piece to? (e.g. 3 4)";

    Player(Color color, PlayerInputInterface inputInterface) {
        this.color = color;
        this.inputInterface = inputInterface;
    }

    Player(Color color) {
        this.color = color;
        this.inputInterface = new ScannerPlayerInput(new Scanner(System.in));
    }

    Point readPosition() {
        return readPosition(readSourceMessage);
    }

    String askName(int playerNum) {
        System.out.println("Player" + playerNum + "[" + color + "]: Please, insert your name:");
        return inputInterface.getString();
    }

    void setName(String name) {
        this.name = name;
    }

    void initializePlayerName(int playerNum) {
        setName(askName(playerNum));
    }

    Point readPoint() throws InputMismatchException {
        int column = inputInterface.getInt();
        int row = inputInterface.getInt();
        return new Point(row - 1, column - 1);
    }

    Point readPosition(String message) {
        System.out.println(message);

        while (true) {
            try {
                return readPoint();

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid expression");
                inputInterface.skipToNextInput();
            }
        }
    }


    Point readSource() {
        return readPosition(readSourceMessage);
    }

    Point readDestination() {
        return readPosition(readDestinationMessage);
    }

    Color getColor() {
        return color;
    }

    public void displayHolder() {
        System.out.println("Player " + name + "[" + color + "]:");
    }
}
