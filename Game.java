import java.util.Random;

public class Game {
    public Board myBoard;
	public Board enemyBoard;
	private String playerName;
    public static void main(String[] args){
		Game game = new Game();
		game.enemyBoard.addYourShot("MISS", 'C',0);
		game.enemyBoard.display();
	}
	public Game(){
		myBoard = new Board(true);
		enemyBoard = new Board(false);
	}
	public void printBoards(){
		System.out.println("My Board");
		myBoard.display();
		System.out.println("Enemy Board");
		enemyBoard.display();
	}
}