package game;

import game.Enumerations.TileTypes;

import java.awt.geom.Point2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Tile {
	
	//Tile size
	public final static int TILE_SIZE = 64;
	
	//Tile variables
	public TileTypes type;
	protected Image sprite;
	
	//Information about the tile
	protected Point2D.Float tileNo;
	protected Rectangle collisionBox;
	protected Map map;
	protected SpriteSheet tileSet;
	
	//Variables for checkTiles()
	protected TileTypes[] unsuit = {TileTypes.EMPTY, TileTypes.ONEWAY, TileTypes.SLOPE, TileTypes.INVERSESLOPE, TileTypes.SLIDE, TileTypes.SPIKE};
	protected TileTypes[] sloped = {TileTypes.SLOPE, TileTypes.SLIDE};

	public Tile(int x, int y, Map map, SpriteSheet tileSet) {
		
		type = TileTypes.EMPTY;
		
		tileNo = new Point2D.Float(x, y);
		collisionBox = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		this.map = map;
		this.tileSet = tileSet;
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(Color.white);
		//g.draw(collisionBox);
		//g.drawString((int)tileNo.x + " " + (int)tileNo.y, tileNo.x * TILE_SIZE + 1, tileNo.y * TILE_SIZE + 1);
		
	}
	
	public void checkTiles() throws SlickException {
		
	}
	
	public Rectangle getCollisionBox() {
		
		return collisionBox;
		
	}
	
	public int getY() {
		
		return (int)tileNo.y;
		
	}
	
	public int getX() {
		
		return (int)tileNo.x;
		
	}
	
	protected boolean inArray(TileTypes value, TileTypes[] array) {
		
		for (TileTypes s : array) {
			if (value.equals(s)) {
				return true;
			}
		}
		return false;
	}

	public boolean horizontalCollision(Entity entity) {
		
		return false;
		
	}

	public boolean verticalCollision(Entity entity, float yVelocity) {
		
		return false;
		
	}
	
}
