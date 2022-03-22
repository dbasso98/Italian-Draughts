package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.PlayerInputInterface;
import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;
import dssc.exam.draughts.display.DisplayBoard;
import dssc.exam.draughts.display.DisplayGame;
import dssc.exam.draughts.exceptions.*;
import dssc.exam.draughts.moveLogics.CandidateSkipPathBuilder;
import dssc.exam.draughts.moveLogics.Move;
import dssc.exam.draughts.moveLogics.SimpleMoveRules;
import dssc.exam.draughts.utilities.Color;

import java.awt.*;
import java.util.ArrayList;

public class Game {
    private final DisplayBoard displayBoard;
    private final DisplayGame displayGame;

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private Board board = new Board();
    private int round = 0;

    public Game(DisplayBoard displayBoard, DisplayGame displayGame, PlayerInputInterface playerInputInterface, Player whitePlayer, Player blackPlayer) {
        this.displayBoard = displayBoard;
        this.displayGame = displayGame;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
    }

    public Game(Player whitePlayer, Player blackPlayer) {
        this(new DisplayBoard(), new DisplayGame(), new ScannerPlayerInput(), whitePlayer, blackPlayer);
    }

    public Game(PlayerInputInterface playerInputInterface, Player whitePlayer, Player blackPlayer) {
        this(new DisplayBoard(), new DisplayGame(), playerInputInterface, whitePlayer, blackPlayer);
    }

    public Game(DisplayBoard displayBoard, DisplayGame displayGame, PlayerInputInterface playerInputInterface) {
        this(displayBoard, displayGame, playerInputInterface, new Player(Color.WHITE, playerInputInterface), new Player(Color.BLACK, playerInputInterface));
    }

    public Game() {
        this(new DisplayBoard(), new DisplayGame(), new ScannerPlayerInput());
    }

    public void startGame() {
        initPlayers();
        play();
    }

    public void initPlayers() {
        whitePlayer.initializeName(1);
        blackPlayer.initializeName(2);
    }

    public void loadGame(Board board, int round) {
        this.board = board;
        this.round = round;
        this.currentPlayer = (round % 2 == 0) ? this.whitePlayer : this.blackPlayer;
    }

    public void play() {
        while (whitePlayerHasPieces() & blackPlayerHasPieces()) {
            try {
                playRound();
            } catch (CannotMoveException exception) {
                exception.printMessage();
                break;
            }
        }
        changePlayer();
        displayGame.winner(currentPlayer);
    }

    public void playRound() throws CannotMoveException {
        displayGame.initialInformation(board, currentPlayer);
        throwExceptionIfCannotMakeAtLeastOneMove();
        readAndPerformMove();
        changePlayer();
        ++round;
    }

    private void throwExceptionIfCannotMakeAtLeastOneMove() throws CannotMoveException {
        var canNotMakeASkip = CandidateSkipPathBuilder.build(board, currentPlayer.getColor()).isEmpty();
        var canNotMakeASimpleMove = SimpleMoveRules.CanNotMakeASimpleMove(board, currentPlayer.getColor());
        if (canNotMakeASkip && canNotMakeASimpleMove)
            throw new CannotMoveException("Cannot perform any move!" + System.lineSeparator() + "*******GAME OVER*******");
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
                exception.printInformativeMessage("Invalid move: ");
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
            e.printMessage();
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
                e.printInformativeMessage("Invalid move: ");
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

    void changePlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
            return;
        }
        currentPlayer = blackPlayer;
    }

    private boolean blackPlayerHasPieces() {
        return board.getNumberOfPiecesOfColor(Color.BLACK) != 0;
    }

    private boolean whitePlayerHasPieces() {
        return board.getNumberOfPiecesOfColor(Color.WHITE) != 0;
    }

    int getRound() {
        return round;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
