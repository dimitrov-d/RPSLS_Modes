package Modes;

import java.util.Scanner;

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
		System.out.println("Player: " + player);
		Player[] players = new Player[numPlayers];
		Logic.initializePlayers(players);
		long start = System.currentTimeMillis();

		playGame(players, numGames);

		if (player == 0)
		{
			printScoreboard(numGames, players);
			System.out.println(
					"\n Gameplay runtime took: " + ((double) (System.currentTimeMillis() - start) / 1000) + " seconds");
		}
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

	private static void printScoreboard(int numGames, Player[] players)
	{
		int numPlayers = players.length;
		int formula = ((numPlayers * (numPlayers + 1)) / 2) - numPlayers;
		System.out.println("Total number of games played: " + (formula * numGames));
		System.out.println("Maximum wins per player: " + (numPlayers - 1) * numGames);
		System.out.println("\n\n    SCOREBOARD    \n");

		for (int i = 0; i < players.length; i++)
			System.out.println(" Player " + (i + 1) + " score: " + players[i].getScore());

		System.out.println(" Ties: " + Logic.getTies());
		System.out.println("\n Winner is: Player " + getWinner(players));

	}
}
