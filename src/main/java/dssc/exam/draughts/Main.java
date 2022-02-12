package dssc.exam.draughts;

public class Main {
    public static void main (String ... args){
        try{
        System.out.println("ciao!");
        Player player = new Player(Color.WHITE);
        Game game = new Game();
        game.playRound();
        game.playRound();
        game.playRound();
        }
        catch (Exception e) {
            ;
        }
    }
}
