package com.dontmiss;


import com.badlogic.gdx.math.Circle;

public class God 
{

	public static void checkCollision(Circle circleTurret)
	{
		//Checks collision between projectiles and enemies
		for (int x=0; x<GodConfig.projectiles.size();x++)
		{
			for (int k=0; k<GodConfig.enemies.size();k++)
			{
				if( GodConfig.projectiles.get(x).getCircle().overlaps( GodConfig.enemies.get(k).getCircle() ) )
				{
					//Removes both the projectile and enemy from the screen
					GodConfig.enemies.get(k).setIsExpired(true);
					GodConfig.projectiles.get(x).setIsExpired(true);
					//Sets the new background color, randomly
					GodConfig.colorR = (GodConfig.rdm.nextFloat()); 
					GodConfig.colorG = (GodConfig.rdm.nextFloat());
					GodConfig.colorB = (GodConfig.rdm.nextFloat());
				}
			}
		}
		//Checks collision between projectiles and the player
		for(int  x= 0; x<GodConfig.enemies.size();x++)
		{
			if(GodConfig.enemies.get(x).getCircle().overlaps(circleTurret))
				System.exit(0);
		}
	}
	public static void spawn()
	{
		
	}
	public static void removeEntities()
	{
		//removes projectiles
		for(int i=0;i<GodConfig.projectiles.size();i++)
		{
			if(GodConfig.projectiles.get(i).isExpired())
				GodConfig.projectiles.remove(i);
		}
		//removes enemies
		for(int i=0;i<GodConfig.enemies.size();i++)
		{
			if(GodConfig.enemies.get(i).isExpired())
				GodConfig.enemies.remove(i);
		}
	}
	public static void updateTimer(float delta)
	{
		if(GodConfig.timerTotalSecs<=0)
		{
			GodConfig.timerTotalMins-= 1;
			GodConfig.timerTotalSecs=59;
			if(GodConfig.timerTotalMins<=-1)
			{
				//end game code
			}
		}
		else
		{
			GodConfig.timerTotalSecs-=delta;
		}
	}
//	public static void updateDisplay(SpriteBatch batch,Sprite sprTurret,float delta,OrthographicCamera cam)
//	{
//		
//	}
}
