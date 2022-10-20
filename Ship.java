/**
 * Holds the information for one ship.
 */

public class Ship {
	private int length;
	private int hits;
	private boolean sunk = false;
	private String name;
	
	public Ship(int length,String title)
	{
		this.length = length;
		if(length == 2){
			name = "Destroyer";
		}else if(length == 3){	
			name = title;
		}
		else if(length == 4){
			name = "Battleship";
		}else if(length == 5){
			name = "Carrier";
		}
		hits = 0;
	}
	
	public void shot()
	{
		hits++;
	}
	public boolean isSunk()
	{
		if(hits == length)
		{
			sunk = true;
		}
		
		return sunk;
	}

	public String getName()
	{
		return name;
	}

}
