package Modes;

import java.util.Arrays;

import Utility.Logic;
import Utility.Player;
import mpi.*;

public class Distributed
{
	static int rank;
	static Player[] players;
	static int numPlayers;
	static int[] scores;
	static int[] ties;
	static int numGames;
	static long start;

	public static void main(String[] args) throws Exception
	{
		MPI.Init(args);
		initializeProperties();
		Logic.initializePlayers(players);
		playGame(players, numGames);
		evaluateScores();
		MPI.Finalize();
	}
	
	public static void initializeProperties()
	{
		rank = MPI.COMM_WORLD.Rank();
		numPlayers = MPI.COMM_WORLD.Size();
		scores = new int[numPlayers];
		ties = new int[1];
		numGames = 5;
		start = System.currentTimeMillis();
		players = new Player[numPlayers];
	}

	public static void playGame(Player[] players, int numGames)
	{
		Logic.resetTies();
		Logic.resetScores(players);
		for (int i = rank; i < players.length; i++)
		{
			if (i == rank)
				continue;
			for (int j = 0; j < numGames; j++)
			{
				Logic.getWinnerPlayer(players[rank], players[i]);
				Logic.randomizePlayers(players);
			}
		}
	}

	private static void evaluateScores()
	{
		for (int i = 0; i < scores.length; i++)
			scores[i] = players[i].getScore();
		ties[0] = Logic.getTies();

		gatherScores();
	}

	public static void gatherScores()
	{
		// Arrays used for gathering scores and ties from each distribution
		int[] allScores = new int[scores.length * numPlayers];
		int[] allTies = new int[numPlayers];

		MPI.COMM_WORLD.Gather(scores, 0, scores.length, MPI.INT, allScores, 0, scores.length, MPI.INT, 0);
		MPI.COMM_WORLD.Gather(ties, 0, ties.length, MPI.INT, allTies, 0, ties.length, MPI.INT, 0);
		
		if (rank == 0)
		{
			// Add the scores and ties to the original Player array
			Logic.resetScores(players);
			for (int i = 0; i < allScores.length; i++)
				players[i % numPlayers].setScore(players[i % numPlayers].getScore() + allScores[i]);
			int totalTies = Arrays.stream(allTies).sum();

			Logic.printScoreboard(numGames, players, totalTies);
			System.out.println(
					"\n Gameplay runtime took: " + ((double) (System.currentTimeMillis() - start) / 1000) + " seconds");
		}
	}
}
