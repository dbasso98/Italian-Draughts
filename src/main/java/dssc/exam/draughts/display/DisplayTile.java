package dssc.exam.draughts.display;

import dssc.exam.draughts.Tile;

public class DisplayTile {
    public static String display(Tile tile) {
        if (tile.isEmpty()) {
            return displayEmptyTile();
        }
        return tile.getPiece().display();
    }

    private static String displayEmptyTile() {
        return "[ ]";
    }
}
