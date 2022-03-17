package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;
import dssc.exam.draughts.display.DisplayPlayer;
import dssc.exam.draughts.utilities.Color;

import java.awt.*;
import java.util.InputMismatchException;

public class Player {
    public String name = "";
    private final Color color;
    private final PlayerInputInterface inputInterface;
    private final DisplayPlayer out;

    // queste variabili non dovrebbero essere parte della input interface? che senso ha che stanno qui?
    private static final String readSourceMessage = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
    private static final String readDestinationMessage = "What are the coordinates (x, y) of the Tile you intend to move the piece to? (e.g. 3 4)";

    public Player(Color color, PlayerInputInterface inputInterface, DisplayPlayer displayPlayer) {
        this.color = color;
        this.inputInterface = inputInterface;
        this.out = displayPlayer;
    }

    public Player(Color color, PlayerInputInterface inputInterface) {
        this(color, inputInterface, new DisplayPlayer());
    }

    public Player(Color color) {
        this(color, new ScannerPlayerInput());
    }
    // stesso discorso di board, secondo me tutte queste astrazioni non servono quando nell'atto pratico usiamo solo un ctor

    public void initializePlayerName(int playerNum) {
        setName(getName(playerNum));
    }

    private String getName(int playerNum) {
        out.askName(this, playerNum);
        return inputInterface.getString();
    }

    public void setName(String name) {
        this.name = name;
    } // qual'è il senso di questo metodo e quello initialize player name che chiama questo e nient'altro?


    public Point readSource() {
        return readPosition(readSourceMessage);
    }

    public Point readDestination() {
        return readPosition(readDestinationMessage);
    }

    public Point readPosition(String message) {
        System.out.println(message);

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
