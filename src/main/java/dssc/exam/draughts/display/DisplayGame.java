package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.core.Player;

public class DisplayGame {
    private static final String sourceMessage = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
    private static final String destinationMessage = "What are the coordinates (x, y) of the Tile you intend to move the piece to? (e.g. 3 4)";

    public void message(String gameMessage){
        System.out.println(gameMessage);
    }
    public String getSourceMessage(){
        return sourceMessage;
    }

    public String getDestinationMessage(){
        return destinationMessage;
    }

    public void initialInformation(Board board, Player player) {
        new DisplayBoard().display(board);
        new DisplayPlayer().display(player);
    }

    public void winner(Player player) {
        System.out.println("The winner is " + player.name);
    }

}
