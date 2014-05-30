package com.dontmiss.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;


public class Projectile extends Entity
{
	
	
	private float dirX;
	private float dirY;
	private float time;
	private float timeToExpire;
	
	
	
	public Projectile(Sprite sprProjectile,float degrees,float speed)
	{
		super(sprProjectile);
		     
		timeToExpire = 2;
		dirX = MathUtils.cosDeg(degrees)*speed;
		dirY = MathUtils.sinDeg(degrees)*speed;
		
		System.out.println(Gdx.graphics.getWidth());
		sprEntity.setPosition((Gdx.graphics.getWidth()/2)-(sprEntity.getWidth()/2),(Gdx.graphics.getHeight()/2)-(sprEntity.getHeight()/2));
		sprEntity.setRotation(degrees);
		
		//sprEntity.setScale(2f);

		time=0;
	}
	@Override
	public void update(float delta) 
	{
		time+=delta;
		if(time>=timeToExpire)
			isExpired=true;
		updateBounds();
		
		sprEntity.setPosition(sprEntity.getX()+dirX, sprEntity.getY()+dirY);
		
	}

}
