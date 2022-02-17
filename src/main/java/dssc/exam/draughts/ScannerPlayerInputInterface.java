package dssc.exam.draughts;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerPlayerInputInterface implements PlayerInputInterface {
    Scanner scanner;

    ScannerPlayerInputInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    ScannerPlayerInputInterface() {
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
