package com.dontmiss;
import java.util.ArrayList;
import com.dontmiss.entity.Enemy;
import com.dontmiss.entity.Projectile;
import java.util.Random;
import java.text.DecimalFormat;


public class GodConfig 
{
	//color variables for the background
	public static float colorR=1;
	public static float colorG=1;
	public static float colorB=1;
	public static float colorA = 1;

	//all the objects on the screen besides the player
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	//speed variables
	public static float projectileSpeed = 0;
	public static float enemySpeed = 0;
	public static float rotationSpeed = 140f;

	//timer variables
	public static int timerTotalMins = 2;
	public static float timerTotalSecs = 59;

	//pause variable
	public static boolean paused = false;

	//the variable that keeps track of where the turret is pointing
	public static float degreesCounter= 0;

	//this message displayed after winning
	public static String victoryMessage = "I wasted so much of your time :)";

	//random variable used to spice up the game
	//decimal format to make numbers look cleaner
	public static Random rdm = new Random();
	public static DecimalFormat df = new DecimalFormat("00");
	
}
