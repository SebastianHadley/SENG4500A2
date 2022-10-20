import java.util.Random;

public class Game {
    public Board myBoard;
	public Board enemyBoard;
	private String playerName;
    public static void main(String[] args){
		Game game = new Game("Player");
		game.enemyBoard.addYourShot("MISS", 'C',0);
		game.enemyBoard.display();
	}
	public Game(String name){
		playerName = name;
		myBoard = new Board(true);
		enemyBoard = new Board(false);
	}
}