package dssc.exam.draughts.IOInterfaces;

import dssc.exam.draughts.core.Player;
import dssc.exam.draughts.display.DisplayGame;
import dssc.exam.draughts.exceptions.SurrendException;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class ScannerPlayerInput implements PlayerInputInterface {
    Scanner scanner;

    public ScannerPlayerInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getString() {
        var string = scanner.next();
        scanner.nextLine();
        return string;
    }

    @Override
    public int getInt() throws InputMismatchException, SurrendException {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException exception) {
            var in = getString();
            if (in.equals("s")) {
                throw new SurrendException("You decided to surrender");
            }
            throw exception;
        }
    }


    @Override
    public void skipToNextInput() {
        scanner.next();
    }

    @Override
    public Point readPoint() throws InputMismatchException, SurrendException {
        int column = getInt();
        int row = getInt();
        scanner.nextLine();
        return new Point(row - 1, column - 1);
    }

    @Override
    public void askName(Player player, int playerNum) {
        System.out.println("Player" + playerNum + "[" + player.getColor() + "]: Please, insert your name:");
    }

    @Override
    public Point readSource() throws SurrendException {
        return readPosition(new DisplayGame().getSourceMessage());
    }

    @Override
    public Point readDestination() throws SurrendException {
        return readPosition(new DisplayGame().getDestinationMessage());
    }

    @Override
    public Point readPosition(String message) throws SurrendException {
        new DisplayGame().message(message);
        while (true) {
            try {
                return readPoint();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid expression");
                skipToNextInput();
            }
        }
    }

    @Override
    public void initializePlayerName(Player player, int playerNum) {
        player.setName(getName(player, playerNum));
    }

    @Override
    public String getName(Player player, int playerNum) {
        askName(player, playerNum);
        return getString();
    }

}
