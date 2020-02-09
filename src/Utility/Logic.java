package Utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

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
		ties=0;
	}
	
	public static void resetScores(Player[] players)
	{
		Arrays.asList(players).forEach(p -> p.setScore(0));
	}

	public static void initializePlayers(Player[] players)
	{
		for (int i = 0; i < players.length; i++)
		{
			players[i] = new Player();
		}

	}
	
	public static boolean equalScoreExists(Player[] players)
	{
		var set = new HashSet<Integer>();
		for (int i = 0; i < players.length; i++)
		{
			if (set.contains(players[i].getScore()))
				return true;
			set.add(players[i].getScore());

		}

		return false;
	}
}
