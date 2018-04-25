package game;

import game.Enumerations.TileTypes;
import game.Enumerations.TileSlopeDirection;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileBlock extends Tile {

	public TileBlock(int x, int y, Map map, SpriteSheet tileSet) throws SlickException {
		
		super(x, y, map, tileSet);
		
		type = TileTypes.BLOCK;
		
		sprite = tileSet.getSprite(2, 1);
		
	}
	
	public void render(Graphics g) {
		
		sprite.draw(tileNo.x * TILE_SIZE, tileNo.y * TILE_SIZE);
		
		g.setColor(Color.red);
		//g.draw(collisionBox);
		
	}
	
	public boolean horizontalCollision(Entity entity) {
		
		if (entity.velocity.x >= 0) {
			entity.location.x = (int)(collisionBox.getMinX() - entity.collisionBox.getWidth() - entity.trailoffset.x);
			entity.velocity.x = 0;
		} else {
			entity.location.x = (int)(collisionBox.getMaxX() - entity.offset.x);
			entity.velocity.x = 0;
		}
		
		return true;
		
	}
	
	public boolean verticalCollision(Entity entity, float yVelocity) {
		
		if (entity.velocity.y >= 0) {
			entity.location.y = (int)(collisionBox.getMinY() - entity.collisionBox.getHeight() - entity.trailoffset.y);
			entity.velocity.y = 0;
			entity.falling = false;
		} else {
			entity.location.y = (int)(collisionBox.getMaxY() - entity.offset.y);
			entity.velocity.y = 0;
		}
		
		return true;
		
	}
	
	public void checkTiles() throws SlickException {
		
		//Setting up required variables
		int bitNo = 0;
		int bitNo2 = 0;
		int slopeNo = 0;
		int invSlopeNo = 0;
		int x = (int)tileNo.x;
		int y = (int)tileNo.y;
		Tile[] tiles = {map.getTile(x, y - 1), map.getTile(x + 1, y - 1), map.getTile(x + 1, y), map.getTile(x + 1, y + 1), map.getTile(x, y + 1),
				map.getTile(x - 1, y + 1), map.getTile(x - 1, y), map.getTile(x - 1, y - 1)
		};
		
		//Check tile above
		if (tiles[0] != null && inArray(tiles[0].type, unsuit)) {
			if (inArray(tiles[0].type, sloped)) {
				TileSlope ts = (TileSlope)tiles[0];
				if (ts.direction == TileSlopeDirection.LEFT) {
					slopeNo += 1;
				} else {
					slopeNo += 2;
				}
			} else {
				bitNo += 1;
			}
		}
		
		//Check tile to the right
		if (tiles[2] != null && inArray(tiles[2].type, unsuit)) {
			if (inArray(tiles[2].type, sloped)) {
				TileSlope ts = (TileSlope)tiles[2];
				if (ts.direction == TileSlopeDirection.RIGHT) {
					bitNo += 2;
				} else {
					slopeNo += 4;
				}
			} else if (tiles[2].type == TileTypes.INVERSESLOPE) {
				TileSlope ts = (TileSlope)tiles[2];
				if (ts.direction == TileSlopeDirection.LEFT){
					bitNo += 2;
				} else {
					invSlopeNo += 4;
				}
			} else {
				bitNo += 2;
			}
		}
		
		//Check tile below
		if (tiles[4] != null && inArray(tiles[4].type, unsuit)) {
			if (tiles[4].type == TileTypes.INVERSESLOPE) {
				TileSlope ts = (TileSlope)tiles[4];
				if (ts.direction == TileSlopeDirection.RIGHT) {
					invSlopeNo += 1;
				} else {
					invSlopeNo += 2;
				}
			} else {
				bitNo += 4;
			}
		}
		
		//Check tile to the left
		if (tiles[6] != null && inArray(tiles[6].type, unsuit)) {
			if (inArray(tiles[6].type, sloped)) {
				TileSlope ts = (TileSlope)tiles[6];
				if (ts.direction == TileSlopeDirection.LEFT) {
					bitNo += 8;
				} else {
					slopeNo += 8;
				}
			} else if (tiles[6].type == TileTypes.INVERSESLOPE) {
				TileSlope ts = (TileSlope)tiles[6];
				if (ts.direction == TileSlopeDirection.RIGHT){
					bitNo += 8;
				} else {
					invSlopeNo += 8;
				}
			} else {
				bitNo += 8;
			}
		}
		
		//Check tile above and to the right
		if (tiles[1] != null && inArray(tiles[1].type, unsuit)) {
			if (inArray(tiles[1].type, sloped)) {
				TileSlope ts = (TileSlope)tiles[1];
				if (ts.direction == TileSlopeDirection.RIGHT) {
					bitNo2 += 1;
				}
			} else {
				bitNo2 += 1;
			}
		}
		
		//Check tile below and to the right
		if (tiles[3] != null && inArray(tiles[3].type, unsuit)) {
			if (tiles[3].type == TileTypes.INVERSESLOPE) {
				TileSlope ts = (TileSlope)tiles[3];
				if (ts.direction == TileSlopeDirection.LEFT){
					bitNo2 += 2;
				}
			} else {
				bitNo2 += 2;
			}
		}
		
		//Check tile below and to the left
		if (tiles[5] != null && inArray(tiles[5].type, unsuit)) {
			if (tiles[5].type == TileTypes.INVERSESLOPE) {
				TileSlope ts = (TileSlope)tiles[5];
				if (ts.direction == TileSlopeDirection.RIGHT){
					bitNo2 += 4;
				}
			} else {
				bitNo2 += 4;
			}
		}
		
		//Check tile above and to the left
		if (tiles[7] != null && inArray(tiles[7].type, unsuit)) {
			if (inArray(tiles[7].type, sloped)) {
				TileSlope ts = (TileSlope)tiles[7];
				if (ts.direction == TileSlopeDirection.LEFT) {
					bitNo2 += 8;
				}
			} else {
				bitNo2 += 8;
			}
		}
		
		//Check which sprite to use
		switch (bitNo) {
		case 0:			
			switch (bitNo2) {
			case 0:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 16);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(2, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(6, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(7, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 5);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 1:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 2);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(2, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(0, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(2, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(1, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(8, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(9, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 5);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 2:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 0);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 16);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(0, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(1, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(7, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(5, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 3:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 1);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(1, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(0, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(0, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(2, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(1, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(8, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(8, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 4:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 0);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 16);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(2, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(1, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(2, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(5, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(6, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 5:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(2, 4);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(6, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(2, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(0, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(2, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(1, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(2, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(4, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(9, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(9, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 6:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 0);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 16);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(1, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(1, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(1, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(4, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(6, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(5, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 7:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 3);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(6, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(1, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(0, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(1, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(1, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(1, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(4, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(9, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(8, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 8:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 2);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(4, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(4, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(2, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(2, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(6, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(7, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 5);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 9:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 2);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(3, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(1, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(1, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(8, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(9, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 5);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 10:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 4);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(4, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(2, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(1, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(4, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(7, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(5, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 16);
						break;
					}
					break;
				}
				break;
			case 11:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 4);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(3, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(8, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(1, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(1, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(4, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(8, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(8, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(7, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(0, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 12:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 1);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(1, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(4, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(2, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(5, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(4, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(5, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(6, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 13:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 4);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(8, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(1, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(5, 6);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(4, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(3, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(4, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(9, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(9, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(0, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 14:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(9, 3);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(1, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(2, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(0, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(4, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(4, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(4, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(6, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(5, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(6, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			case 15:
				switch (slopeNo) {
				case 0:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 1);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(8, 9);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(8, 11);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(1, 13);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(0, 7);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(4, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(4, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(5, 13);
						break;
					}
					break;
				case 2:
				case 8:
				case 10:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(3, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(9, 10);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(8, 12);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(7, 13);
						break;
					}
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					switch (invSlopeNo) {
					case 0:
						sprite = tileSet.getSprite(8, 8);
						break;
					case 1:
					case 4:
					case 5:
						sprite = tileSet.getSprite(5, 16);
						break;
					case 2:
					case 8:
					case 10:
						sprite = tileSet.getSprite(6, 16);
						break;
					case 6:
					case 9:
					case 12:
					case 13:
					case 14:
						sprite = tileSet.getSprite(3, 13);
						break;
					}
					break;
				}
				break;
			}
			break;
		case 1:
			switch (bitNo2) {
			case 0:
			case 1:
			case 8:
			case 9:
				switch (invSlopeNo) {
				case 0:
					sprite = tileSet.getSprite(2, 0);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(8, 16);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(0, 17);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(3, 17);
					break;
				}
				break;
			case 2:
			case 3:
			case 10:
			case 11:
				switch (invSlopeNo) {
				case 0:
					sprite = tileSet.getSprite(4, 3);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(8, 16);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(5, 11);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(3, 17);
					break;
				}
				break;
			case 4:
			case 5:
			case 12:
			case 13:
				switch (invSlopeNo) {
				case 0:
					sprite = tileSet.getSprite(7, 3);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(5, 9);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(0, 17);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(3, 17);
					break;
				}
				break;
			case 6:
			case 7:
			case 14:
			case 15:
				switch (invSlopeNo) {
				case 0:
					sprite = tileSet.getSprite(5, 0);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(5, 9);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(5, 11);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(3, 17);
					break;
				}
				break;
			}
			break;
		case 2:
			switch (bitNo2) {
			case 0:
			case 1:
			case 2:
			case 3:
				switch (slopeNo) {
				case 0:
				case 1:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(3, 1);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(2, 17);
						break;
					}
					break;
				case 2:
				case 8:
				case 9:
				case 10:
				case 13:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(7, 7);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(7, 12);
						break;
					}
					break;
				}
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				switch (slopeNo) {
				case 0:
				case 1:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(5, 3);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(2, 17);
						break;
					}
					break;
				case 2:
				case 8:
				case 9:
				case 10:
				case 13:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(2, 8);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(7, 12);
						break;
					}
					break;
				}
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				switch (slopeNo) {
				case 0:
				case 1:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(7, 4);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(7, 11);
						break;
					}
					break;
				case 2:
				case 8:
				case 9:
				case 10:
				case 13:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(7, 7);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(7, 12);
						break;
					}
					break;
				}
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				switch (slopeNo) {
				case 0:
				case 1:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(6, 1);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(7, 11);
						break;
					}
					break;
				case 2:
				case 8:
				case 9:
				case 10:
				case 13:
					switch (invSlopeNo) {
					case 0:
					case 1:
						sprite = tileSet.getSprite(2, 8);
						break;
					case 2:
					case 8:
					case 9:
					case 10:
					case 13:
						sprite = tileSet.getSprite(7, 12);
						break;
					}
					break;
				}
				break;
			}
			break;
		case 3:
			switch (bitNo2) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 8:
			case 9:
			case 10:
			case 11:
				switch (invSlopeNo) {
				case 0:
				case 1:
					sprite = tileSet.getSprite(3, 0);
					break;
				case 2:
				case 8:
				case 9:
				case 10:
				case 13:
					sprite = tileSet.getSprite(1, 17);
					break;
				}
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 12:
			case 13:
			case 14:
			case 15:
				switch (invSlopeNo) {
				case 0:
				case 1:
					sprite = tileSet.getSprite(6, 0);
					break;
				case 2:
				case 8:
				case 9:
				case 10:
				case 13:
					sprite = tileSet.getSprite(1, 17);
					break;
				}
				break;
			}
			break;
		case 4:
			switch (bitNo2) {
			case 0:
			case 2:
			case 4:
			case 6:
				switch (slopeNo) {
				case 0:
					sprite = tileSet.getSprite(2, 2);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(7, 6);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(9, 7);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(6, 8);
					break;
				}
				break;
			case 1:
			case 3:
			case 5:
			case 7:
				switch (slopeNo) {
				case 0:
					sprite = tileSet.getSprite(6, 4);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(7, 6);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(5, 8);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(6, 8);
					break;
				}
				break;
			case 8:
			case 10:
			case 12:
			case 14:
				switch (slopeNo) {
				case 0:
					sprite = tileSet.getSprite(5, 4);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(2, 7);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(9, 7);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(6, 8);
					break;
				}
				break;
			case 9:
			case 11:
			case 13:
			case 15:
				switch (slopeNo) {
				case 0:
					sprite = tileSet.getSprite(5, 2);
					break;
				case 1:
				case 4:
				case 5:
					sprite = tileSet.getSprite(2, 7);
					break;
				case 2:
				case 8:
				case 10:
					sprite = tileSet.getSprite(5, 8);
					break;
				case 6:
				case 9:
				case 12:
				case 13:
				case 14:
					sprite = tileSet.getSprite(6, 8);
					break;
				}
				break;
			}
			break;
		case 5:
			sprite = tileSet.getSprite(2, 3);
			break;
		case 6:
			switch (bitNo2) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				switch (slopeNo) {
				case 0:
				case 1:
					sprite = tileSet.getSprite(3, 2);
					break;
				case 2:
				case 8:
				case 9:
				case 10:
					sprite = tileSet.getSprite(0, 8);
					break;
				}
				break;
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
				switch (slopeNo) {
				case 0:
				case 1:
					sprite = tileSet.getSprite(6, 2);
					break;
				case 2:
				case 8:
				case 9:
				case 10:
					sprite = tileSet.getSprite(0, 8);
					break;
				}
				break;
			}
			break;
		case 7:
			sprite = tileSet.getSprite(3, 3);
			break;
		case 8:
			switch (bitNo2) {
			case 0:
			case 4:
			case 8:
			case 12:
				switch (slopeNo) {
				case 0:
				case 2:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(1, 1);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(9, 16);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(4, 6);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(3, 10);
						break;
					}
					break;
				}
				break;
			case 1:
			case 5:
			case 9:
			case 13:
				switch (slopeNo) {
				case 0:
				case 2:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(4, 4);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(7, 9);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(4, 6);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(3, 10);
						break;
					}
					break;
				}
				break;
			case 2:
			case 6:
			case 10:
			case 14:
				switch (slopeNo) {
				case 0:
				case 2:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(6, 3);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(9, 16);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(9, 6);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(3, 10);
						break;
					}
					break;
				}
				break;
			case 3:
			case 7:
			case 11:
			case 15:
				switch (slopeNo) {
				case 0:
				case 2:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(4, 1);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(7, 9);
						break;
					}
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					switch (invSlopeNo) {
					case 0:
					case 2:
						sprite = tileSet.getSprite(9, 6);
						break;
					case 1:
					case 4:
					case 5:
					case 6:
						sprite = tileSet.getSprite(3, 10);
						break;
					}
					break;
				}
				break;
			}
			break;
		case 9:
			switch (bitNo2) {
			case 0:
			case 1:
			case 4:
			case 5:
			case 8:
			case 9:
			case 12:
			case 13:
				switch (invSlopeNo) {
				case 0:
				case 2:
					sprite = tileSet.getSprite(1, 0);
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					sprite = tileSet.getSprite(7, 16);
					break;
				}
				break;
			case 2:
			case 3:
			case 6:
			case 7:
			case 10:
			case 11:
			case 14:
			case 15:
				switch (invSlopeNo) {
				case 0:
				case 2:
					sprite = tileSet.getSprite(4, 0);
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					sprite = tileSet.getSprite(7, 16);
					break;
				}
				break;
			}
			break;
		case 10:
			sprite = tileSet.getSprite(0, 1);
			break;
		case 11:
			sprite = tileSet.getSprite(0, 0);
			break;
		case 12:
			switch (bitNo2) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 8:
			case 10:
			case 12:
			case 14:
				switch (slopeNo) {
				case 0:
				case 2:
					sprite = tileSet.getSprite(1, 2);
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					sprite = tileSet.getSprite(6, 6);
					break;
				}
				break;
			case 1:
			case 3:
			case 5:
			case 7:
			case 9:
			case 11:
			case 13:
			case 15:
				switch (slopeNo) {
				case 0:
				case 2:
					sprite = tileSet.getSprite(4, 2);
					break;
				case 1:
				case 4:
				case 5:
				case 6:
					sprite = tileSet.getSprite(6, 6);
					break;
				}
				break;
			}
			break;
		case 13:
			sprite = tileSet.getSprite(1, 3);
			break;
		case 14:
			sprite = tileSet.getSprite(0, 2);
			break;
		case 15:
			sprite = tileSet.getSprite(5, 1);
			break;
		}
		
	}

}
