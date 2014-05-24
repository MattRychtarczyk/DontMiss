package com.dontmiss.display;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.dontmiss.Asset;
import com.dontmiss.GameInput;
import com.dontmiss.entity.Enemy;
import com.dontmiss.entity.Projectile;

public class PlayDisplay implements Screen
{
	private SpriteBatch batch;
	private OrthographicCamera cam;
	

	private Sprite sprTurret;
	private Circle circleTurret;
	private BitmapFont fontAbstract;
	
	private InputProcessor gameInput;
	
	//rates and their counters
	private float rateOfFire;
	private float rateOfFireCounter;
	private float rateChangingTheSpin;
	private float rateChangingTheSpinCounter;
	private float spawnRate;
	private float spawnRateCounter;
	
	private Game game;
	
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

	public PlayDisplay(Game game)
	{
		//color variables for the background
		colorR = 1;
		colorG = 1;
		colorB = 1;
		colorA = 1;

		//all the objects on the screen besides the player
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		
		//speed variables
		projectileSpeed = 35f;
		enemySpeed = .05f;
		rotationSpeed = 155f;//140f

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
		
		this.game = game;
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		sprTurret = new Sprite((Asset.manager.get(Asset.imgTurret)));
		sprTurret.setOriginCenter();
		sprTurret.setPosition(((Gdx.graphics.getWidth()/2)-(sprTurret.getWidth()/2)), ((Gdx.graphics.getHeight()/2)-(sprTurret.getHeight()/2)));
		sprTurret.rotate(270);
		sprTurret.setSize(256,256);
		sprTurret.setOrigin(128f, 128f);
		
		circleTurret = new Circle(sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2),sprTurret.getHeight()/2);
		
		fontAbstract = Asset.manager.get(Asset.fontAbstract);
		fontAbstract.setScale(3f);
		rateChangingTheSpin = 1f;
		rateChangingTheSpinCounter = 0;
		rateOfFire =.5f;
		rateOfFireCounter = 0;
		spawnRate=6f;
		spawnRateCounter=0;
		
		//sets the input
		gameInput = new GameInput(this);
		Gdx.input.setInputProcessor(gameInput);
	}

	@Override
	public void render(float delta) 
	{
		if(!(paused))
		{
			//start clearing of the screen with set color
			Gdx.gl.glClearColor(colorR,colorG,colorB,colorA);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//end clearing of the screen with set color
			//all rates are adding on time
			rateOfFireCounter+=delta;
			spawnRateCounter+=delta;
			rateChangingTheSpinCounter+= delta;
			//adds on degrees and rotates the turret per frame
			degreesCounter += rotationSpeed*delta;
			sprTurret.rotate(rotationSpeed*delta);
			
			
			if(Gdx.input.isKeyPressed(Keys.C)&&rateChangingTheSpinCounter>=rateChangingTheSpin)
			{
				rateChangingTheSpinCounter = 0;
				rotationSpeed *= -1;
			}
			updateTimer(delta);
			spawn();
			updateDisplay(delta);
			checkCollision();
			removeEntities();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show()
	{
		
	}
	@Override
	public void hide() 
	{
		
		
	}
	@Override
	public void pause() 
	{
		
	}
	@Override
	public void resume() 
	{
		
		
	}
	@Override
	public void dispose() 
	{
		//throws the object in the garbage
		batch.dispose();
	}
	public float getRateOfFire() {
		return rateOfFire;
	}

	public float getRateOfFireCounter() {
		return rateOfFireCounter;
	}

	public float getRateOfChangingTheSpin() {
		return rateChangingTheSpin;
	}

	public float getRateOfChangingTheSpinCounter() {
		return rateChangingTheSpinCounter;
	}

	public float getSpawnRate() {
		return spawnRate;
	}

	public float getSpawnRateCounter() {
		return spawnRateCounter;
	}

	public float getProjectileSpeed() {
		return projectileSpeed;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public int getTimerTotalMins() {
		return timerTotalMins;
	}

	public float getTimerTotalSecs() {
		return timerTotalSecs;
	}

	public float getDegreesCounter() {
		return degreesCounter;
	}

	public void setRateOfFire(float rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public void setRateOfFireCounter(float rateOfFireCounter) {
		this.rateOfFireCounter = rateOfFireCounter;
	}

	public void setRateOfChangingTheSpin(float rateOfChangingTheSpin) {
		this.rateChangingTheSpin = rateOfChangingTheSpin;
	}

	public void setRateOfChangingTheSpinCounter(float rateOfChangingTheSpinCounter) {
		this.rateChangingTheSpinCounter = rateOfChangingTheSpinCounter;
	}

	public void setSpawnRate(float spawnRate) {
		this.spawnRate = spawnRate;
	}

	public void setSpawnRateCounter(float spawnRateCounter) {
		this.spawnRateCounter = spawnRateCounter;
	}

	public void setProjectileSpeed(float projectileSpeed) {
		this.projectileSpeed = projectileSpeed;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public void setTimerTotalMins(int timerTotalMins) {
		this.timerTotalMins = timerTotalMins;
	}

	public void setTimerTotalSecs(float timerTotalSecs) {
		this.timerTotalSecs = timerTotalSecs;
	}

	public void setDegreesCounter(float degreesCounter) {
		this.degreesCounter = degreesCounter;
	}

	private void updateDisplay(float delta)
	{
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
			//draws text
			fontAbstract.draw(batch, (timerTotalMins + ":" + df.format(timerTotalSecs) + "   " + victoryMessage), 1200, 900);
		batch.end();
	}
	private void checkCollision()
	{
		//Checks collision between projectile and enemies
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
				enemies.add(new Enemy(new Sprite((Asset.manager.get(Asset.imgEnemy))), tempDegrees, enemySpeed,sprTurret));
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
	private void reset()
	{
		//color variables for the background
		colorR = 1;
		colorG = 1;
		colorB = 1;
		colorA = 1;
		
		//speed variables
		projectileSpeed = 35f;
		enemySpeed = .05f;
		rotationSpeed = 170f;//140f

		//timer variables
		timerTotalMins = 2;
		timerTotalSecs = 59;

		//pause variable
		paused = false;

		//the variable that keeps track of where the turret is pointing
		degreesCounter= 0;

		//this message displayed after winning
		victoryMessage = "";

		sprTurret.setOriginCenter();
		sprTurret.setPosition(((Gdx.graphics.getWidth()/2)-(sprTurret.getWidth()/2)), ((Gdx.graphics.getHeight()/2)-(sprTurret.getHeight()/2)));
		sprTurret.rotate(270);
		sprTurret.setSize(256,256);
		sprTurret.setOrigin(128f, 128f);
		
		rateChangingTheSpin = 1f;
		rateChangingTheSpinCounter = 0;
		rateOfFire =.5f;
		rateOfFireCounter = 0;
		spawnRate=6f;
		spawnRateCounter=0;
		
		//clears entities
		clearEnemies();
		clearProjectiles();
		
		
	}
	private void clearEnemies()
	{
		for(int i = 0;i<=enemies.size();i++)
			enemies.remove(i);
	}
	private void clearProjectiles()
	{
		for(int i = 0;i<=projectiles.size();i++)
			projectiles.remove(i);
	}
	public void createProjectiles()
	{
		rateOfFireCounter=0;
		projectiles.add(new Projectile(new Sprite(Asset.manager.get(Asset.imgProjectile)),degreesCounter,projectileSpeed));
	}
}
