package dssc.exam.draughts.IOInterfaces;

import java.awt.*;
import java.util.InputMismatchException;

public interface PlayerInputInterface {
    String getString();

    int getInt() throws InputMismatchException;

    Point readPoint() throws InputMismatchException;

    void skipToNextInput();
}
