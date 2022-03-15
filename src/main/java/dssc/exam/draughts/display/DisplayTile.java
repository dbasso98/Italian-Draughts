package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Tile;

public class DisplayTile implements Display<Tile> {

    @Override
    public void display(Tile tile) {
        if (tile.isEmpty()) {
            displayEmptyTile();
        }
        else
            tile.getPiece().display();
    }

    private void displayEmptyTile() {
        System.out.print("[ ]");
    }
}

