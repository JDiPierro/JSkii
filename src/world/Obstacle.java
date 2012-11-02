package world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Obstacle
{
	public static enum ObstacleType
	{
		ERROR(0),
		TREE(1), ROCK(2);
		
		private final int sx;
		ObstacleType(int in_sx) { this.sx = in_sx; }
		public int getValue() { return sx; }
		
		public static ObstacleType int2Type(int inInt)
		{
			switch(inInt)
			{
			case 1: return TREE;
			case 2: return ROCK;
			default: return ERROR;
			}					
		}
	}
	
	public static int numObsTypes = 2;
	
	/////////////////////////////////////////////////////
	
	private SpriteSheet myPic;
	private ObstacleType type;
	private double xCoord;
	private double yCoord;
	
	public Obstacle(SpriteSheet inPic, double inX, double inY, ObstacleType inType)
	{
		xCoord = inX;
		yCoord = inY;
		myPic = inPic;
		type = inType;
	}
	
	public double getX()
	{
		return xCoord;
	}
	
	public double getY()
	{
		return yCoord;
	}
	
	public void setY(double newY)
	{
		yCoord = newY;
	}
	
	public void render(GameContainer arg0, Graphics arg1)
			throws SlickException
	{
		myPic.startUse();
		myPic.renderInUse((int)(xCoord), (int)(yCoord), type.getValue(), 0);
		myPic.endUse();
	}
};