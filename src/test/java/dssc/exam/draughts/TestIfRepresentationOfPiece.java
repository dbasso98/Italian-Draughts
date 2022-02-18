package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfRepresentationOfPiece {
    @Test
    void displayBlackMan(){
        Piece blackMan = new Piece(1, Color.BLACK);
        assertEquals("[b]", blackMan.display());
    }

    @Test
    void displayWhiteMan(){
        Piece whiteMan = new Piece(1, Color.WHITE);
        assertEquals("[w]", whiteMan.display());
    }

    @Test
    void displayBlackKing(){
        Piece blackKing = new Piece(1, Color.BLACK);
        blackKing.upgradeToKing();
        assertEquals("[B]", blackKing.display() );
    }

    @Test
    void displayWhiteKing(){
        Piece whiteKing = new Piece(1, Color.WHITE);
        whiteKing.upgradeToKing();
        assertEquals("[W]", whiteKing.display() );
    }
}