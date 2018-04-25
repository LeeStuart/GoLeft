package game;

import game.Enumerations.TileSlopeDirection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Map {
	
	//Map information
	private Tile[][] mapData;
	private String name;
	private Image background;
	private SpriteSheet tileSet;
	
	//Entities
	private ArrayList<Entity> entities;
	private Player player;

	public Map(String name, Player player) throws SlickException {
		
		this.name = name;
		this.tileSet = ImageHandler.tileSet1;
		//this.background = new Image("images/tilesets/default background.png");
		this.background = tileSet.getSprite(0, 3);

		loadMapData();
		
		entities = new ArrayList<Entity>();
		this.player = player;
		entities.add(new Spider(340, 180, this));
		
	}
	
	public void render(Graphics g, Rectangle screen) {
		
		//Draw background of map
		for (int i = 0; i < getWidth(); i += background.getWidth()) {
			for (int j = 0; j < getHeight(); j += background.getHeight()) {
				if (i + background.getWidth() > screen.getMinX() && i < screen.getMaxX() && j + background.getHeight() > screen.getMinY() && j + 1 < screen.getMaxY()) {
					g.drawImage(background, i, j);
				}
			}
		}
		
		//Draw tiles
		for (Tile[] tileRow : mapData) {
			for (Tile t : tileRow) {
				if (screen.intersects(t.collisionBox)) {
					t.render(g);
				}
			}
		}
		
		//Draw entities
		for (Entity e : entities) {
			if (screen.intersects(e.collisionBox)) {
				e.render(g);
			}
		}
		player.render(g);
		
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
			
		//Update entities
		for (Entity e : entities) {
			e.update(delta);
		}
		player.update(container, delta);
		
	}
	
	private void loadMapData() throws SlickException {
		
		//Read information from file
		File map = new File("maps/" + name);
		ArrayList<String> raw = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(map));
			String text;
			raw = new ArrayList<String>();
			while((text = reader.readLine()) != null) {
				raw.add(text);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		//Parse information to tiles
		if (raw != null) {
			mapData = new Tile[raw.size()][];
			for (int y = 0; y < mapData.length; y++) {
				mapData[y] = new Tile[raw.get(y).length()];
				for (int x = 0; x < mapData[y].length; x++) {
					if (raw.get(y).charAt(x) == 'B') {
						mapData[y][x] = new TileBlock(x, y, this, tileSet);
					} else if (raw.get(y).charAt(x) == 'H') {
						mapData[y][x] = new TileHiddenBlock(x, y, this, tileSet);
					} else if (raw.get(y).charAt(x) == '\\') {
						mapData[y][x] = new TileSlope(x, y, TileSlopeDirection.LEFT, this, tileSet);
					} else if (raw.get(y).charAt(x) == '/') {
						mapData[y][x] = new TileSlope(x, y, TileSlopeDirection.RIGHT, this, tileSet);
					} else if (raw.get(y).charAt(x) == '|') {
						mapData[y][x] = new TileInverseSlope(x, y, TileSlopeDirection.LEFT, this, tileSet);
					} else if (raw.get(y).charAt(x) == '?') {
						mapData[y][x] = new TileInverseSlope(x, y, TileSlopeDirection.RIGHT, this, tileSet);
					} else if (raw.get(y).charAt(x) == '>') {
						mapData[y][x] = new TileSlide(x, y, TileSlopeDirection.LEFT, this, tileSet);
					} else if (raw.get(y).charAt(x) == '<') {
						mapData[y][x] = new TileSlide(x, y, TileSlopeDirection.RIGHT, this, tileSet);
					} else if (raw.get(y).charAt(x) == 'O') {
						mapData[y][x] = new TileOneWay(x, y, this, tileSet);
					} else if (raw.get(y).charAt(x) == 'T') {
						mapData[y][x] = new TileHiddenOneWay(x, y, this, tileSet);
					} else if (raw.get(y).charAt(x) == 'W') {
						mapData[y][x] = new TileSpike(x, y, this, tileSet, 0);
					} else if (raw.get(y).charAt(x) == 'D') {
						mapData[y][x] = new TileSpike(x, y, this, tileSet, 1);
					} else if (raw.get(y).charAt(x) == 'S') {
						mapData[y][x] = new TileSpike(x, y, this, tileSet, 2);
					} else if (raw.get(y).charAt(x) == 'A') {
						mapData[y][x] = new TileSpike(x, y, this, tileSet, 3);
					} else {
						mapData[y][x] = new Tile(x, y, this, tileSet);
					}
				}
			}
		}
		
		checkTiles();
		
	}
	
	private void checkTiles() throws SlickException {
		
		for (Tile[] tileRow : mapData) {
			for (Tile t : tileRow) {
				t.checkTiles();
			}
		}
	}

	public Tile getTile(int x, int y) {

		//Check if tile exists out of bounds
		if (y < 0 || y >= mapData.length) {
			return null;
		}
		if (x < 0 || x >= mapData[y].length) {
			return null;
		}
		
		return mapData[y][x];
		
	}
	
	public int getWidth() {
		
		return mapData[0].length * Tile.TILE_SIZE;
		
	}
	
	public int getHeight() {
		
		return mapData.length * Tile.TILE_SIZE;
		
	}

	public ArrayList<ArrayList<Tile>> getTiles(Rectangle collisionBox) {
		
		ArrayList<ArrayList<Tile>> tilesArray = new ArrayList<ArrayList<Tile>>();
		for (Tile[] tileRow : mapData) {
			ArrayList<Tile> tileRowArray = new ArrayList<Tile>();
			for (Tile t : tileRow) {
				if (collisionBox.intersects(t.getCollisionBox())) {
					tileRowArray.add(t);
				}	
			}
			tilesArray.add(tileRowArray);
		}
		return tilesArray;
	}

	public Player getPlayer() {

		return player;
		
	}

}
