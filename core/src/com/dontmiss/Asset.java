package com.dontmiss;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Asset 
{
	public static final AssetManager manager = new AssetManager();
	public static final AssetDescriptor<Texture> projectile = new AssetDescriptor<Texture>("bullet.png", Texture.class);
	public static final AssetDescriptor<Texture> turret = new AssetDescriptor<Texture>("turret.png", Texture.class);
	public static final AssetDescriptor<Texture> enemy = new AssetDescriptor<Texture>("enemy.png", Texture.class);
	public static void load()
	{
		manager.load(projectile);
		manager.load(turret);
		manager.load(enemy);
	}
	
	public static void dispose()
	{
		manager.dispose();
	}
}
