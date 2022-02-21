import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Paddle extends Rectangle{
	
	int id;
	int yVelocity;  // is going to be how fast the paddle is moving up and down when we press a button;
	int speed=10;
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
		super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);  // the super constructor;
		this.id=id;
	}
	public void keyPressed(KeyEvent e) {
		switch(id) {
			case 1:
				if(e.getKeyCode()==KeyEvent.VK_Z) {
					SetYDirection(-speed);
				}
				if(e.getKeyCode()==KeyEvent.VK_S) {
					SetYDirection(+speed);
				}
				break;
			case 2:
				if(e.getKeyCode()==KeyEvent.VK_UP) {
					SetYDirection(-speed);
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN) {
					SetYDirection(+speed);
				}
				break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(this.id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_Z) {
				SetYDirection(0);
				
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				SetYDirection(0);
					
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				SetYDirection(0);
					
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				SetYDirection(0);
					
			}
			break;
		}		
	}
	public void SetYDirection(int yDirection) {
		yVelocity = yDirection;
	}
	public void move() {
		y = y + yVelocity;
	}
	public void draw(Graphics g) {
		if(id==1) {
			g.setColor(Color.blue);
		}else {
			g.setColor(Color.red);
		}
		g.fillRect(x, y, width, height);
	}
}