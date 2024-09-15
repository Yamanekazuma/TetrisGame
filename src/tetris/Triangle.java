package tetris;

import java.awt.Polygon;

public class Triangle {
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;

	public static Polygon create(int x, int y, int width, int height, int way) {
		int[] xpoints = new int[3];
		int[] ypoints = new int[3];

		switch (way) {
		case NORTH:
			xpoints[0] = x+(width/2);
			xpoints[1] = x;
			xpoints[2] = x+width;
			ypoints[0] = y-height;
			ypoints[1] = y;
			ypoints[2] = y;
			break;
		case EAST:
			xpoints[0] = x+width;
			xpoints[1] = x;
			xpoints[2] = x;
			ypoints[0] = y-(height/2);
			ypoints[1] = y-height;
			ypoints[2] = y;
			break;
		case SOUTH:
			xpoints[0] = x+(width/2);
			xpoints[1] = x+width;
			xpoints[2] = x;
			ypoints[0] = y;
			ypoints[1] = y-height;
			ypoints[2] = y-height;
			break;
		case WEST:
			xpoints[0] = x;
			xpoints[1] = x+width;
			xpoints[2] = x+width;
			ypoints[0] = y-(height/2);
			ypoints[1] = y;
			ypoints[2] = y-height;
			break;
		}
		
		Polygon p = new Polygon();
		
		for(int i=0;i<3;i++){
			p.addPoint(xpoints[i], ypoints[i]);
		}
		
		return p;

	}
}
