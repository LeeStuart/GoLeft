package game;

import game.Enumerations.ControlValues;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game {
	
	//Pause flag to check if game is paused
	public static boolean pauseFlag;
	
	//Pause menu
	private PauseMenu pauseMenu;
	
	//Variables for game
	private Map map;
	private Player player;
	private Camera camera;
	private HUD hud;

	public Game(GameContainer container) throws SlickException {
		
		pauseFlag = false;
		
		pauseMenu = new PauseMenu();
		
		player = new Player(container);
		map = new Map("test.map", player);
		player.load(150, 150, map);
		camera = new Camera(container, map);
		hud = new HUD();
		
	}
	
	public void render(Graphics g) {
		
		//Draw camera
		camera.render(g);
		
		//Draw hud
		hud.render(g);
		
		//Draw pause menu if necessary
		if (pauseFlag) {
			pauseMenu.render(g);
		}
		
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
		
		//Check if pause key is pressed
		Input input = container.getInput();
		if (input.isKeyPressed(Main.options.getControls(ControlValues.PAUSE)) || input.isControlPressed(11, Main.currentController) || (pauseFlag && input.isControlPressed(5, Main.currentController))) {
			pauseFlag = !pauseFlag;
			
			//Reset keys pressed
			for (ControlValues cv : ControlValues.values()) {
				input.isKeyPressed(Main.options.getControls(cv));
			}
			for (int i = 0; i < 16; i++) {
				input.isControlPressed(i);
			}
		}
		
		//Update pause menu if necessary
		if (pauseFlag) {
			Main.cursorFlag = true;
			pauseMenu.update(container);
			camera.updateVariables();
			return;
		} else {
			Main.cursorFlag = false;
		}
		
		//Update map
		map.update(container, delta);
		camera.update();
		hud.update(container);
		
	}

}
