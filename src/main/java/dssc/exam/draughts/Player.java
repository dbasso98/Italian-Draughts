package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.EmptyTileException;
import dssc.exam.draughts.exceptions.InvalidColorException;

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

    // maybe to be changes to Tiles?
    private void TestPieceValidity(Point source, Board board) throws Exception {
        Tile sourceTile = board.getTile(source);
        if (sourceTile.isEmpty())
            throw new EmptyTileException("The first Tile you selected contains no Piece");
        if (sourceTile.getPiece().getColor() != getColor())
            throw new InvalidColorException("The piece you intend to move belongs to your opponent");
    }

    Color getColor() {
        return color;
    }

    public void displayHolder() {
        System.out.println("Player " + name + "[" + color + "]:");
    }
}
