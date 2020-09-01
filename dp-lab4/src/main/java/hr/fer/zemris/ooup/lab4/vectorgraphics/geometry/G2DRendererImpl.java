package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;

public class G2DRendererImpl implements Renderer {
	
	private Graphics2D g2d;
	
	public G2DRendererImpl(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void drawLine(Point s, Point e) {
		g2d.setColor(Color.BLUE);
		g2d.drawLine(s.getX(), s.getY(), e.getX(), e.getY());
	}

	@Override
	public void fillPolygon(List<Point> points) {
		g2d.setColor(Color.BLUE);
		int numberOfPoints = points.size();
		
		int[] xpoints = new int[numberOfPoints];
		int[] ypoints = new int[numberOfPoints];
		for (int i = 0; i < numberOfPoints; i++) {
			Point point = points.get(i);
			xpoints[i] = point.getX();
			ypoints[i] = point.getY();
		}
		
		Polygon polygon = new Polygon(xpoints, ypoints, numberOfPoints);
		g2d.fillPolygon(polygon);
		
		g2d.setColor(Color.RED);
		g2d.drawPolygon(polygon);
	}
	
}
