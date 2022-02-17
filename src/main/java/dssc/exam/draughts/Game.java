package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.EmptyTileException;
import dssc.exam.draughts.exceptions.IncompleteMoveException;
import dssc.exam.draughts.exceptions.InvalidColorException;

import java.awt.*;

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

    void play() {
        while (whitePlayerHasPieces() & blackPlayerHasPieces()) {
            playRound();
        }
        changePlayer();
        System.out.println("The winner is " + currentPlayer.name);
    }

    void initPlayers() {
        whitePlayer.initializePlayerName(1);
        blackPlayer.initializePlayerName(2);
    }

    void startGame() {
        initPlayers();
        play();
    }

    void playRound() {
        board.display();
        currentPlayer.displayHolder();
        boolean isMoveInvalid = true;
        while (isMoveInvalid) {
            try {
                Point source = currentPlayer.getSource();
                Point destination = currentPlayer.getDestination();

                TestPieceValidity(source);
                Move.moveDecider(board, source, destination);
                isMoveInvalid = false;
            } catch (IncompleteMoveException e) {
                int movesToCompleteTurn = e.getSkipPath().size() - 1;
                Point source = e.getNewSource();
                while (movesToCompleteTurn > 1) {
                    board.display();
                    System.out.println(e.getMessage());
                    boolean isInvalidDestination = true;
                    while (isInvalidDestination) {
                        try {
                            Point destination = currentPlayer.getDestination();
                            Move.continueToSkip(board, source, destination, e.getSkipPath());
                            --movesToCompleteTurn;
                            source = destination;
                            isInvalidDestination = false;
                        } catch (Exception e2) {
                            System.out.print("Invalid move: ");
                            System.out.println(e2.getMessage());
                        }
                    }
                    isMoveInvalid = false;
                }

            } catch (Exception e) {
                System.out.print("Invalid move: ");
                System.out.println(e.getMessage());
            }
        }
        changePlayer();
        ++round;
    }


    private void TestPieceValidity(Point source) throws Exception {
        Tile sourceTile = board.getTile(source);
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
