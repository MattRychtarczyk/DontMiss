package com.dontmiss;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.dontmiss.display.PlayDisplay;

public class GameInput implements InputProcessor
{
	private PlayDisplay playDisplay;
	
	public GameInput(PlayDisplay playDisplay)
	{
		this.playDisplay = playDisplay;
	}
	
	@Override
	public boolean keyDown(int keycode) 
	{
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) 
	{
		if(keycode == Keys.P&&(!playDisplay.isPaused()))
			playDisplay.setPaused(true);
		else if(keycode == Keys.P&&(playDisplay.isPaused()))
			playDisplay.setPaused(false);
		return false;
	}

	@Override
	public boolean keyTyped(char character) 
	{
		
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) 
	{
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) 
	{
		if(playDisplay.getRateFireCounter()>=playDisplay.getRateFire())
			playDisplay.createProjectiles();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) 
	{
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) 
	{
		
		return false;
	}

	@Override
	public boolean scrolled(int amount) 
	{
		
		return false;
	}

}
