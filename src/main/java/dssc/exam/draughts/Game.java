package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.EmptyTileException;
import dssc.exam.draughts.exceptions.InvalidColorException;

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

    void playRound() throws Exception {
        // To be tested, not trivial
        board.display();
        boolean isMoveInvalid = true;
        while (isMoveInvalid) {
            try {
                Move move = currentPlayer.getMove();
                TestPieceValidity(move);
                move.executeOn(board);
                isMoveInvalid = false;

            } catch (Exception e) {
                System.out.print("Invalid move: ");
                System.out.println(e.getMessage());
            }
        }
        changePlayer();
        ++round;
    }

    private void TestPieceValidity(Move move) throws Exception {
        Tile sourceTile = board.getTile(move.source);
        if (sourceTile.isEmpty())
            throw new EmptyTileException("The first Tile you selected contains no Piece");
        if (sourceTile.getPiece().getColor() != currentPlayer.getColor())
            throw new InvalidColorException("The piece you intend to move belongs to your opponent");
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
