package hr.fer.zemris.ooup.lab4.vectorgraphics.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.GraphicalObject;
import hr.fer.zemris.ooup.lab4.vectorgraphics.interfaces.Renderer;

/**
 * 
 * 
 * @author Ivan Skorupan
 */
public class Oval extends AbstractGraphicalObject {
	
	public Oval() {
		this(new Point(10, 0), new Point(0, 10));
	}
	
	public Oval(Point rightHotPoint, Point bottomHotPoint) {
		super(Arrays.asList(rightHotPoint, bottomHotPoint));
	}
	
	private List<Point> convertToPoints(int a, int b) {
		List<Point> points = new ArrayList<>();
		Point center = new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
		
		for (int i = 0; i < 100; i++) {
			int x = (int)Math.round(center.getX() + a * Math.cos(i * 2 * Math.PI / 100.));
			int y = (int)Math.round(center.getY() + b * Math.sin(i * 2 * Math.PI / 100.));
			points.add(new Point(x, y));
		}
		
		return points;
	}
	
	@Override
	public Rectangle getBoundingBox() {
		int width = (getHotPoint(0).getX() - getHotPoint(1).getX()) * 2;
		int height = (getHotPoint(1).getY() - getHotPoint(0).getY()) * 2;
		
		int x = getHotPoint(0).getX() - width;
		int y = getHotPoint(1).getY() - height;
		
		return new Rectangle(x, y, width, height);
	}
	
	@Override
	public double selectionDistance(Point mousePoint) {
		if (isInsideOval(mousePoint)) {
			return 0;
		}
		
		int a = getHotPoint(0).getX() - getHotPoint(1).getX();
		int b = getHotPoint(1).getY() - getHotPoint(0).getY();
		List<Point> points = convertToPoints(a, b);
		double minDistance = 100;
		for (Point point : points) {
			double distance = GeometryUtil.distanceFromPoint(point, mousePoint);
			if (distance < minDistance) {
				minDistance = distance;
			}
		}
		
		return minDistance;
	}
	
	private boolean isInsideOval(Point point) {
		Point center = new Point(getHotPoint(1).getX(), getHotPoint(0).getY());
		int a = getHotPoint(0).getX() - getHotPoint(1).getX();
		int b = getHotPoint(1).getY() - getHotPoint(0).getY();
		int h = center.getX();
		int k = center.getY();
		int x = point.getX();
		int y = point.getY();
		
		double value = ((x - h) * (x - h) / (double)(a * a)) + ((y - k) * (y - k) / (double)(b * b));
		if (value <= 1) return true;
		return false;
	}
	
	@Override
	public String getShapeName() {
		return "Oval";
	}

	@Override
	public GraphicalObject duplicate() {
		return new Oval(getHotPoint(0).copy(), getHotPoint(1).copy());
	}

	@Override
	public void render(Renderer r) {
		int a = getHotPoint(0).getX() - getHotPoint(1).getX();
		int b = getHotPoint(1).getY() - getHotPoint(0).getY();
		
		List<Point> points = convertToPoints(a, b);
		r.fillPolygon(points);
	}
	
	@Override
	public String getShapeID() {
		return "@OVAL";
	}
	
	@Override
	public void save(List<String> rows) {
		Point rightHotPoint = getHotPoint(0);
		Point bottomHotPoint = getHotPoint(1);
		String row = getShapeID() + " " + rightHotPoint.getX() + " " + rightHotPoint.getY() + " "
				+ bottomHotPoint.getX() + " " + bottomHotPoint.getY();
		rows.add(row);
	}

	@Override
	public void load(Stack<GraphicalObject> stack, String data) {
		String[] tokens = data.split(" ");
		int x1 = Integer.parseInt(tokens[0]);
		int y1 = Integer.parseInt(tokens[1]);
		int x2 = Integer.parseInt(tokens[2]);
		int y2 = Integer.parseInt(tokens[3]);
		
		stack.push(new Oval(new Point(x1, y1), new Point(x2, y2)));
	}
	
}
