package com.mycompany.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String[] options = {"Iniciar Jogo", "Sair"};
	public int curOption = 0;
	public int maxOption = options.length-1;
	public boolean up, down, enter, pause = false;
	
	public void tick() {
		if(up) {
			up = false;
			curOption--;
			if(curOption < 0) {
				curOption = maxOption;
			}
		}
		
		if(down) {
			down = false;
			curOption++;
			if(curOption> maxOption) {
				curOption = 0;
			}
		}
		
		if(enter) {
			enter = false;
			if(options[curOption] == "Iniciar Jogo" || options[curOption] == "Continuar") {
				Game.GAME_STATE = "normal";
				pause = false;
			}
			if(options[curOption] == "Sair") {
				System.exit(1);
			}
			
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0,0,Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		//titulo
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD,36));
		g.drawString("PAC-MAN", (Game.WIDTH*Game.SCALE)/2 - 210, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.setColor(Color.yellow);
		g.setFont(new Font("arial", Font.BOLD,36));
		g.setColor(Color.red);
		g.drawString("VS", (Game.WIDTH*Game.SCALE)/2 - 25, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.setColor(Color.blue);
		g.drawString("TOGURO", (Game.WIDTH*Game.SCALE)/2 + 45, (Game.HEIGHT*Game.SCALE)/2 - 100);
		
		//opcoes menu
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,28));
		if(pause == false)
		g.drawString("Iniciar Jogo", (Game.WIDTH*Game.SCALE)/2 - 88, (Game.HEIGHT*Game.SCALE)/2);
		else g.drawString("Continuar", (Game.WIDTH*Game.SCALE)/2 - 78, (Game.HEIGHT*Game.SCALE)/2);
		g.setFont(new Font("arial", Font.BOLD,28));
		g.drawString("Sair", (Game.WIDTH*Game.SCALE)/2 - 38, (Game.HEIGHT*Game.SCALE)/2 +78);
		
		
		if(options[curOption] == "Iniciar Jogo") {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD,24));
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 120, (Game.HEIGHT*Game.SCALE)/2);	
		}else if(options[curOption] == "Sair") {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD,24));
			g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 70, (Game.HEIGHT*Game.SCALE)/2+77);
		}
		
		
		
	}
	
	
}
