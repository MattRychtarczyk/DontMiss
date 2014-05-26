package com.dontmiss;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.dontmiss.entity.Enemy;

public class Challenge 
{
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
//	private float currentTimeSecs;
//	private boolean changeDirWithKill = false;
//	private boolean enemyColorMatchesWithProjecileColor = false;
//	private boolean EnemiesDisappearHalfway = false;
//	private boolean changeDirWithKill = false;
//	private boolean changeDirWithKill = false;
	private float opacityRate;
	private float radiusToDisappear;
	private float opacityCurrent;
	public Challenge(ArrayList<Enemy> enemies)
	{
//		currentTimeSecs = 0;
		this.enemies = enemies;
		opacityRate = .5f;
		opacityCurrent = 1;
		radiusToDisappear = 6;
	}
	public void update(int mins,float secs,ArrayList<Enemy> enemies,float delta)
	{
//		this.enemies = enemies;
//		currentTimeSecs = (mins*60) + secs;
//		if(currentTimeSecs <= 150)
//		{
//			
//		}
		disappear(delta);
	}
	public void disappear(float delta)
	{	
		for(int i = 0;i<enemies.size();i++)
		{
			if(Math.sqrt(Math.pow(((enemies.get(i).getSprite().getX() + (enemies.get(i).getSprite().getWidth()/2) ) - (Gdx.graphics.getWidth()/2)),2)+ Math.pow( ((enemies.get(i).getSprite().getY() + (enemies.get(i).getSprite().getHeight()/2)) - (Gdx.graphics.getHeight()/2)),2))<= Gdx.graphics.getWidth()/radiusToDisappear)
			{
				if(opacityCurrent>0)
					opacityCurrent -= opacityRate * delta;
				enemies.get(i).getSprite().setAlpha(opacityCurrent);
			}
				
		}
	}
	
}
