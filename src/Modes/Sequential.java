package Modes;

import java.util.Scanner;

import Utility.*;

public class Sequential
{

	public static void main(String[] args)
	{

		Player[] players = new Player[5];
		Logic.initializePlayers(players);
		int numGames = enterNumGames();
		long start = System.currentTimeMillis();

		while (Logic.equalScoreExists(players))
			playGame(players, numGames);
		System.out.println("Total number of games played: " + 10 * numGames);
		System.out.println("Maximum wins per player: " + 4 * numGames);
		System.out.println("\n\n    SCOREBOARD    \n");

		for (int i = 0; i < players.length; i++)
			System.out.println(" Player " + (i + 1) + " score: " + players[i].getScore());
		System.out.println("\n Ties: " + Logic.getTies());
		System.out.println(
				" Gameplay runtime took: " + ((double) (System.currentTimeMillis() - start) / 1000) + " seconds");

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

	public static void playGame(Player[] players, int numGames)
	{
		Logic.resetTies();
		Logic.resetScores(players);
		for (int i = 0; i < players.length; i++)
		{
			for (int j = i + 1; j < players.length; j++)
			{
				for (int z = 0; z < numGames; z++)
				{
					Logic.getWinnerPlayer(players[i], players[j]);
					Logic.randomizePlayers(players);
				}
			}
		}
	}

	private static int getWinner(Player[] players)
	{
		int maxScore = 0;
		int playerIndex = 0;

		for (int i = 0; i < players.length; i++)
			if (maxScore < players[i].getScore())
			{
				playerIndex = (i + 1);
				maxScore = players[i].getScore();
			}

		return playerIndex;
	}
}
