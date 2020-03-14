package Utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Logic
{
	private static int ties = 0;

	public enum Element
	{
		ROCK, PAPER, SCISSORS, LIZARD, SPOCK
	}

	public static Element getRandomElement()
	{
		return Element.values()[new Random().nextInt(Element.values().length)];
	}

	public static void randomizePlayers(Player[] players)
	{
		Arrays.asList(players).stream().forEach(p -> p.setElement(getRandomElement()));
	}

	public static Element getWinnerElement(Element el1, Element el2)
	{
		switch (el1)
		{
		case ROCK:
			switch (el2)
			{
			case PAPER:
				return Element.PAPER;
			case SCISSORS:
				return Element.ROCK;
			case LIZARD:
				return Element.ROCK;
			case SPOCK:
				return Element.SPOCK;
			default:
				break;
			}

		case LIZARD:
			switch (el2)
			{
			case ROCK:
				return Element.ROCK;
			case PAPER:
				return Element.LIZARD;
			case SCISSORS:
				return Element.SCISSORS;
			case SPOCK:
				return Element.LIZARD;
			default:
				break;
			}
		case PAPER:
			switch (el2)
			{
			case ROCK:
				return Element.PAPER;
			case SCISSORS:
				return Element.SCISSORS;
			case LIZARD:
				return Element.LIZARD;
			case SPOCK:
				return Element.PAPER;
			default:
				break;
			}
		case SCISSORS:
			switch (el2)
			{
			case ROCK:
				return Element.ROCK;
			case PAPER:
				return Element.SCISSORS;
			case LIZARD:
				return Element.SCISSORS;
			case SPOCK:
				return Element.SPOCK;
			default:
				break;
			}
		case SPOCK:
			switch (el2)
			{
			case ROCK:
				return Element.SPOCK;
			case PAPER:
				return Element.PAPER;
			case SCISSORS:
				return Element.SPOCK;
			case LIZARD:
				return Element.LIZARD;
			default:
				break;

			}
		default:
			return el1;
		}
	}

	public static void getWinnerPlayer(Player p1, Player p2)
	{
		if (p1.getElement() == p2.getElement())
		{
			ties++;
			return;
		}

		if (p1.getElement() == getWinnerElement(p1.getElement(), p2.getElement()))
			p1.incrementScore();
		else
			p2.incrementScore();
	}

	public static int getTies()
	{
		return ties;
	}

	public static void resetTies()
	{
		ties = 0;
	}

	public static void resetScores(Player[] players)
	{
		Arrays.asList(players).forEach(p -> p.setScore(0));
	}

	public static void initializePlayers(Player[] players)
	{
		for (int i = 0; i < players.length; i++)
			players[i] = new Player();

	}

	public static boolean maxScoreTieExists(Player[] players)
	{
		var set = new HashSet<Integer>();
		if (players[0] == null)
			return true;
		for (int i = 0; i < players.length; i++)
		{
			int score = players[i].getScore();
			int maxScore = getMaxScore(players);

			if (score == maxScore && set.contains(score))
				return true;

			set.add(score);
		}
		return false;
	}

	private static int getMaxScore(Player[] players)
	{
		int maxScore = 0;

		for (int i = 0; i < players.length; i++)
			if (maxScore < players[i].getScore())
				maxScore = players[i].getScore();

		return maxScore;
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

	public static void printScoreboard(int numGames, Player[] players)
	{
		int numPlayers = players.length;
		int formula = ((numPlayers * (numPlayers + 1)) / 2) - numPlayers;
		System.out.println("Total number of games played: " + (formula * numGames));
		System.out.println("Maximum wins per player: " + (numPlayers - 1) * numGames);
		System.out.println("\n\n    SCOREBOARD    \n");

		for (int i = 0; i < players.length; i++)
			System.out.println(" Player " + (i + 1) + " score: " + players[i].getScore());

		System.out.println(" Ties: " + getTies());
		System.out.println("\n Winner is: Player " + getWinner(players));

	}

	public static void playGame(Player[] players, int numGames)
	{
		resetTies();
		resetScores(players);
		for (int i = 0; i < players.length; i++)
			for (int j = i + 1; j < players.length; j++)
				for (int z = 0; z < numGames; z++)
				{
					getWinnerPlayer(players[i], players[j]);
					randomizePlayers(players);
				}
	}
}
