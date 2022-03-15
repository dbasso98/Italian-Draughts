package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.core.Player;

public class DisplayPlayer implements Display<Player> {

    @Override
    public void display(Player player) {
        System.out.println("Player " + player.name + "[" + player.getColor() + "]:");
    }

    public void initialInformation(Board board, Player player) {// put in game
        new DisplayBoard().display(board);
        display(player);
    }

    public void displayWinner(Player player) {
        System.out.println("The winner is " + player.name);
    }

    public void askName(Player player, int playerNum) {
        System.out.println("Player" + playerNum + "[" + player.getColor() + "]: Please, insert your name:");
    }

}
