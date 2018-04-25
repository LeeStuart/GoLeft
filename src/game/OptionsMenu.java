package game;

import game.Enumerations.ControlValues;

import java.awt.Font;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

public class OptionsMenu implements KeyListener {
	
	//Rectangle for mouse
	private Rectangle mouse;
	
	//Buttons
	private Rectangle btnBack;
	private Rectangle btnGraphics;
	private Rectangle btnAudio;
	private Rectangle btnControls;
	
	//Button images
	private Image imgBack;
	private Image imgGraphics;
	private Image imgAudio;
	private Image imgControls;
	
	//Tab stuff
	private Rectangle background;
	private String currentTab;
	private HashMap<Rectangle, Image> tabButtonImages;
	private HashMap<Rectangle, String> tabButtonStrings;
	
	//Control change stuff
	private boolean acceptingInput;
	private boolean gotInput;
	private int lastKeyPressed;
	private ControlValues key;
	
	//Keyboard Menu Select stuff
	private boolean keyboardControlFlag;
	private float oldMouseX;
	private float oldMouseY;
	private int selectedButton;
	private int tabSelectedButton;
	private boolean tabButtonFlag;
	private float[][] buttonPositions = {{0, 0}, {0, 0}, {0, 0}, {0, 0}};
	
	//Font
	private TrueTypeFont ttf;

	public OptionsMenu(GameContainer container) throws SlickException {
		
		mouse = new Rectangle(0, 0, 1, 1);
		
		btnBack = new Rectangle(0, 0, 150, 50);
		btnGraphics = new Rectangle(0, 0, 150, 50);
		btnAudio = new Rectangle(0, 0, 150, 50);
		btnControls = new Rectangle(0, 0, 150, 50);
		
		imgBack = ImageHandler.optionsMenu.getSprite(0, 3);
		imgGraphics = ImageHandler.optionsMenu.getSprite(0, 0);
		imgAudio = ImageHandler.optionsMenu.getSprite(0, 1);
		imgControls = ImageHandler.optionsMenu.getSprite(0, 2);
		
		currentTab = "graphics";
		background = new Rectangle(160, 10, 1000, 1000);
		tabButtonImages = new HashMap<Rectangle, Image>();
		tabButtonStrings = new HashMap<Rectangle, String>();
		
		acceptingInput = false;
		gotInput = false;
		container.getInput().addKeyListener(this);
		
		selectedButton = 0;
		tabButtonFlag = false;
		keyboardControlFlag = false;
		
		Font f = new Font("Sans", Font.PLAIN, 15);
		ttf = new TrueTypeFont(f, false);
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(imgBack, (int)btnBack.getX(), (int)btnBack.getY());
		g.drawImage(imgGraphics, (int)btnGraphics.getX(), (int)btnGraphics.getY());
		g.drawImage(imgAudio, (int)btnAudio.getX(), (int)btnAudio.getY());
		g.drawImage(imgControls, (int)btnControls.getX(), (int)btnControls.getY());
		
		g.setColor(new Color(8, 172, 0));
		g.fill(background);
		
		ttf.drawString(background.getWidth() + 150 - ttf.getWidth(Main.VERSION_NUMBER), background.getHeight() - 20, Main.VERSION_NUMBER);
		
		g.setColor(Color.black);
		for (Rectangle r: tabButtonImages.keySet()) {
			g.drawImage(tabButtonImages.get(r), 180, (int)r.getY());
			ttf.drawString(340, (int)r.getCenterY() - 10, tabButtonStrings.get(r));
		}
		
	}
	
	public void update(GameContainer container) throws SlickException {
		
		//Set button positions
		btnBack.setX(10);
		btnBack.setY(container.getHeight() - 60);
		btnGraphics.setX(10);
		btnGraphics.setY(10);
		btnAudio.setX(10);
		btnAudio.setY(70);
		btnControls.setX(10);
		btnControls.setY(130);

		buttonPositions[0][0] = btnGraphics.getCenterX();
		buttonPositions[0][1] = btnGraphics.getCenterY();
		buttonPositions[1][0] = btnAudio.getCenterX();
		buttonPositions[1][1] = btnAudio.getCenterY();
		buttonPositions[2][0] = btnControls.getCenterX();
		buttonPositions[2][1] = btnControls.getCenterY();
		buttonPositions[3][0] = btnBack.getCenterX();
		buttonPositions[3][1] = btnBack.getCenterY();
		
		//Set button images
		imgBack = ImageHandler.optionsMenu.getSprite(0, 3);
		imgGraphics = ImageHandler.optionsMenu.getSprite(0, 0);
		imgAudio = ImageHandler.optionsMenu.getSprite(0, 1);
		imgControls = ImageHandler.optionsMenu.getSprite(0, 2);
		background = new Rectangle(160, 10, container.getWidth() - 170, container.getHeight() - 20);
		
		//Get input
		Input input = container.getInput();

		//Check keyboard input
		if (!tabButtonFlag) {
			if (input.isKeyPressed(Input.KEY_DOWN) || input.isControlPressed(3, Main.currentController)) {
				if (selectedButton == -1 || selectedButton >= buttonPositions.length - 1) {
					selectedButton = 0;
				} else {
					selectedButton++;
				}
				if (currentTab.equals("graphics") && selectedButton == 0) {
					selectedButton = 1;
				} else if (currentTab.equals("audio") && selectedButton == 1) {
					selectedButton = 2;
				} else if (currentTab.equals("controls") && selectedButton == 2) {
					selectedButton = 3;
				}
				keyboardControlFlag = true;
			} else if (input.isKeyPressed(Input.KEY_UP) || input.isControlPressed(2, Main.currentController)) {
				if (selectedButton == -1 || selectedButton <= 0) {
					selectedButton = buttonPositions.length - 1;
				} else {
					selectedButton--;
				}
				if (currentTab.equals("graphics") && selectedButton == 0) {
					selectedButton = 3;
				} else if (currentTab.equals("audio") && selectedButton == 1) {
					selectedButton = 0;
				} else if (currentTab.equals("controls") && selectedButton == 2) {
					selectedButton = 1;
				}
				keyboardControlFlag = true;
			}
		} else {
			if (input.isKeyPressed(Input.KEY_DOWN) || input.isControlPressed(3, Main.currentController)) {
				if (tabSelectedButton == -1) {
					tabSelectedButton = 0;
				} else {
					tabSelectedButton++;
				}
				keyboardControlFlag = true;
			} else if (input.isKeyPressed(Input.KEY_UP) || input.isControlPressed(2, Main.currentController)) {
				if (tabSelectedButton != -1) {
					tabSelectedButton--;
				}
				keyboardControlFlag = true;
			}
		}
		if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_RIGHT) || input.isControlPressed(0, Main.currentController) || input.isControlPressed(1, Main.currentController)) {
			tabButtonFlag = !tabButtonFlag;
		}
		
		//Check if mouse has been moved
		if (input.getMouseX() != oldMouseX && input.getMouseY() != oldMouseY) {
			keyboardControlFlag = false;
			tabButtonFlag = false;
			selectedButton = -1;
			tabSelectedButton = -1;
		}
		
		//Update "mouse" position
		if (!keyboardControlFlag) {
			mouse.setCenterX(input.getMouseX());
			mouse.setCenterY(input.getMouseY());
		} else if (!tabButtonFlag && selectedButton != -1) {
			mouse.setCenterX(buttonPositions[selectedButton][0]);
			mouse.setCenterY(buttonPositions[selectedButton][1]);
		}
		oldMouseX = input.getMouseX();
		oldMouseY = input.getMouseY();
		
		//Clear tab stuff
		tabButtonImages.clear();
		tabButtonStrings.clear();
		
		//Get which tab to update
		if (currentTab.equals("graphics")) {
			imgGraphics = ImageHandler.optionsMenu.getSprite(2, 0);
			updateGraphicsTab(container);
		} else if (currentTab.equals("audio")) {
			imgAudio = ImageHandler.optionsMenu.getSprite(2, 1);
			updateAudioTab(container);
		} else if (currentTab.equals("controls")) {
			imgControls = ImageHandler.optionsMenu.getSprite(2, 2);
			updateControlsTab(container);
		}
		
		//Check if action is required
		if (mouse.intersects(btnBack)) {
			imgBack = ImageHandler.optionsMenu.getSprite(1, 3);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				//Reset variables
				Main.optionsFlag = false;
				acceptingInput = false;
				gotInput = false;
				key = null;
				Main.options.saveOptions();
				
				//Reset keys pressed
				for (ControlValues cv : ControlValues.values()) {
					input.isKeyPressed(Main.options.getControls(cv));
				}
				
				//Apply new settings
				Main.updateDisplay((AppGameContainer)container);
			}
		}
		if (mouse.intersects(btnGraphics) && !currentTab.equals("graphics")) {
			imgGraphics = ImageHandler.optionsMenu.getSprite(1, 0);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				currentTab = "graphics";
				acceptingInput = false;
				gotInput = false;
				key = null;
			}
		}
		if (mouse.intersects(btnAudio) && !currentTab.equals("audio")) {
			imgAudio = ImageHandler.optionsMenu.getSprite(1, 1);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				currentTab = "audio";
				acceptingInput = false;
				gotInput = false;
				key = null;
			}
		}
		if (mouse.intersects(btnControls) && !currentTab.equals("controls")) {
			imgControls = ImageHandler.optionsMenu.getSprite(1, 2);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				currentTab = "controls";
				acceptingInput = false;
				gotInput = false;
				key = null;
			}
		}
		
		//Check if user presses escape or 'b'
		if ((input.isKeyPressed(Input.KEY_ESCAPE) || input.isControlPressed(5, Main.currentController))) {
			
			//Reset variables
			Main.optionsFlag = false;
			acceptingInput = false;
			gotInput = false;
			key = null;
			Main.options.saveOptions();
			
			//Reset keys pressed
			for (ControlValues cv : ControlValues.values()) {
				input.isKeyPressed(Main.options.getControls(cv));
			}
			for (int i = 0; i < 16; i++) {
				input.isControlPressed(i);
			}
			
			//Apply new settings
			Main.updateDisplay((AppGameContainer)container);
		}
		
		//Check cursor
		Main.cursorFlag = !keyboardControlFlag;
		
	}
	
	private void updateGraphicsTab(GameContainer container) throws SlickException {
		
		//Set buttons up
		Rectangle btnResolution = new Rectangle(180, 20, 150, 50);
		Image imgResolution = ImageHandler.optionsGraphics.getSprite(0, 0);
		Rectangle btnFullscreen = new Rectangle(180, 80, 150, 50);
		Image imgFullscreen = ImageHandler.optionsGraphics.getSprite(0, 1);
		
		//Check button
		if (tabButtonFlag) {
			if (tabSelectedButton < 0) {
				tabSelectedButton = 1;
			} else if (tabSelectedButton > 1) {
				tabSelectedButton = 0;
			}
			switch (tabSelectedButton) {
			case 0:
				mouse.setX(btnResolution.getCenterX());
				mouse.setY(btnResolution.getCenterY());
				break;
			case 1:
				mouse.setX(btnFullscreen.getCenterX());
				mouse.setY(btnFullscreen.getCenterY());
				break;
			}
		}
		
		//Get input
		Input input = container.getInput();
		
		//Check if action is required
		if (mouse.intersects(btnResolution)) {
			imgResolution = ImageHandler.optionsGraphics.getSprite(1, 0);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Main.options.changeResolution(true);
			} else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) || input.isKeyPressed(Input.KEY_BACK) || input.isControlPressed(6, Main.currentController)) {
				Main.options.changeResolution(false);
			}
		}
		tabButtonStrings.put(btnResolution, Main.options.getStringResolution());
		
		if (mouse.intersects(btnFullscreen)) {
			imgFullscreen = ImageHandler.optionsGraphics.getSprite(1, 1);
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER) || input.isControlPressed(4, Main.currentController)) {
				Main.options.changeFullscreen();
			}
		}
		tabButtonStrings.put(btnFullscreen, "" + Main.options.getFullscreen());
		
		//Set images for tab buttons
		tabButtonImages.put(btnResolution, imgResolution);
		tabButtonImages.put(btnFullscreen, imgFullscreen);
		
	}
	
	private void updateAudioTab(GameContainer container) {
		
	}
	
	private void updateControlsTab(GameContainer container) throws SlickException {
		
		//Set buttons up
		Rectangle btnJump = new Rectangle(180, 20, 150, 50);
		Image imgJump = ImageHandler.optionsControls.getSprite(0, 0);
		Rectangle btnLeft = new Rectangle(180, 80, 150, 50);
		Image imgLeft = ImageHandler.optionsControls.getSprite(0, 1);
		Rectangle btnRight = new Rectangle(180, 140, 150, 50);
		Image imgRight = ImageHandler.optionsControls.getSprite(0, 2);
		Rectangle btnDown = new Rectangle(180, 200, 150, 50);
		Image imgDown = ImageHandler.optionsControls.getSprite(0, 3);
		Rectangle btnPause = new Rectangle(180, 260, 150, 50);
		Image imgPause = ImageHandler.optionsControls.getSprite(0, 4);
		
		//Check button
		if (tabButtonFlag) {
			if (tabSelectedButton < 0) {
				tabSelectedButton = 4;
			} else if (tabSelectedButton > 4) {
				tabSelectedButton = 0;
			}
			switch (tabSelectedButton) {
			case 0:
				mouse.setX(btnJump.getCenterX());
				mouse.setY(btnJump.getCenterY());
				break;
			case 1:
				mouse.setX(btnLeft.getCenterX());
				mouse.setY(btnLeft.getCenterY());
				break;
			case 2:
				mouse.setX(btnRight.getCenterX());
				mouse.setY(btnRight.getCenterY());
				break;
			case 3:
				mouse.setX(btnDown.getCenterX());
				mouse.setY(btnDown.getCenterY());
				break;
			case 4:
				mouse.setX(btnPause.getCenterX());
				mouse.setY(btnPause.getCenterY());
				break;
			}
		}
		
		//Get input
		Input input = container.getInput();
		
		//Key has been pressed, so set the control to that key
		if (acceptingInput == false && gotInput == true) {
			Main.options.setControlKey(key, lastKeyPressed);
			input.isKeyPressed(lastKeyPressed);
			key = null;
			gotInput = false;
		}
		
		//Check if action is required
		if (mouse.intersects(btnJump)) {
			imgJump = ImageHandler.optionsControls.getSprite(1, 0);
			if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER)) && acceptingInput == false) {
				key = ControlValues.JUMP;
				acceptingInput = true;
			}
		}
		tabButtonStrings.put(btnJump, Input.getKeyName(Main.options.getControls(ControlValues.JUMP)));
		
		if (mouse.intersects(btnLeft)) {
			imgLeft = ImageHandler.optionsControls.getSprite(1, 1);
			if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER)) && acceptingInput == false) {
				key = ControlValues.LEFT;
				acceptingInput = true;
			}
		}
		tabButtonStrings.put(btnLeft, Input.getKeyName(Main.options.getControls(ControlValues.LEFT)));
		
		if (mouse.intersects(btnRight)) {
			imgRight = ImageHandler.optionsControls.getSprite(1, 2);
			if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER)) && acceptingInput == false) {
				key = ControlValues.RIGHT;
				acceptingInput = true;
			}
		}
		tabButtonStrings.put(btnRight, Input.getKeyName(Main.options.getControls(ControlValues.RIGHT)));
		
		if (mouse.intersects(btnDown)) {
			imgDown = ImageHandler.optionsControls.getSprite(1, 3);
			if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER)) && acceptingInput == false) {
				key = ControlValues.DOWN;
				acceptingInput = true;
			}
		}
		tabButtonStrings.put(btnDown, Input.getKeyName(Main.options.getControls(ControlValues.DOWN)));
		
		if (mouse.intersects(btnPause)) {
			imgPause = ImageHandler.optionsControls.getSprite(1, 4);
			if ((input.isMousePressed(Input.MOUSE_LEFT_BUTTON) || input.isKeyPressed(Input.KEY_ENTER)) && acceptingInput == false) {
				key = ControlValues.PAUSE;
				acceptingInput = true;
			}
		}
		tabButtonStrings.put(btnPause, Input.getKeyName(Main.options.getControls(ControlValues.PAUSE)));
		
		//Set last button pressed to set image
		if (key == ControlValues.JUMP) {
			imgJump = ImageHandler.optionsControls.getSprite(2, 0);
		} else if (key == ControlValues.LEFT) {
			imgLeft = ImageHandler.optionsControls.getSprite(2, 1);
		} else if (key == ControlValues.RIGHT) {
			imgRight = ImageHandler.optionsControls.getSprite(2, 2);
		} else if (key == ControlValues.DOWN) {
			imgDown = ImageHandler.optionsControls.getSprite(2, 3);
		} else if (key == ControlValues.PAUSE) {
			imgPause = ImageHandler.optionsControls.getSprite(2, 4);
		}
		
		//Set tab button images
		tabButtonImages.put(btnJump, imgJump);
		tabButtonImages.put(btnLeft, imgLeft);
		tabButtonImages.put(btnRight, imgRight);
		tabButtonImages.put(btnDown, imgDown);
		tabButtonImages.put(btnPause, imgPause);
		
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		
		return true;
		
	}

	@Override
	public void setInput(Input input) {
		
	}

	@Override
	public void keyPressed(int key, char c) {
		
		//If accepting input is false, then not looking for key
		if (acceptingInput == false) {
			return;
		}
		
		//Set last key pressed and stop looking for key
		lastKeyPressed = key;
		acceptingInput = false;
		gotInput = true;
		
	}

	@Override
	public void keyReleased(int key, char c) {
		
	}

}
