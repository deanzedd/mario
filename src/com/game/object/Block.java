package com.game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.object.util.ObjectId;

public class Block extends GameObject {
	
	public Block(int x, int y, int width, int height, int scale) {
		super(x, y, ObjectId.Block, width, height, scale);
	}

	@Override
	public void tick() {
		
		
	}

	@Override
	public void render(Graphics g) {
		//temporary code
		g.setColor(Color.white);
		g.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight()); //tao block mau trang
		
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
	}
	
	
	
	
	
}
