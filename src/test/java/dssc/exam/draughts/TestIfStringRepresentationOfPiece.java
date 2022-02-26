package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfStringRepresentationOfPiece {
    @Test
    void displayBlackMan(){
        Piece blackMan = new Piece(Color.BLACK);
        assertEquals("[b]", blackMan.display());
    }

    @Test
    void displayWhiteMan(){
        Piece whiteMan = new Piece(Color.WHITE);
        assertEquals("[w]", whiteMan.display());
    }

    @Test
    void displayBlackKing(){
        Piece blackKing = new Piece(Color.BLACK);
        blackKing.upgradeToKing();
        assertEquals("[B]", blackKing.display() );
    }

    @Test
    void displayWhiteKing(){
        Piece whiteKing = new Piece(Color.WHITE);
        whiteKing.upgradeToKing();
        assertEquals("[W]", whiteKing.display() );
    }
}
