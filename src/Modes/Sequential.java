package Modes;

import Utility.*;

public class Sequential
{

	public static void main(String[] args)
	{
		// Change the parameter below to change number of players
		Player[] players = new Player[5];
		Logic.initializePlayers(players);
		int numGames = Logic.enterNumGames();
		long start = System.currentTimeMillis();

		while (Logic.maxScoreTieExists(players))
			playGame(players, numGames);

		Logic.printScoreboard(numGames, players, -1);
		System.out.println(
				"\n Gameplay runtime took: " + ((double) (System.currentTimeMillis() - start) / 1000) + " seconds");
	}
	
	public static void playGame(Player[] players, int numGames)
	{
		Logic.resetTies();
		Logic.resetScores(players);
		for (int i = 0; i < players.length; i++)
			for (int j = i + 1; j < players.length; j++)
				for (int z = 0; z < numGames; z++)
				{
					Logic.getWinnerPlayer(players[i], players[j]);
					Logic.randomizePlayers(players);
				}
	}

}
