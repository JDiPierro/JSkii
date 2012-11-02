package world;

import java.util.Random;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Map
{	
	private static int MAX_OBSTACLES = 100;
	
	/******************************************************/
	private int[][] map;
	private Obstacle[] obstacles;
	private SpriteSheet worldsheet;
	private game.Game theGame;
	
	private int boundsX;
	private int boundsY;
	private int numObs = 0;
	
	//@Purpose: Initial object instantiation and map generation.
	public Map(game.Game inGame)
	{
		theGame = inGame;
		try
		{
			worldsheet = new SpriteSheet("graphics/worldsheet.jpg",20,20);
		}
		catch (SlickException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boundsX = game.Settings.getWinW();
		boundsY = game.Settings.getWinH() ;
		
		map = new int[boundsX / game.Settings.getTileDim()][boundsY / game.Settings.getTileDim()];
		obstacles = new Obstacle[MAX_OBSTACLES];
		
		//Fill with grass <--change to snow.
		for(int x = 0; x < boundsX / game.Settings.getTileDim(); x++)
		{
			for(int y = 0; y < boundsY / game.Settings.getTileDim(); y++)
			{
				map[x][y] = 0;
			}
		}
		
		//Determine random number of trees to place
		Random rand = new Random();
			
		//Determine random placement of Obstacles
		int[][] tempObsTiles = new int[boundsX / game.Settings.getTileDim()][boundsY / game.Settings.getTileDim()];
		while(numObs < MAX_OBSTACLES)
		{
			int randX = rand.nextInt(boundsX) / game.Settings.getTileDim();
			int randY = rand.nextInt(boundsY) / game.Settings.getTileDim();
			int obsType = rand.nextInt(Obstacle.numObsTypes) + 1;
			if(tempObsTiles[randX][randY] != 1)
			{
				obstacles[numObs] = new Obstacle(worldsheet,								//Sprite Sheet
												randX * game.Settings.getTileDim(),			//X position
												randY * game.Settings.getTileDim(),			//Y position
												Obstacle.ObstacleType.int2Type(obsType));	//Object Type
				numObs++;
				tempObsTiles[randX][randY] = 1;
			}
		}//End While		
	}
	
	public int getTile(int x, int y)
	{
		return map[x][y];
	}
	
	public void scrollMap()
	{
		//Scroll existing obstacles
		for(int i = 0; i < numObs; i++)
		{
			double currY = obstacles[i].getY();
			currY -= theGame.getSpeed();
			//If the obstacle is going off the screen
			if(currY < (0 - game.Settings.getTileDim()))
			{
				//'delete' it
				if(i != (numObs - 1))
				{
					obstacles[i] = obstacles[numObs-1];
					numObs--;
				}
				else numObs--;
			}
			else obstacles[i].setY(currY);
			
			//Move outside of this method.
			Random rand = new Random();
			
			//Create new obstacles at the bottom of the screen.
			int[] tempObsTiles = new int[boundsX / game.Settings.getTileDim()];
			
			for(int j = 0; j < (MAX_OBSTACLES - numObs); j++)
			{
				int randX = rand.nextInt(boundsX) / game.Settings.getTileDim();
				if(tempObsTiles[randX] != 1)
				{
					int obsType = rand.nextInt(Obstacle.numObsTypes) + 1;
					
					obstacles[numObs] = new Obstacle(worldsheet, randX * game.Settings.getTileDim(), boundsY, Obstacle.ObstacleType.int2Type(obsType));
					
					numObs++;
					tempObsTiles[randX] = 1;
				}
			}
		}
	}
	
	private boolean posValidEntity(double inX, double inY, int i)
	{
		double pT = inY;
		double pB = inY + 20;
		double pL = inX;
		double pR = inX + 20;
		
		double nT, nB, nL, nR;
		
		
		nT = obstacles[i].getY();
		nB = nT + 20;
		nL = obstacles[i].getX();
		nR = nL + 20;
		
		if(pB < nT) return false;
		if(pT > nB) return false;
		
		if(pR < nL) return false;
		if(pL > nR) return false;
		
		return true;
	}
	
	public boolean validMove(double inX, double inY)
	{
		boolean Ret = true;
		
		for(int i = 0; i < obstacles.length; i++)
		{
			if(posValidEntity(inX, inY, i))
				Ret = false;
		}
		
		return Ret;
	}
	
	/////////////////////////////////////////////////////
	public void render(GameContainer arg0, Graphics arg1)
			throws SlickException
	{
		//Draw the grass
		for(int x = 0; x < boundsX / game.Settings.getTileDim(); x++)
		{
			for(int y = 0; y < boundsY / game.Settings.getTileDim(); y++)
			{
				worldsheet.startUse();
				worldsheet.renderInUse(x*game.Settings.getTileDim(),y*game.Settings.getTileDim(),map[x][y],0);
				worldsheet.endUse();
			}
			for(int i = 0; i < numObs; i++)
			{
				obstacles[i].render(arg0, arg1);
			}
		}
		
	}
}
