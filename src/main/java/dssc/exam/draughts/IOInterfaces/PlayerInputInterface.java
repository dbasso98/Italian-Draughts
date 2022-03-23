package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.core.Player;
import dssc.exam.draughts.exceptions.SurrendException;

import java.awt.*;
import java.util.InputMismatchException;

public interface PlayerInputInterface {
    String getString();

    int getInt() throws InputMismatchException, SurrendException;

    Point readPoint() throws InputMismatchException, SurrendException;

    void skipToNextInput();

    void askName(Player player, int playerNum);

    Point readSource() throws SurrendException;

    Point readDestination() throws SurrendException;

    Point readPosition(String message) throws SurrendException;

    void initializePlayerName(Player player, int playerNum);

    String getName(Player player, int playerNum);
}
