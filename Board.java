import java.util.Random;

public class Board {
    private int length;
    private int[][] board;
    private Ship[] ships;
    public static void main(String[] args){
        Board board = new Board();
		System.out.println(board.doShot('F',5));
		board.displayFull();
    }
    
	public Board()
    {
        length = 10;
        board = new int[length][length];
		ships = new Ship[5];
		fillBoard();
		for(int i = 0; i < 5; i++){
			ships[i] = new Ship(i+1);
		}
	}   
	
	public String doShot(char xchar, int y){
		int x = getNumberForChar(xchar);
		int cellValue = board[x][y];
		if(cellValue == 6 || cellValue == 7){
			return "MISS";
		}else if(cellValue == 0){
			board[x][y] = 7;
			return "MISS";
		}else if(cellValue < 6)
		{
			ships[cellValue-1].shot();
			board[x][y] = 6;
			boolean gameWon = true;
			for(int i =0; i < 5; i++){
				if(!ships[i].isSunk()){
					gameWon = false;
				}
			}
			if(gameWon){
				return "GAME OVER";
			}
			else if(ships[cellValue-1].isSunk()){
				return "SUNK";
			}
			return "HIT";
		}
		return "ERROR";
	}

	private void fillBoard(){
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
	private int getNumberForChar(char i) {
		int coordinate = 11;
		if(i == 'A'){
			coordinate = 0;
		}else if(i == 'B'){
			coordinate = 1;
		}else if(i == 'C'){
			coordinate = 2;
		}else if(i =='D'){
			coordinate = 3;
		}else if( i =='E'){
			coordinate = 4;
		}else if(i == 'F'){
			coordinate = 5;
		}else if(i == 'G'){
			coordinate = 6;
		}else if(i =='H'){
			coordinate = 7;
		}else if(i =='I'){
			coordinate = 8;
		}else if(i =='J'){
			coordinate = 9;
		}
		return coordinate;
	}
}
