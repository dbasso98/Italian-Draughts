package dssc.exam.draughts;

import java.nio.FloatBuffer;

public class Game {
    Player whitePlayer = new Player(Color.WHITE);
    Player blackPlayer = new Player(Color.BLACK);
    Player currentPlayer = whitePlayer;
    private Board board = new Board();
    public int round = 0;

    void loadGame(Board board, int round) {
        this.board = board;
        this.round = round;
        if ((round % 2) == 0)
            this.currentPlayer = this.whitePlayer;
        else
            this.currentPlayer = this.blackPlayer;
    }

    void playRound() {
        // To be tested, not trivial
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

    boolean blackPlayerHasPieces() {
        return board.getNumberOfPiecesOfColor(Color.BLACK) != 0;
    }

    boolean whitePlayerHasPieces() {
        return board.getNumberOfPiecesOfColor(Color.WHITE) != 0;
    }

    int getRound() {
        return round;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

}
