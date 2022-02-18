package dssc.exam.draughts;

public class Piece {
    private final int id;
    private final Color pieceColor;
    private boolean isKing;
    private RepresentationOfPiece representation;

    // primitive obsession
    private final String blackKingRepresentation = "[B]";
    private final String blackManRepresentation = "[b]";
    private final String whiteKingRepresentation = "[W]";
    private final String whiteManRepresentation = "[w]";

    Piece(int id, Color pieceColor) {
        this.id = id;
        this.pieceColor = pieceColor;
        this.isKing = false;
        this.representation = RepresentationOfPiece.representation(this);
    }

    public void upgradeToKing() {
        this.isKing = true;
        this.representation = RepresentationOfPiece.representation(this);
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
        return representation.getPieceRepresentation();

        //if (isBlack()) {
          //  return blackKingRepresentation;
       // }
        //return whiteKingRepresentation;
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
