package com.dontmiss.display;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

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
	
	private float rateOfChangingTheSpinCounter;
	
	private float spawnRate;
	private float spawnRateCounter;
	
	private Texture projectileTex;
	private Texture enemyTex;
	
	private Circle circleTurret;
	
	private BitmapFont font;
	
	private ShapeRenderer sr;
	
	private Game controller;
	
	//color variables for the background
	private float colorR;
	private float colorG;
	private float colorB;
	private float colorA;

	//all the objects on the screen besides the player
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;

	//speed variables
	private float projectileSpeed;
	private float enemySpeed;
	private float rotationSpeed;

	//timer variables
	private int timerTotalMins;
	private float timerTotalSecs;

	//pause variable
	private boolean paused;

	//the variable that keeps track of where the turret is pointing
	private float degreesCounter;

	//this message displayed after winning
	private String victoryMessage;

	//random variable used to spice up the game
	//decimal format to make numbers look cleaner
	private Random rdm;
	private DecimalFormat df;

	public PlayDisplay(Game controller)
	{
		//color variables for the background
		  colorR=1;
		  colorG=1;
		  colorB=1;
		  colorA = 1;

		//all the objects on the screen besides the player
		 projectiles = new ArrayList<Projectile>();
		 enemies = new ArrayList<Enemy>();

		//speed variables
		  projectileSpeed = 0;
		  enemySpeed = 0;
		  rotationSpeed = 140f;

		//timer variables
		  timerTotalMins = 2;
		  timerTotalSecs = 59;

		//pause variable
		  paused = false;

		//the variable that keeps track of where the turret is pointing
		  degreesCounter= 0;

		//this message displayed after winning
		  victoryMessage = "";

		//random variable used to spice up the game
		//decimal format to make numbers look cleaner
		  rdm = new Random();
		  df = new DecimalFormat("00");
		
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
		sprTurret.setOrigin(128f, 128f);
	
		rateOfChangingTheSpin = 3f;
		rateOfChangingTheSpinCounter = 0;
		rateOfFire =2f;
		spawnRate=6f;
		
		spawnRateCounter=0;
		
		projectileTex = new Texture("bullet.png");
		enemyTex = new Texture("enemy.png");
		
		circleTurret = new Circle(sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2),sprTurret.getHeight()/2);
		
		sr = new ShapeRenderer(); 

	}

	@Override
	public void render(float delta) 
	{
		//start clearing of the screen with set color
		Gdx.gl.glClearColor(colorR,colorG,colorB,colorA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//end clearing of the screen with set color
		degreesCounter += rotationSpeed*delta;
		rateOfFireCounter+=delta;
		spawnRateCounter+=delta;
		rateOfChangingTheSpinCounter +=delta;
		
		sprTurret.rotate(rotationSpeed*delta);
//		sr.begin(ShapeType.Line);
//			sr.setColor(Color.BLACK);
//			
//			sr.line(sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2),sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2));
//		sr.end();
		
		
		updateTimer(delta);
		
		if((Gdx.input.isKeyPressed(Keys.SPACE)||Gdx.input.isTouched())&&rateOfFireCounter>=rateOfFire)
		{
			rateOfFireCounter=0;
			
			projectiles.add(new Projectile(new Sprite(projectileTex),degreesCounter,35f));
		}
		if(Gdx.input.isKeyPressed(Keys.C)&&rateOfChangingTheSpinCounter>=rateOfChangingTheSpin)
		{
			rateOfChangingTheSpinCounter = 0;
			
			rotationSpeed *= -1;
		}
		spawn();
		
		
		batch.setProjectionMatrix(cam.combined);
				batch.begin();
					
					sprTurret.draw(batch);
					//updates enemies and draws them
					for(int i=0;i<enemies.size();i++)
					{
						enemies.get(i).update(delta);
						enemies.get(i).getSprite().draw(batch);
					}
					//updates projectiles and draws them
					for(int i=0;i<projectiles.size();i++)
					{
						projectiles.get(i).update(delta);
						projectiles.get(i).getSprite().draw(batch);
					}

					font.draw(batch, (timerTotalMins + ":" + df.format(timerTotalSecs) + "   " + victoryMessage), 1200, 900);
				batch.end();

		checkCollision();
		removeEntities();
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
	
	private void checkCollision()
	{
		//Checks collision between projectiles and enemies
		for (int x=0; x<projectiles.size();x++)
		{
			for (int k=0; k<enemies.size();k++)
			{
				if( projectiles.get(x).getCircle().overlaps( enemies.get(k).getCircle() ) )
				{
					//Removes both the projectile and enemy from the screen
					enemies.get(k).setIsExpired(true);
					projectiles.get(x).setIsExpired(true);
					//Sets the new background color, randomly
					colorR = (rdm.nextFloat()); 
					colorG = (rdm.nextFloat());
					colorB = (rdm.nextFloat());
					if(colorR >=.9 && colorA <=.1 && colorB <=.1)
					{
						while((colorR >=.9 && colorA <=.1 && colorB <=.1))
						{
							colorR = (rdm.nextFloat()); 
							colorG = (rdm.nextFloat());
							colorB = (rdm.nextFloat());
						}
					}
					
	
					
				}
			}
		}
		
		//Checks collision between projectiles and the player
		for(int  x= 0; x<enemies.size();x++)
		{
			if(enemies.get(x).getCircle().overlaps(circleTurret))
				System.exit(0);
		}
	}
	private void spawn()
	{
		if(spawnRateCounter>=spawnRate)
		{
			float degreesInterval = 90;
			if(rdm.nextBoolean())
			{
				degreesInterval=90;
			}
			else
			{
				degreesInterval =75 ;
			}
			if(timerTotalMins<=-1)
				degreesInterval = 5;
			int n = (int) (360/degreesInterval);
			float tempDegrees = 0;
			for(int i = 0;i < n ; i++)
			{
				tempDegrees+=degreesInterval;
				enemies.add(new Enemy(new Sprite(enemyTex), tempDegrees, .05f,sprTurret));
			}
			
			spawnRateCounter=0;
			
		}
	}
	private void removeEntities()
	{
		//removes projectiles
		for(int i=0;i<projectiles.size();i++)
		{
			if(projectiles.get(i).isExpired())
				projectiles.remove(i);
		}
		//removes enemies
		for(int i=0;i<enemies.size();i++)
		{
			if(enemies.get(i).isExpired())
				enemies.remove(i);
		}
	}
	private void updateTimer(float delta)
	{
		if(timerTotalSecs<=0)
		{
			timerTotalMins-= 1;
			timerTotalSecs=59;
			if(timerTotalMins<=-1)
			{
				//end game code
			}
		}
		else
		{
			timerTotalSecs-=delta;
		}
	}
	

}
