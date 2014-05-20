package com.dontmiss;

import com.badlogic.gdx.Game;

import com.dontmiss.display.PlayDisplay;

public class DontMiss extends Game
{

	private PlayDisplay pd;
	public void create() 
	{
		pd = new PlayDisplay(this);
		setScreen(pd);
	}
	
}