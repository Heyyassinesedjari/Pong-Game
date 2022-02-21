import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameFrame extends JFrame{ // so we can treat our GameFrame class as a JFrame class
	
	GamePanel Panel;
	
	GameFrame() {
		Panel = new GamePanel();
		this.add(Panel);
		this.setTitle("Pong Game");
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); 				//the window frame is going to adjust to fit the size of the game panel
		this.setVisible(true);
		this.setLocationRelativeTo(null);	
	}
}
