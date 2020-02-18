package Utility;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import Modes.Parallel;
import Utility.Logic.Element;

public class PlayerThread extends Thread
{
	Element element;
	public AtomicInteger score;

	public PlayerThread()
	{
		score = new AtomicInteger(0);
		element = Logic.getRandomElement();
	}

	@Override
	public void run()
	{
		PlayerThread players[] = Parallel.players;
		int numGames = Parallel.numGames;

		String name = Thread.currentThread().getName();
		int playerNum = Character.getNumericValue(name.charAt(name.length() - 1) - 1);

		for (int i = playerNum; i < players.length; i++)
		{
			if (i == playerNum)
				continue;

			for (int j = 0; j < numGames; j++)
			{
				playGame(players[playerNum], players[i]);
				randomizePlayers(players);
			}
		}
	}

	public void playGame(PlayerThread p1, PlayerThread p2)
	{
		if (p1.element == p2.element)
		{
			Parallel.ties++;
			return;
		}

		if (p1.element == Logic.getWinnerElement(p1.element, p2.element))
			p1.score.addAndGet(1);
		else
			p2.score.addAndGet(1);
	}

	protected static void randomizePlayers(PlayerThread[] players)
	{
		Arrays.asList(players).stream().forEach(p -> p.element = Logic.getRandomElement());
	}

}