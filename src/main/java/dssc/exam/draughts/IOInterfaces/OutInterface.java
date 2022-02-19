package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.Board;
import dssc.exam.draughts.Player;

public interface OutInterface {

    void displayBoard(Board board);

    void displayMessage(String Message);

    void signalInvalidMove(Exception exception);

    void giveInitialRoundInformationToThePlayer(Board board, Player player);

    void displayWinner(Player player);

    void askName(Player player, int playerNum);
}
