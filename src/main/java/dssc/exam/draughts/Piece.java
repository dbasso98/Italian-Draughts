package dssc.exam.draughts;

public class Piece {
    public final int id;
    public final Color pieceColor;
    public boolean isKing = false;

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

    private boolean isBlack() {
        return pieceColor == Color.BLACK;
    }

    public String displayKing(){
        if (isBlack()) {
            return "[B]";
        }
        return "[W]";
    }


    public String display() {
        if (isKing()){
            return displayKing();
        }

        if (isBlack()) {
            return "[b]";
        }
        return "[w]";
    }
}
