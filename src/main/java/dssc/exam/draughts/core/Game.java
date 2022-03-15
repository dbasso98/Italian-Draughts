package dssc.exam.draughts.core;

import dssc.exam.draughts.moveLogics.CandidateSkipPathBuilder;
import dssc.exam.draughts.moveLogics.Move;
import dssc.exam.draughts.moveLogics.SimpleMoveRules;
import dssc.exam.draughts.display.DisplayBoard;
import dssc.exam.draughts.display.DisplayPlayer;
import dssc.exam.draughts.exceptions.*;
import dssc.exam.draughts.utilities.Color;

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


    public void loadGame(Board board, int round) {
        this.board = board;
        this.round = round;
        this.currentPlayer = (round % 2 == 0) ? this.whitePlayer : this.blackPlayer;
    }

    public void startGame() {
        initPlayers();
        play();
    }

    public void initPlayers() {
        whitePlayer.initializePlayerName(1);
        blackPlayer.initializePlayerName(2);
    }

    public void play() {
        while (whitePlayerHasPieces() & blackPlayerHasPieces()) {
            try {
                playRound();
            } catch (CannotMoveException exception) {
                System.out.println(exception.getMessage());
                break;
            }
        }
        changePlayer();
        displayPlayer.displayWinner(currentPlayer);
    }

    public void playRound() throws CannotMoveException {
        displayPlayer.initialInformation(board, currentPlayer);
        throwExceptionIfCannotMakeAtLeastOneMove();
        readAndPerformMove();
        changePlayer();
        ++round;
    }

    private void throwExceptionIfCannotMakeAtLeastOneMove() throws CannotMoveException {
        var canNotMakeASkip = CandidateSkipPathBuilder.build(board, currentPlayer.getColor()).isEmpty();
        var canNotMakeASimpleMove = SimpleMoveRules.CanNotMakeASimpleMove(board, currentPlayer.getColor());
        if (canNotMakeASkip && canNotMakeASimpleMove)
            throw new CannotMoveException("Cannot perform any move!"+ System.lineSeparator() +"*******GAME OVER*******");
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

    private Move getMoveFromPlayer() throws DraughtsException {
        Point source = currentPlayer.readSource();
        testSourceValidity(source);
        Point destination = currentPlayer.readDestination();
        return new Move(board, source, destination);
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
        if (!board.isPositionInsideTheBoard(source))
            throw new IndexException("The Tile you selected is not inside the board");
        Tile sourceTile = board.getTile(source);
        if (sourceTile.isEmpty())
            throw new TileException("The first Tile you selected is empty");
        if (sourceTile.getPiece().getColor() != currentPlayer.getColor())
            throw new MoveException("The piece you intend to move belongs to your opponent");
    }

    public void changePlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
            return;
        }
        currentPlayer = blackPlayer;
    }

    boolean blackPlayerHasPieces() {
        return board.getNumberOfPiecesOfColor(dssc.exam.draughts.utilities.Color.BLACK) != 0;
    }

    boolean whitePlayerHasPieces() {
        return board.getNumberOfPiecesOfColor(dssc.exam.draughts.utilities.Color.WHITE) != 0;
    }

    public int getRound() {
        return round;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Game(DisplayPlayer displayPlayer, DisplayBoard displayBoard, Player whitePlayer, Player blackPlayer) {
        this.displayPlayer = displayPlayer;
        this.displayBoard = displayBoard;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
    }

    public Game(Player whitePlayer, Player blackPlayer) { // solo nei test viene usato.
        this(new DisplayPlayer(), new DisplayBoard(), whitePlayer, blackPlayer);
    }

    public Game(DisplayPlayer displayPlayer, DisplayBoard displayBoard) {
        this(displayPlayer, displayBoard, new Player(dssc.exam.draughts.utilities.Color.WHITE), new Player(Color.BLACK));
    }

    public Game() {
        this(new DisplayPlayer(), new DisplayBoard());
    }
}
