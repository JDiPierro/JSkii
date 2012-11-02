package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player
{
	private static int TUMBLE_TIME = 80;
	
	public enum PLAYER_STATE
	{
		NORMAL, IN_AIR, TUMBLING
	}
	
	private world.Map map;
	private game.Game mainGame;
	private SpriteSheet mySpriteSheet;
	
	private Integer stateChangeTick;
	
	private double myX;
	private double myY;
	private double speed;
	
	public boolean moveUp;
	public boolean moveDown;
	public boolean moveLeft;
	public boolean moveRight;
	
	public PLAYER_STATE state;
	
	public Player(game.Game inGame, world.Map inMap,int startX, int startY)
	{
		try {
			mySpriteSheet = new SpriteSheet("graphics/playersheet.png",20,20);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myX = startX;
		myY = startY;
		speed = 0.7;
		map = inMap;
		mainGame = inGame;
		state = PLAYER_STATE.NORMAL;
		
		stateChangeTick = null;
	}
	
	/////////////////////////////////////////////////////
	
	public void render(GameContainer arg0, Graphics arg1)
			throws SlickException
	{
		mySpriteSheet.startUse();
		mySpriteSheet.renderInUse((int)myX,(int)myY,0,0);
		mySpriteSheet.endUse();
	}
	
	//////////
	
	private void moveLogic()
	{
		if(map.validMove(myX,  myY))
		{
			if(moveUp)
			{
				myY-= speed;
			}
			else if(moveDown)
			{
				myY += speed;
			}
			if(moveRight)
			{
				myX += speed;
			}
			else if(moveLeft)
			{
				myX -= speed;
			}
		}
		else
		{
			if( speed > 0.7)
				speed = speed / 2;
			state = PLAYER_STATE.TUMBLING;
			stateChangeTick = mainGame.timer.getTicks();
			mainGame.SlowDown();
		}
	}
	public void update()
	{
		if(state == PLAYER_STATE.NORMAL)
		{
			moveLogic();
		}
		else
		{
			if(stateChangeTick != null && mainGame.timer.getTicks() > stateChangeTick + TUMBLE_TIME)
			{
				state = PLAYER_STATE.NORMAL;
			}
		}
	}
	
	public void increaseSpeed()
	{
		speed += 0.2;
	}

}
