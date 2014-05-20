package com.dontmiss.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

public abstract class Entity 
{
	protected Circle cirBounds;
	protected Sprite sprEntity; 
	protected boolean isExpired;
	public Entity(Sprite sprEntity)
	{
		this.sprEntity=sprEntity;                
		isExpired = false;
		cirBounds = new Circle(
				sprEntity.getX() + (sprEntity.getWidth()/2), 
				sprEntity.getY() + (sprEntity.getHeight()/2),
				sprEntity.getHeight()/2);
	}
	public abstract void update(float delta);
	public Circle getCircle()
	{
		return cirBounds;
	}
	public void updateBounds()
	{
		cirBounds.setPosition(
				sprEntity.getX() + (sprEntity.getWidth()/2), 
				sprEntity.getY() + (sprEntity.getHeight()/2));
		
	}
	public Sprite getSprite()
	{
		return sprEntity;
	}
	public void setIsExpired(boolean isExpired)
	{
		this.isExpired = isExpired;
	}
	public boolean isExpired()
	{
		return isExpired;
	}
}
