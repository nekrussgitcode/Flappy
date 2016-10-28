package flappyBirds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBirds implements ActionListener, MouseListener {

	public static FlappyBirds flappyBirds;

	public final int HEIGHT = 600, WIDTH = 1000;

	public Renderer renderer;

	public Rectangle bird;

	public int ticks, yMotion, score;

	public boolean gameOver, started;

	public ArrayList<Rectangle> column;

	public Random rand;

	public FlappyBirds() {

		JFrame frame = new JFrame();
		Timer timer = new Timer(20, this);
		rand = new Random();

		renderer = new Renderer();
		frame.add(renderer);
		frame.setSize(WIDTH, HEIGHT);

		frame.addMouseListener(this);

		frame.setTitle("Квадрат");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// the path must be relative to your *class* files

		frame.setVisible(true);

		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		column = new ArrayList<Rectangle>();

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);

		timer.start();
	}

	public void addColumn(boolean start) {

		int space = 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		if (start) {

			column.add(new Rectangle(WIDTH + width + column.size() * 300, HEIGHT - height - 120, width, height));
			column.add(new Rectangle(WIDTH + width + (column.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}

		else {

			column.add(new Rectangle(column.get(column.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			column.add(new Rectangle(column.get(column.size() - 1).x, 0, width, HEIGHT - height - space));

		}
	}

	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}

	public void jump() {

		if (gameOver) {

			bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			column.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;

		}

		if (!started) {

			started = true;

		} else if (!gameOver) {

			if (yMotion > 0) {

				yMotion = yMotion - 77;
			}
			yMotion -= 15;
		}

	}

	public void actionPerformed(ActionEvent e) {

		int speed = 7;

		ticks++;

		if (started) {
			for (int i = 0; i < column.size(); i++) {

				Rectangle columns = column.get(i);
				columns.x -= speed;
			}

			if (ticks % 2 == 0 && yMotion < 460) {
				yMotion += 10;
			}

			for (int i = 0; i < column.size(); i++) {

				Rectangle columns = column.get(i);
				if (columns.x + columns.width < 0) {
					column.remove(columns);
					if (columns.y == 0) {
						addColumn(false);
					}
				}
			}

			bird.y = yMotion;

			for (Rectangle column : column) {
				if (column.intersects(bird)) {
					gameOver = true;
					bird.x = column.x - bird.width;
				}
			}

			if (bird.y > HEIGHT - 120 || bird.y < 0) {
				gameOver = true;
			}

			if (gameOver) {
				bird.y = HEIGHT - 120 - bird.height;
				started = false;

			}
		}
		renderer.repaint();

	}

	public void repaint(Graphics g) {

		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.ORANGE);
		g.fillRect(0, HEIGHT - 120, WIDTH, 150);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 120, WIDTH, 10);

		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		for (Rectangle column : column) {
			paintColumn(g, column);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if (!started) {
			g.drawString("Game Over", 250, HEIGHT / 2 - 50);
		}

		if (gameOver) {
			g.drawString("Game Over", 250, HEIGHT / 2 - 50);
		}

	}

	public static void main(String[] args) {

		flappyBirds = new FlappyBirds();

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		jump();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
