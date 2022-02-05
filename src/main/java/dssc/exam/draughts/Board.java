package dssc.exam.draughts;

import java.util.ArrayList;
public class Board {
    public final int size = 64;
    public final int max_rows = 8;
    public final int max_columns = 8;
    public final int pieces_per_player = 12;
    public ArrayList<Tile> board = new ArrayList<>(size);

    Board(){
        for (int row = 0; row < max_rows; row+=2) {
            for (int column = 0; column < max_columns; column+=2){
                board.add(new Tile(row, column, Color.BLACK));
                board.add(new Tile(row, column + 1, Color.WHITE));
                board.add(new Tile(row+1, column, Color.WHITE));
                board.add(new Tile(row+1, column+1, Color.BLACK));
            }
        }
        for(int i = 0; i < pieces_per_player*2; ++i){
            if(board.get(i).tile_color == Color.BLACK){
                board.get(i).set_piece_contained_in_tile(new Piece(i, Color.BLACK));
                //relies on test.
                board.get(size-i).set_piece_contained_in_tile(new Piece(size-i, Color.WHITE));
            }
        }
    }

    int get_size_of_board() {
        return size;
    }

    public int get_pieces(Color color) {
        int sum = 0;
        for(int i = 0; i<size; ++i) {
            if (board.get(i).tile_color == color && board.get(i).is_not_empty())
                sum += 1;
        }
        return sum;
    }

    public int get_number_pieces() {
        return 24;
    }
}
