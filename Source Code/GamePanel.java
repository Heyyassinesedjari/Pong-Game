import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


//so we can have this running on a thread and the frame it's kind of the frame around it :Cadre
/* the game panel will be the canvas on which we are painting : la toile
 * and the frame it's kind of the frame around it : Cadre
 */

public class GamePanel extends JPanel implements Runnable{
	//Game
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.555555));  //real Pong table dimension ratio
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	//Ball
	static final int BALL_DIAMETER = 20;
	//Paddles
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	
	GamePanel() {
		newPaddles();
		newBall();
		score= new Score(GAME_WIDTH, GAME_HEIGHT);
		//if we press any keys it will have focus then so it's going to read these key presses
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this); //since we're implementing the Runnable interface
		gameThread.start();
	}
	//we will call it ever since we want to start the game
	public void newBall() {
		random = new Random();
		
		ball = new Ball( (GAME_WIDTH - BALL_DIAMETER)/2, random.nextInt(GAME_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
	}
	public void newPaddles() {
		paddle1= new Paddle(0, (GAME_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
		paddle2= new Paddle(GAME_WIDTH-PADDLE_WIDTH, (GAME_HEIGHT - PADDLE_HEIGHT) / 2, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		Toolkit.getDefaultToolkit().sync();
	}
	//so we move things after each iteration of our game loop 
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	public void checkCollision(){
		//stops ball off top and bottom window edges;
		
		if(ball.y<=0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y>= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		//Bounce ball off paddles
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = -Math.abs(ball.xVelocity);
			ball.xVelocity--;
			
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		
		// stops paddles at window edges
		if(paddle1.y <= 0) {
			paddle1.y = 0;
		}
		if(paddle1.y >= GAME_HEIGHT-PADDLE_HEIGHT) {
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
		}
		if(paddle2.y <= 0) {
			paddle2.y = 0;
		}
		if(paddle2.y >= GAME_HEIGHT-PADDLE_HEIGHT) {
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
		}
		
		//give a player 1 point and creates new paddles & ball
		
		if(ball.x<0) {
			score.player2++;
			newPaddles();
			newBall();
		}
		if(ball.x > GAME_WIDTH - BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
		}
	}
	public void run() {
		//game loop hoping 60 frame per second
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		while(true) {
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime=now;
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
		
	}
}
