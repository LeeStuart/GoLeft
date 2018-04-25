package game;

import game.Enumerations.TileSlopeDirection;
import game.Enumerations.TileTypes;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileSlide extends TileSlope {

	public TileSlide(int x, int y, TileSlopeDirection direction, Map map, SpriteSheet tileSet) throws SlickException {
		
		super(x, y, direction, map, tileSet);
		type = TileTypes.SLIDE;
		
	}

	public void render(Graphics g) {
		
		super.render(g);
		
		if (direction == TileSlopeDirection.LEFT) { 
			g.drawImage(tileSet.getSprite(4, 17), tileNo.x * TILE_SIZE, tileNo.y * TILE_SIZE);
		} else {
			g.drawImage(tileSet.getSprite(5, 17), tileNo.x * TILE_SIZE, tileNo.y * TILE_SIZE);
		}
		
	}

}
