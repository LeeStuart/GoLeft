package game;

import game.Enumerations.TileTypes;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileOneWay extends Tile {

	public TileOneWay(int x, int y, Map map, SpriteSheet tileSet) throws SlickException {
		
		super(x, y, map, tileSet);
		
		type = TileTypes.ONEWAY;
		
		sprite = tileSet.getSprite(8, 5);
		
	}
	
	public void render(Graphics g) {
		
		sprite.draw(tileNo.x * TILE_SIZE, tileNo.y * TILE_SIZE);
		
		g.setColor(Color.magenta);
		//g.draw(collisionBox);
		
	}

	public boolean verticalCollision(Entity entity, float yVelocity) {
		
		if (entity.velocity.y >= 0) {
			if (entity.quickDown) {
				entity.location.y += 1;
				return false;
			} else {
				if ((int)entity.collisionBox.getMaxY() <= (int)collisionBox.getMinY()) {
					entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
					entity.velocity.y = 0;
					entity.falling = false;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
		
	}

}
