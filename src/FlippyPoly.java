


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class FlippyPoly {

	public static void update() {
		birdPanel.player = new Rectangle();
		Point pPoint = new Point(100, birdPanel.player_Y);
		Dimension pDim = new Dimension(17, 12);
		birdPanel.player.setFrame(pPoint, pDim);
		for(int i = 0; i < 4; ++i)
		{
			Point point = new Point(birdPanel.camerapos[i], birdPanel.position[i]);
			Dimension dim = new Dimension(50, birdPanel.random_length[i]);
			birdPanel.pipe[i] = new Rectangle();
			birdPanel.pipe[i].setFrame(point, dim);
		}
	}
}
