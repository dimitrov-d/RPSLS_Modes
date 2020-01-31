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

		// Total number of games: 5 * 4 * number_of_games
		// Maximum number of wins per player:
		// 8 * number_of_games (4 * number_of_games + 4 *number_of_games)

		for (int i = 0; i < players.length; i++)
		{
			for (int j = 0; j < players.length; j++)
			{
				if (i == j)
					continue;

				for (int z = 0; z < numGames; z++)
				{
					Logic.getWinnerPlayer(players[i], players[j]);
					Logic.randomizePlayers(players);
				}
			}
		}

		System.out.println("\n\n\n    SCOREBOARD    \n");

		for (int i = 0; i < players.length; i++)
			System.out.println(" Player " + (i + 1) + " score: " + players[i].getScore());
		System.out.println("\n Ties: " + Logic.getTies());

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
