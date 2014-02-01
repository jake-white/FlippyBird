package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class birdPanel extends JPanel implements KeyListener {
	public Timer advance = new Timer(30, new advanceTimer());
	public static int player_Y = 140;
	public static int velocity, score, highscore;
	public static int[] camerapos = new int[4];
	public static int[] position = new int[4];
	public static int[] random_length = new int[4];
	public static Random gen = new Random();
	public static boolean jump, ascending;
	public static Rectangle2D[] pipe = new Rectangle2D[4];
	public static Rectangle2D player;
	public static boolean dead;
	public static boolean[] counted = new boolean[2];
	public static PrintWriter filesaver;
	Scanner filereader;

	public void init() {
		System.out.println("started");
		try {
			filereader = new Scanner(new File("highscores.txt"));
			highscore = filereader.nextInt();
			filereader.close();
			System.out.println(highscore);
		} catch (Exception e) {
			highscore = 0;
		}
		for (int i = 0; i < 4; ++i) {
			reset_pos(i);

		}
		player_Y = 140;
		score = 0;
		camerapos[0] = 350;
		camerapos[1] = 550;
		camerapos[2] = 350;
		camerapos[3] = 550;
		System.out.println("Finished initialization sequence");
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		FlippyPoly.update();
		for (int i = 0; i < 4; ++i) {
			if (player.intersects(pipe[i]) || player.getMinY() > 400)
				dead = true;
		}
		g.setColor(Color.RED);
		g2d.fill(player);
		g.setColor(Color.GREEN);
		for (int i = 0; i < 2; ++i) {
			if (player.getMinX() > pipe[i].getMinX() && !counted[i]) {
				++score;
				counted[i] = true;
			}
		}
		if (camerapos[0] < -50)
			reset_pos(0);
		if (camerapos[1] < -50)
			reset_pos(1);
		for (int count = 0; count < 4; ++count) {
			g2d.fill(pipe[count]);
		}
		g.setColor(Color.BLACK);
		g2d.drawString("High score: " + highscore, 10, 10);
		Font score_font = g.getFont().deriveFont(20.0F);
		g2d.setFont(score_font);
		g2d.drawString("" + score, 130, 100);
		if (dead) {
			if(score > highscore)
			{
				try {
					filesaver = new PrintWriter("highscores.txt");
				} catch (FileNotFoundException e) {
				}
				System.out.println("HIGHSCORE REACHED, " + score + " > " + highscore);
				highscore = score;
			filesaver.print(highscore);
			filesaver.close();
			}
			g2d.setFont(g.getFont().deriveFont(15.0F));
			g2d.drawString("Press enter to restart", 80, 120);
		}
	}

	public void reset_pos(int i) {
		if (i == 0) {
			counted[i] = false;
			camerapos[i] = camerapos[1] + 200;
			random_length[i] = gen.nextInt(150) + 50;
			camerapos[i + 2] = camerapos[1] + 200;
			random_length[i + 2] = 400;
			position[i + 2] = random_length[i] + 100;
		} else if (i == 1) {
			counted[i] = false;
			camerapos[i] = camerapos[0] + 200;
			random_length[i] = gen.nextInt(150) + 50;
			camerapos[i + 2] = camerapos[0] + 200;
			random_length[i + 2] = 400;
			position[i + 2] = random_length[i] + 100;
		}
	}

	public class advanceTimer implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			if (!dead) {
				guiFrame.frame.repaint();
				for (int x = 0; x <= 1; ++x) {
					camerapos[x] -= 3;
					camerapos[x + 2] -= 3;
				}
				--velocity;
				player_Y -= velocity;
			}
		}

	}

	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE && !jump) {
			velocity = 10;
			jump = true;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER && dead) {
			init();
			dead = false;
			velocity = 0;
		}

	}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE)
			jump = false;

	}

	public void keyTyped(KeyEvent arg0) {

	}
}
