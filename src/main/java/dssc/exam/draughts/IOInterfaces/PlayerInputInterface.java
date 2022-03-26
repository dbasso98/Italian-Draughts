package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.core.Player;
import dssc.exam.draughts.exceptions.SurrenderException;

import java.awt.*;
import java.util.InputMismatchException;

public interface PlayerInputInterface {
    String getString();

    int getInt() throws InputMismatchException, SurrenderException;

    Point readPoint() throws InputMismatchException, SurrenderException;

    void skipToNextInput();

    void askName(Player player, int playerNum);

    Point readSource() throws SurrenderException;

    Point readDestination() throws SurrenderException;

    Point readPosition(String message) throws SurrenderException;

    void initializePlayerName(Player player, int playerNum);

    String getName(Player player, int playerNum);
}
