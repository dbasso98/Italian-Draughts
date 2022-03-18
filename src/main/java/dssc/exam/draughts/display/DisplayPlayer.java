package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.core.Player;

public class DisplayPlayer implements Display<Player> {

    @Override
    public void display(Player player) {
        System.out.println("Player " + player.name + "[" + player.getColor() + "]:");
    }

    // put all to another class?? ---------------------------------------------------------
    public void initialInformation(Board board, Player player) {// put in game?
        new DisplayBoard().display(board);
        display(player);
    }


}
