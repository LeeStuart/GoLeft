package game;

import game.Enumerations.TileTypes;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileHiddenBlock extends TileBlock {
	
	protected boolean found;

	public TileHiddenBlock(int x, int y, Map map, SpriteSheet tileSet) throws SlickException {
		
		super(x, y, map, tileSet);
		
		found = false;
		type = TileTypes.HIDDENBLOCK;
		
	}
	
	public boolean horizontalCollision(Entity entity) {
		
		if (!found) {
			if (entity.velocity.x >= 0) {
				if (entity.collisionBox.getMaxX() > collisionBox.getMinX() + 1) {
					checkHiddenArea();
				}
			} else {
				if (entity.collisionBox.getMinX() < collisionBox.getMaxX()) {
					checkHiddenArea();
				}
			}
		}
		
		return false;
		
	}
	
	public boolean verticalCollision(Entity entity, float yVelocity) {
		
		if (!found) checkHiddenArea();
		
		return false;
		
	}
	
	public void checkHiddenArea() {
		
		if (found) return;
		
		found = true;
		sprite.setImageColor(1, 1, 1, 0.5f);
		
		int x = (int)tileNo.x;
		int y = (int)tileNo.y;
		Tile[] surroundingTiles = {map.getTile(x, y - 1), map.getTile(x + 1, y), map.getTile(x, y + 1), map.getTile(x - 1, y)};
		
		for (Tile t : surroundingTiles) {
			if (t != null && t.type == TileTypes.HIDDENBLOCK) {
				TileHiddenBlock thb = (TileHiddenBlock)t;
				thb.checkHiddenArea();
			}
		}
	}

}
