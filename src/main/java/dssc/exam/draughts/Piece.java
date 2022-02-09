package dssc.exam.draughts;

public class Piece {
    private final int id;
    private final Color pieceColor;
    private boolean isKing = false;

    private final String blackKingRepresentation = "[B]";
    private final String blackManRepresentation = "[b]";
    private final String whiteKingRepresentation = "[W]";
    private final String whiteManRepresentation = "[w]";

    Piece(int id, Color pieceColor) {
        this.id = id;
        this.pieceColor = pieceColor;
    }

    public void upgradePieceToKing() {
        this.isKing = true;
    }

    public boolean isKing() {
        return this.isKing;
    }

    public Color getColorOfPiece() {
        return this.pieceColor;
    }

    public int getIdOfPiece() {
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

    public void printPieceInfo() {
        System.out.println(this);
    }

    public Color getColor() {
        return pieceColor;
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
