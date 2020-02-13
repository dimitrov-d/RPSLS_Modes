package Modes;

import Utility.*;
import Utility.Logic.Element;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Parallel
{
	static PlayerThread[] players;
	static int numGames;
	static int ties = 0;

	public static void main(String args[]) throws InterruptedException
	{
		numGames = enterNumGames();
		players = new PlayerThread[5];
		int numPlayers = players.length;
		int formula = ((numPlayers * (numPlayers + 1)) / 2) - numPlayers;
		for (int i = 0; i < players.length; i++)
		{
			players[i] = new PlayerThread();
			players[i].setName("Player " + (i + 1));
		}
		long start = System.currentTimeMillis();

		for (int i = 0; i < players.length; i++)
			players[i].start();

		for (int i = 0; i < players.length; i++)
			players[i].join();

		System.out.println("Total number of games played: " + (formula * numGames));
		System.out.println("Maximum wins per player: " + (numPlayers - 1) * numGames);
		System.out.println("\n\n    SCOREBOARD    \n");

		for (int i = 0; i < players.length; i++)
			System.out.println(" Player " + (i + 1) + " score: " + players[i].score);

		System.out.println(" Ties: " + ties);
		System.out.println("\n Winner is: Player " + getWinner(players));
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
	
	protected static int getWinner(PlayerThread[] players)
	{
		int maxScore = 0;
		int playerIndex = 0;

		for (int i = 0; i < players.length; i++)
			if (maxScore < players[i].score.get())
			{
				playerIndex = (i + 1);
				maxScore = players[i].score.get();
			}

		return playerIndex;
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
		int playerNum = Character.getNumericValue(name.charAt(name.length() - 1) - 1);

		for (int i = playerNum; i < players.length; i++)
		{
			if (i == playerNum)
				continue;

			for (int j = 0; j < numGames; j++)
			{
				playGame(players[playerNum], players[i]);
				randomizePlayers(players);
			}
		}
	}

	public void playGame(PlayerThread p1, PlayerThread p2)
	{
		if (p1.element == p2.element)
		{
			Parallel.ties++;
			return;
		}

		if (p1.element == Logic.getWinnerElement(p1.element, p2.element))
			p1.score.addAndGet(1);
		else
			p2.score.addAndGet(1);
	}

	protected static void randomizePlayers(PlayerThread[] players)
	{
		Arrays.asList(players).stream().forEach(p -> p.element = Logic.getRandomElement());
	}
	
}
