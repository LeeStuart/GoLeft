package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class MainMenu {
	
	//Rectangle for mouse
	private Rectangle mouse;
	
	//Buttons
	private Rectangle btnGame;
	private Rectangle btnOptions;
	private Rectangle btnExit;
	
	//Button images
	private Image imgGame;
	private Image imgOptions;
	private Image imgExit;
	
	//Keyboard Menu Select stuff
	private boolean keyboardControlFlag;
	private float oldMouseX;
	private float oldMouseY;
	private int selectedButton;
	private float[][] buttonPositions = {{0, 0}, {0, 0}, {0, 0}};

	public MainMenu() throws SlickException {
		
		mouse = new Rectangle(0, 0, 1, 1);
		
		btnGame = new Rectangle(0, 0, 250, 75);
		btnOptions = new Rectangle(0, 0, 250, 75);
		btnExit = new Rectangle(0, 0, 250, 75);

		imgGame = ImageHandler.mainMenu.getSprite(0, 0);
		imgOptions = ImageHandler.mainMenu.getSprite(0, 1);
		imgExit = ImageHandler.mainMenu.getSprite(0, 2);
		
		selectedButton = -1;
		keyboardControlFlag = false;
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(imgGame, (int)btnGame.getX(), (int)btnGame.getY());
		g.drawImage(imgOptions, (int)btnOptions.getX(), (int)btnOptions.getY());
		g.drawImage(imgExit, (int)btnExit.getX(), (int)btnExit.getY());
		
	}
	
	public void update(GameContainer container) throws SlickException {
		
		//Set button positions
		btnGame.setCenterX(container.getWidth() / 2);
		btnGame.setCenterY(-85 + container.getHeight() / 2);
		btnOptions.setCenterX(container.getWidth() / 2);
		btnOptions.setCenterY(container.getHeight() / 2);
		btnExit.setCenterX(container.getWidth() / 2);
		btnExit.setCenterY(85 + container.getHeight() / 2);
		
		buttonPositions[0][0] = btnGame.getCenterX();
		buttonPositions[0][1] = btnGame.getCenterY();
		buttonPositions[1][0] = btnOptions.getCenterX();
		buttonPositions[1][1] = btnOptions.getCenterY();
		buttonPositions[2][0] = btnExit.getCenterX();
		buttonPositions[2][1] = btnExit.getCenterY();
		
		//Set default button images
		imgGame = ImageHandler.mainMenu.getSprite(0, 0);
		imgOptions = ImageHandler.mainMenu.getSprite(0, 1);
		imgExit = ImageHandler.mainMenu.getSprite(0, 2);
		
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
		if (mouse.intersects(btnGame)) {
			imgGame = ImageHandler.mainMenu.getSprite(1, 0);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Main.newGameFlag = true;
			}
		}
		if (mouse.intersects(btnOptions)) {
			imgOptions = ImageHandler.mainMenu.getSprite(1, 1);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Main.optionsFlag = true;
			}
		}
		if (mouse.intersects(btnExit)) {
			imgExit = ImageHandler.mainMenu.getSprite(1, 2);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				System.exit(0);
			}
		}
		
		//Check if user presses escape or 'b'
		if (input.isKeyPressed(Input.KEY_ESCAPE) || input.isControlPressed(5, Main.currentController)) {
			System.exit(0);
		}
		
		//Check cursor
		Main.cursorFlag = !keyboardControlFlag;
		
	}

}
