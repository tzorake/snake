package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener 
{
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 50;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 200;
	
	List<Vector2> snake;
	Vector2 direction = Vector2.RIGHT.scalar(UNIT_SIZE);
	
	Vector2 food;
	int foodEaten;
	
	boolean running = false;
	Timer timer;
	Random random;

	Game()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if (!direction.equals(Vector2.RIGHT.scalar(UNIT_SIZE))) 
						{
							direction = Vector2.LEFT.scalar(UNIT_SIZE);
						}
						break;
						
					case KeyEvent.VK_RIGHT:
						if (!direction.equals(Vector2.LEFT.scalar(UNIT_SIZE))) 
						{
							direction = Vector2.RIGHT.scalar(UNIT_SIZE);
						}
						break;
						
					case KeyEvent.VK_UP:
						if (!direction.equals(Vector2.DOWN.scalar(UNIT_SIZE))) 
						{
							direction = Vector2.UP.scalar(UNIT_SIZE);
						}
						break;
						
					case KeyEvent.VK_DOWN:
						if (!direction.equals(Vector2.UP.scalar(UNIT_SIZE))) 
						{
							direction = Vector2.DOWN.scalar(UNIT_SIZE);
						}
						break;
				}
			}
		});
		startGame();
	}
	
	public void startGame() 
	{
		snake = new ArrayList<Vector2>();
		for (int i = 0; i <= 4; i++)
		{
			snake.add(new Vector2(0, 0));
		}
		
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) 
	{
		if (running) 
		{
			g.setColor(Color.red);
			g.fillRect(food.getX(), food.getY(), UNIT_SIZE, UNIT_SIZE);
		
			for (int i = 0; i < snake.size();i++) 
			{
				if (i == 0) 
				{
					g.setColor(new Color(0, 127, 0));
				}
				else 
				{
					g.setColor(new Color(0, 255, 0));
				}			
				g.fillRect(snake.get(i).getX(), snake.get(i).getY(), UNIT_SIZE, UNIT_SIZE);
			}
			
			// draw grid
			g.setColor(new Color(40, 40, 40));
			// vertical grid lines
			for (int i = 0; i < SCREEN_WIDTH/UNIT_SIZE;i++) 
			{
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			}
			// horizontal grid lines
			for (int j = 0; j < SCREEN_HEIGHT/UNIT_SIZE; j++)
			{
				g.drawLine(0, j*UNIT_SIZE, SCREEN_WIDTH, j*UNIT_SIZE);
			}
			
			g.setColor(Color.red);
			g.setFont( new Font("Roboto",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + foodEaten)) / 2, g.getFont().getSize());
		
			
		}
		else 
		{
			gameOver(g);
		}
		
	}
	
	public void newApple()
	{
		food = new Vector2(random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE, random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE);
	}
	
	public void move(){
		for (int i = snake.size() - 1; i > 0; i--) 
		{
			snake.set(i, new Vector2(snake.get(i - 1)));
		}
		
		snake.set(0, snake.get(0).add(direction));
		
	}
	
	public void checkApple() 
	{
		if ((snake.get(0).getX() == food.getX()) && (snake.get(0).getY() == food.getY())) 
		{
			snake.add(new Vector2(snake.get(1)));
			foodEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() 
	{
		for (int i = snake.size() - 1; i > 0; i--) 
		{
			if ((snake.get(0).getX() == snake.get(i).getX()) && (snake.get(0).getY() == snake.get(i).getY())) 
			{
				running = false;
			}
		}

		if (snake.get(0).getX() < 0 || snake.get(0).getX() >= SCREEN_WIDTH || snake.get(0).getY() < 0 || snake.get(0).getY() >= SCREEN_HEIGHT) 
		{
			running = false;
		}
		
		if (!running) 
		{
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) 
	{
		//Score
		g.setColor(Color.red);
		g.setFont( new Font("Roboto",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + foodEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + foodEaten))/2, g.getFont().getSize());
		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Roboto",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (running) 
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
}