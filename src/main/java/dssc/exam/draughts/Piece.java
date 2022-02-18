package dssc.exam.draughts;

public class Piece {
    private final int id;
    private final Color pieceColor;
    private boolean isKing = false;

    private final String blackKingRepresentation = "[B]";
    private final String blackManRepresentation = "[b]";
    private final String whiteKingRepresentation = "[W]";
    private final String whiteManRepresentation = "[w]";

    public Piece(int id, Color pieceColor) {
        this.id = id;
        this.pieceColor = pieceColor;
    }

    public void upgradeToKing() {
        this.isKing = true;
    }

    public boolean isKing() {
        return this.isKing;
    }

    public Color getColor() {
        return this.pieceColor;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", color=" + pieceColor +
                ", isKing=" + isKing +
                '}';
    }

    public void printInfo() {
        System.out.println(this);
    }

    private boolean isBlack() {
        return pieceColor == Color.BLACK;
    }

    private String displayKing(){
        if (isBlack()) {
            return blackKingRepresentation;
        }
        return whiteKingRepresentation;
    }

    public String display() {
        if (isKing()){
            return displayKing();
        }

        if (isBlack()) {
            return blackManRepresentation;
        }
        return whiteManRepresentation;
    }
}
