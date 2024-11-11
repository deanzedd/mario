package com.game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game.gfx.Windows;
import com.game.object.Block;
import com.game.object.Player;
import com.game.object.util.Handler;
import com.game.object.util.KeyInput;

public class Game extends Canvas implements Runnable {
	
	//game constants
	private static final int MILLIS_PER_SEC = 1000;
	private static final int NANOS_PER_SEC = 1000000000;
	private static final double NUM_TICKS = 60.0;
	private static final String NAME = "Super Mario Bros";
	
	private static final int WINDOW_WIDTH = 960;
	private static final int WINDOW_HEIGHT = 720;
	
	// game variables
	private boolean running;
	
	
	// game components (thanh phan tro choi)
	private Thread thread; // luong
	private Handler handler; //xu li
	
	public Game() {
		initialize();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();

	}
	
	private void initialize() {
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
		
		
		//temporary code
		handler.setPlayer(new Player(32, 32, 1, handler));
		for (int i=0; i < 20; i++) {
			//create new block
			handler.addObj(new Block(i*32, 32*10, 32, 32, 1));
		}
		for (int i=0; i< 30; i++) {
			handler.addObj(new Block(i*32, 32*15, 32, 32, 1));
		}
		
		
		
		new Windows(WINDOW_WIDTH, WINDOW_HEIGHT, NAME, this);
		
		start();
	}
	
	private synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	private synchronized void stop() {	
		try {
			thread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = NUM_TICKS;
		double ns = NANOS_PER_SEC /amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ ns;
			lastTime = now;
			
			while(delta >=1) {
				tick();
				updates++;
				delta--;
			}
			
			if (running) {
				render();
				frames++; //update frames
			}
			
			if(System.currentTimeMillis() - timer > MILLIS_PER_SEC ) {
				timer += MILLIS_PER_SEC;
				System.out.println("FPS: " + frames + " TPS: " + updates);
				updates = 0;
				frames = 0;
			}
		}
		
		stop();
	}
	private void tick() {
		handler.tick();
	}
	
	private void render() {
		BufferStrategy buf = this.getBufferStrategy();
		if(buf ==  null) {
			this.createBufferStrategy(3);
			return;
		}
		
		// draw graphics
		Graphics g = buf.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);//fill rectangle (The left and right edges of the rectangle are at x and x + width - 1.The top and bottom edges are at y and y + height - 1)
		
		handler.render(g);
		
		g.dispose(); // lam vo hieu graphic g
		buf.show(); // render next buffer or next frame
				
	}
	
	public static int getWindowHeight() {
		return WINDOW_HEIGHT;
	}
	
	public static int getWindowWidth() {
		return WINDOW_WIDTH;
	}
	
}
