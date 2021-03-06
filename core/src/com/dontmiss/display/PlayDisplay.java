package com.dontmiss.display;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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
	private float rateFire;
	private float rateFireCounter;
	private float rateChangingSpin;
	private float rateChangingSpinCounter;
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
	private int timerMins;
	private float timerSecs;

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

	private float opacityRate;
	private float radiusToDisappear;
	private boolean isChangingDir;
	
	
	
	/* USER INTERFACE */
//	private Stage stage;
//	private Label lblTimer;
//	private LabelStyle lblStyleMain;
//	private TextField txt;
//	private TextFieldStyle txtStyle;
	
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
		timerMins = 2;
		timerSecs = 59;

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
		//sprTurret.setSize(256,256);
		sprTurret.setOrigin(sprTurret.getWidth()/2, sprTurret.getHeight()/2);
		
		circleTurret = new Circle(sprTurret.getX()+(sprTurret.getWidth()/2),sprTurret.getY()+(sprTurret.getHeight()/2),sprTurret.getHeight()/2);
		
		fontAbstract = Asset.manager.get(Asset.fontAbstract);
		fontAbstract.setScale(3f);
		
		rateChangingSpin = 1f;
		rateChangingSpinCounter = 0;
		rateFire =.001f;
		rateFireCounter = 0;
		spawnRate=6f;
		spawnRateCounter=0;
		
		
		
		
		
		
		opacityRate = .3f;
		isChangingDir = false;
		radiusToDisappear = 2.5f;
		
		
		/* User Interface */
		
//		stage = new Stage(new ScreenViewport(),batch);
//		//stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
//		
//		lblStyleMain = new LabelStyle(fontAbstract,Color.WHITE);
//		lblTimer = new Label("hello",lblStyleMain);
//		lblTimer.setHeight(300);
//		lblTimer.setWidth(900);
//		lblTimer.setVisible(true);
//		lblTimer.setPosition(300, 300);
//		
//	
//		
//		stage.addActor(lblTimer);

		
		
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
			rateFireCounter+=delta;
			spawnRateCounter+=delta;
			rateChangingSpinCounter+= delta;
			//adds on degrees and rotates the turret per frame
			degreesCounter += rotationSpeed*delta;
			sprTurret.rotate(rotationSpeed*delta);
			
			
			if(Gdx.input.isKeyPressed(Keys.C)&&rateChangingSpinCounter>=rateChangingSpin)
			{
				rateChangingSpinCounter = 0;
				rotationSpeed *= -1;
			}
			
			updateChallenge(delta);
			updateTimer(delta);
			spawn();
			//updateGUI();
			updateDisplay(delta);
			checkCollision();
			removeEntities();
			//stage.act();
		}
	}

	@Override
	public void resize(int width, int height) 
	{
		//stage.getViewport().update(width,height,true);
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
	public float getRateFire() {
		return rateFire;
	}

	public float getRateFireCounter() {
		return rateFireCounter;
	}

	public boolean isPaused() 
	{
		return paused;
	}

	public float getRateChangingTheSpin() 
	{
		return rateChangingSpin;
	}

	public float getRateChangingSpinCounter() {
		return rateChangingSpinCounter;
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

	public int getTimerMins() {
		return timerMins;
	}

	public float getTimerSecs() {
		return timerSecs;
	}

	public float getDegreesCounter() {
		return degreesCounter;
	}

	public void setRateFire(float rateOfFire) {
		this.rateFire = rateOfFire;
	}

	public void setRateFireCounter(float rateOfFireCounter) {
		this.rateFireCounter = rateOfFireCounter;
	}

	public void setRateChangingSpin(float rateOfChangingTheSpin) {
		this.rateChangingSpin = rateOfChangingTheSpin;
	}

	public void setRateChangingSpinCounter(float rateOfChangingTheSpinCounter) {
		this.rateChangingSpinCounter = rateOfChangingTheSpinCounter;
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

	public void setTimerMins(int timerTotalMins) {
		this.timerMins = timerTotalMins;
	}

	public void setTimerSecs(float timerTotalSecs) {
		this.timerSecs = timerTotalSecs;
	}

	public void setDegreesCounter(float degreesCounter) {
		this.degreesCounter = degreesCounter;
	}
	
	public void setPaused(boolean paused) 
	{
		this.paused = paused;
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
			fontAbstract.draw(batch, (timerMins + ":" + df.format(timerSecs) + "   " + victoryMessage), 950, 700);
			
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
					newBGColor();
					if(isChangingDir)
					{
						rotationSpeed*=-1;
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
	private void newBGColor()
	{
		//Sets the new background color, randomly
		colorR = (rdm.nextFloat()); 
		colorG = (rdm.nextFloat());
		colorB = (rdm.nextFloat());
		if(colorR >=.7 && colorA <=.15 && colorB <=.15)
		{
			while((colorR >=.7 && colorA <=.15 && colorB <=.15))
			{
				colorR = (rdm.nextFloat()); 
				colorG = (rdm.nextFloat());
				colorB = (rdm.nextFloat());
			}
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
			if(timerMins<=-1)
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
		if(timerSecs<=0)
		{
			timerMins-= 1;
			timerSecs=59;
			if(timerMins<=-1)
			{
				victoryMessage = "Victory!";
			}
		}
		else
		{
			timerSecs-=delta;
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
		timerMins = 2;
		timerSecs = 59;

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
		
		rateChangingSpin = 1f;
		rateChangingSpinCounter = 0;
		rateFire =.5f;
		rateFireCounter = 0;
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
		rateFireCounter=0;
		projectiles.add(new Projectile(new Sprite(Asset.manager.get(Asset.imgProjectile)),degreesCounter,projectileSpeed));
	}
	public void updateChallenge(float delta)
	{
		if(timerSecs<30)
			disappear(delta);
		if(timerSecs<25)
			isChangingDir=true;
		else if(timerSecs<=1)
			isChangingDir=false;
		if(timerMins==2)
			enemySpeed=.05f;
		else if(timerMins==1)
			enemySpeed=.07f;
		else if(timerMins==0)
			enemySpeed=.09f;
	}
	public void disappear(float delta)
	{	
		for(int i = 0;i<enemies.size();i++)
		{
			if(Math.sqrt(Math.pow(((enemies.get(i).getSprite().getX() + (enemies.get(i).getSprite().getWidth()/2) ) - (Gdx.graphics.getWidth()/2)),2)+ Math.pow( ((enemies.get(i).getSprite().getY() + (enemies.get(i).getSprite().getHeight()/2)) - (Gdx.graphics.getHeight()/2)),2))<= Math.sqrt(   (Math.pow((Gdx.graphics.getWidth()/2),2)) + (Math.pow((Gdx.graphics.getHeight()/2),2)) )/radiusToDisappear)
			{
				enemies.get(i).setOpacity(enemies.get(i).getOpacity()-delta*opacityRate);
			}
				
		}
	}
	
	
	
//	public void updateGUI()
//	{
//		lblTimer.setText(timerMins + ":" + df.format(timerSecs));
//	}
}
