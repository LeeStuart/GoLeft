package game;

import game.Enumerations.TileSlopeDirection;
import game.Enumerations.TileTypes;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileSlope extends Tile {
	
	//Tile variables
	public TileSlopeDirection direction;

	public TileSlope(int x, int y, TileSlopeDirection direction, Map map, SpriteSheet tileSet) throws SlickException {
		
		super(x, y, map, tileSet);
		
		type = TileTypes.SLOPE;
		
		this.direction = direction;
		
	}
	
	public void render(Graphics g) {
		
		sprite.draw(tileNo.x * TILE_SIZE, tileNo.y * TILE_SIZE);
		
		if (direction == TileSlopeDirection.LEFT) {
			g.setColor(Color.orange);
		} else {
			g.setColor(Color.cyan);
		}
		//g.draw(collisionBox);

	}
	
	public boolean horizontalCollision(Entity entity) {
		
		if (entity.velocity.x >= 0) {
			if (direction == TileSlopeDirection.RIGHT) {
				if ((int)entity.collisionBox.getMaxY() > (int)collisionBox.getMaxY()) {
					entity.location.x = (int)(collisionBox.getMinX() - entity.collisionBox.getWidth() - entity.trailoffset.x);
					entity.velocity.x = 0;
				} else {
					return false;
				}
			} else {
				if ((int)entity.collisionBox.getMaxX() <= (int)collisionBox.getMinX()) {
					entity.location.x = (int)(collisionBox.getMinX() - entity.collisionBox.getWidth() - entity.trailoffset.x);
					entity.velocity.x = 0;
				} else {
					return false;
				}
			}
		} else {
			if (direction == TileSlopeDirection.LEFT) {
				if ((int)entity.collisionBox.getMaxY() > (int)collisionBox.getMaxY()) {
					entity.location.x = (int)(collisionBox.getMaxX() - entity.offset.x);
					entity.velocity.x = 0;
				} else {
					return false;
				}
			} else {
				if ((int)entity.collisionBox.getMinX() >= (int)collisionBox.getMaxX()) {
					entity.location.x = (int)(collisionBox.getMaxX() - entity.offset.x);
					entity.velocity.x = 0;
				} else {
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	public boolean verticalCollision(Entity entity, float yVelocity) {
		
		if (entity.velocity.y >= 0) {
			if (direction == TileSlopeDirection.RIGHT) {
				if ((int)(entity.collisionBox.getMaxX() + entity.velocity.x) > (int)collisionBox.getMaxX()) {
					entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
					entity.velocity.y = 0;
					entity.falling = false;
				} else if (entity.falling == false) {
					entity.location.y = (int)(collisionBox.getMaxY() - ((entity.collisionBox.getMaxX() + entity.velocity.x) - collisionBox.getMinX()) - entity.collisionBox.getHeight() - entity.trailoffset.y);
					if ((int)(entity.location.y + entity.collisionBox.getHeight() + entity.trailoffset.y) >= (int)collisionBox.getMaxY()) {
						entity.location.y = (int)(collisionBox.getMaxY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
//					} else if ((int)(entity.location.y + entity.collisionBox.getHeight() + entity.trailoffset.y) <= (int)(collisionBox.getMinY())) {
//						entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
					}
					entity.velocity.y = 0;
				} else if ((int)(entity.collisionBox.getMaxY() + yVelocity) > (int)(collisionBox.getMaxY() - (entity.collisionBox.getMaxX() - collisionBox.getMinX()))) {
					entity.location.y = (int)(collisionBox.getMaxY() - (entity.collisionBox.getMaxX() - collisionBox.getMinX()) - entity.collisionBox.getHeight() - entity.trailoffset.y);
					entity.velocity.y = 0;
					entity.falling = false;
				} else {
					return false;
				}
			} else {
				if ((int)(entity.collisionBox.getMinX() + entity.velocity.x) < (int)collisionBox.getMinX()) {
					entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
					entity.velocity.y = 0;
					entity.falling = false;
				} else if (entity.falling == false) {
					entity.location.y = (int)(collisionBox.getMaxY() - (collisionBox.getMaxX() - (entity.collisionBox.getMinX() + entity.velocity.x)) - entity.collisionBox.getHeight() - entity.trailoffset.y);
					if ((int)(entity.location.y + entity.collisionBox.getHeight() + entity.trailoffset.y) >= (int)collisionBox.getMaxY()) {
						entity.location.y = (int)(collisionBox.getMaxY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
//					} else if ((int)(entity.location.y + entity.collisionBox.getHeight() + entity.trailoffset.y) <= (int)(collisionBox.getMinY())) {
//						entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
					}
					entity.velocity.y = 0;
				} else if ((int)(entity.collisionBox.getMaxY() + yVelocity) > (int)(collisionBox.getMaxY() - (collisionBox.getMaxX() - entity.collisionBox.getMinX()))) {
					entity.location.y = (int)(collisionBox.getMaxY() - (collisionBox.getMaxX() - entity.collisionBox.getMinX()) - entity.collisionBox.getHeight() - entity.trailoffset.y);
					entity.velocity.y = 0;
					entity.falling = false;
				} else {
					return false;
				}
			}
		} else {
			if ((int)entity.collisionBox.getMinY() >= (int)collisionBox.getMaxY()) {
				entity.location.y = (int)(collisionBox.getMaxY() - entity.offset.y);
				entity.velocity.y = 0;
			}
		}
		
		return true;
		
	}
	
	public void checkTiles() throws SlickException {
		
		//Setting up variables
		int bitNo = 0;
		boolean invSlopeFlag = false;
		int x = (int)tileNo.x;
		int y = (int)tileNo.y;
		Tile[] tiles = {map.getTile(x + 1, y), map.getTile(x + 1, y + 1), map.getTile(x, y + 1),
				map.getTile(x - 1, y + 1), map.getTile(x - 1, y)
		};
		
		if (direction == TileSlopeDirection.LEFT) {
			
			//Check tile below
			if (tiles[2] != null && inArray(tiles[2].type, unsuit)) {
				if (tiles[2].type != TileTypes.INVERSESLOPE) {
					bitNo += 1;
				} else {
					TileSlope ts = (TileSlope)tiles[2];
					if (ts.direction == TileSlopeDirection.LEFT) {
						invSlopeFlag = true;
					}
				}
			}
			
			//Check tile to the left
			if (tiles[4] != null && inArray(tiles[4].type, unsuit)) {
				if (inArray(tiles[4].type, sloped)) {
					TileSlope ts = (TileSlope)tiles[4];
					if (ts.direction == TileSlopeDirection.LEFT) {
						bitNo += 2;
					}
				} else if (tiles[4].type == TileTypes.INVERSESLOPE) {
					TileSlope ts = (TileSlope)tiles[4];
					if (ts.direction == TileSlopeDirection.RIGHT){ 
						bitNo += 2;
					} else {
						invSlopeFlag = true;
					}
				} else {
					bitNo += 2;
				}
			}
			
			//Check tile below and to the left
			if (tiles[3] != null && inArray(tiles[3].type, unsuit)) {
				if (tiles[3].type == TileTypes.INVERSESLOPE){
					TileSlope ts = (TileSlope)tiles[3];
					if (ts.direction == TileSlopeDirection.RIGHT){
						bitNo += 4;
					}
				} else {
					bitNo += 4;
				}
			}
			
			//Check which sprite to use
			switch (bitNo) {
			case 0:
				if (invSlopeFlag) {
					sprite = tileSet.getSprite(9, 11);
				} else {
					sprite = tileSet.getSprite(0, 5);
				}
				break;
			case 1:
			case 5:
				sprite = tileSet.getSprite(1, 5);
				break;
			case 2:
			case 6:
				sprite = tileSet.getSprite(2, 5);
				break;
			case 3:
			case 7:
				sprite = tileSet.getSprite(3, 5);
				break;
			case 4:
				if (invSlopeFlag) {
					sprite = tileSet.getSprite(9, 11);
				} else {
					sprite = tileSet.getSprite(0, 4);
				}
				break;
			}
			
		} else {
			
			//Check tile below
			if (tiles[2] != null && inArray(tiles[2].type, unsuit)) {
				if (tiles[2].type != TileTypes.INVERSESLOPE) {
					bitNo += 1;
				} else {
					TileSlope ts = (TileSlope)tiles[2];
					if (ts.direction == TileSlopeDirection.RIGHT) {
						invSlopeFlag = true;
					}
				}
			}
			
			//Check tile to the right
			if (tiles[0] != null && inArray(tiles[0].type, unsuit)) {
				if (inArray(tiles[0].type, sloped)) {
					TileSlope ts = (TileSlope)tiles[0];
					if (ts.direction == TileSlopeDirection.RIGHT) {
						bitNo += 2;
					}
				} else if (tiles[0].type == TileTypes.INVERSESLOPE) {
					TileSlope ts = (TileSlope)tiles[0];
					if (ts.direction == TileSlopeDirection.LEFT){ 
						bitNo += 2;
					} else {
						invSlopeFlag = true;
					}
				} else {
					bitNo += 2;
				}
			}
			
			//Check tile below and to the right
			if (tiles[1] != null && inArray(tiles[1].type, unsuit)) {
				if (tiles[1].type == TileTypes.INVERSESLOPE) {
					TileSlope ts = (TileSlope)tiles[1];
					if (ts.direction == TileSlopeDirection.LEFT) { 
						bitNo += 4;
					}
				} else {
					bitNo += 4;
				}
			}
			
			//Check which sprite to use
			switch (bitNo) {
			case 0:
				if (invSlopeFlag) {
					sprite = tileSet.getSprite(9, 9);
				} else {
					sprite = tileSet.getSprite(4, 5);
				}
				break;
			case 1:
			case 5:
				sprite = tileSet.getSprite(5, 5);
				break;
			case 2:
			case 6:
				sprite = tileSet.getSprite(6, 5);
				break;
			case 3:
			case 7:
				sprite = tileSet.getSprite(7, 5);
				break;
			case 4:
				if (invSlopeFlag) {
					sprite = tileSet.getSprite(9, 9);
				} else {
					sprite = tileSet.getSprite(1, 4);
				}
				break;
			}
			
		}
		
	}

}
