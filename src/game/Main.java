package game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame implements ControllerListener {
	
	//Version Number
	public static final String VERSION_NUMBER = "0.7.3";
	
	//Options variable
	public static Options options;
	
	//Flags to check what should be displayed
	public static boolean optionsFlag;
	public static boolean gameFlag;
	public static boolean cursorFlag;
	
	//Flag to set new game
	public static boolean newGameFlag;
	
	//Menus and game
	private MainMenu mainMenu;
	private OptionsMenu optionsMenu;
	private Game game;
	
	//Controllers
	public static int currentController;
	private float[] controllerAxisValues;
	public static boolean[] controllerFixed;
	public static boolean[] controllerHasAxis;

	public Main(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g)	throws SlickException {
		
		//Check which part to render
		if (optionsFlag) {
			optionsMenu.render(g);
		} else if (gameFlag) {
			game.render(g);
		} else {
			mainMenu.render(g);
		}
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		
		//Back end options init
		options = new Options();
		updateDisplay((AppGameContainer)container);
		ImageHandler.loadImages();
		
		//Menus init
		mainMenu = new MainMenu();
		optionsMenu = new OptionsMenu(container);
		game = new Game(container);
		
		//Variables init
		optionsFlag = false;
		gameFlag = false;
		newGameFlag = false;
		cursorFlag = true;
		
		//Controller
		Input input = container.getInput();
		controllerAxisValues = new float[input.getControllerCount()];
		controllerHasAxis = new boolean[input.getControllerCount()];
		controllerFixed = new boolean[input.getControllerCount()];
		for (int i = 0; i < input.getControllerCount(); i++) {
			try {
				controllerAxisValues[i] = input.getAxisValue(i, 0);
				controllerHasAxis[i] = true;
			} catch (Exception e) {
				controllerHasAxis[i] = false;
			}
			controllerFixed[i] = false;
		}
		currentController = 0;
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		//Fixes controller issue
		Input input = container.getInput();
		if (input.getControllerCount() > 0 && controllerHasAxis[currentController] && controllerAxisValues[currentController] == input.getAxisValue(currentController, 0) && !controllerFixed[currentController]) {
			container.getInput().clearControlPressedRecord();
		} else if (input.getControllerCount() > 0 && controllerHasAxis[currentController] && controllerAxisValues[currentController] != input.getAxisValue(currentController, 0) && !controllerFixed[currentController]) {
			controllerFixed[currentController] = true;
		}
		
		//Cursor display
		if (cursorFlag) {
			container.setMouseGrabbed(false);
		} else {
			container.setMouseGrabbed(true);
		}
		
		//Start new game
		if (newGameFlag) {
			game = new Game(container);
			gameFlag = true;
			newGameFlag = false;
		}
		
		//Check what part to update
		if (optionsFlag) {
			optionsMenu.update(container);
		} else if (gameFlag) {
			game.update(container, delta);
		} else {
			mainMenu.update(container);
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		boolean debug = false;
		System.out.println(args.length);
		if (args.length > 0) {
			debug = true;
		}

		PrintStream out = new PrintStream(new FileOutputStream("output.log"));
		if (!debug) {
			System.setOut(out);
			System.setErr(out);
		}
		
		Main main = new Main("Go Left");
		AppGameContainer container;
		try {
			container = new AppGameContainer(main);
			container.setIcon("icon.png");
			//container.setTargetFrameRate(160);
			container.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.close();
		
	}
	
	//Update display to match new resolution
	public static void updateDisplay(AppGameContainer container) throws SlickException {
		
		container.setDisplayMode(Main.options.getResolutionWidth(), Main.options.getResolutionHeight(), Main.options.getFullscreen());
		
	}
	
	@Override
	public boolean isAcceptingInput() {
		
		return true;
		
	}
	
	@Override
	public void controllerButtonPressed(int controller, int button) {
		
		currentController = controller;
		
	}
	
	@Override
	public void controllerLeftPressed(int controller) {
		
		currentController = controller;
		
	}
	
	@Override
	public void controllerRightPressed(int controller) {
		
		currentController = controller;
		
	}
	
	@Override
	public void controllerUpPressed(int controller) {
		
		currentController = controller;
		
	}
	
	@Override
	public void controllerDownPressed(int controller) {
		
		currentController = controller;
		
	}

}
