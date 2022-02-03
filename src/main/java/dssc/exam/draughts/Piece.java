package dssc.exam.draughts;

public enum Color {
    BLACK(0), WHITE(1)
}
public class Piece {
    public final int id;
    public final Color color;
    public boolean is_king = false;

    Piece(int id, Color color){
        this.id = id;
        this.color = color;
    }
    public void upgrade_piece_to_king(){
        this.is_king = true;
    }
    public Color get_color_of_piece(){
        return this.color;
    }
    public int id_of_piece(){
        return this.id;
    }
    public Color color_of_piece(){
        return this.color;
    }
}
