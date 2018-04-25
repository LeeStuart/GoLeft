package game;

public class Enumerations {
	
	//Controls needed
	enum ControlValues {
		JUMP,
		LEFT,
		RIGHT,
		DOWN,
		PAUSE
	}
	
	//Types of tiles
	enum TileTypes {
		EMPTY,
		BLOCK,
		HIDDENBLOCK,
		ONEWAY,
		SLOPE,
		INVERSESLOPE,
		SLIDE,
		SPIKE
	}
	
	//Tile slope direction
	enum TileSlopeDirection {
		RIGHT,
		LEFT
	}
	
	//Types of entities
	enum EntityTypes {
		ENTITY,
		PLAYER,
		SPIDER
	}
	
}
