package dssc.exam.draughts;

import dssc.exam.draughts.display.DisplayBoard;
import dssc.exam.draughts.display.DisplayPlayer;
import dssc.exam.draughts.exceptions.*;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private Board board = new Board();
    private int round = 0;
    private DisplayPlayer displayPlayer;
    private DisplayBoard displayBoard;


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
                break;
            }
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

    private Move getMoveFromPlayer() throws DraughtsException {
        Point source = currentPlayer.readSource();
        testSourceValidity(source);
        Point destination = currentPlayer.readDestination();
        return new Move(board, source, destination);
    }


    void playRound() throws CannotMoveException {
        displayPlayer.initialInformation(board, currentPlayer);
        readAndPerformMove();
        changePlayer();
        ++round;
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
                System.out.println("Invalid move: " + exception.getMessage());
            }
        }
    }

    private void continueSkipMove(IncompleteMoveException e) {
        int movesToCompleteTurn = e.getNumberOfSkips();
        Point newSource = e.getNewSource();
        while (movesToCompleteTurn > 1) {
            displayBoard.display(board);
            System.out.println(e.getMessage());
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
                System.out.println("Invalid move: " + e.getMessage());
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

    Game(DisplayPlayer displayPlayer, DisplayBoard displayBoard, Player whitePlayer, Player blackPlayer) {
        this.displayPlayer = displayPlayer;
        this.displayBoard = displayBoard;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
    }

    Game(Player whitePlayer, Player blackPlayer) { // solo nei test viene usato.
        this(new DisplayPlayer(), new DisplayBoard(), whitePlayer, blackPlayer);
    }

    Game(DisplayPlayer displayPlayer, DisplayBoard displayBoard) {
        this(displayPlayer, displayBoard, new Player(Color.WHITE), new Player(Color.BLACK));
    }

    Game() {
        this(new DisplayPlayer(), new DisplayBoard());
    }
}
