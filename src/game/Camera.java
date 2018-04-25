package game;

import java.awt.geom.Point2D;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Camera {
	
	//Camera variables
	private GameContainer container;
	private Map map;
	private Point2D.Float location;
	
	//Container information
	private float scale;
	private Rectangle screen;
	
	//Variables to update location
	private int defaultHeight = 720;
	private int defaultWidth;
	private Player player;
	private Point2D.Float center;
	private Point2D.Float size;
	private float offsetMaxX;
	private float offsetMaxY;
	private float offsetMinX;
	private float offsetMinY;

	public Camera(GameContainer container, Map map) {
		
		this.container = container;
		this.map = map;
		location = new Point2D.Float(0, 0);
		updateVariables();
		
	}
	
	public void render(Graphics g) {

		g.scale(scale, scale);
		g.translate((-location.x), (-location.y));
		map.render(g, screen);
		g.resetTransform();
		
	}
	
	public void update() {
		
		//Get the location of the camera
		location.x = (player.collisionBox.getCenterX() - center.x);
		location.y = (player.collisionBox.getCenterY() - center.y);
		
		if (location.x >= offsetMaxX) {
			location.x = offsetMaxX;
		}
		if (location.x <= offsetMinX) {
			location.x = offsetMinX;
		}
		if (location.y >= offsetMaxY) {
			location.y = offsetMaxY;
		}
		if (location.y <= offsetMinY) {
			location.y = offsetMinY;
		}
		
		//Calculate the area of the screen
		screen = new Rectangle(location.x, location.y, defaultWidth, defaultHeight);
		
	}
	
	public void updateVariables() {
		
		//Get the default size of the container
		defaultWidth = container.getWidth() * defaultHeight / container.getHeight();
		
		//Work how much to scale the graphics by
		scale = (float)container.getWidth() / defaultWidth;
		
		//Get center and size of map
		center = new Point2D.Float(defaultWidth / 2, defaultHeight / 2);
		size = new Point2D.Float(map.getWidth() - defaultWidth, map.getHeight() - defaultHeight);
		player = map.getPlayer();

		//Calculate the max and min of the location
		offsetMaxX = size.x;
		offsetMaxY = size.y;
		offsetMinX = 0;
		offsetMinY = 0;
		
	}

}
