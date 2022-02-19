package dssc.exam.draughts;

import dssc.exam.draughts.IOInterfaces.OutInterface;
import dssc.exam.draughts.IOInterfaces.OutInterfaceStdout;
import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    public String name = "";
    private final Color color;
    private final PlayerInputInterface inputInterface;
    private final OutInterface out;

    private static final String readSourceMessage = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
    private static final String readDestinationMessage = "What are the coordinates (x, y) of the Tile you intend to move the piece to? (e.g. 3 4)";

    Player(Color color, PlayerInputInterface inputInterface, OutInterface outInterface) {
        this.color = color;
        this.inputInterface = inputInterface;
        this.out = outInterface;
    }


    Player(Color color, PlayerInputInterface inputInterface) {
        this(color, inputInterface, new OutInterfaceStdout());
    }

    Player(Color color) {
        this(color, new ScannerPlayerInput(new Scanner(System.in)));
    }

    Point readPosition() {
        return readPosition(readSourceMessage);
    }

    String getName(int playerNum) {
        out.askName(this, playerNum);
        return inputInterface.getString();
    }

    void setName(String name) {
        this.name = name;
    }

    void initializePlayerName(int playerNum) {
        setName(getName(playerNum));
    }

    Point readPoint() throws InputMismatchException {
        int column = inputInterface.getInt();
        int row = inputInterface.getInt();
        return new Point(row - 1, column - 1);
    }

    Point readPosition(String message) {
        out.displayMessage(message);

        while (true) {
            try {
                return readPoint();

            } catch (InputMismatchException e) {
                out.displayMessage("Please enter a valid expression");
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

    public Color getColor() {
        return color;
    }

}
