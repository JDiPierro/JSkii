package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer app = new AppGameContainer(new Game());
		
			app.setDisplayMode(800,  600, false);
			app.start();
		}
		catch(SlickException e)
		{
			System.out.println("Game Start Fail");
		}

	}

}
