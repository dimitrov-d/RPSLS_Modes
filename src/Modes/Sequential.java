package Modes;

import java.util.Scanner;
import Utility.*;

public class Sequential
{

	public static void main(String[] args)
	{

		Player[] players = new Player[5];
		Logic.initializePlayers(players);
		Scanner scan = new Scanner(System.in);
		int numGames = scan.nextInt();

		// Total number of games: 5 * 4 * number_of_games
		// Maximum number of wins: 8 * number_of_games (4 * number_of_games + 4 *
		// number_of_games)

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

		scan.close();
	}
}
