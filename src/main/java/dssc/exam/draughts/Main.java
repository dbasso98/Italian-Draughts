package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.DraughtsException;

public class Main {
    public static void main(String... args) throws DraughtsException {
        System.out.println("ciao!");
        Player player = new Player(Color.WHITE);
        Game game = new Game();
        game.startGame();
    }
}
