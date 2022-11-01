package com.mycompany.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.mycompany.entities.Entity;
import com.mycompany.entities.Player;
import com.mycompany.graficos.Spritesheet;
import com.mycompany.graficos.UI;
import com.mycompany.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	private boolean showMessageGameOver = false, restartGame = false;
	private int framesGO = 0;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;
	
	private BufferedImage image;
	

	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static World world;
	public static Player player;
	
	public UI ui;
	
	public static int frutas_cont = 0;
	public static int curFrutas = 0;
	public static int whey_cont = 0;
	
	public static String GAME_STATE = "menu";
	public Menu menu;
	
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(0,0,16,16,1,spritesheet.getSprite(32, 0,16,16));
		world = new World("/level1.png");
		ui = new UI();
		entities.add(player);
		menu = new Menu();
	}
	
	public void initFrame(){
		frame = new JFrame("Pac-Man");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		
		if(GAME_STATE == "normal") {
			this.restartGame = false;
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
		}
		else if(GAME_STATE == "game_over") {
			this.framesGO++;
			if(this.framesGO == 30) {
				this.framesGO = 0;
				if(showMessageGameOver){
					showMessageGameOver = false;
				}
				else showMessageGameOver = true;
			}
		}else if(GAME_STATE == "vitoria") {
			this.framesGO++;
			if(this.framesGO == 30) {
				this.framesGO = 0;
				if(showMessageGameOver){
					showMessageGameOver = false;
				}
				else showMessageGameOver = true;
			}
		}else if(GAME_STATE == "menu") {
			menu.tick();
			
		}
		
		if(restartGame) {
			this.restartGame = false;
			GAME_STATE = "normal";
			World.restartGame();
		}
	}

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		
		if(GAME_STATE == "game_over") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 48));
			g.setColor(Color.white);
			g.drawString("FIM DE JOGO", (WIDTH*SCALE)/2-150, (HEIGHT*SCALE)/2-35);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("TOGURO GANHOU KKKKKK", (WIDTH*SCALE)/2-120, (HEIGHT*SCALE)/2-5);
			g.setFont(new Font("arial", Font.BOLD, 14));
			if(showMessageGameOver) g.drawString(">aperta enter pra reniciar ae<", (WIDTH*SCALE)/2-100, (HEIGHT*SCALE)/2+25);
		}
		
		if(GAME_STATE == "vitoria") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 48));
			g.setColor(Color.white);
			g.drawString("FIM DE JOGO", (WIDTH*SCALE)/2-150, (HEIGHT*SCALE)/2-35);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("TOGURO PERDEU EM PLENO 2022", (WIDTH*SCALE)/2-157, (HEIGHT*SCALE)/2-5);
			g.setFont(new Font("arial", Font.BOLD, 14));
			if(showMessageGameOver) g.drawString(">aperta enter pra reniciar ae<", (WIDTH*SCALE)/2-100, (HEIGHT*SCALE)/2+25);
		}
		
		if(GAME_STATE == "menu") {
			menu.render(g);
		}
		
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = true;
			if(GAME_STATE == "menu") {
				menu.up = true;
			}
			
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if(GAME_STATE == "menu") {
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(GAME_STATE == "game_over" || GAME_STATE == "vitoria") {
				World.restartGame();
			}
			if(GAME_STATE == "menu") {
				menu.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GAME_STATE = "menu";
			menu.pause = true;
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||
				e.getKeyCode() == KeyEvent.VK_D){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT ||
				e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP ||
				e.getKeyCode() == KeyEvent.VK_W){
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN ||
				e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
