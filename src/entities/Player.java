package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.ATTACK_1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utilz.LoadSave;

public class Player extends Entity{

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 20;
	private int playerAction = IDLE;
	private boolean left, up, right, down;
	private boolean moving = false, attacking = false;
	private float playerSpeed = 2.0f;
	private float diagonal = 0.0f;
	private int playerDir = -1;
	
	public Player(float x, float y) {
		super(x, y);
		loadAnimations();
	}
	
	public void setDirection(int direction) {
		this.playerDir = direction;
		moving = true;
	}
	
	public void update() {
		
		updatePos();
		updateAnimationTick();
		setAnimation();
		
	}
	
	public void render(Graphics g) {
		
		g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 256, 160, null);
	}
	
	
	
	private void updateAnimationTick() {
		
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}
	}
	
	private void setAnimation() {
		
		int startAni = playerAction;
		
		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;
		
		if(attacking)
			playerAction = ATTACK_1;
		
		if (startAni != playerAction)
			resetAnimationIndex();
		
	}
	
	private void resetAnimationIndex() {
		aniTick = 0;
		aniIndex = 0;
		
	}

	private void updatePos() {
		
		moving = false;
		diagonal = 0.0f;
		
		if ((up || down) && (left || right))
			diagonal = playerSpeed * 0.314f;
		
		
		if (left && !right) {
			x -= (playerSpeed - diagonal);
			moving = true;
		} else if (right && !left) {
			x += (playerSpeed -diagonal);
			moving = true;
		}
		
		if (up && !down) {
			y -= (playerSpeed - diagonal);
			moving = true;
		} else if (down && !up) {
			y += (playerSpeed - diagonal);
			moving = true;
		}
		
		
			
	}
	
	private void loadAnimations() {
		
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		
		animations = new BufferedImage[9][6];	
		for (int j=0; j<animations.length; j++)
			for (int i=0; i<animations[j].length; i++)
				animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
	
	}
	
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isLeft() {
		return left;	
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
		
	}
	
	
}
