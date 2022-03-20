package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;
import dssc.exam.draughts.display.DisplayPlayer;
import dssc.exam.draughts.utilities.Color;

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

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }
}
