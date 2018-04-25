package game;

import game.Enumerations.TileTypes;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public class TileSpike extends Tile {

	public TileSpike(int x, int y, Map map, SpriteSheet tileSet, int direction) {
		
		super(x, y, map, tileSet);
		
		type = TileTypes.SPIKE;
		
		switch (direction) {
		case 0:
			sprite = tileSet.getSprite(6, 17);
			break;
		case 1:
			sprite = tileSet.getSprite(7, 17);
			break;
		case 2:
			sprite = tileSet.getSprite(8, 17);
			break;
		case 3:
			sprite = tileSet.getSprite(9, 17);
			break;
		}
		
	}
	
	public void render(Graphics g) {
		
		sprite.draw(tileNo.x * TILE_SIZE, tileNo.y * TILE_SIZE);
		
	}
	
}
