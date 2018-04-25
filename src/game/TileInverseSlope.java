package game;

import game.Enumerations.TileSlopeDirection;
import game.Enumerations.TileTypes;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileInverseSlope extends TileSlope {

	public TileInverseSlope(int x, int y, TileSlopeDirection direction,	Map map, SpriteSheet tileSet) throws SlickException {
		
		super(x, y, direction, map, tileSet);
		type = TileTypes.INVERSESLOPE;

	}
	
	public boolean horizontalCollision(Entity entity) {
		
		if (entity.velocity.x >= 0) {
			if (direction == TileSlopeDirection.LEFT) {
				if ((int)entity.collisionBox.getMinY() < (int)collisionBox.getMinY()) {
					entity.location.x = (int)(collisionBox.getMinX() - entity.collisionBox.getWidth() - entity.trailoffset.x);
					entity.velocity.x = 0;
				} else if ((int)(entity.collisionBox.getMaxX() + entity.velocity.x) >= (int)(collisionBox.getMinX() + (entity.collisionBox.getMinY() - collisionBox.getMinY()))) {
					entity.location.x = (int)(collisionBox.getMinX() - entity.collisionBox.getWidth() - entity.trailoffset.x + (entity.collisionBox.getMinY() - collisionBox.getMinY()));
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
			if (direction == TileSlopeDirection.RIGHT) {
				if ((int)entity.collisionBox.getMinY() < (int)collisionBox.getMinY()) {
					entity.location.x = (int)(collisionBox.getMaxX() - entity.offset.x);
					entity.velocity.x = 0;
				} else if ((int)(entity.collisionBox.getMinX() + entity.velocity.x) < (int)(collisionBox.getMaxX() - (entity.collisionBox.getMinY() - collisionBox.getMinY()))) {
					entity.location.x = (int)(collisionBox.getMaxX() - entity.offset.x - (entity.collisionBox.getMinY() - collisionBox.getMinY()));
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
		
		if (yVelocity >= 0) {
			if ((int)entity.collisionBox.getMaxY() <= (int)collisionBox.getMinY()) {
				entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
				entity.velocity.y = 0;
				entity.falling = false;
			} else {
				return false;
			}
		} else {
			if (direction == TileSlopeDirection.LEFT) {
				if ((int)entity.collisionBox.getMaxX() > (int)collisionBox.getMaxX()) {
					entity.location.y = (int)(collisionBox.getMaxY() - entity.offset.y);
					entity.velocity.y = 0;
				} else if ((int)(entity.collisionBox.getMinY() + yVelocity) < (int)(collisionBox.getMaxY() - (collisionBox.getMaxX() - entity.collisionBox.getMaxX()))) {
					entity.location.y = (int)(collisionBox.getMaxY() - entity.offset.y - (collisionBox.getMaxX() - entity.collisionBox.getMaxX()));
					entity.velocity.y = 0;
				} else {
					return false;
				}
			} else {
				if ((int)entity.collisionBox.getMinX() < (int)collisionBox.getMinX()) {
					entity.location.y = (int)(collisionBox.getMaxY() - entity.offset.y);
					entity.velocity.y = 0;
				} else if ((int)(entity.collisionBox.getMinY() + yVelocity) < (int)(collisionBox.getMaxY() - (entity.collisionBox.getMinX() - collisionBox.getMinX()))) {
					entity.location.y = (int)(collisionBox.getMaxY() - entity.offset.y - (entity.collisionBox.getMinX() - collisionBox.getMinX()));
					entity.velocity.y = 0;
				} else {
					return false;
				}
			}
		}
		
		return true;
		
	}
	
public void checkTiles() throws SlickException {
		
		//Setting up variables
		int bitNo = 0;
		boolean slopeFlag = false;
		boolean oneWayFlag = false;
		int x = (int)tileNo.x;
		int y = (int)tileNo.y;
		Tile[] tiles = {map.getTile(x + 1, y), map.getTile(x + 1, y - 1), map.getTile(x, y - 1),
				map.getTile(x - 1, y - 1), map.getTile(x - 1, y)
		};
		
		if (direction == TileSlopeDirection.LEFT) {
			
			//Check tile above
			if (tiles[2] != null && inArray(tiles[2].type, unsuit)) {
				if (!inArray(tiles[2].type, sloped)) {
					bitNo += 1;
				} else {
					TileSlope ts = (TileSlope)tiles[2];
					if (ts.direction == TileSlopeDirection.LEFT) {
						slopeFlag = true;
					}
				}
			}
			
			//Check tile to the right
			if (tiles[0] != null && inArray(tiles[0].type, unsuit)) {
				if (tiles[0].type == TileTypes.INVERSESLOPE) {
					TileSlope ts = (TileSlope)tiles[0];
					if (ts.direction == TileSlopeDirection.LEFT) {
						bitNo += 2;
					}
				} else if (inArray(tiles[0].type, sloped)) {
					TileSlope ts = (TileSlope)tiles[0];
					if (ts.direction == TileSlopeDirection.RIGHT){ 
						bitNo += 2;
					} else {
						slopeFlag = true;
					}
				} else {
					bitNo += 2;
				}
			}
			
			//Check above below and to the right
			if (tiles[1] != null && inArray(tiles[1].type, unsuit)) {
				if (inArray(tiles[1].type, sloped)){
					TileSlope ts = (TileSlope)tiles[1];
					if (ts.direction == TileSlopeDirection.RIGHT){
						bitNo += 4;
					}
				} else {
					bitNo += 4;
				}
			}
			
			//Check to see if one way flag needed
			if (tiles[4].type == TileTypes.ONEWAY) {
				oneWayFlag = true;
			}
			
			//Check which sprite to use
			switch (bitNo) {
			case 0:
				if (slopeFlag) {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(7, 15);
					} else {
						sprite = tileSet.getSprite(5, 14);
					}
				} else {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(6, 15);
					} else {
						sprite = tileSet.getSprite(4, 14);
					}
				}
				break;
			case 1:
			case 5:
				if (oneWayFlag) {
					sprite = tileSet.getSprite(9, 15);
				} else {
					sprite = tileSet.getSprite(7, 14);
				}
				break;
			case 2:
			case 6:
				if (oneWayFlag) {
					sprite = tileSet.getSprite(0, 16);
				} else {
					sprite = tileSet.getSprite(8, 14);
				}
				break;
			case 3:
			case 7:
				if (oneWayFlag) {
					sprite = tileSet.getSprite(1, 15);
				} else {
					sprite = tileSet.getSprite(9, 14);
				}
				break;
			case 4:
				if (slopeFlag) {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(7, 15);
					} else {
						sprite = tileSet.getSprite(5, 14);
					}
				} else {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(8, 15);
					} else {
						sprite = tileSet.getSprite(6, 14);
					}
				}
				break;
			}
			
		} else {
			
			//Check tile above
			if (tiles[2] != null && inArray(tiles[2].type, unsuit)) {
				if (!inArray(tiles[2].type, sloped)) {
					bitNo += 1;
				} else {
					TileSlope ts = (TileSlope)tiles[2];
					if (ts.direction == TileSlopeDirection.RIGHT) {
						slopeFlag = true;
					}
				}
			}
			
			//Check tile to the left
			if (tiles[4] != null && inArray(tiles[4].type, unsuit)) {
				if (tiles[4].type == TileTypes.INVERSESLOPE) {
					TileSlope ts = (TileSlope)tiles[4];
					if (ts.direction == TileSlopeDirection.RIGHT) {
						bitNo += 2;
					}
				} else if (inArray(tiles[4].type, sloped)) {
					TileSlope ts = (TileSlope)tiles[4];
					if (ts.direction == TileSlopeDirection.LEFT){ 
						bitNo += 2;
					} else {
						slopeFlag = true;
					}
				} else {
					bitNo += 2;
				}
			}
			
			//Check tile below and to the right
			if (tiles[3] != null && inArray(tiles[3].type, unsuit)) {
				if (inArray(tiles[3].type, sloped)) {
					TileSlope ts = (TileSlope)tiles[3];
					if (ts.direction == TileSlopeDirection.LEFT) { 
						bitNo += 4;
					}
				} else {
					bitNo += 4;
				}
			}
			
			//Check to see if one way flag needed
			if (tiles[0].type == TileTypes.ONEWAY) {
				oneWayFlag = true;
			}
			
			//Check which sprite to use
			switch (bitNo) {
			case 0:
				if (slopeFlag) {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(1, 15);
					} else {
						sprite = tileSet.getSprite(9, 13);
					}
				} else {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(0, 15);
					} else {
						sprite = tileSet.getSprite(8, 13);
					}
				}
				break;
			case 1:
			case 5:
				if (oneWayFlag) {
					sprite = tileSet.getSprite(3, 15);
				} else {
					sprite = tileSet.getSprite(1, 14);
				}
				break;
			case 2:
			case 6:
				if (oneWayFlag) {
					sprite = tileSet.getSprite(4, 15);
				} else {
					sprite = tileSet.getSprite(2, 14);
				}
				break;
			case 3:
			case 7:
				if (oneWayFlag) {
					sprite = tileSet.getSprite(5, 15);
				} else {
					sprite = tileSet.getSprite(3, 14);
				}
				break;
			case 4:
				if (slopeFlag) {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(1, 15);
					} else {
						sprite = tileSet.getSprite(9, 13);
					}
				} else {
					if (oneWayFlag) {
						sprite = tileSet.getSprite(2, 15);
					} else {
						sprite = tileSet.getSprite(0, 14);
					}
				}
				break;
			}
			
		}
		
	}

}
