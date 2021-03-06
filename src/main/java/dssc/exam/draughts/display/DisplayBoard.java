package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.utilities.BoardSpecifications;
import dssc.exam.draughts.core.Tile;
import dssc.exam.draughts.exceptions.IndexException;

public class DisplayBoard implements Display<Board> {

    @Override
    public void display(Board board) {
        String indexLine = "   1  2  3  4  5  6  7  8";
        try {
            System.out.println(indexLine);
            displayInnerPartOfBoard(board);
            System.out.println(indexLine);
        } catch (IndexException e) {
            handleInvalidIndexInDisplay(e);
        }
    }

    private void displayInnerPartOfBoard(Board board) throws IndexException {
        for (int row = BoardSpecifications.numberOfRows() - 1; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < BoardSpecifications.numberOfRows(); col++) {
                Tile tile = board.getTile(row, col);
                new DisplayTile().display(tile);
            }
            System.out.println(" " + (row + 1));
        }
    }

    private void handleInvalidIndexInDisplay(IndexException e) {
        System.out.println("ERROR: Unable to print the board, the tiles are corrupted: ");
        System.out.println(e.getMessage());
        System.exit(1);
    }

}
