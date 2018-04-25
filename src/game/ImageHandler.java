package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ImageHandler {
	
	//Menu sprite sheets
	public static SpriteSheet mainMenu;
	public static SpriteSheet pauseMenu;
	public static SpriteSheet optionsMenu;
	public static SpriteSheet optionsControls;
	public static SpriteSheet optionsGraphics;
	
	//Tile sets
	public static SpriteSheet tileSet1;
	public static SpriteSheet tileSet2;
	
	//Enemy sprite sheets
	public static SpriteSheet spiderSheet;

	public ImageHandler() throws SlickException {
		
		loadImages();
		
	}
	
	public static void loadImages() throws SlickException {
		
		//Menu sprite sheets
		mainMenu = new SpriteSheet("images/main menu/main menu sheet.png", 250, 75, 2);
		pauseMenu = new SpriteSheet("images/pause menu/pause menu sheet.png", 250, 75, 2);
		optionsMenu = new SpriteSheet("images/options menu/options menu sheet.png", 150, 50, 2);
		optionsControls = new SpriteSheet("images/options menu/options menu controls sheet.png", 150, 50, 2);
		optionsGraphics = new SpriteSheet("images/options menu/options menu graphics sheet.png", 150, 50, 2);
		
		//Tile sets
		tileSet1 = new SpriteSheet("images/tilesets/default.png", 64, 64, 2);
		tileSet1.setFilter(Image.FILTER_NEAREST);
		tileSet2 = new SpriteSheet("images/tilesets/default2.png", 64, 64, 2);
		tileSet2.setFilter(Image.FILTER_NEAREST);
		
		//Enemy sprite sheets
		spiderSheet = new SpriteSheet("images/enemies/spiderSheet.png", 50, 30, 0);
		
	}

}
