package com.mycompany.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.mycompany.main.Game;
import com.mycompany.world.Camera;
import com.mycompany.world.World;



public class Enemy extends Entity{
	
	private BufferedImage[] sprite_left_angry, sprite_up_angry,sprite_down_angry, sprite_right_angry,
	sprite_escape, sprite_left_whey, sprite_right_whey, sprite_up_whey, sprite_down_whey;
	private int frames = 0,maxFrames = 7,index = 0,maxIndex = 2;
	private int frames2 = 0,maxFrames2 = 7,index2 = 0,maxIndex2 = 2;
	private int frames3 = 0,maxFrames3 = 11,index3 = 0,maxIndex3 = 2;
	private int lastDir = 0, lastDir2 = 0;
	public boolean right,up,left,down, mov,mov2, movP = true, stop = false, enemyPower = false;
	public int timer = 5 * 60, timer2 = 3*60;
	
	public Enemy(int x, int y, int width, int height,double speed, BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		sprite_left_angry = new BufferedImage[3];
		sprite_right_angry = new BufferedImage[3];
		sprite_up_angry = new BufferedImage[3];
		sprite_down_angry = new BufferedImage[3];
		sprite_left_whey = new BufferedImage[3];
		sprite_right_whey = new BufferedImage[3];
		sprite_up_whey = new BufferedImage[3];
		sprite_down_whey = new BufferedImage[3];
		sprite_escape = new BufferedImage[3];
		
		
		
		enemySprites();
	}
	
	
	public void enemySprites(){
		sprite_left_angry[0] = Game.spritesheet.getSprite(64, 16, 16, 16);
		sprite_left_angry[1] = Game.spritesheet.getSprite(80, 16, 16, 16);
		sprite_left_angry[2] = Game.spritesheet.getSprite(96, 16, 16, 16);
		
		sprite_up_angry[0] = Game.spritesheet.getSprite(80, 32, 16, 16);
		sprite_up_angry[1] = Game.spritesheet.getSprite(96, 32, 16, 16);
		sprite_up_angry[2] = Game.spritesheet.getSprite(112, 32, 16, 16);
		
		sprite_down_angry[0] = Game.spritesheet.getSprite(64, 16, 16, 16);
		sprite_down_angry[1] = Game.spritesheet.getSprite(80, 16, 16, 16);
		sprite_down_angry[2] = Game.spritesheet.getSprite(96, 16, 16, 16);
		
		
		sprite_right_angry[0] = Game.spritesheet.getSprite(16, 16, 16, 16);
		sprite_right_angry[1] = Game.spritesheet.getSprite(32, 16, 16, 16);
		sprite_right_angry[2] = Game.spritesheet.getSprite(48, 16, 16, 16);
		
		sprite_escape[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
		sprite_escape[1] = Game.spritesheet.getSprite(112, 16, 16, 16);
		sprite_escape[2] = Game.spritesheet.getSprite(128, 16, 16, 16);
		
		sprite_left_whey[0] = Game.spritesheet.getSprite(48, 48, 16, 16);
		sprite_left_whey[1] = Game.spritesheet.getSprite(64, 48, 16, 16);
		sprite_left_whey[2] = Game.spritesheet.getSprite(80, 48, 16, 16);
		
		sprite_right_whey[0] = Game.spritesheet.getSprite(0, 48, 16, 16);
		sprite_right_whey[1] = Game.spritesheet.getSprite(16, 48, 16, 16);
		sprite_right_whey[2] = Game.spritesheet.getSprite(32, 48, 16, 16);
		
		sprite_up_whey[0] = Game.spritesheet.getSprite(96, 48, 16, 16);
		sprite_up_whey[1] = Game.spritesheet.getSprite(112, 48, 16, 16);
		sprite_up_whey[2] = Game.spritesheet.getSprite(128, 48, 16, 16);
		
		sprite_down_whey[0] = Game.spritesheet.getSprite(0, 48, 16, 16);
		sprite_down_whey[1] = Game.spritesheet.getSprite(16, 48, 16, 16);
		sprite_down_whey[2] = Game.spritesheet.getSprite(32, 48, 16, 16);
		
	}

	public void tick(){
		depth = 1;
		mov = false;
		powerOfTheWhey();
		
		if(enemyPower){
			timer2--;
			raiseSpeed();
			if(timer2 == 0) {
				neutralSpeed();
				enemyPower = false;
				mov2 = false;
				movP = true;
			}
		}
		
		if(enemyPower) {
			if(this.x < Game.player.getX() && World.isFree((int)(this.x+speed), this.getY())) {
				mov2 = true;
				this.x+=speed;
				lastDir2 = 0;
			}
			else if(this.x > Game.player.getX()  && World.isFree((int)(this.x-speed), this.getY())) {
				mov2 = true;
				this.x-=speed;
				lastDir2 = 1;
			}
			
			else if(this.y < Game.player.getY()  && World.isFree(this.getX(), (int) (this.y+speed))) {
				mov2 = true;
				this.y+=speed;
				lastDir2 = -2;
			}
			else if(this.y > Game.player.getY() && World.isFree(this.getX(), (int) (this.y-speed))) {
				mov2 = true;
				this.y-=speed;
				lastDir2 = -1;
			}
		}
		if(movP) {
			if(this.x < Game.player.getX() && World.isFree((int)(this.x+speed), this.getY())) {
				mov = true;
				this.x+=speed;
				lastDir = 0;
			}
			else if(this.x > Game.player.getX()  && World.isFree((int)(this.x-speed), this.getY())) {
				mov = true;
				this.x-=speed;
				lastDir = 1;
			}
			
			else if(this.y < Game.player.getY()  && World.isFree(this.getX(), (int) (this.y+speed))) {
				mov = true;
				this.y+=speed;
				lastDir = -2;
			}
			else if(this.y > Game.player.getY() && World.isFree(this.getX(), (int) (this.y-speed))) {
				mov = true;
				this.y-=speed;
				lastDir = -1;
			}
			
		}
		
		if(mov) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex)
					index = 0;
			}
		}
		
		if(mov2) {
			frames3++;
			if(frames3 == maxFrames3) {
				frames3 = 0;
				index3++;
				if(index3 > maxIndex3)
					index3 = 0;
			}
		}
		if(stop) {
			frames2++;
			if(frames2 == maxFrames2) {
				frames2 = 0;
				index2++;
				if(index2 > maxIndex2)
					index2 = 0;
			}
		}
		if(Player.power == true) {
			stop = true;
			movP = false;
			timer--;
			if(timer == 0) {
				movP = true;
				stop = false;
				Player.power = false;
			}
		}
		
	}

	public void powerOfTheWhey() {
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Whey){
				if(Entity.isColidding(this, cur)) {
					Game.entities.remove(i);
					enemyPower = true;
				}
			}
		}
	}
	
	public void raiseSpeed() {
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Enemy){
				cur.speed = 1.5;
			}
		}
	}
	
	public void neutralSpeed() {
		for(int i = 0; i<Game.entities.size();i++) {
			Entity cur = Game.entities.get(i);
			if(cur instanceof Enemy){
				cur.speed = 1;
			}
		}
	}
	
	public void render(Graphics g) {
		if(stop) {
			g.drawImage(sprite_escape[index2],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(lastDir2 == 0 && stop == false && enemyPower == true) {
			g.drawImage(sprite_right_whey[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir2 == 1 && stop == false && enemyPower == true) {
			g.drawImage(sprite_left_whey[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir2 == -1 && stop == false && enemyPower == true) {
			g.drawImage(sprite_up_whey[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir2 == -2 && stop == false && enemyPower == true) {
			g.drawImage(sprite_down_whey[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		if(lastDir == 0 && stop == false && enemyPower == false) {
			 g.drawImage(sprite_right_angry[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir == 1 && stop == false && enemyPower == false) {
			 g.drawImage(sprite_left_angry[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir == -1 && stop == false && enemyPower == false) {
			g.drawImage(sprite_up_angry[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
		else if(lastDir == -2 && stop == false && enemyPower == false) {
			g.drawImage(sprite_down_angry[index],this.getX() - Camera.x,this.getY() - Camera.y,null);
		}
	}
	
	
	
}
