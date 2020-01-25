package application;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	
	public static final int SIZE = 16;
	private int x, y;
	private int xVelocity, yVelocity; //-1 ou 1 (vaoo para cima ou para baixo)
	private int speed = 5;
	
	
	
	
	public Ball() {
		reset();
	}
	
	private void reset() {
		x = Game.WIDHT / 2 - SIZE/2;
		y = Game.HEIGHT / 2 - SIZE/2;
		xVelocity = Game.sign(Math.random() *2 -1);
		yVelocity = Game.sign(Math.random() *2 -1);
		
	}
	
	public void changeXDir() {
		xVelocity *= -1;
	}
	
	public void changeYDir() {
		yVelocity *= -1;
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, SIZE, SIZE);
		
	}
	
	public void update(Paddle paddle1, Paddle paddle2) {
		x += xVelocity * speed;
		y += yVelocity * speed;
		if( y + SIZE >= Game.HEIGHT || y <= 0) {
			changeYDir();
		}
		
		if( x + SIZE >= Game.WIDHT) {
			paddle1.addPoint();
			reset();
		}
		else if( x  <= 0) {
			paddle2.addPoint();
			reset();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
