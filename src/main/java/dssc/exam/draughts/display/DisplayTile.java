package dssc.exam.draughts.display;

import dssc.exam.draughts.Tile;

public class DisplayTile implements Display<Tile> {

    @Override
    public void display(Tile tile) {
        if (tile.isEmpty()) {
            displayEmptyTile();
        }
        tile.getPiece().display();
    }

    private void displayEmptyTile() {
        System.out.println("[ ]");
    }
}

