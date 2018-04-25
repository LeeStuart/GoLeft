package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class PauseMenu {
	
	//Rectangle for mouse
	private Rectangle mouse;
	
	//Buttons
	private Rectangle btnResume;
	private Rectangle btnOptions;
	private Rectangle btnQuit;
	
	//Button images
	private Image imgResume;
	private Image imgOptions;
	private Image imgQuit;
	
	//Keyboard Menu Select stuff
	private boolean keyboardControlFlag;
	private float oldMouseX;
	private float oldMouseY;
	private int selectedButton;
	private float[][] buttonPositions = {{0, 0}, {0, 0}, {0, 0}};

	public PauseMenu() throws SlickException {
		
		mouse = new Rectangle(0, 0, 1, 1);
		
		btnResume = new Rectangle(0, 0, 250, 75);
		btnOptions = new Rectangle(0, 0, 250, 75);
		btnQuit = new Rectangle(0, 0, 250, 75);
		
		imgResume = ImageHandler.pauseMenu.getSprite(0, 0);
		imgOptions = ImageHandler.pauseMenu.getSprite(0, 1);
		imgQuit = ImageHandler.pauseMenu.getSprite(0, 2);
		
		selectedButton = -1;
		keyboardControlFlag = false;
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(imgResume, (int)btnResume.getX(), (int)btnResume.getY());
		g.drawImage(imgOptions, (int)btnOptions.getX(), (int)btnOptions.getY());
		g.drawImage(imgQuit, (int)btnQuit.getX(), (int)btnQuit.getY());
		
	}
	
	public void update(GameContainer container) throws SlickException {
		
		//Set button positions
		btnResume.setCenterX(container.getWidth() / 2);
		btnResume.setCenterY(-85 + container.getHeight() / 2);
		btnOptions.setCenterX(container.getWidth() / 2);
		btnOptions.setCenterY(container.getHeight() / 2);
		btnQuit.setCenterX(container.getWidth() / 2);
		btnQuit.setCenterY(85 + container.getHeight() / 2);
		
		buttonPositions[0][0] = btnResume.getCenterX();
		buttonPositions[0][1] = btnResume.getCenterY();
		buttonPositions[1][0] = btnOptions.getCenterX();
		buttonPositions[1][1] = btnOptions.getCenterY();
		buttonPositions[2][0] = btnQuit.getCenterX();
		buttonPositions[2][1] = btnQuit.getCenterY();
		
		//Set default button images
		imgResume = ImageHandler.pauseMenu.getSprite(0, 0);
		imgOptions = ImageHandler.pauseMenu.getSprite(0, 1);
		imgQuit = ImageHandler.pauseMenu.getSprite(0, 2);
		
		//Get input
		Input input = container.getInput();
		
		//Check keyboard input
		if (input.isKeyPressed(Input.KEY_DOWN) || input.isControlPressed(3, Main.currentController)) {
			if (selectedButton == -1 || selectedButton >= buttonPositions.length - 1) {
				selectedButton = 0;
			} else {
				selectedButton++;
			}
			keyboardControlFlag = true;
		} else if (input.isKeyPressed(Input.KEY_UP) || input.isControlPressed(2, Main.currentController)) {
			if (selectedButton == -1 || selectedButton <= 0) {
				selectedButton = buttonPositions.length - 1;
			} else {
				selectedButton--;
			}
			keyboardControlFlag = true;
		}
		
		//Check if mouse has been moved
		if (input.getMouseX() != oldMouseX && input.getMouseY() != oldMouseY) {
			keyboardControlFlag = false;
			selectedButton = -1;
		}
		
		//Update "mouse" position
		if (!keyboardControlFlag) {
			mouse.setCenterX(input.getMouseX());
			mouse.setCenterY(input.getMouseY());
		} else {
			mouse.setCenterX(buttonPositions[selectedButton][0]);
			mouse.setCenterY(buttonPositions[selectedButton][1]);
		}
		oldMouseX = input.getMouseX();
		oldMouseY = input.getMouseY();
		
		//Check if action is required
		if (mouse.intersects(btnResume)) {
			imgResume = ImageHandler.pauseMenu.getSprite(1, 0);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Game.pauseFlag = false;
			}
		}
		if (mouse.intersects(btnOptions)) {
			imgOptions = ImageHandler.pauseMenu.getSprite(1, 1);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Main.optionsFlag = true;
			}
		}
		if (mouse.intersects(btnQuit)) {
			imgQuit = ImageHandler.pauseMenu.getSprite(1, 2);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Main.gameFlag = false;
			}
		}
		
		//Check cursor
		Main.cursorFlag = !keyboardControlFlag;
		
	}

}
