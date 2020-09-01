package hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.copy;

import java.util.List;

import hr.fer.zemris.ooup.lab4.vectorgraphics.geometry.Point;

/**
 * 
 * 
 * @author Ivan Skorupan
 */
public interface Renderer {
	
	void drawLine(Point s, Point e);
	
	void fillPolygon(List<Point> points);
	
}
