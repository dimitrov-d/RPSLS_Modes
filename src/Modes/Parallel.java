package Modes;

import Utility.Logic;
import Utility.Logic.Element;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Parallel
{
	static PlayerThread[] players;
	static int numGames;

	public static void main(String args[]) throws InterruptedException
	{
		numGames = enterNumGames();
		players = new PlayerThread[5];
		for (int i = 0; i < players.length; i++)
		{
			players[i] = new PlayerThread();
			players[i].setName("Player " + (i));

		}
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < players.length; i++)
			players[i].start();
		
		for (int i = 0; i < players.length; i++)
			players[i].join();
		
		System.out.println(
				"\n Gameplay runtime took: " + ((double) (System.currentTimeMillis() - start) / 1000) + " seconds");

	}
	
	public static int enterNumGames()
	{
		Scanner scan = new Scanner(System.in);
		int numGames;
		do
		{
			System.out.print("Enter the number of games per two players: ");
			while (!scan.hasNextInt())
			{
				System.out.println("That's not a number!");
				scan.next();
			}
			numGames = scan.nextInt();
		} while (numGames <= 0);
		scan.close();

		return numGames;
	}
	
}

class PlayerThread extends Thread
{
	Element element;
	AtomicInteger score;

	public PlayerThread()
	{
		score = new AtomicInteger(0);
		element = Logic.getRandomElement();
	}

	@Override
	public void run()
	{
		PlayerThread players[] = Parallel.players;
		int numGames = Parallel.numGames;
		
		String name = Thread.currentThread().getName();
		int playerNum = Character.getNumericValue(name.charAt(name.length() - 1));
		for (int i = 0; i < players.length; i++)
		{
			if (i == playerNum)
				continue;
			for (int j = 0; j < numGames; j++)
			{
				playGame(Parallel.players[playerNum], Parallel.players[i]);
			}
		}
	}

	public void playGame(PlayerThread p1, PlayerThread p2)
	{
		System.out.println(p1.getName() + " vs " + p2.getName());
		
	}
}
