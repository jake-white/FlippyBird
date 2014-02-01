package flappyBird;

import java.awt.Container;
import java.util.Scanner;

import javax.swing.*;

public class guiFrame {
	public static JFrame frame = new JFrame("Flippy Bird");
	public static  birdPanel bP= new birdPanel();
	public static long time;
	public static void main(String[] args)
	{
		frame.setSize(300, 400);
		Container pane = frame.getContentPane();
		pane.add(bP);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(bP);
		bP.advance.start();
		time = System.currentTimeMillis();
		
		bP.init();
	}

}
