package dssc.exam.draughts;

public class Piece {
    public final int id;
    public final Color piece_color;
    public boolean is_king = false;

    Piece(int id, Color piece_color){
        this.id = id;
        this.piece_color = piece_color;
    }
    public void upgrade_piece_to_king(){
        this.is_king = true;
    }
    public boolean is_king(){return this.is_king;}
    public Color get_color_of_piece(){
        return this.piece_color;
    }
    public int get_id_of_piece(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "id=" + id +
                ", color=" + piece_color +
                ", is_king=" + is_king +
                '}';
    }
    public void print_piece_info(){
        System.out.println(this);
    }
}
