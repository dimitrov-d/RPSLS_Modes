package Utility;

import Utility.Logic.Element;

public class Player
{
	private Element element;
	private int score;

	public Player()
	{
		element = Logic.getRandomElement();
		score = 0;
	}

	public void incrementScore()
	{
		score++;
	}

	public Element getElement()
	{
		return element;
	}

	public void setElement(Element el)
	{
		element = el;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

}
