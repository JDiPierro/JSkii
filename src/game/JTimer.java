package game;

import org.newdawn.slick.GameContainer;

public class JTimer
{

	private int ticks;
	private GameContainer mainGame;
	
	public JTimer(GameContainer gc)
	{
		ticks = 0;
		mainGame = gc;
	}
	
	public void Update()
	{
		ticks++;		
	}
	
	public int getTicks()
	{
		return ticks;
	}
	
	public boolean isOnTick()
	{
		int FPS = mainGame.getFPS();
		if(FPS != 0)
		{
			if(ticks % FPS == 0)
				return true;
		}
		
		return false;
	}
	
}
