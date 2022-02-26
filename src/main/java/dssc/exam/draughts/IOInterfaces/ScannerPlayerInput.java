package dssc.exam.draughts.IOInterfaces;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerPlayerInput implements PlayerInputInterface {
    Scanner scanner;

    public ScannerPlayerInput() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getString() {
        return scanner.next();
    }

    @Override
    public int getInt() throws InputMismatchException {
        return scanner.nextInt();
    }

    @Override
    public void skipToNextInput() {
        scanner.next();
    }
}
