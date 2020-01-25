package application;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;
import java.util.*;

public class Game extends Canvas implements Runnable {
	
	public final static int WIDHT = 1000;
	public  final static int HEIGHT = 562;
	public boolean running = false;
	private Ball ball;
	private Thread gameThread;
	private Paddle paddle1;
	private Paddle paddle2;

	/**
	 * generated id
	 */
	private static final long serialVersionUID = -1498793558776767209L;
	
	public Game() {
		canvasSetup();
		initialize();
		new Window("Pong", this);
		this.addKeyListener(new KeyInput(paddle1,paddle2));
		this.setFocusable(true);
	}

	public void initialize() {
		//inicializar bola e raquetes(paddles)
		ball = new Ball();
		
		paddle1 = new Paddle(Color.GREEN, true);
		paddle2 = new Paddle(Color.RED, false);
		
	}
	
	public void run() {
		this.requestFocus();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
			}
			
			if(running) draw();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
		
		
		
	}
	
	public void update() {
		ball.update(paddle1,paddle2);
		
		paddle1.update(ball);
		paddle2.update(ball);

	}

	public void drawBackground(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 562);
		
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(500, 0, 500, 562);
		
		
		
		
		
	}

	public void draw() {
		BufferStrategy buffer = this.getBufferStrategy();
		
		if(buffer == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = buffer.getDrawGraphics();
		drawBackground(g);
		
		ball.draw(g);
		paddle1.draw(g);
		paddle2.draw(g);
		
		
		g.dispose();
		buffer.show();
		
	}

	public void start() {
		gameThread = new Thread(this);
		gameThread.start();
		running = true;
		
	}
	
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDHT,HEIGHT));
		this.setMinimumSize(new Dimension(WIDHT,HEIGHT));
		this.setMaximumSize(new Dimension(WIDHT,HEIGHT));
	}
	
	public static int sign(double d) {
		if ( d <= 0) {
			return -1;
		}
		else {
			return 1;
		}
		
	}
	
	public static int ensureRange(int max, int val, int min) {
		return ((int) Math.min(Math.max(val, min), max));
	}
	
	public static void main(String[] args) {
		new Game();
	}
	

}
