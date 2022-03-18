package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.core.Player;

import java.awt.*;
import java.util.InputMismatchException;

public interface PlayerInputInterface {
    String getString();

    int getInt() throws InputMismatchException;

    Point readPoint() throws InputMismatchException;

    void skipToNextInput();

    void askName(Player player, int playerNum);

    Point readSource();

    Point readDestination();

    Point readPosition(String message);
}
