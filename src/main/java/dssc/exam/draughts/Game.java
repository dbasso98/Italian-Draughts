package dssc.exam.draughts;

public class Game {
    public Player whitePlayer = new Player(Color.WHITE);
    public Player blackPlayer = new Player(Color.BLACK);
    public Player currentPlayer = whitePlayer;
    private Board board = new Board();
    public int round = 0;

    // may implement custom, ctor, so that a saved game can be loaded;
    void playRound() {
        board.display();
        boolean isMoveInvalid = true;
        while (isMoveInvalid) {
            try {
                Move move = currentPlayer.getMove();
                move.executeOn(board);
                isMoveInvalid = false;

            } catch (Exception e) {
                System.out.println("Invalid move:");
                System.out.println(e.getMessage());
            }
        }
        changePlayer();
        ++round;
    }

    void changePlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
            return;
        }
        currentPlayer = blackPlayer;
    }


}
