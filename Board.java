import java.util.Random;

public class Board {
    private int length;
    private int[][] board;
    private Ship[] ships;
    public static void main(String[] args){
        Board board = new Board();
        board.displayFull();
    }
    
	public Board()
    {
        length = 10;
        board = new int[length][length];
		Random rand = new Random();
		for(int i = 5; i > 0; i--){
				Boolean horizontal = rand.nextBoolean();
				int x = rand.nextInt(length);
				int y = rand.nextInt(length);
				if(horizontal){
					if(x+i > length){
						x -= i;
					}
				}else{
					if(y+i > length){
						y -=i;
					}
				}
				boolean canPlace = true;
				if(horizontal){
					for(int a = x; a< x+i; a++){
						if(board[y][a] != 0)
						{
							canPlace = false;
							break;
						}
					}
				}
				else{
					for(int a = y; a< y+i; a++){
						if(board[a][x] != 0)
						{
							canPlace = false;
							break;
						}
					}
				}
				if(!canPlace){
					i++;
					continue;
				}
				else{
				for (int a = 0; a < i; a++) {
					board[y][x] = i;
					if (horizontal) {
						x++;
					} else {
						y++;
					}
				}
			}
		}
	}   
	

    public void displayFull()
	{   
        System.out.println((int)'a' - 1);
		System.out.println();
		System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
		System.out.println("--+---+---+---+---+---+---+---+---+---+---|");
		for(int x=0; x<board.length; x++)
		{
			System.out.print(getCharForNumber(x+1) + " |");
			
			for(int y = 0; y<board.length; y++)
			{
				System.out.print(" ");
				
				if(board[x][y] == 1 || board[x][y] == 2 || board[x][y] == 3 || board[x][y] == 4 | board[x][y] == 5)
				{
					System.out.print("O");
				}
				
				if(board[x][y] == 6)
				{
					System.out.print("X");
				}
				
				if(board[x][y] == 7)
				{
					System.out.print("-");
				}
				
				if(board[x][y] == 0)
				{
					System.out.print(" ");
				}
				
				System.out.print(" ");
				System.out.print("|");
			}
			System.out.println();
			System.out.println("--+---+---+---+---+---+---+---+---+---+---|");		
		}
	}

    public void displaytoEnemy()
	{
		/**
		 * Displays the board to the enemy.
		 * X denotes where the enemy has managed to hit a ship.
		 * - denotes where the enemy has hit and missed.
		 */
        System.out.println((int)'a' - 'A' - 1);

		System.out.println("This is the enemy board. X: hits / -: misses");
		System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |");
		System.out.println("--+---+---+---+---+---+---+---+---+---+---|");
		
		for(int x=0; x<board.length; x++)
		{
			System.out.print(x + " |");
			
			for(int y = 0; y<board.length; y++)
			{
				System.out.print(" ");
				
				if(board[x][y] == 1 || board[x][y] == 2 || board[x][y] == 3 || board[x][y] == 4 | board[x][y] == 5)
				{
					System.out.print(" ");
				}
				
				if(board[x][y] == 6)
				{
					System.out.print("X");
				}
				
				if(board[x][y] == 7)
				{
					System.out.print("-");
				}
				
				if(board[x][y] == 0)
				{
					System.out.print(" ");
				}
				
				System.out.print(" ");
				System.out.print("|");
			}
			System.out.println();
			System.out.println("--+---+---+---+---+---+---+---+---+---+---|");	
		}
	}
    private String getCharForNumber(int i) {
    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
}
private String getNumberForChar(int i) {
    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
}
}
