package com.dontmiss.display;


 


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.dontmiss.DontMiss;
import com.dontmiss.God;
import com.dontmiss.GodConfig;
import com.dontmiss.entity.Enemy;
import com.dontmiss.entity.Projectile;

public class PlayDisplay implements Screen
{
	
	
	private SpriteBatch batch;
	private OrthographicCamera cam;
	
	private Texture texTurret;
	private Sprite sprTurret;

	private float rateOfFire;
	private float rateOfFireCounter;
	private float rateOfChangingTheSpin;

	private float spawnRate;
	private float spawnRateCounter;
	
	private Texture projectileTex;
	private Texture enemyTex;
	
	private Circle circleTurret;
	
	private BitmapFont font;
	
	private ShapeRenderer sr;
	
	private Game controller;
	
	public PlayDisplay(Game controller)
	{
		this.controller = controller;
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("abstract.fnt"));
		font.setScale(3f);

		cam = new OrthographicCamera();
		cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		
		texTurret = new Texture(Gdx.files.internal("turret.png"));
		sprTurret = new Sprite(texTurret);
	
		sprTurret.setOriginCenter();
		sprTurret.setPosition(((Gdx.graphics.getWidth()/2)-(sprTurret.getWidth()/2)), ((Gdx.graphics.getHeight()/2)-(sprTurret.getHeight()/2)));
		sprTurret.rotate(270);
		sprTurret.setSize(256,256);
		
		rateOfChangingTheSpin = 3f;
		rateOfFire =.3f;
		spawnRate=6f;
		
		spawnRateCounter=0;
		
		projectileTex = new Texture("bullet.png");
		enemyTex = new Texture("enemy.png");
		
		circleTurret = new Circle(sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2),sprTurret.getHeight()/2);
		
		sr = new ShapeRenderer();
		
		sprTurret.setOrigin(128f, 128f);
	}

	@Override
	public void render(float delta) 
	{
		
		Gdx.gl.glClearColor(GodConfig.colorR,GodConfig.colorG,GodConfig.colorB,GodConfig.colorA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		GodConfig.degreesCounter += GodConfig.rotationSpeed*delta;
		rateOfFireCounter+=delta;
		spawnRateCounter+=delta;
		rateOfChangingTheSpin +=delta;
		sprTurret.rotate(GodConfig.rotationSpeed*delta);
//		sr.begin(ShapeType.Line);
//			sr.setColor(Color.BLACK);
//			
//			sr.line(sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2),sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2));
//		sr.end();
		
		
		God.updateTimer(delta);
		
		if((Gdx.input.isKeyPressed(Keys.SPACE)||Gdx.input.isTouched())&&rateOfFireCounter>=rateOfFire)
		{
			rateOfFireCounter=0;
			
			GodConfig.projectiles.add(new Projectile(new Sprite(projectileTex),GodConfig.degreesCounter,35f));
		}
		
		
		if(spawnRateCounter>=spawnRate)
		{
			float degreesInterval = 90;
			if(GodConfig.rdm.nextBoolean())
			{
				degreesInterval=90;
			}
			else
			{
				degreesInterval =75 ;
			}
			if(GodConfig.timerTotalMins<=-1)
				degreesInterval = 5;
			int n = (int) (360/degreesInterval);
			float tempDegrees = 0;
			for(int i = 0;i < n ; i++)
			{
				tempDegrees+=degreesInterval;
				GodConfig.enemies.add(new Enemy(new Sprite(enemyTex), tempDegrees, .05f,sprTurret));
			}
			
			spawnRateCounter=0;
			
		}
		batch.setProjectionMatrix(cam.combined);
				batch.begin();
					
					sprTurret.draw(batch);
					//updates enemies and draws them
					for(int i=0;i<GodConfig.enemies.size();i++)
					{
						GodConfig.enemies.get(i).update(delta);
						GodConfig.enemies.get(i).getSprite().draw(batch);
					}
					//updates projectiles and draws them
					for(int i=0;i<GodConfig.projectiles.size();i++)
					{
						GodConfig.projectiles.get(i).update(delta);
						GodConfig.projectiles.get(i).getSprite().draw(batch);
					}

					font.draw(batch, (GodConfig.timerTotalMins + ":" + GodConfig.df.format(GodConfig.timerTotalSecs) + "   " + GodConfig.victoryMessage), 1200, 900);
				batch.end();

		God.checkCollision(circleTurret);
		God.removeEntities();
		//God.updateDisplay(batch,sprTurret,delta,cam);
		
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	

}
