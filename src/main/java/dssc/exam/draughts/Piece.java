package dssc.exam.draughts;

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
    public boolean is_king(){return this.is_king == true;}
    public Color get_color_of_piece(){
        return this.color;
    }
    public int get_id_of_piece(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", color=" + color +
                ", is_king=" + is_king +
                '}';
    }
}
