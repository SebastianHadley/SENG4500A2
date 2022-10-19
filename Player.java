/**
 * Player class for the game of battleship; holds information for one player.
 * Holds the player's board, fleet, and record of attacks.
 */

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

public class Player {
	private String playerName;
	private Board board;
	public boolean loss;
	private ShipList fleet;
	private AttackRecord record;
	private boolean human;

	
	public Player(String aname, boolean h)
/**
 * Instantiates a new player.
 */
	{
		playerName = aname;
		board = new Board();

	}
	
	
	
	public void displaytoMe()
	{
		board.displaytoMe();
	}
	
	public void displaytoEnemy()
	{
		board.displaytoEnemy();
	}
	
	public Coordinates attack()
	{
		int x = 0;
		int y = 0;
		boolean badshot = true;;
		if(human)
		{
			while(redundant)
			{
				try
				{
					System.out.println("Please select a coordinate on the vertical axis to target.");
					Scanner targetx = new Scanner(System.in);
					x = targetx.nextInt();
					if (x < 0 || x > 9)
					{
						InputMismatchException f = new InputMismatchException();
						throw f;
					}
					System.out.println("Please select a coordinate on the horizontal axis to target.");
					Scanner targety = new Scanner(System.in);
					y = targety.nextInt();
					if (y < 0 || y > 9)
					{
						InputMismatchException f = new InputMismatchException();
						throw f;
					}
					if(record.isRedundant(x, y))
					{
						System.out.println("You've already hit there. Please select different coordinates.");
					}
					else
					{
						redundant = false;
					}
				}
				catch (InputMismatchException f)
				{
					System.out.println("This is not a valid coordinate. Coordinates must be integers between 0 and 9.");
					redundant = true;
				}
			}
			
		}
		else
		{
			while (redundant)
			{
				x = (int) (Math.random()*10);
				y = (int) (Math.random()*10);
				if(record.isRedundant(x, y) == false)
				{
					redundant = false;
				}
			}
		}
		Coordinates attackCoordinates = new Coordinates(x, y);
		record.addAttack(attackCoordinates);
		return attackCoordinates;
	}
	
	public void receiveAttack(Coordinates c)
	/**
	 * Deals with an attack from the enemy player.
	 * Checks coordinates to see whether there was
	 * a ship at those coordinates.
	 * 
	 * @param c coordinates for an attack initiated
	 * by the enemy.
	 */
	{
		int status = board.checkHit(c);
		if (status == 0)
		{
			System.out.println("The attack has missed.");
			board.receiveMiss(c);
		}
		else
		{
			System.out.println(playerName + "'s ship has been hit.");
			board.receiveHit(c);
			fleet.shipHit(status);
		}
	}
	
	public boolean hasLost()
	/**
	 * Checks whether the player has lost.
	 * @return loss Returns true when this player has lost.
	 */
	{
		if (fleet.isLost())
		{
			loss = true;
		}
		return loss;
		
	}
}
