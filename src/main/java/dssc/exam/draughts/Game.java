package dssc.exam.draughts;

import dssc.exam.draughts.IOInterfaces.OutInterface;
import dssc.exam.draughts.IOInterfaces.OutInterfaceStdout;
import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private Board board = new Board();
    private int round = 0;
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
            try {
                playRound();
            } catch (CannotMoveException exception) {
                System.out.println(exception.getMessage());
                break;
            }
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

    private Move getMoveFromPlayer() throws DraughtsException {
        Point source = currentPlayer.readSource();
        testSourceValidity(source);
        Point destination = currentPlayer.readDestination();
        return new Move(board, source, destination);
    }


    void playRound() throws CannotMoveException {
        out.giveInitialRoundInformationToThePlayer(board, currentPlayer);
        throwExceptionIfCannotMakeAtLeastOneMove();
        readAndPerformMove();
        changePlayer();
        ++round;
    }

    private void throwExceptionIfCannotMakeAtLeastOneMove() throws CannotMoveException {
        var canNotMakeASkip = CandidateSkipPathBuilder.build(board, currentPlayer.getColor()).isEmpty();
        var canNotMakeASimpleMove = SimpleMoveRules.CanNotMakeASimpleMove(board, currentPlayer.getColor());
        if (canNotMakeASkip && canNotMakeASimpleMove)
            throw new CannotMoveException("Cannot perform any move!\n*******GAME OVER*******");
    }

    private void readAndPerformMove() throws CannotMoveException {
        while (true) {
            try {
                getMoveFromPlayer().moveDecider();
                break;
            } catch (IncompleteMoveException exception) {
                continueSkipMove(exception);
                break;

            } catch (CannotMoveException cannotMoveException) {
                throw cannotMoveException;

            } catch (DraughtsException exception) {
                out.signalInvalidMove(exception);
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
            } catch (DraughtsException e) {
                out.signalInvalidMove(e);
            }
        }
        return source;
    }

    private void testSourceValidity(Point source) throws DraughtsException {
        Tile sourceTile = board.getTile(source);
        if (sourceTile.isEmpty())
            throw new TileException("The first Tile you selected is empty");
        if (sourceTile.getPiece().getColor() != currentPlayer.getColor())
            throw new MoveException("The piece you intend to move belongs to your opponent");
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

    Game(OutInterface outInterface, Player whitePlayer, Player blackPlayer) {
        this.out = outInterface;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
    }

    Game(Player whitePlayer, Player blackPlayer) {
        this(new OutInterfaceStdout(), whitePlayer, blackPlayer);
    }

    Game(OutInterface outInterface) {
        this(outInterface, new Player(Color.WHITE), new Player(Color.BLACK));
    }

    Game() {
        this(new OutInterfaceStdout());
    }
}
