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
    private final PlayerInputInterface in;

    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private Board board = new Board();
    private int round = 0;

    public Game(DisplayBoard displayBoard, DisplayGame displayGame, PlayerInputInterface in, Player whitePlayer, Player blackPlayer) {
        this.displayBoard = displayBoard;
        this.displayGame = displayGame;
        this.in = in;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = whitePlayer;
    }

    public Game(Player whitePlayer, Player blackPlayer) {
        this(new DisplayBoard(), new DisplayGame(), new ScannerPlayerInput(), whitePlayer, blackPlayer);
    }

    public Game(PlayerInputInterface in, Player whitePlayer, Player blackPlayer) {
        this(new DisplayBoard(), new DisplayGame(), in, whitePlayer, blackPlayer);
    }

    public Game(DisplayBoard displayBoard, DisplayGame displayGame, PlayerInputInterface in) {
        this(displayBoard, displayGame, in, new Player(Color.WHITE, in), new Player(Color.BLACK, in));
    }

    public Game() {
        this(new DisplayBoard(), new DisplayGame(), new ScannerPlayerInput());
    }

    public void startGame() {
        initPlayers();
        play();
    }

    public void initPlayers() {
        whitePlayer.initializePlayerName(1);
        blackPlayer.initializePlayerName(2);
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
                exception.printInformativeMessage("Invalid move: ");
            }
        }
    }

    private Move getMoveFromPlayer() throws DraughtsException {
        Point source = in.readSource();
        testSourceValidity(source);
        Point destination = in.readDestination();
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
                Point destination = in.readDestination();
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

    public void changePlayer() {
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

    public int getRound() {
        return round;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
