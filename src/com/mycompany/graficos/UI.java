package com.mycompany.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.mycompany.main.Game;

public class UI {

	public void render(Graphics g) {
		
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,18));
		g.drawString("Cerejas: " + Game.curFrutas + "/" + Game.frutas_cont, 30, 30);
		
		
	}
	
}
