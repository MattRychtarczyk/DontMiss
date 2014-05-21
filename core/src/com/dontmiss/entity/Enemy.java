package com.dontmiss.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Enemy extends Entity 
{
	private float x;
	private float y;
	
	private float speed;
	
	private float dirX;
	private float dirY;
	
	private Sprite sprTarget;
	
	public Enemy(Sprite sprEntity,float degrees,float speed,Sprite sprTarget) //Polar Coordinate Constructor, Just Pass in the degrees and the constructor will do the rest
	{
		super(sprEntity);
		
		this.speed = speed;
		
		float radius = (float) Math.sqrt(   (Math.pow((Gdx.graphics.getWidth()/2),2)) + (Math.pow((Gdx.graphics.getHeight()/2),2)) );
		
		x=(MathUtils.cosDeg(degrees)*radius)+((Gdx.graphics.getWidth()/2)-(sprEntity.getWidth()/2));
		y=(MathUtils.sinDeg(degrees)*radius)+((Gdx.graphics.getHeight()/2)-(sprEntity.getHeight()/2));
		
		sprEntity.setPosition(x, y);
		
		this.sprTarget = sprTarget;
	}
	public Enemy(Sprite sprEntity,float x,float y,float speed,Sprite sprTarget) //Rectangular Coordinate Constructor, Just Pass in the X and Y and the constructor will do the rest
	{
		super(sprEntity);
		
		this.speed = speed;
		
		this.sprTarget=sprTarget;
		
		this.x=x;
		this.y=y;
		
		sprEntity.setPosition(x, y);
		
	}
	
	
	
	@Override
	public void update(float delta)
	{
		updateBounds();
		
		dirX=((x-((sprTarget.getX()+(sprTarget.getWidth()/2))-(sprEntity.getWidth()/2)))*speed)*delta;
		dirY=((y-((sprTarget.getY()+(sprTarget.getHeight()/2))-(sprEntity.getHeight()/2)))*speed)*delta;
		sprEntity.translate(-dirX, -dirY);
	}

}
