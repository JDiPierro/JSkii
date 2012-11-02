package game;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Game  extends BasicGame
{
	public enum GAME_STATE
	{
		PAUSE, PLAY
	}	
	
	private static double MAX_SPEED = 8.0;
	public static boolean DEBUG = true;
	
	private world.Map map;
	private entities.Player player;
	private double speed;
	public JTimer timer;
	
	private debug.kbdControls debugInput;
	private GAME_STATE state;
	
	public Game()
	{
		super("JSkii");
	}

	@Override
	public void init(GameContainer arg0)
			throws SlickException
	{
		// TODO Auto-generated method stub
		map = new world.Map(this);
		player = new entities.Player(this, map, Settings.getWinW() / 2, 40);
		debugInput = new debug.kbdControls(arg0, this, player);
		state = GAME_STATE.PLAY;
		arg0.setVSync(true);
		arg0.setTargetFrameRate(80);
		speed = 1;
		timer = new JTimer(arg0);
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1)
			throws SlickException
	{
		map.render(arg0, arg1);
		player.render(arg0, arg1);
	}
	
	@Override
	public void update(GameContainer arg0, int arg1)
			throws SlickException
	{
		// TODO Auto-generated method stub
		timer.Update();
		debugInput.update();
		player.update();
		if( state == GAME_STATE.PLAY )
			map.scrollMap();
		
		if(timer.isOnTick())
		{
			if(speed < MAX_SPEED)
			{
				speed+=0.25;
				player.increaseSpeed();
			}
		}
	}
	
	public void toggleState()
	{
		state = (state == GAME_STATE.PLAY) ? GAME_STATE.PAUSE : GAME_STATE.PLAY;
	}
	public double getSpeed()
	{ return speed; }
	
	public void SlowDown()
	{
		if(speed > 1)
			speed = speed / 2;
	}
}
