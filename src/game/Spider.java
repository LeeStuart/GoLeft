package game;

import game.Enumerations.EntityTypes;
import game.Enumerations.TileTypes;

import java.awt.geom.Point2D;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Spider extends Entity {
	
	private float animationCooldown;
	private int selectedImage;
	
	private boolean turretMode;
	int nearestWallDirection;

	public Spider(float x, float y, Map map) throws SlickException {
		
		super(x, y, map);
		type = EntityTypes.SPIDER;
		
		this.sprite = new Image("images/enemies/spider.png");
		this.offset = new Point2D.Float(10, 1);
		this.trailoffset = new Point2D.Float(10, 1);
		collisionBox = new Rectangle(offset.x, offset.y, sprite.getWidth() - trailoffset.x - offset.x, sprite.getHeight() - trailoffset.y - offset.y);
		collisionBox.setLocation(x + offset.x, y + offset.y);		
		
		selectedImage = 0;
		
		turretMode = true;
		nearestWallDirection = findNearestWall();
		
	}
	
	public void update(int delta) throws SlickException {
		
		if (turretMode) {
			sprite = ImageHandler.spiderSheet.getSprite(0, selectedImage);
			switch (nearestWallDirection) {
			case 0:
				acceleration.y = -0.5f;
				sprite = sprite.getFlippedCopy(false, true);
				break;
			case 1:
				acceleration.x = 0.5f;
				acceleration.y = 0;
				sprite.rotate(-90);
				break;
			case 2:
				acceleration.x = -0.5f;
				acceleration.y = 0;
				sprite.rotate(90);
				break;
			case 3:
				breakTurretMode();
				break;
			}
			
			float playerX = map.getPlayer().collisionBox.getCenterX();
			float playerY = map.getPlayer().collisionBox.getCenterY();
			float distanceX = playerX - collisionBox.getCenterX();
			float distanceY = playerY - collisionBox.getCenterY();
			float angle = (float)Math.toDegrees(Math.atan2(distanceY, distanceX));
			System.out.println(angle);
			
			if (collisionBox.intersects(map.getPlayer().collisionBox)) {
				breakTurretMode();
			}
		} else {
			if (!falling && (collisionBox.getMinX() > map.getPlayer().collisionBox.getMaxX() || collisionBox.getMaxX() < map.getPlayer().collisionBox.getMinX())) {
				if (map.getPlayer().location.x < location.x) { 
					acceleration.x = -0.3f;
				} else {
					acceleration.x = 0.3f;
				}
			} else {
				acceleration.x = 0;
			}
			
			if (!falling) {
				if (velocity.x != 0) {
					if (animationCooldown < 0) {
						if (selectedImage == 0) {
							selectedImage = 1;
						} else {
							selectedImage = 0;
						}
						animationCooldown = 100;
					} else {
						animationCooldown -= 0.8 * delta;
					}
				} else {
					selectedImage = 0;
				}
			} else {
				selectedImage = 1;
			}
			sprite = ImageHandler.spiderSheet.getSprite(0, selectedImage);
			
		}

		super.update(delta);
		
	}
	
	public void breakTurretMode() {
		
		turretMode = false;
		
	}
	
	private int findNearestWall() {
		
		boolean found = false;
		int direction = 3;
		int i = 0;
		
		int tileX = (int)collisionBox.getCenterX() / Tile.TILE_SIZE;
		int tileY = (int)collisionBox.getCenterY() / Tile.TILE_SIZE;
		
		while (!found) {
			i++;
			Tile t = map.getTile(tileX, tileY - i);
			if (t.type != TileTypes.EMPTY && t.type != TileTypes.ONEWAY) {
				direction = 0;
				found = true;
				break;
			}
			
			t = map.getTile(tileX + i, tileY);
			if (t.type != TileTypes.EMPTY && t.type != TileTypes.ONEWAY) {
				direction = 1;
				found = true;
				break;
			}
			
			t = map.getTile(tileX - i, tileY);
			if (t.type != TileTypes.EMPTY && t.type != TileTypes.ONEWAY) {
				direction = 2;
				found = true;
				break;
			}
			
			t = map.getTile(tileX, tileY + i);
			if (t.type != TileTypes.EMPTY) {
				direction = 3;
				found = true;
				break;
			}
		}
		
		return direction;
		
	}

}
