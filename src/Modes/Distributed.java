package Modes;

import Utility.Logic;
import Utility.Player;
import mpi.*;

public class Distributed
{
	static int player;

	public static void main(String args[]) throws Exception
	{
		MPI.Init(args);
		player = MPI.COMM_WORLD.Rank();
		int numPlayers = MPI.COMM_WORLD.Size();
		int numGames = 5;
		
		Player[] players = new Player[numPlayers];
		System.out.println("Player: " + player);
		Logic.initializePlayers(players);
		
//		long start = System.currentTimeMillis();

		playGame(players, numGames);
		evaluateScores(players);
		
		if (player == 0)
			Logic.printScoreboard(numGames, players);
		MPI.Finalize();
	}

	public static void playGame(Player[] players, int numGames)
	{
		Logic.resetTies();
		Logic.resetScores(players);
		for (int i = player; i < players.length; i++)
		{
			if (i == player)
				continue;
			for (int j = 0; j < numGames; j++)
			{
				Logic.getWinnerPlayer(players[player], players[i]);
				Logic.randomizePlayers(players);
			}
		}
	}

	private static void evaluateScores(Player[] players)
	{
		int numPlayers = players.length;
		int scores[][] = new int[numPlayers][numPlayers + 1];

		for (int i = 0; i < scores.length - 1; i++)
		{
			if (player == i)
			{
				for (int j = 0; j < scores[i].length; j++)
				{
					if (j == scores[i].length - 1)
						scores[i][j] = Logic.getTies();
					else
						scores[i][j] = players[j].getScore();
					System.out.print(scores[i][j]);
				}
			}
			System.out.println();
		}

	}

}
