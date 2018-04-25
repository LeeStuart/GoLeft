package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileHiddenOneWay extends TileHiddenBlock {

	public TileHiddenOneWay(int x, int y, Map map, SpriteSheet tileSet)	throws SlickException {
		
		super(x, y, map, tileSet);
		
	}
	
	public boolean verticalCollision(Entity entity, float yVelocity) {
		
		if (entity.velocity.y >= 0) {
			if (entity.quickDown) {
				entity.location.y += 1;
				if (!found) checkHiddenArea();
				return false;
			} else {
				if ((int)entity.collisionBox.getMaxY() <= (int)collisionBox.getMinY()) {
					entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
					entity.velocity.y = 0;
					entity.falling = false;
				} else {
					if (!found) checkHiddenArea();
					return false;
				}
			}
		} else {
			if (!found) checkHiddenArea();
			return false;
		}
		
		return true;
		
	}

}
