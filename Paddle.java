package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Paddle {
	
	private int x,y;
	private int speed = 10;
	private int vel = 0;
	private int width = 22, height = 85;
	private int score = 0;
	private Color color;
	private boolean left;
	
	public Paddle(Color c, boolean left) {
		color = c;
		this.left = left;
		if(left) {
			x = 0;
		}
		else {
			x = Game.WIDHT - width;
		}
		y = Game.HEIGHT/2 - height/2;
		
	}
	
	public void addPoint() {
		score++;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		int px;
		String scoreText = Integer.toString(score);
		Font font = new Font("Roboto", Font.PLAIN, 50);
		int stringWidth = g.getFontMetrics(font).stringWidth(scoreText)+ 1;
		int padding = 25;
		
		if(left) {
			px = 500 - padding -stringWidth;
		}
		else {
			px =  500 + padding;
		}
		
		g.setFont(font);
		g.drawString(scoreText, px, 50);
		
	}
	
	public void update(Ball ball) {
		y = Game.ensureRange(562 - height, y += vel, 0);
		
		int ballX = ball.getX();
		int ballY = ball.getY();
		
		System.out.println(left);
		if(left) {
			if(ballX <= width && ballY + Ball.SIZE >= y && ballY <= y+height) {
				ball.changeXDir();
			}
		}
		else {
			if( ballX + Ball.SIZE >= Game.WIDHT - width && ballY + Ball.SIZE >= y && ballY <= y + height) {
				ball.changeXDir();
			}
		}
	}

	public void switchDirection(int i) {
		vel = speed * i;	
	}
	
	public void stop() {
		vel = 0;
	}

}
