package game;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

public class HUD {
	
	private int screenWidth;
	private int screenHeight;
	
	private TrueTypeFont ttf;

	public HUD() {
		
		Font f = new Font("Sans", Font.PLAIN, 15);
		ttf = new TrueTypeFont(f, false);

	}
	
	public void render(Graphics g) {
		
		ttf.drawString(screenWidth - 10 - ttf.getWidth(Main.VERSION_NUMBER), screenHeight - 25, Main.VERSION_NUMBER);
		
	}
	
	public void update(GameContainer container) {
		
		screenWidth = container.getWidth();
		screenHeight = container.getHeight();
		
	}

}
