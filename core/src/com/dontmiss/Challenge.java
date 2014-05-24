package com.dontmiss;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.dontmiss.entity.Enemy;

public class Challenge 
{
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private float currentTimeSecs;
//	private boolean changeDirWithKill = false;
//	private boolean enemyColorMatchesWithProjecileColor = false;
//	private boolean EnemiesDisappearHalfway = false;
//	private boolean changeDirWithKill = false;
//	private boolean changeDirWithKill = false;
	public Challenge(ArrayList<Enemy> enemies)
	{
		currentTimeSecs = 0;
		this.enemies = enemies;
	}
	public void update(int mins,float secs,ArrayList<Enemy> enemies)
	{
		this.enemies = enemies;
		currentTimeSecs = (mins*60) + secs;
		if(currentTimeSecs <= 145)
		{
			disappear();
		}
	}
	public void disappear()
	{	
		for(int i = 0;i<enemies.size();i++)
		{
			if(( enemies.get(i).getX()-enemies.get(i).getSprite().getX()>Gdx.graphics.getWidth()/2 ) || ( enemies.get(i).getY()-enemies.get(i).getSprite().getY()>Gdx.graphics.getHeight()/2 ))
				enemies.get(i).getSprite().setAlpha(0);
		}
	}
	
}
