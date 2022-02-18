package dssc.exam.draughts;

public class RepresentationOfPiece {

    private String pieceRepresentation;

    RepresentationOfPiece(String representation){
        this.pieceRepresentation = representation;
    }

    public String getPieceRepresentation() {
        return pieceRepresentation;
    }

    public static RepresentationOfPiece updateRepresentation(Piece piece){
        return representation(piece);
    }

    private static RepresentationOfPiece kingRepresentation(Piece piece){
        if(piece.getColor() == Color.BLACK)
            return new RepresentationOfPiece("[B]");
        else
            return new RepresentationOfPiece("[W]");
    }

    private static RepresentationOfPiece manRepresentation(Piece piece){
        if(piece.getColor() == Color.BLACK)
            return new RepresentationOfPiece("[b]");
        else
            return new RepresentationOfPiece("[w]");
    }

    public static RepresentationOfPiece representation(Piece piece){
        if(piece.isKing()){
            return kingRepresentation(piece);
        }
        else
            return manRepresentation(piece);
    }
}
