package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;
import dssc.exam.draughts.display.DisplayGame;
import dssc.exam.draughts.display.DisplayPlayer;
import dssc.exam.draughts.utilities.Color;

import java.awt.*;
import java.util.InputMismatchException;

public class Player {
    public String name = "";
    private final Color color;
    private final PlayerInputInterface inputInterface;
    private final DisplayPlayer displayPlayer;

    public Player(Color color, PlayerInputInterface inputInterface, DisplayPlayer displayPlayer) {
        this.color = color;
        this.inputInterface = inputInterface;
        this.displayPlayer = displayPlayer;
    }

    public Player(Color color, PlayerInputInterface inputInterface) {
        this(color, inputInterface, new DisplayPlayer());
    }

    public Player(Color color) {
        this(color, new ScannerPlayerInput());
    }

    public void initializePlayerName(int playerNum) {
        setName(getName(playerNum));
    }

    private String getName(int playerNum) {
        inputInterface.askName(this, playerNum);
        return inputInterface.getString();
    }

    public void setName(String name) {
        this.name = name;
    }


    public Point readSource() {
        return readPosition(new DisplayGame().getSourceMessage());
    }

    public Point readDestination() {
        return readPosition(new DisplayGame().getDestinationMessage());
    }

    public Point readPosition(String message) {
        new DisplayGame().message(message);

        while (true) {
            try {
                return inputInterface.readPoint();

            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid expression");
                inputInterface.skipToNextInput();
            }
        }
    }

    public Color getColor() {
        return color;
    }


}
