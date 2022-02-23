package dssc.exam.draughts;

import dssc.exam.draughts.IOInterfaces.OutInterface;
import dssc.exam.draughts.IOInterfaces.OutInterfaceStdout;
import dssc.exam.draughts.exceptions.IncompleteMoveException;
import dssc.exam.draughts.exceptions.InvalidColorException;
import dssc.exam.draughts.exceptions.TileException;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    Player whitePlayer = new Player(Color.WHITE);
    Player blackPlayer = new Player(Color.BLACK);
    Player currentPlayer = whitePlayer;
    private Board board = new Board();
    public int round = 0;
    private final OutInterface out;

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
        out.displayWinner(currentPlayer);
    }

    void initPlayers() {
        whitePlayer.initializePlayerName(1);
        blackPlayer.initializePlayerName(2);
    }

    void startGame() {
        initPlayers();
        play();
    }

    private void performSimpleAction() throws Exception {
        Point source = currentPlayer.readSource();
        TestSourceValidity(source);

        Point destination = currentPlayer.readDestination();
        Move move = new Move(board, source, destination);
        move.moveDecider();
    }


    void playRound() {
        out.giveInitialRoundInformationToThePlayer(board, currentPlayer);
        readAndPerformMove();
        changePlayer();
        ++round;
    }

    private void readAndPerformMove() {
        while (true) {
            try {
                performSimpleAction();
                break;
            } catch (IncompleteMoveException e) {
                continueSkipMove(e);
                break;
            } catch (Exception e) {
                out.signalInvalidMove(e);
            }
        }
    }

    private void continueSkipMove(IncompleteMoveException e) {
        int movesToCompleteTurn = e.getNumberOfSkips();
        Point newSource = e.getNewSource();
        while (movesToCompleteTurn > 1) {
            out.displayBoard(board);
            out.displayMessage(e.getMessage());
            newSource = makeAStepInMultipleSkip(e.getSkipPath(), newSource);
            --movesToCompleteTurn;
        }
    }

    private Point makeAStepInMultipleSkip(ArrayList<Tile> skipPath, Point source) {
        while (true) {
            try {
                Point destination = currentPlayer.readDestination();
                new Move(board, source, destination).continueToSkip(skipPath);
                source = destination;
                break;
            } catch (Exception e) {
                out.signalInvalidMove(e);
            }
        }
        return source;
    }

    private void TestSourceValidity(Point source) throws Exception {
        Tile sourceTile = board.getTile(source);
        if (sourceTile.isEmpty())
            throw new TileException("The first Tile you selected is empty");
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

    Game(OutInterface outInterface) {
        this.out = outInterface;
    }

    Game() {
        this.out = new OutInterfaceStdout();
    }
}
