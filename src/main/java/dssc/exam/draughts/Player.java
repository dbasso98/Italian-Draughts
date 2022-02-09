package dssc.exam.draughts;

import javax.swing.text.Position;
import java.awt.*;
import java.util.Scanner;

public class Player {

    private static final String readMessage = "What are the coordinates (row, column) of the piece you intend to move? \n(e.g. 3 4)";

    Point readPosition() {
        return readPosition(readMessage);
    }

    Point readPosition(String message) {
        System.out.println(message);
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int column = input.nextInt();
        return new Point(row, column);
    }

}
