package com.mycompany.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.mycompany.main.Game;
import com.mycompany.world.Camera;
import com.mycompany.world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down, moved;
	private BufferedImage[] sprite_left, sprite_up,sprite_down, sprite_right;
	private int frames = 0,maxFrames = 3,index = 0,maxIndex = 5;
	public static boolean power;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		sprite_left = new BufferedImage[6];
		sprite_right = new BufferedImage[6];
		sprite_up = new BufferedImage[6];
		sprite_down = new BufferedImage[6];
		
		playerSprites();
			
	}
	
	public void playerSprites(){
		sprite_left[0] = Game.spritesheet.getSprite(144, 0, 16, 16);
		sprite_left[1] = Game.spritesheet.getSprite(128, 0, 16, 16);
		sprite_left[2] = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_left[3] = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_left[4] = Game.spritesheet.getSprite(128, 0, 16, 16);
		sprite_left[5] = Game.spritesheet.getSprite(144, 0, 16, 16);
		
		sprite_up[0] = Game.spritesheet.getSprite(16, 32, 16, 16);
		sprite_up[1] = Game.spritesheet.getSprite(48, 32, 16, 16);
		sprite_up[2] = Game.spritesheet.getSprite(64, 0, 16, 16);
		sprite_up[3] = Game.spritesheet.getSprite(64, 0, 16, 16);
		sprite_up[4] = Game.spritesheet.getSprite(48, 32, 16, 16);
		sprite_up[5] = Game.spritesheet.getSprite(16, 32, 16, 16);
		
		sprite_down[0] = Game.spritesheet.getSprite(32, 32, 16, 16);
		sprite_down[1] = Game.spritesheet.getSprite(64, 32, 16, 16);
		sprite_down[2] = Game.spritesheet.getSprite(80, 0, 16, 16);
		sprite_down[3] = Game.spritesheet.getSprite(80, 0, 16, 16);
		sprite_down[4] = Game.spritesheet.getSprite(64, 32, 16, 16);
		sprite_down[5] = Game.spritesheet.getSprite(32, 32, 16, 16);
		
		sprite_right[0] = Game.spritesheet.getSprite(96, 0, 16, 16);
		sprite_right[1] = Game.spritesheet.getSprite(112, 0, 16, 16);
		sprite_right[2] = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprite_right[3] = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprite_right[4] = Game.spritesheet.getSprite(112, 0, 16, 16);
		sprite_right[5] = Game.spritesheet.getSprite(96, 0, 16, 16);
	}
	
	public int lastDir = 0;
	
	public void tick(){
		depth = 1;
		moved = false;
		
		if(right && World.isFree((int)(x+speed),this.getY())) {
			moved = true;
			x+=speed;
			lastDir = 0;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			moved = true;
			x-=speed;
			lastDir = 1;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			moved = true;
			y-=speed;
			lastDir = -1;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			moved = true;
			y+=speed;
			lastDir = -2;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		pegaFrutaChecker();
		pegaWheyChecker();
		if(enemyCollisionChecker() && power == false){
			Game.GAME_STATE = "game_over";
		}
		if(enemyCollisionChecker() && power == true) {
			enemyEat();
		}
		if(winChecker()) {
			Game.GAME_STATE = "vitoria";
			power = false;
		}
		
		
		
	}
	
	public void pegaWheyChecker(){
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Whey){
				if(Entity.isColidding(this, cur)) {
					Game.entities.remove(i);
					power = true;
					return;
				}
			}
		}
	}
	
	public boolean winChecker() {
		if(Game.curFrutas == Game.frutas_cont) {
			return true;
		}
		return false;
	}
	
	public void enemyEat() {
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Enemy){
				if(Entity.isColidding(this, cur)) {
					Game.entities.remove(i);
					power = false;
					return;
				}
			}
		}
	}
	
	public boolean enemyCollisionChecker(){
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Enemy){
				if(Entity.isColidding(this, cur)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void pegaFrutaChecker() {
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Fruta){
				if(Entity.isColidding(this, cur)) {
					Game.entities.remove(i);
					Game.curFrutas++;
					return;
				}
			}
		}
	}

	public void render(Graphics g) {
		if(lastDir == 0) {
			g.drawImage(sprite_right[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir == 1){
			g.drawImage(sprite_left[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
	
		}
		else if(lastDir == -1) {
			g.drawImage(sprite_up[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir == -2) {
			g.drawImage(sprite_down[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		
	}


}
