package debug;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class kbdControls
{
	private Input input;
	private entities.Player player;
	private game.Game mainGame;
	
	public kbdControls(GameContainer gc, game.Game inGame, entities.Player player)
	{
		input = gc.getInput();
		this.player = player;
		mainGame = inGame;
	}
	
	public void update()
	{
		if(input.isKeyDown(Input.KEY_W))
		{
			player.moveUp = true;
		}
		else player.moveUp = false;
		
		if(input.isKeyDown(Input.KEY_S))
		{
			player.moveDown = true;
		}
		else player.moveDown = false;
		
		if(input.isKeyDown(Input.KEY_A))
		{
			player.moveLeft = true;
		}
		else player.moveLeft = false;
		
		if(input.isKeyDown(Input.KEY_D))
		{
			player.moveRight = true;
		}
		else player.moveRight = false;
		
		if(input.isKeyDown(Input.KEY_SPACE))
		{
			mainGame.toggleState();
		}
	}

}
