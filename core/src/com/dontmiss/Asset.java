package com.dontmiss;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Asset 
{
	public static final AssetManager manager = new AssetManager();
	public static final AssetDescriptor<Texture> imgProjectile = new AssetDescriptor<Texture>("projectileBlue.png", Texture.class);
	public static final AssetDescriptor<Texture> imgTurret = new AssetDescriptor<Texture>("turret.png", Texture.class);
	public static final AssetDescriptor<Texture> imgEnemy = new AssetDescriptor<Texture>("enemyRed.png", Texture.class);
	public static final AssetDescriptor<BitmapFont> fontAbstract = new AssetDescriptor<BitmapFont>("abstract.fnt", BitmapFont.class);
	
	public static void load()
	{
		manager.load(imgProjectile);
		manager.load(imgTurret);
		manager.load(imgEnemy);
		manager.load(fontAbstract);
		

	}
	public static void setFilters()
	{
		manager.get(imgTurret).setFilter(TextureFilter.Linear,TextureFilter.Linear);
	}
	public static void dispose()
	{
		manager.dispose();
	}
}
