package Modes;

import Utility.*;

import java.util.concurrent.atomic.AtomicInteger;

public class Parallel
{
	static TPlayer[] players;

	public static void main(String args[]) throws InterruptedException
	{
		players = new TPlayer[5];
		for (int i = 0; i < players.length; i++)
		{
			players[i] = new TPlayer();
			players[i].setName("Player " + (i));

		}

		for (int i = 0; i < players.length; i++)
		{
			players[i].start();
		}

	}
}

class TPlayer extends Thread
{
	AtomicInteger score;

	public TPlayer()
	{
		score = new AtomicInteger(0);
	}

	@Override
	public void run()
	{
		TPlayer players[] = Parallel.players;
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

	public void playGame(TPlayer p1, TPlayer p2)
	{
		System.out.println(p1.getName() + " vs " + p2.getName());
		
	}
}