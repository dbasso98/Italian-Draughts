package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Player;

public class DisplayPlayer implements Display<Player> {

    @Override
    public void display(Player player) {
        System.out.println("Player " + player.name + "[" + player.getColor() + "]:");
    }

}
