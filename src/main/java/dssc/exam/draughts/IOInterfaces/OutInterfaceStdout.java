package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.Board;
import dssc.exam.draughts.BoardSpecifications;
import dssc.exam.draughts.Player;
import dssc.exam.draughts.exceptions.IndexException;

public class OutInterfaceStdout implements OutInterface {
    @Override
    public void displayBoard(Board board) {
        String indexLine = "   1  2  3  4  5  6  7  8";
        try {
            System.out.println(indexLine);
            displayInnerPartOfBoard(board);
            System.out.println(indexLine);
        } catch (IndexException e) {
            handleInvalidIndexInDisplay(e);
        }
    }

    private void handleInvalidIndexInDisplay(IndexException e) {
        System.out.println("ERROR: Unable to print the board, the Tile are corrupted: ");
        System.out.println(e.getMessage());
        System.exit(1);
    }

    private void displayInnerPartOfBoard(Board board) throws IndexException {
        for (int row = BoardSpecifications.numberOfRows() - 1; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < BoardSpecifications.numberOfRows(); col++) {
                System.out.print(board.getTile(row, col).display());
            }
            System.out.println(" " + (row + 1));
        }
    }


    @Override
    public void displayMessage(String Message) {
        System.out.println(Message);
    }

    @Override
    public void signalInvalidMove(Exception exception) {
        System.out.print("Invalid move: ");
        System.out.println(exception.getMessage());
    }

    @Override
    public void giveInitialRoundInformationToThePlayer(Board board, Player player) {
        displayBoard(board);
        displayHolder(player);
    }

    private void displayHolder(Player player) {
        System.out.println("Player " + player.name + "[" + player.getColor() + "]:");
    }

    @Override
    public void displayWinner(Player player) {
        System.out.println("The winner is " + player.name);
    }

    @Override
    public void askName(Player player, int playerNum) {
        System.out.println("Player" + playerNum + "[" + player.getColor() + "]: Please, insert your name:");
    }
}
