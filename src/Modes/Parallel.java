package Modes;

import Utility.*;
import java.util.HashSet;
import java.util.Scanner;

public class Parallel
{
	public static PlayerThread[] players;
	public static int numGames;
	public static int ties = 0;

	public static void main(String args[]) throws InterruptedException
	{
		numGames = enterNumGames();
		// Modify length of array to change number of players
		players = new PlayerThread[5];
		long start = System.currentTimeMillis();

		while (maxScoreTieExists(players))
			initializePlayers(players);

		printScoreboard(numGames, players);
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
				System.err.println("That's not a number!");
				scan.next();
			}
			numGames = scan.nextInt();
		} while (numGames <= 0);
		scan.close();

		return numGames;
	}

	private static int getWinner(PlayerThread[] players)
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

	private static int getMaxScore(PlayerThread[] players)
	{
		int maxScore = 0;

		for (int i = 0; i < players.length; i++)
			if (maxScore < players[i].score.get())
				maxScore = players[i].score.get();

		return maxScore;

	}

	private static void initializePlayers(PlayerThread[] players) throws InterruptedException
	{
		// These loops cannot be run as a single loop, it makes a difference.
		Parallel.ties = 0;
		for (int i = 0; i < players.length; i++)
		{
			players[i] = new PlayerThread();
			players[i].setName("Player " + (i + 1));
		}
		for (int i = 0; i < players.length; i++)
			players[i].start();

		for (int i = 0; i < players.length; i++)
			players[i].join();
	}

	private static void printScoreboard(int numGames, PlayerThread[] players)
	{
		int numPlayers = players.length;
		int formula = ((numPlayers * (numPlayers + 1)) / 2) - numPlayers;
		System.out.println("Total number of games played: " + (formula * numGames));
		System.out.println("Maximum wins per player: " + (numPlayers - 1) * numGames);
		System.out.println("\n\n    SCOREBOARD    \n");

		for (int i = 0; i < players.length; i++)
			System.out.println(" Player " + (i + 1) + " score: " + players[i].score);

		System.out.println(" Ties: " + ties);
		System.out.println("\n Winner is: Player " + getWinner(players));
	}

	private static boolean maxScoreTieExists(PlayerThread[] players)
	{
		var set = new HashSet<Integer>();
		if (players[0] == null)
			return true;
		for (int i = 0; i < players.length; i++)
		{
			int score = players[i].score.get();
			int maxScore = getMaxScore(players);

			if (score == maxScore && set.contains(score))
				return true;

			set.add(score);
		}
		return false;
	}

}
