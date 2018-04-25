package game;

import java.awt.geom.Point2D;

import game.Enumerations.ControlValues;
import game.Enumerations.EntityTypes;
import game.Enumerations.TileSlopeDirection;
import game.Enumerations.TileTypes;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Player extends Entity implements KeyListener, ControllerListener{
	
	private final int JUMPING_TIME = 300;
	
	private boolean flying;
	private float animationCooldown;
	private boolean facingRight;

	private Image imgStanding;
	private Image imgRunning1;
	private Image imgRunning2;
	private Image imgJumping;
	
	public Player(GameContainer container) throws SlickException {
		
		super();
		type = EntityTypes.PLAYER;
		
		sprite = new Image("images/player/player standing.png");
		this.offset = new Point2D.Float(12, 0);
		this.trailoffset = new Point2D.Float(12, 0);
		collisionBox = new Rectangle(offset.x, offset.y, sprite.getWidth() - trailoffset.x - offset.x, sprite.getHeight() - trailoffset.y - offset.y);
		
		animationCooldown = 100;
		facingRight = true;
		jumpingCooldown = JUMPING_TIME;
		
		imgStanding = new Image("images/player/player standing.png");
		imgRunning1 = new Image("images/player/player running 1.png");
		imgRunning2 = new Image("images/player/player running 2.png");
		imgJumping = new Image("images/player/player falling.png");
		
		container.getInput().addControllerListener(this);
		container.getInput().addKeyListener(this);
		
	}
	
	public void load(float x, float y, Map map) {
		
		location.x = x;
		location.y = y;
		collisionBox.setLocation(x + offset.x, y + offset.y);
		this.map = map;
		flying = false;
		
	}
	
	public void update(GameContainer container, int delta) throws SlickException {
		
		quickDown = false;
		
		Input input = container.getInput();
		
		if (input.isKeyPressed(Input.KEY_F)) {
			flying = !flying;
		}
		
		Tile tl = map.getTile((int)(collisionBox.getMinX() / 64), (int)((collisionBox.getMaxY() + 1) / 64));
		Tile tr = map.getTile((int)(collisionBox.getMaxX() / 64), (int)((collisionBox.getMaxY() + 1) / 64));
		
		if (flying) {
			if (input.isKeyDown(Main.options.getControls(ControlValues.JUMP))) { 
				acceleration.y = -0.5f;
			} else if (input.isKeyDown(Main.options.getControls(ControlValues.DOWN))) {
				acceleration.y = 0.5f;
			} else {
				acceleration.y = 0;
			}
			falling = false;
		} else {
			if (jumping) {
				acceleration.y += -0.005f * delta;
				jumpingCooldown -= delta;
				if (jumpingCooldown < 0) {
					jumping = false;
					jumpingCooldown = JUMPING_TIME;
				}
			} else if ((input.isKeyPressed(Main.options.getControls(ControlValues.JUMP)) || input.isControlPressed(4, Main.currentController)) && falling == false) {
				
			} else if (input.isKeyPressed(Main.options.getControls(ControlValues.DOWN)) || input.isControlPressed(5, Main.currentController)) {
				quickDown = true;
			}
		}
		
		if (!falling && (tl.type == TileTypes.SLIDE || tr.type == TileTypes.SLIDE)) {
			if (tl.type == TileTypes.SLIDE) {
				TileSlide ts = (TileSlide)tl;
				if (ts.direction == TileSlopeDirection.LEFT) {
					acceleration.x = 0.7f;
				}
			}
			if (tr.type == TileTypes.SLIDE) {
				TileSlide ts = (TileSlide)tr;
				if (ts.direction == TileSlopeDirection.RIGHT) {
					acceleration.x = -0.7f;
				}
			}
		} else {
			if (input.isKeyDown(Main.options.getControls(ControlValues.LEFT)) && input.isKeyDown(Main.options.getControls(ControlValues.RIGHT))) {
				acceleration.x = 0;
			} else if (input.isKeyDown(Main.options.getControls(ControlValues.LEFT)) || (input.isControllerLeft(Main.currentController) && Main.controllerFixed[Main.currentController])) {
				if (!falling && acceleration.x > 0) {
					acceleration.x = 0;
				}
				acceleration.x += -0.0015 * delta;
				if (acceleration.x <= -0.5) {
					acceleration.x = -0.5f;
				}
			} else if (input.isKeyDown(Main.options.getControls(ControlValues.RIGHT)) || (input.isControllerRight(Main.currentController) && Main.controllerFixed[Main.currentController])) {
				if (!falling && acceleration.x < 0) {
					acceleration.x = 0;
				}
				acceleration.x += 0.0015 * delta;
				if (acceleration.x >= 0.5) {
					acceleration.x = 0.5f;
				}
			} else {
				if (!falling) {
					if (acceleration.x > 0) {
						acceleration.x += -0.003 * delta;
						if (acceleration.x < 0) {
							acceleration.x = 0;
						}
					} else {
						acceleration.x += 0.003 * delta;
						if (acceleration.x > 0) {
							acceleration.x = 0;
						}
					}
				}
			}
		}
		
		if (!falling) {
			if (velocity.x != 0) {
				if (animationCooldown < 0) {
					if (!sprite.getResourceReference().equals("images/player/player running 1.png")) {
						sprite = imgRunning1;
						if (!facingRight) {
							sprite = sprite.getFlippedCopy(true, false);
						}
					} else {
						sprite = imgRunning2;
						if (!facingRight) {
							sprite = sprite.getFlippedCopy(true, false);
						}
					}
					animationCooldown = 100;
				} else {
					animationCooldown -= 0.8 * delta;
				}
			} else {
				sprite = imgStanding;
				if (!facingRight) {
					sprite = sprite.getFlippedCopy(true, false);
				}
			}
		} else {
			sprite = imgJumping;
			if (!facingRight) {
				sprite = sprite.getFlippedCopy(true, false);
			}
		}
		
		if (velocity.x > 0 && !facingRight) {
			facingRight = true;
		} else if (velocity.x < 0 && facingRight) {
			facingRight = false;
		}
		
		super.update(delta);
		
	}

	@Override
	public void setInput(Input input) {
		
	}

	@Override
	public boolean isAcceptingInput() {
		
		return true;
		
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public void controllerLeftPressed(int controller) {
		
	}

	@Override
	public void controllerLeftReleased(int controller) {
		
	}

	@Override
	public void controllerRightPressed(int controller) {
		
	}

	@Override
	public void controllerRightReleased(int controller) {
		
	}

	@Override
	public void controllerUpPressed(int controller) {
		
	}

	@Override
	public void controllerUpReleased(int controller) {
		
	}

	@Override
	public void controllerDownPressed(int controller) {
		
	}

	@Override
	public void controllerDownReleased(int controller) {
		
	}

	@Override
	public void controllerButtonPressed(int controller, int button) {
		
		if (button == 1 && !falling) {
			jumping = true;
			acceleration.y = -1.1f;
		}
		
	}

	@Override
	public void controllerButtonReleased(int controller, int button) {
		
		if (button == 1 && jumping) {
			jumping = false;
			jumpingCooldown = JUMPING_TIME;
		}
		
	}

	@Override
	public void keyPressed(int key, char c) {
		
		if (key == Main.options.getControls(ControlValues.JUMP) && !falling) {
			jumping = true;
			acceleration.y = -1.1f;
		}
		
	}

	@Override
	public void keyReleased(int key, char c) {

		if (key == Main.options.getControls(ControlValues.JUMP) && jumping) {
			jumping = false;
			jumpingCooldown = JUMPING_TIME;
		}
		
	}

}
