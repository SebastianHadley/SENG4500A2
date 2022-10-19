/**
 * Holds the information for one ship.
 */

public class Ship {
	private int length;
	private int hits;
	private boolean sunk = false;
	
	public Ship(int length)
	{
		length = this.length;
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


}
