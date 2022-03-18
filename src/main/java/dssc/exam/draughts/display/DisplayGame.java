package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.core.Player;

public class DisplayGame {

    public void initialInformation(Board board, Player player) {
        new DisplayBoard().display(board);
        new DisplayPlayer().display(player);
    }

    public void winner(Player player) {
        System.out.println("The winner is " + player.name);
    }

}
