package dssc.exam.draughts.IOInterfaces;

import java.util.InputMismatchException;

public interface PlayerInputInterface {
    String getString();

    int getInt() throws InputMismatchException;

    void skipToNextInput();
}
