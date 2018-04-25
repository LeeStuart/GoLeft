package game;

import game.Enumerations.EntityTypes;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Entity {
	
	//Constants
	protected static final float GRAVITY = 0.005f;
	
	//Others
	protected Image sprite;
	protected Map map;
	protected String debug;
	protected EntityTypes type;
	
	//Information about movement and collision
	protected Rectangle collisionBox;
	protected Point2D.Float location;
	protected Point2D.Float velocity;
	protected Point2D.Float acceleration;
	protected Point2D.Float offset;
	protected Point2D.Float trailoffset;
	
	//Information about what entity is doing
	protected int onSlopeNo;
	protected boolean quickDown;
	protected boolean falling;
	protected int fallingCooldown;
	protected boolean jumping;
	protected int jumpingCooldown;

	public Entity(float x, float y, Map map) throws SlickException {
		
		this();
		
		this.map = map;
		debug = "";
		
		location = new Point2D.Float(x, y);
		collisionBox.setLocation(x, y);
		
		quickDown = false;
		falling = false;
		jumping = false;
		
	}
	
	public Entity() throws SlickException {
		
		this.sprite = new Image("images/entities/entity.png");
		
		this.location = new Point2D.Float(0, 0);
		this.velocity = new Point2D.Float(0, 0);
		this.acceleration = new Point2D.Float(0, 0);
		this.offset = new Point2D.Float(0, 0);
		this.trailoffset = new Point2D.Float(0, 0);
		collisionBox = new Rectangle(offset.x, offset.y, sprite.getWidth() - trailoffset.x - offset.x, sprite.getHeight() - trailoffset.y - offset.y);
		
		onSlopeNo = -1;
		
	}
	
	public void render(Graphics g) {
		
		sprite.draw(location.x, location.y);
		
		//g.draw(collisionBox);
		
		g.drawString(debug, location.x + 50, location.y + 50);
		
	}
	
	public void update(int delta) throws SlickException {

		debug = "";
		
		//Get velocities
		velocity.x = acceleration.x * delta;
		velocity.y = acceleration.y * delta;
		
		//Check collision detection
		collisionDetection(delta);
		if (velocity.y == 0) {
			acceleration.y = 0;
			jumping = false;
		}
//		if (velocity.x == 0) {
//			acceleration.x = 0;
//		}
		
		//Check if falling
		if (velocity.y != 0 && fallingCooldown <= 0) {
			falling = true;
		} else if (velocity.y != 0) {
			fallingCooldown -= delta;
		} else {
			fallingCooldown = 100;
		}
		
		//Set new location
		location.x += velocity.x;
		location.y += velocity.y;
		acceleration.y += GRAVITY * delta;
		collisionBox.setLocation(location.x + offset.x, location.y + offset.y);
		
	}
	
	private void collisionDetection(int delta) {
		
		edgeCollision(delta);
		
		//Horizontal movement
		Rectangle horizontalCollisionBox = collisionBox;
		
		if (velocity.x >= 0) {
			horizontalCollisionBox = new Rectangle(collisionBox.getX() + 1, collisionBox.getY() + 1, collisionBox.getWidth() + velocity.x - 1, collisionBox.getHeight() - 2);
		} else {
			horizontalCollisionBox = new Rectangle(collisionBox.getX() + velocity.x, collisionBox.getY() + 1, collisionBox.getWidth() - velocity.x - 1, collisionBox.getHeight() - 2);
		}
		
		ArrayList<ArrayList<Tile>> horizontalTiles = map.getTiles(horizontalCollisionBox);
		
		boolean horizontalCollided = false;
		for (ArrayList<Tile> tileRow : horizontalTiles) {
			for (Tile t : tileRow) {
				if (!horizontalCollided) {
					horizontalCollided = t.horizontalCollision(this);
				}
			}
		}
		
		//Vertical movement
		Rectangle verticalCollisionBox = collisionBox;
		float yVelocity = velocity.y;
		
		if (velocity.y >= 0) {
			verticalCollisionBox = new Rectangle(collisionBox.getX() + 1, collisionBox.getY() + 1, collisionBox.getWidth() - 2, collisionBox.getHeight() + yVelocity - 1);
		} else {
			verticalCollisionBox = new Rectangle(collisionBox.getX() + 1, collisionBox.getY() + yVelocity, collisionBox.getWidth() - 2, collisionBox.getHeight() - yVelocity - 1);
		}
		
		ArrayList<ArrayList<Tile>> verticalTiles = map.getTiles(verticalCollisionBox);
		
		boolean verticalCollided = false;
		for (ArrayList<Tile> tileRow : verticalTiles) {
			for (Tile t : tileRow) {
				if (!verticalCollided) {
					verticalCollided = t.verticalCollision(this, yVelocity);
				}
			}
		}
		
	}
	
	private void edgeCollision(int delta) {
		
		if (collisionBox.getMinX() + velocity.x < 0) {
			location.x = 0 - offset.x;
			velocity.x = 0;
		}
		
		if (collisionBox.getMaxX() + velocity.x > map.getWidth()) {
			location.x = map.getWidth() - collisionBox.getWidth() - trailoffset.x;
			velocity.x = 0;
		}
		
		if (collisionBox.getMinY() + (velocity.y) < 0) {
			location.y = 0 - offset.y;
			velocity.y = 0;
		}
		
		if (collisionBox.getMaxY() + (velocity.y) > map.getHeight()) {
			location.y = map.getHeight() - collisionBox.getHeight() - trailoffset.x;
			velocity.y = 0;
			falling = false;
		}
		
	}

}
